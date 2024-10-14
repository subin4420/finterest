package org.finterest.adminChart.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminChartMapper {
    // 오늘의 가입자 수 조회
    int getTodaySignupCount();
    // 활성 사용자 수 조회
    int getActiveUser();
    // 등록된 퀴즈 수 조회
    int getTotalQuizCount();
    //  용어 사전 수 조회
    int getTotalArchiveCount();

    // 특정 월의 주별 퀴즈 완료 횟수 조회
    @MapKey("quiz_week")
    List<Map<String, Object>> getWeeklyQuizCompletionCounts(
            @Param("year") int year,
            @Param("month") int month
    );

    // 월별 퀴즈 완료 횟수 조회
    @MapKey("quiz_month")
    List<Map<String, Object>> getMonthlyQuizCompletionCounts(@Param("year") int year);

    // 특정 월의 주별 학습자료 완료 횟수 조회
    @MapKey("archive_week")
    List<Map<String, Object>> getWeeklyArchiveCompletionCounts(
            @Param("year") int year,
            @Param("month") int month
    );

    // 월별 학습자료 완료 횟수 조회
    @MapKey("archive_month")
    List<Map<String, Object>> getMonthlyArchiveCompletionCounts(@Param("year") int year);

    // 활동별 포인트 획득 비율 조회
    @MapKey("point")
    List<Map<String, Object>> getActivityPointsBreakdown();

    // 총 포인트 획득량 조회
    int getTotalPointsEarned();


    // 4.2 퀴즈 및 학습 자료 통계
    // 모든 사용자 이름 조회
    List<String> getAllUserNames();

    // 모든 사용자 ID 조회
    List<Integer> getAllUserIds();

    // 사용자별 퀴즈 참여 수 조회
    @MapKey("user_id")
    List<Map<String, Object>> getQuizParticipationCountByUser(@Param("userId") int userId);

    // 사용자별 퀴즈 평균 점수 조회
    @MapKey("user_id")
    List<Map<String, Object>> getAverageQuizScoreByUser(@Param("userId") int userId);

    // 사용자별 학습 자료 완료 횟수 조회
    @MapKey("user_id")
    List<Map<String, Object>> getLearningMaterialCompletionCountByUser(@Param("userId") int userId);

    // 사용자별 학습 자료 완료 비율 조회
    @MapKey("user_id")
    List<Map<String, Object>> getCompletionRateByUser(@Param("userId") int userId);
}
