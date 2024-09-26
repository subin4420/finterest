package org.finterest.point.dao;

import org.finterest.point.domain.PointRulesVO;
import org.finterest.point.mapper.AdminPointRulesMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminPointRulesDAO {
    private final SqlSessionTemplate sqlSession;

    @Autowired
    public AdminPointRulesDAO(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 1. 포인트 정책 전체 조회
    public List<PointRulesVO> getAllPointRules() {
        return sqlSession.getMapper(AdminPointRulesMapper.class).getAllPointRules();
    }

    // 2. 포인트 정책 추가
    public int addPointRule(PointRulesVO pointRule) {
        return sqlSession.getMapper(AdminPointRulesMapper.class).addPointRule(pointRule);
    }

    // 3. 포인트 정책 수정
    public int updatePointRule(PointRulesVO pointRule) {
        return sqlSession.getMapper(AdminPointRulesMapper.class).updatePointRule(pointRule);
    }

    // 4. 포인트 정책 삭제
    public int deletePointRule(int ruleId) {
        return sqlSession.getMapper(AdminPointRulesMapper.class).deletePointRule(ruleId);
    }
}
