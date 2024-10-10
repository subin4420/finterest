package org.finterest.adminChart.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminChartMapper {
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
}
