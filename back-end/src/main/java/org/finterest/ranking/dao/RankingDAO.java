package org.finterest.ranking.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.mapper.RankingMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankingDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public RankingDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<RankingDto> getTopRankings(){
        return sqlSessionTemplate.getMapper(RankingMapper.class).getTopRankings();
    }
    public RankingDto getUserRanking(int userId){
        return sqlSessionTemplate.getMapper(RankingMapper.class).getUserRanking(userId);
    }
}
