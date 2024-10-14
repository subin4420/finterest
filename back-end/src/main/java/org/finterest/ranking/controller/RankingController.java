package org.finterest.ranking.controller;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.ognl.Token;
import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.service.RankingService;
import org.finterest.security.util.TokenUtil;
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
    private final TokenUtil tokenUtil;

    @Autowired
    public RankingController(RankingService rankingService, TokenUtil tokenUtil) {
        this.rankingService = rankingService;
        this.tokenUtil = tokenUtil;
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

    // JWT를 이용한 인증된 사용자 개인 랭킹 조회
    @GetMapping("/me")
    public ResponseEntity<RankingDto> getUserRanking(@RequestHeader("Authorization") String token) {
        // JWT 토큰에서 'Bearer ' 부분을 제거하고 실제 토큰만 추출
        String jwtToken = token.substring(7);

        // JWT에서 userId를 추출
        int userId = tokenUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드

        // 해당 userId로 랭킹 조회
        RankingDto ranking = rankingService.getUserRanking(userId);

        // 사용자 랭킹이 존재하지 않을 경우 404 Not Found 반환
        if (ranking == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ranking);
    }
}
