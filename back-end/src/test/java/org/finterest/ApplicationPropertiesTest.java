package org.finterest;

import org.aspectj.lang.annotation.Before;
import org.finterest.common.config.RootConfig;
import org.finterest.user.controller.UserController;
import org.finterest.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)  // RootConfig 설정 사용
@PropertySource("classpath:/application.properties")
public class ApplicationPropertiesTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Value("${avatar.path}")
    private String avatarPath;

    @BeforeEach
    public void setup() {
        // Mockito 초기화
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAvatar_UserAvatarExists() throws Exception {
        // Mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        System.out.println("avatiarPath = "+avatarPath);
        // 가상의 사용자 이름 설정
        String username = "susu";

        // 실제 파일 경로 확인
        String filePath = avatarPath + "/" + username + ".png";
        File file = new File(filePath);

        // 가상으로 파일 생성
        if (!file.exists()) {
            file.createNewFile();  // 테스트 용도 파일 생성
        }

        // 테스트 실행
        userController.getAvatar(username, response);

        // 상태 코드 200 확인
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());

        // 생성된 파일 삭제 (테스트 후 정리)
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testGetAvatar_UserAvatarNotFound() throws Exception {
        // Mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 존재하지 않는 사용자 이름 설정
        String username = "nonexistentuser";

        // 테스트 실행
        userController.getAvatar(username, response);

        // 상태 코드 200 확인 (기본 이미지 반환 확인)
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }
}
