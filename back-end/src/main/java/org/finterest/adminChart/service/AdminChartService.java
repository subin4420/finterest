package org.finterest.adminChart.service;

import org.finterest.adminChart.dao.AdminChartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminChartService {
    public final AdminChartDAO chartDAO;

    @Autowired
    public AdminChartService(AdminChartDAO chartDAO) {
        this.chartDAO = chartDAO;
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

}
