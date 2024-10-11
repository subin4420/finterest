package org.finterest.point.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;

import java.util.Date;
import java.util.List;


@Mapper
public interface PointMapper {

    // 전체 포인트 내역 조회
    List<PointVO> selectAllPoints(@Param("userId") int userId,
                                  @Param("filter") String filter,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);
    int selectTotalPoints(@Param("userId") int userId);

    int selectTotalMoney(@Param("userId") int userId);

    UserPointVO getUserById(@Param("userId") int userId);

    int insertPointHistory(@Param("userId") int userId);
}
