package org.finterest.point.service;

import org.finterest.point.dao.AdminPointRulesDAO;
import org.finterest.point.domain.PointRulesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPointRulesService {

    private final AdminPointRulesDAO adminPointRulesDAO;

    @Autowired
    public AdminPointRulesService(AdminPointRulesDAO adminPointRulesDAO) {
        this.adminPointRulesDAO = adminPointRulesDAO;
    }

    // 1. 포인트 정책 전체 조회
    public List<PointRulesVO> getAllPointRules() {
        return adminPointRulesDAO.getAllPointRules();
    }

    // 2. 포인트 정책 추가
    public boolean addPointRule(PointRulesVO pointRule) {
        int result = adminPointRulesDAO.addPointRule(pointRule);
        return result > 0;  // 성공 여부를 반환
    }

    // 3. 포인트 정책 수정
    public boolean updatePointRule(PointRulesVO pointRule) {
        int result = adminPointRulesDAO.updatePointRule(pointRule);
        return result > 0;  // 성공 여부를 반환
    }

    // 4. 포인트 정책 삭제
    public boolean deletePointRule(int ruleId) {
        int result = adminPointRulesDAO.deletePointRule(ruleId);
        return result > 0;  // 성공 여부를 반환
    }
}
