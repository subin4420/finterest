package org.finterest.point.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminPointMapper {

    // 포인트 적립/차감 내역 조회
    List<PointVO> selectPointDetails(
            @Param("userId") Integer userId,
            @Param("filter") String filter,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    // 전체 사용자 누적 포인트 조회
    List<UserPointVO> selectUserPointSummaries(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    // 포인트 추가
    void insertPoint(Map<String, Object> params);

    // 사용자 누적 포인트 업데이트
    void updateUserTotalPoints(
            @Param("userId") int userId,
            @Param("pointsChange") int pointsChange
    );

}
