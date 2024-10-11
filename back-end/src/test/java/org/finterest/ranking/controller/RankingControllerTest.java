package org.finterest.ranking.controller;


import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;

// Logger 관련 import 추가
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.*;

class RankingControllerTest {

    // Logger 수동 선언
    private static final Logger logger = LogManager.getLogger(RankingControllerTest.class);


    private MockMvc mockMvc;

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController;

    @BeforeEach
    void setup(){
        // Mockito 어노테이션 초기화
        MockitoAnnotations.openMocks(this);

        // MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(rankingController).build();
    }

    @Test
    void getTopRankings_ShouldReturnRankingList() throws Exception {
        // Given
        when(rankingService.getTopRankings()).thenReturn(
                Arrays.asList(
                        new RankingDto(1, "user1", 5000, 1, "Gold"),
                        new RankingDto(2, "user2", 4000, 2, "Silver")
                )
        );

        // When & Then
        mockMvc.perform(get("/api/rankings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].username", is("user1")))
                .andExpect(jsonPath("$[0].totalPoints", is(5000)))
                .andExpect(jsonPath("$[0].rankPosition", is(1)))
                .andExpect(jsonPath("$[0].rankTitle", is("Gold")));

        // 로깅 예시
        logger.info("getTopRankings_ShouldReturnRankingList 테스트 성공");
    }


    @Test
    void getUserRanking_ShouldReturnUserRanking() throws Exception {
        // Given
        int userId = 1;
        when(rankingService.getUserRanking(userId)).thenReturn(
                new RankingDto(1, "user1", 5000, 1, "Gold")
        );

        // When & Then
        mockMvc.perform(get("/api/rankings/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.username", is("user1")))
                .andExpect(jsonPath("$.totalPoints", is(5000)))
                .andExpect(jsonPath("$.rankPosition", is(1)))
                .andExpect(jsonPath("$.rankTitle", is("Gold")));

        // 로깅 예시
        logger.info("getUserRanking_ShouldReturnUserRanking 테스트 성공");
    }
}