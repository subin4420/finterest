package org.finterest.achieve.mapper;

import org.finterest.achieve.domain.AchieveVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AchieveMapper {
    List<AchieveVO> all();
    AchieveVO one(int id);
    List<AchieveVO> categoryFilter(int category_id);
}
