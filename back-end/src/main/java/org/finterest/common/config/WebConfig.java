package org.finterest.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

@Slf4j
@Configuration
@PropertySource({"classpath:/application.properties"})
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer implements WebMvcConfigurer {


    // application.properties에서 업로드 경로를 가져옴
    @Value("${upload.path}")
    private String location;
    final long MAX_FILE_SIZE = 1024 * 1024 * 10L;
    final long MAX_REQUEST_SIZE = 1024 * 1024 * 20L;
    final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // 경로 유효성 검사 및 디렉토리 생성
        String a = "/Users/park/Desktop/upload";
        System.out.println("location is "+location);
        File uploadDir = new File(a);
        if (!uploadDir.exists()) {
            boolean dirCreated = uploadDir.mkdirs();  // 디렉토리 생성
            if (dirCreated) {
                log.info("Created upload directory: " + a);
            } else {
                log.error("Failed to create upload directory: " + a);
            }
        }


        // 파일 업로드 설정
        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        location,           // 업로드 처리 디렉토리 경로
                        MAX_FILE_SIZE,      // 업로드 가능한 파일 하나의 최대 크기
                        MAX_REQUEST_SIZE,   // 업로드 가능한 전체 최대 크기(여러 파일 업로드 하는 경우)
                        FILE_SIZE_THRESHOLD // 메모리 파일의 최대 크기(이보다 작으면 실제 메모리에서만 작업)
                );
        registration.setMultipartConfig(multipartConfig);
    }

    // CORS 설정 추가
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:5173")  // 특정 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메소드 지정
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 인증 정보 허용
    }
}
