package org.finterest.point.controller;

import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.finterest.point.service.PointService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PointController {
    private final PointService pointService;
    private final TokenUtil tokenUtil;

    @Autowired
    public PointController(PointService pointService, TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
        this.pointService = pointService;
    }

    // 포인트 적립/차감 내역 조회 API
    @GetMapping("/points")
    public ResponseEntity<Map<String, Object>> getPoints(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        // userId를 토큰에서 추출 (JWT에서 추출하는 부분은 생략)
        int userId = tokenUtil.getUserIdFromToken(token);

        // 포인트 조회
        List<PointVO> points = pointService.getPoint(userId, filter, startDate, endDate);

        // 결과를 points 키 아래에 담아 반환
        Map<String, Object> response = new HashMap<>();
        response.put("points", points);

        return ResponseEntity.ok(response);
    }


    // 사용자 누적 포인트 조회 API
    @GetMapping("/total_points")
    public Map<String, Object> getTotalPoints(
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);

        // 사용자 정보 조회
        UserPointVO user = pointService.getUserById(userId);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("userId", user.getUserId());
            response.put("totalPoints", user.getTotalPoints());
        } else {
            response.put("message", "User not found");
        }

        return response;
    }

    // 사용자 누적 가상자금 조회 API
    @GetMapping("/total_money")
    public Map<String, Object> getTotalMoney(
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);

        // 사용자 정보 조회
        UserPointVO user = pointService.getUserById(userId);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("userId", userId);
            response.put("totalMoney", pointService.getTotalMoney(userId));
        } else {
            response.put("message", "User not found");
        }

        return response;
    }
}
