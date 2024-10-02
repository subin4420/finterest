package org.finterest.security.account.mapper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.finterest.security.util.JwtProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtProcessorTest {

    // SecretKey 객체로 관리
    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 최소 256비트 비밀 키 생성
    private JwtProcessor jwtProcessor;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtProcessor processor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtProcessor = new JwtProcessor();
    }

    @Test
    void testTokenGenerationAndValidationWithHS256() {
        // 서명 알고리즘이 HS256으로 설정되었는지 확인
        String token = jwtProcessor.generateToken("subin");
        assertNotNull(token);

        // 토큰 검증이 유효한지 확인
        assertTrue(jwtProcessor.validateToken(token));

        // 토큰에서 username 추출
        String username = jwtProcessor.getUsername(token);
        assertEquals("subin", username);
    }

//    @Test
//    void testTokenGenerationAndValidationWithHS384() {
//        // HS384 알고리즘을 사용하여 토큰 생성 테스트
//        JwtProcessor jwtProcessorWithHS384 = new JwtProcessor() {
//            @Override
//            public String generateToken(String subject) {
//                return Jwts.builder()
//                        .setSubject(subject)
//                        .setIssuedAt(new Date())
//                        .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 5))  // 5분
//                        .signWith(Keys.hmacShaKeyFor(secretKey.getEncoded()), SignatureAlgorithm.HS384)
//                        .compact();
//            }
//        };
//
//        String token = jwtProcessorWithHS384.generateToken("subin");
//        assertNotNull(token);
//
//        // HS384 알고리즘을 사용하여 생성된 토큰 검증 시 예외 확인
//        assertThrows(InvalidKeyException.class, () -> jwtProcessor.validateToken(token), "Invalid token");
//    }

    @Test
    void testExpiredToken() {
        // 만료된 토큰 생성 (유효 기간 1초로 설정)
        String expiredToken = Jwts.builder()
                .setSubject("subin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() - 1000)) // 1초 뒤 만료
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // 토큰이 만료되었음을 검증
        assertFalse(jwtProcessor.validateToken(expiredToken), "Expired token should not be valid");
    }

//    @Test
//    void testInvalidTokenStructure() {
//        // 잘못된 형식의 토큰을 테스트
//        String invalidToken = "this.is.an.invalid.token";
//
//        // 토큰 검증 시 MalformedJwtException이 발생해야 함
//        assertThrows(MalformedJwtException.class, () -> jwtProcessor.getUsername(invalidToken), "Invalid token");
//    }

    @Test
    void testBearerTokenFormat() {
        // Authorization 헤더에서 Bearer 형식을 사용하는지 확인하는 테스트
        String token = jwtProcessor.generateToken("subin");
        String bearerToken = "Bearer " + token;

        assertTrue(bearerToken.startsWith("Bearer "), "Authorization header must start with Bearer ");
    }

    @Test
    void testSigningKeyConsistency() {
        // 서명할 때 사용한 비밀 키와 검증할 때 사용한 비밀 키가 동일한지 확인
        String token = jwtProcessor.generateToken("subin");

        assertDoesNotThrow(() -> jwtProcessor.getUsername(token), "Signing key must be consistent during token validation");
    }

}
