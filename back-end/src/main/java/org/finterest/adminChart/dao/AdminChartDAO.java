package org.finterest.adminChart.dao;

import org.finterest.adminChart.mapper.AdminChartMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminChartDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminChartDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 특정 월의 주별 퀴즈 완료 횟수 조회
    public List<Map<String, Object>> getWeeklyQuizCompletionCounts(int year, int month) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getWeeklyQuizCompletionCounts(year, month);
    }

    // 월별 퀴즈 완료 횟수 조회
    public List<Map<String, Object>> getMonthlyQuizCompletionCounts(int year) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getMonthlyQuizCompletionCounts(year);
    }

    // 특정 월의 주별 학습자료 완료 횟수 조회
    public List<Map<String, Object>> getWeeklyArchiveCompletionCounts(int year, int month) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getWeeklyArchiveCompletionCounts(year, month);
    }

    // 월별 학습자료 완료 횟수 조회
    public List<Map<String, Object>> getMonthlyArchiveCompletionCounts(int year) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getMonthlyArchiveCompletionCounts(year);
    }

    // 활동별 포인트 획득 비율 조회
    public List<Map<String, Object>> getActivityPointsBreakdown() {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getActivityPointsBreakdown();
    }

    // 총 포인트 획득량 조회
    public int getTotalPointsEarned() {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class)
                .getTotalPointsEarned();
    }

}
