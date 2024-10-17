package org.finterest.security.util;

import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.finterest.common.config.RootConfig;
import org.finterest.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {RootConfig.class, SecurityConfig.class}
)
@Log4j
class JwtProcessorTest {
    @Autowired
    JwtProcessor jwtProcessor;

    @Test
    void generateToken() {
        String username = "user1";
        String token = jwtProcessor.generateToken(username);
        log.info(token);
        assertNotNull(token);
    }

    @Test
    void getUsername() {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcyNzMzOTE1MSwiZXhwIjoxNzI3MzQ2MzUxfQ.VFcnvZTgqE28SyA0252lph4gh6qBkNyKnjWl_PtixcUog-tB3bWJtuNTXRGjbhiX";
        String username = jwtProcessor.getUsername(token);
        log.info(username);
        assertNotNull(username);
    }

    @Test
    void validateToken() {
        // 5분 경과 후 테스트
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcyNzMzOTE1MSwiZXhwIjoxNzI3MzQ2MzUxfQ.VFcnvZTgqE28SyA0252lph4gh6qBkNyKnjWl_PtixcUog-tB3bWJtuNTXRGjbhiX";
        boolean isValid = jwtProcessor.validateToken(token); // 5분 경과 후면 예외 발생
        log.info(isValid);
        assertTrue(isValid);    // 5분전이면 true,
    }
}