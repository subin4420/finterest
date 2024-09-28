package org.finterest.point.service;

import org.finterest.point.dao.AdminPointDAO;
import org.finterest.point.domain.PointVO;
import org.finterest.point.domain.UserPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminPointService {
    private final AdminPointDAO adminPointDAO;

    @Autowired
    public AdminPointService(AdminPointDAO adminPointDAO) {
        this.adminPointDAO = adminPointDAO;
    }

    // 포인트 적립/차감 내역 조회
    public List<PointVO> selectPointDetails(Integer userId, String filter, Date startDate, Date endDate){
        return adminPointDAO.selectPointDetails(userId, filter, startDate, endDate);
    }

    // 전체 사용자 누적 포인트 조회
    public List<UserPointVO> selectUserPointSummaries(Date startDate, Date endDate){
        return adminPointDAO.selectUserPointSummaries(startDate, endDate);
    }

    // 포인트 추가 및 사용자 누적 포인트 업데이트
    public void addPoint(int userId, String activityName, int pointsChange) {
        // 포인트 추가
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("activityName", activityName);
        params.put("pointsChange", pointsChange);
        adminPointDAO.insertPoint(params);

        // 사용자 누적 포인트 업데이트
        adminPointDAO.updateUserTotalPoints(userId, pointsChange);
    }

}
