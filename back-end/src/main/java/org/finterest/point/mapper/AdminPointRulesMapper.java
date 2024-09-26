package org.finterest.point.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.point.domain.PointRulesVO;

import java.util.List;

@Mapper
public interface AdminPointRulesMapper {
    List<PointRulesVO> getAllPointRules();

    int addPointRule(PointRulesVO pointRule);

    int updatePointRule(PointRulesVO pointRule);

    int deletePointRule(@Param("ruleId") int ruleId);
}
