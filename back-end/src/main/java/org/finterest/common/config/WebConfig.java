package org.scoula.common.config;

import lombok.extern.slf4j.Slf4j;
import org.scoula.security.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

@Slf4j
@Configuration
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // 업로드 경로 설정
    final String LOCATION = "/Users/park/Desktop/upload";
    final long MAX_FILE_SIZE = 1024 * 1024 * 10L;
    final long MAX_REQUEST_SIZE = 1024 * 1024 * 20L;
    final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class, SecurityConfig.class };
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
        File uploadDir = new File(LOCATION);
        if (!uploadDir.exists()) {
            boolean dirCreated = uploadDir.mkdirs();  // 디렉토리 생성
            if (dirCreated) {
                log.info("Created upload directory: " + LOCATION);
            } else {
                log.error("Failed to create upload directory: " + LOCATION);
            }
        }

        // 파일 업로드 설정
        MultipartConfigElement multipartConfig =
                new MultipartConfigElement(
                        LOCATION,           // 업로드 처리 디렉토리 경로
                        MAX_FILE_SIZE,      // 업로드 가능한 파일 하나의 최대 크기
                        MAX_REQUEST_SIZE,   // 업로드 가능한 전체 최대 크기(여러 파일 업로드 하는 경우)
                        FILE_SIZE_THRESHOLD // 메모리 파일의 최대 크기(이보다 작으면 실제 메모리에서만 작업)
                );
        registration.setMultipartConfig(multipartConfig);
    }
}
