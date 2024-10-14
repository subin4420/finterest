package org.finterest.ranking.service;

import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.mapper.RankingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RankingService {
    private RankingMapper rankingMapper;

    @Autowired
    public RankingService(RankingMapper rankingMapper) {
        this.rankingMapper = rankingMapper;
    }

    public List<RankingDto> getTopRankings(){
        return rankingMapper.getTopRankings();
    }

    public RankingDto getUserRanking(int userId){
        return rankingMapper.getUserRanking(userId);
    }

}
