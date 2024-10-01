package org.finterest.ranking.controller;

import lombok.RequiredArgsConstructor;
import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity<List<RankingDto>> getTopRankings() {
        List<RankingDto> rankings = rankingService.getTopRankings();
        return ResponseEntity.ok(rankings);
    }

    //개인 랭킹 조회
    @GetMapping("/{userId}")
    public ResponseEntity<RankingDto> getUserRanking(@PathVariable("userId") int userId) {
        RankingDto ranking = rankingService.getUserRanking(userId);
        return ResponseEntity.ok(ranking);
    }

}
