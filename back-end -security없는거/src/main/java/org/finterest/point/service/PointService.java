package org.finterest.point.service;

import org.apache.ibatis.annotations.Param;
import org.finterest.point.dao.PointDAO;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PointService {
    private final PointDAO pointDAO;

    @Autowired
    public PointService(PointDAO pointDAO) {
        this.pointDAO = pointDAO;
    }

    // 전체 포인트 내역 조회
    public List<PointVO> getPoint(int userId, String filter, Date startDate, Date endDate){
        return pointDAO.selectAllPoints(userId, filter, startDate, endDate);
    }

    public int getTotalPoints(@Param("userId") int userId){
        return pointDAO.selectTotalPoints(userId);
    }

    public UserPointVO getUserById(int userId){
        return pointDAO.getUserById(userId);
    }


}
