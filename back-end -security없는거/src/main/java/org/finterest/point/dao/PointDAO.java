package org.finterest.point.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.finterest.point.mapper.PointMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class PointDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public PointDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 전체 포인트 내역 조회
    public List<PointVO> selectAllPoints(int userId, String filter, Date startDate, Date endDate){
        return sqlSessionTemplate.getMapper(PointMapper.class).selectAllPoints(userId, filter, startDate, endDate);
    }

    public int selectTotalPoints(@Param("userId") int userId){
        return sqlSessionTemplate.getMapper(PointMapper.class).selectTotalPoints(userId);
    }

    public UserPointVO getUserById(@Param("userId") int userId){
        return sqlSessionTemplate.getMapper(PointMapper.class).getUserById(userId);
    }
}
