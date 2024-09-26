package org.finterest.point.dao;


import org.apache.ibatis.annotations.Param;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.finterest.point.mapper.AdminPointMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class AdminPointDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminPointDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 포인트 적립/차감 내역 조회
    public List<PointVO> selectPointDetails(Integer userId, String filter, Date startDate, Date endDate){
        return sqlSessionTemplate.getMapper(AdminPointMapper.class).selectPointDetails(userId, filter, startDate, endDate);
    }

    // 전체 사용자 누적 포인트 조회
    public List<UserPointVO> selectUserPointSummaries(Date startDate, Date endDate){
        return sqlSessionTemplate.getMapper(AdminPointMapper.class).selectUserPointSummaries(startDate, endDate);
    }

    // 포인트 추가
    public void insertPoint(Map<String, Object> params) {
        sqlSessionTemplate.getMapper(AdminPointMapper.class).insertPoint(params);
    }

    // 사용자 누적 포인트 업데이트
    public void updateUserTotalPoints(int userId, int pointsChange) {
        sqlSessionTemplate.getMapper(AdminPointMapper.class).updateUserTotalPoints(userId, pointsChange);
    }
}
