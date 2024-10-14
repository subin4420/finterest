package org.finterest.ranking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.ranking.dto.RankingDto;

import java.util.List;

@Mapper
public interface RankingMapper {
    List<RankingDto> getTopRankings();
    RankingDto getUserRanking(@Param("userId") int userId);

}
