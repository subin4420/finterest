package org.finterest.security.filter;


import lombok.extern.slf4j.Slf4j;
import org.finterest.security.account.dto.LoginDTO;
import org.finterest.security.handler.LoginFailureHandler;
import org.finterest.security.handler.LoginSuccessHandler;
import org.finterest.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    // 스프링 생성자 주입을 통해 전달
    public JwtUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;

        setFilterProcessesUrl("/api/auth/login");		          // POST 로그인 요청 url
        setAuthenticationSuccessHandler(loginSuccessHandler);	// 로그인 성공 핸들러 등록
        setAuthenticationFailureHandler(loginFailureHandler);  // 로그인 실패 핸들러 등록
    }

    // 로그인 요청 URL인 경우 로그인 작업 처리
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 요청 BODY의 JSON에서 username, password  LoginDTO
        LoginDTO login = LoginDTO.of(request);
        // 비밀번호 추출
        String rawPassword = login.getPassword();
        // 데이터베이스에서 사용자 정보 가져오기

        UserDetails userFromDb = userDetailsService.loadUserByUsername(login.getUsername());

        log.info("입력된 비밀번호: " + rawPassword);  // 입력된 비밀번호 로그
        log.info("암호화된 비밀번호: " + userFromDb.getPassword());
        log.info("비밀번호 일치 여부: " + passwordEncoder.matches(rawPassword, userFromDb.getPassword()));

        // 인증 토큰(UsernamePasswordAuthenticationToken) 구성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        // AuthenticationManager에게 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);
    }

}
