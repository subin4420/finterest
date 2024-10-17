package org.finterest.point.controller;

import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.finterest.point.service.AdminPointService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminPointController {

    private final AdminPointService adminPointService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminPointController(AdminPointService adminPointService, TokenUtil tokenUtil) {
        this.adminPointService = adminPointService;
        this.tokenUtil = tokenUtil;
    }


    // 포인트 적립/차감 내역 조회 및 전체 사용자 누적 포인트 조회 API
    @GetMapping("/points")
    public ResponseEntity<Map<String, Object>> getPoints(
            @RequestHeader("Authorization") String adminToken,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "viewType", required = false, defaultValue = "summary") String viewType
    ) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }


        Map<String, Object> response = new HashMap<>();

        if ("detail".equalsIgnoreCase(viewType)) {
            // 포인트 적립/차감 내역 조회
            List<PointVO> pointDetails = adminPointService.selectPointDetails(userId, filter, startDate, endDate);
            response.put("points", pointDetails);
        } else {
            // 전체 사용자 누적 포인트 조회
            List<UserPointVO> userPointSummaries = adminPointService.selectUserPointSummaries(startDate, endDate);
            response.put("users", userPointSummaries);
        }

        return ResponseEntity.ok(response);
    }

    // 특정 사용자에게 포인트 추가 (적립 또는 차감) API
    @PostMapping("/points/add")
    public ResponseEntity<Map<String, String>> addPoint(
            @RequestHeader("Authorization") String adminToken,
            @RequestBody Map<String, Object> requestBody
    ) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        // 값이 null인 경우 예외 처리
        if (requestBody.get("userId") == null || requestBody.get("activityName") == null || requestBody.get("pointsChange") == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid request: missing required fields."));
        }

        int userId = (int) requestBody.get("userId");
        String activityName = (String) requestBody.get("activityName");
        int pointsChange = (int) requestBody.get("pointsChange");

        // 포인트 추가 서비스 호출
        adminPointService.addPoint(userId, activityName, pointsChange);

        Map<String, String> response = new HashMap<>();

        if(pointsChange > 0) {
            response.put("message", "포인트가 성공적으로 적립되었습니다.");
        }else{
            response.put("message", "포인트가 성공적으로 차감되었습니다.");
        }

        return ResponseEntity.status(201).body(response);
    }

}
