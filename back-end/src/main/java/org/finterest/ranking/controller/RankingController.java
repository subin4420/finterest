package org.finterest.ranking.controller;

import lombok.RequiredArgsConstructor;
import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {
    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    // 전체 사용자 랭킹 조회
    @GetMapping
    public ResponseEntity<Map<String, List<RankingDto>>> getTopRankings() {
        List<RankingDto> rankings = rankingService.getTopRankings();

        // "rankings"라는 key로 랭킹 리스트를 반환
        Map<String, List<RankingDto>> response = new HashMap<>();
        response.put("rankings", rankings);

        return ResponseEntity.ok(response);
    }

    // 특정 사용자 랭킹 조회
    @GetMapping("/{userId}")
    public ResponseEntity<RankingDto> getUserRanking(@PathVariable("userId") int userId) {
        RankingDto ranking = rankingService.getUserRanking(userId);

        // 사용자 랭킹이 존재하지 않을 경우 404 Not Found 반환
        if (ranking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ranking);
    }
}
