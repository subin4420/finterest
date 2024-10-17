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

    // 오늘의 가입자 수 조회
    public int getTodaySignupCount(){
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getTodaySignupCount();
    }

    // 활성 사용자 수 조회
    public int getActiveUser(){
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getActiveUser();
    }

    // 등록된 퀴즈 수 조회
    public int getTotalQuizCount(){
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getTotalQuizCount();
    }
    //  용어 사전 수 조회
    public int getTotalArchiveCount(){
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getTotalArchiveCount();
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

    // 모든 사용자 이름 조회
    public List<String> getAllUserNames() {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getAllUserNames();
    }

    // 모든 사용자 ID 조회
    public List<Integer> getAllUserIds() {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getAllUserIds();
    }

    // 사용자별 퀴즈 참여 수 조회
    public List<Map<String, Object>> getQuizParticipationCountByUser(int userId) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getQuizParticipationCountByUser(userId);
    }

    // 사용자별 퀴즈 평균 점수 조회
    public List<Map<String, Object>> getAverageQuizScoreByUser(int userId) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getAverageQuizScoreByUser(userId);
    }

    // 사용자별 학습 자료 완료 횟수 조회
    public List<Map<String, Object>> getLearningMaterialCompletionCountByUser(int userId) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getLearningMaterialCompletionCountByUser(userId);
    }

    // 사용자별 학습 자료 완료 비율 조회
    public List<Map<String, Object>> getCompletionRateByUser(int userId) {
        return sqlSessionTemplate.getMapper(AdminChartMapper.class).getCompletionRateByUser(userId);
    }

}
