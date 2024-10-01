package org.finterest.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.security.filter.AuthenticationErrorFilter;
import org.finterest.security.filter.JwtAuthenticationFilter;
import org.finterest.security.filter.JwtUsernamePasswordAuthenticationFilter;
import org.finterest.security.handler.CustomAccessDeniedHandler;
import org.finterest.security.handler.CustomAuthenticationEntryPoint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@MapperScan(basePackages  = {"org.finterest.security.account.mapper"})
@ComponentScan(basePackages  = {"org.finterest.security"})
@EnableWebSecurity
@Log4j
@RequiredArgsConstructor

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter;

    private final UserDetailsService userDetailsService;
    private final AuthenticationErrorFilter authenticationErrorFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Authentication Manger  구성
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // cross origin 접근 허용
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // 접근 제한 무시 경로 설정 – resource
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/*", "/api/member/**");
    }

    // 문자셋 필터
    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 한글 인코딩 필터 설정
        http.addFilterBefore(encodingFilter(), CsrfFilter.class)
                // 인증 에러 필터
                .addFilterBefore(authenticationErrorFilter, UsernamePasswordAuthenticationFilter.class)
                // Jwt 인증 필터
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 로그인 인증 필터
                .addFilterBefore(jwtUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 예외 처리 설정
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        http
                .authorizeRequests()
                //cros 를 위해 OPTIONS는 모두 허용으로
                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                //회원
                //회원가입도 인증 안된사람도 할 수 있게 만들어야함
                .antMatchers(HttpMethod.POST,"/api/users/join").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/users", "/api/users/*/changepassword").authenticated()
                //학습자료

                //퀴즈
                .antMatchers(HttpMethod.POST, "/api/quizzes/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/quizzes/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/quizzes/**").authenticated()
                //퀴즈세트
                .antMatchers(HttpMethod.POST, "/api/quiz-sets/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/quiz-sets/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/quiz-sets/**").authenticated()
                .anyRequest().permitAll()
        ;

        http    .cors().and()
                .httpBasic().disable()		// 기본 HTTP 인증 비활성화
                .csrf().disable()       // CSRF 비활성화
                .formLogin().disable()  // formLogin 비활성화  관련 필터 해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 생성 모드 설정
    }


}
