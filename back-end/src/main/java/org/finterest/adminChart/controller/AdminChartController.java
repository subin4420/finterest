package org.finterest.adminChart.controller;

import org.finterest.adminChart.service.AdminChartService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/chart")
public class AdminChartController {
    private final AdminChartService chartService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminChartController(AdminChartService chartService, TokenUtil tokenUtil) {
        this.chartService = chartService;
        this.tokenUtil = tokenUtil;
    }

    // 여러 통계를 조회
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardStats(@RequestHeader("Authorization") String token) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            // 서비스에서 오늘의 가입자 수, 등록된 퀴즈 수, 등록된 용어 사전 수를 가져오기
            int todaySignupCount = chartService.getTodaySignupCount();
            int activeUserCount = chartService.getActiveUser();
            int totalQuizCount = chartService.getTotalQuizCount();
            int totalArchiveCount = chartService.getTotalArchiveCount();

            // 모든 통계를 하나의 Map으로 반환
            Map<String, Object> stats = Map.of(
                    "todaySignupCount", todaySignupCount,
                    "activeUserCount", activeUserCount,
                    "totalQuizCount", totalQuizCount,
                    "totalArchiveCount", totalArchiveCount
            );

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }



    // 특정 월의 주별 퀴즈 완료 횟수 조회
    @GetMapping("/quiz-completion/weekly")
    public ResponseEntity<?> getWeeklyQuizCompletionCounts(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            List<Map<String, Object>> weeklyCounts = chartService.getWeeklyQuizCompletionCounts(year, month);

            // 요청된 형식에 맞게 데이터 구성
            Map<String, Object> response = Map.of(
                    "period", "weekly",
                    "year", year,
                    "month", month,
                    "quizCompletionCounts", weeklyCounts
            );
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다."));
        }
    }

    // 월별 퀴즈 완료 횟수 조회
    @GetMapping("/quiz-completion/monthly")
    public ResponseEntity<?> getMonthlyQuizCompletionCounts(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            List<Map<String, Object>> monthlyCounts = chartService.getMonthlyQuizCompletionCounts(year);

            // 요청된 형식에 맞게 데이터 구성
            Map<String, Object> response = Map.of(
                    "period", "monthly",
                    "year", year,
                    "quizCompletionCounts", monthlyCounts
            );
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다."));
        }
    }

    // 특정 월의 학습자료 퀴즈 완료 횟수 조회
    @GetMapping("/archive-completion/weekly")
    public ResponseEntity<?> getWeeklyArchiveCompletionCounts(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            List<Map<String, Object>> weeklyCounts = chartService.getWeeklyArchiveCompletionCounts(year, month);

            // 요청된 형식에 맞게 데이터 구성
            Map<String, Object> response = Map.of(
                    "period", "weekly",
                    "year", year,
                    "month", month,
                    "learningCounts", weeklyCounts
            );
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다."));
        }
    }

    // 월별 학습자료 완료 횟수 조회
    @GetMapping("/archive-completion/monthly")
    public ResponseEntity<?> getMonthlyArchiveCompletionCounts(
            @RequestHeader("Authorization") String token,
            @RequestParam("year") int year) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            List<Map<String, Object>> monthlyCounts = chartService.getMonthlyArchiveCompletionCounts(year);

            // 요청된 형식에 맞게 데이터 구성
            Map<String, Object> response = Map.of(
                    "period", "monthly",
                    "year", year,
                    "learningCounts", monthlyCounts
            );
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다."));
        }
    }

    // 활동별 포인트 획득 비율 조회
    @GetMapping("/points")
    public ResponseEntity<?> getActivityPointsBreakdown(
            @RequestHeader("Authorization") String token) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            // 포인트 획득 비율 데이터 조회
            List<Map<String, Object>> activityBreakdown = chartService.getActivityPointsBreakdown();
            int totalPoints = chartService.getTotalPointsEarned();

            // 퍼센트 계산 및 데이터 구성
            activityBreakdown.forEach(activity -> {
                BigDecimal pointsDecimal = (BigDecimal) activity.get("points");
                int points = pointsDecimal.intValue(); // BigDecimal을 정수로 변환
                double percentage = totalPoints > 0 ? (points * 100.0) / totalPoints : 0.0;
                activity.put("percentage", Math.round(percentage * 10.0) / 10.0); // 소수점 한 자리로 반올림
            });

            // 요청된 형식에 맞게 데이터 구성
            Map<String, Object> response = Map.of(
                    "totalPoints", totalPoints,
                    "activityBreakdown", activityBreakdown
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }

    // 퀴즈 및 학습 자료 통계 조회
    @GetMapping("/statistics")
    public ResponseEntity<?> getUserStatistics(@RequestHeader("Authorization") String token) {
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }
        try {
            List<Map<String, Object>> statisticsData = chartService.getUserStatistics();

            Map<String, Object> response = Map.of(
                    "statistics", statisticsData
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "데이터를 조회하는 도중 오류가 발생했습니다.", "error", e.getMessage()));
        }
    }
}
