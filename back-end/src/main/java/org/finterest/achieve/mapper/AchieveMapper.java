package org.finterest.achieve.mapper;

import org.finterest.achieve.domain.AchieveVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AchieveMapper {
    List<AchieveVO> selectAllAchieve();
    AchieveVO selectAchieveById(int id);
    List<AchieveVO> selectAchieveByCategory(int category_id);
}
