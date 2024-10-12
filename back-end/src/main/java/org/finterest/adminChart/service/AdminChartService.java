package org.finterest.adminChart.service;

import org.finterest.adminChart.dao.AdminChartDAO;
import org.finterest.adminChart.mapper.AdminChartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminChartService {
    public final AdminChartDAO chartDAO;

    @Autowired
    public AdminChartService(AdminChartDAO chartDAO) {
        this.chartDAO = chartDAO;
    }

    // 오늘의 가입자 수 조회
    public int getTodaySignupCount(){
        return chartDAO.getTodaySignupCount();
    }

    // 활성 사용자 수 조회
    public int getActiveUser(){
        return chartDAO.getActiveUser();
    }

    // 등록된 퀴즈 수 조회
    public int getTotalQuizCount(){
        return chartDAO.getTotalQuizCount();
    }
    //  용어 사전 수 조회
    public int getTotalArchiveCount(){
        return chartDAO.getTotalArchiveCount();
    }

    // 특정 월의 주별 퀴즈 완료 횟수 조회
    public List<Map<String, Object>> getWeeklyQuizCompletionCounts(int year, int month) {
        return chartDAO.getWeeklyQuizCompletionCounts(year, month);
    }

    // 월별 퀴즈 완료 횟수 조회
    public List<Map<String, Object>> getMonthlyQuizCompletionCounts(int year) {
        return chartDAO.getMonthlyQuizCompletionCounts(year);
    }

    // 특정 월의 주별 학습자료 완료 횟수 조회
    public List<Map<String, Object>> getWeeklyArchiveCompletionCounts(int year, int month) {
        return chartDAO.getWeeklyArchiveCompletionCounts(year, month);
    }

    // 월별 학습자료 완료 횟수 조회
    public List<Map<String, Object>> getMonthlyArchiveCompletionCounts(int year) {
        return chartDAO.getMonthlyArchiveCompletionCounts(year);
    }

    // 활동별 포인트 획득 비율 조회
    public List<Map<String, Object>> getActivityPointsBreakdown() {
        return chartDAO.getActivityPointsBreakdown();
    }

    // 총 포인트 획득량 조회
    public int getTotalPointsEarned() {
        return chartDAO.getTotalPointsEarned();
    }

    // 사용자별 통계 데이터 가져오기
    public List<Map<String, Object>> getUserStatistics() {
        // 사용자 목록 가져오기
        List<Integer> userIds = chartDAO.getAllUserIds(); // userIds를 가져옴

        // 각 사용자에 대한 통계 정보를 수집
        return userIds.stream().map(userId -> {
            // 사용자별 통계 조회
            long quizParticipationCount = chartDAO.getQuizParticipationCountByUser(userId).stream()
                    .filter(userStats -> userId.equals(userStats.get("user_id")))
                    .map(userStats -> ((Number) userStats.get("quizParticipationCount")).longValue())
                    .findFirst().orElse(0L);

            double averageQuizScore = chartDAO.getAverageQuizScoreByUser(userId).stream()
                    .filter(userStats -> userId.equals(userStats.get("user_id")))
                    .map(userStats -> ((Number) userStats.get("averageQuizScore")).doubleValue())
                    .findFirst().orElse(0.0);

            long learningMaterialCompletionCount = chartDAO.getLearningMaterialCompletionCountByUser(userId).stream()
                    .filter(userStats -> userId.equals(userStats.get("user_id")))
                    .map(userStats -> ((Number) userStats.get("learningMaterialCompletionCount")).longValue())
                    .findFirst().orElse(0L);

            String completionRate = chartDAO.getCompletionRateByUser(userId).stream()
                    .filter(userStats -> userId.equals(userStats.get("user_id")))
                    .map(userStats -> (String) userStats.get("completionRate"))
                    .findFirst().orElse("0%");

            // 사용자별 통계 데이터를 Map<String, Object>로 구성
            Map<String, Object> userStatistics = new HashMap<>();
            userStatistics.put("userId", userId);
            userStatistics.put("quizParticipationCount", quizParticipationCount);
            userStatistics.put("averageQuizScore", averageQuizScore);
            userStatistics.put("learningMaterialCompletionCount", learningMaterialCompletionCount);
            userStatistics.put("completionRate", completionRate);

            return userStatistics;
        }).collect(Collectors.toList());
    }


}
