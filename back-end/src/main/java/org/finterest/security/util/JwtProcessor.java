package org.finterest.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProcessor {
    static private final long TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 2; // 2시간

    //private String secretKey = "충분히 긴 임의의(랜덤한) 비밀키 문자열 배정";
    //private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    //이렇게 되면 서버가 다운 됐을 경우 기존의 JWT토큰은 모두 사용할 수 없게된다.
    //서버 다운 시 새로운 문자열이
    //리프레쉬 토큰과 같이 유효기간이 긴 토큰에 경우 모두 무효됨
    //private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);  //-- 운영시 사용
    // 강력한 비밀 키 생성 (HS256 알고리즘 사용)
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // 안전한 키 생성

    // JWT 생성
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + TOKEN_VALID_MILISECOND))
                .signWith(key)
                .compact();
    }

    // JWT Subject(username) 추출 - 해석 불가인 경우 예외 발생
    // 예외 ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
    //      IllegalArgumentException

    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();  // 토큰에서 subject 추출
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            System.out.println("Token has expired");
            throw new IllegalArgumentException("Expired JWT token", e);
        } catch (JwtException e) {
            // 잘못된 서명, 구조 오류 등 다양한 JWT 관련 예외
            System.out.println("Invalid JWT token");
            throw new IllegalArgumentException("Invalid JWT token", e);
        } catch (IllegalArgumentException e) {
            // 토큰이 null이거나 빈 문자열일 경우
            System.out.println("Token is null or empty");
            throw new IllegalArgumentException("JWT token is null or empty", e);
        }
    }

    // JWT 검증(유효 기간 검증) - 해석 불가인 경우 예외 발생
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date()); // 토큰 만료 시간 확인
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired");
            return false;
        } catch (JwtException e) {
            System.out.println("It is Invalid token");
            return false;
        }
    }

}
