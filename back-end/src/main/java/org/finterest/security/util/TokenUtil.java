package org.finterest.security.util;

import org.finterest.user.dto.UserDTO;
import org.finterest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

    private final JwtProcessor jwtProcessor;
    private final UserService userService;

    @Autowired
    public TokenUtil(JwtProcessor jwtProcessor, UserService userService) {
        this.jwtProcessor = jwtProcessor;
        this.userService = userService;
    }

    public Integer getUserIdFromToken(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        // Bearer 토큰에서 실제 JWT 추출
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;

        try {
            // JwtProcessor의 getUsername 메서드를 사용해 JWT에서 사용자 ID 추출
            String username = jwtProcessor.getUsername(token);
            UserDTO userDTO = userService.get(username);
            return userDTO.toVO().getUserId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    // 관리자인 경우 true 반환
    public boolean isAdmin(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        // Bearer 토큰에서 실제 JWT 추출
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;

        try {
            // JwtProcessor의 getUsername 메서드를 사용해 JWT에서 사용자 이름 추출
            String username = jwtProcessor.getUsername(token);
            UserDTO userDTO = userService.get(username);

            // 사용자의 권한 목록에서 "ROLE_ADMIN" 권한을 확인
            return userDTO.getAuthList().contains("ROLE_ADMIN");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token or not authorized", e);
        }
    }
}
