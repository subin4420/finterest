package org.finterest.security.handler;

import lombok.RequiredArgsConstructor;
import org.finterest.point.service.PointService;
import org.finterest.security.account.domain.CustomUser;
import org.finterest.security.account.dto.AuthResultDTO;
import org.finterest.security.account.dto.UserInfoDTO;
import org.finterest.security.util.JsonResponse;
import org.finterest.security.util.JwtProcessor;
import org.finterest.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

//로그인 성공 시 호출되는 핸들러
//JWT 토큰을 생성하고 인증 결과를 클라이언트에게 응답
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;
    private final PointService pointService;  // PointService 주입

    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //authentication은 인증 객체이 인증 객체에 사용자 정보가 담겨있음
        //그걸 꺼내는게 getPrincipal() 이걸 담기 위해 우리는 CustomUser 클래스를 만들었었음
        // 인증 결과 Principal
        // 인증된 사용자 정보 가져오기
        CustomUser user = (CustomUser) authentication.getPrincipal();
        int userId = user.getUserVO().getUserId();

        // 마지막 로그인 시간이 오늘 이전일 때만 출석 포인트 추가
        if (user.getUserVO().getLastLogin() == null || user.getUserVO().getLastLogin().isBefore(LocalDate.now())) {
            pointService.insertPointForAttendance(userId);  // 출석 포인트 추가
        }
        
        // 마지막 로그인 시간 업데이트
        userService.updateLastLoginByUsername(user.getUsername());


        //받아온 유저정보로
        // 인증 성공 결과를 JSON으로 직접 응답
        AuthResultDTO result = makeAuthResult(user);


        JsonResponse.send(response, result);
    }

    private AuthResultDTO makeAuthResult(CustomUser user) {
        String username = user.getUsername();
        // 토큰 생성
        String token = jwtProcessor.generateToken(username);

        // 토큰 + 사용자 기본 정보 (사용자명, ...)를 묶어서 AuthResultDTO 구성
        return new AuthResultDTO(token, UserInfoDTO.of(user.getUserVO()));
    }


}
