package org.finterest.ranking.service;

import org.finterest.ranking.dto.RankingDto;
import org.finterest.ranking.mapper.RankingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.*;


import java.util.Arrays;
import java.util.List;


class RankingServiceTest {

    @InjectMocks
    private RankingService rankingService;

    @Mock
    private RankingMapper rankingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTopRankings_ShouldReturnRankingList() {
        // Given
        int limit = 10;
        List<RankingDto> mockRankingList = Arrays.asList(
                new RankingDto(1, "user1", 5000, 1, "Gold"),
                new RankingDto(2, "user2", 4000, 2, "Silver")
                // 필요에 따라 더 추가
        );
        when(rankingMapper.getTopRankings()).thenReturn(mockRankingList);

        // When
        List<RankingDto> rankings = rankingService.getTopRankings();

        // Then
        assertThat(rankings).isNotNull();
        assertThat(rankings.size()).isEqualTo(mockRankingList.size());
        assertThat(rankings).isEqualTo(mockRankingList);
        verify(rankingMapper, times(1)).getTopRankings();
    }


    @Test
    void getUserRanking_ShouldReturnUserRanking() {
        // Given
        int userId = 1;
        RankingDto mockRanking = new RankingDto(1, "user1", 5000, 1, "Gold");
        when(rankingMapper.getUserRanking(userId)).thenReturn(mockRanking);

        // When
        RankingDto ranking = rankingService.getUserRanking(userId);

        // Then
        assertThat(ranking).isNotNull();
        assertThat(ranking.getUserId()).isEqualTo(userId);
        assertThat(ranking).isEqualTo(mockRanking);
        verify(rankingMapper, times(1)).getUserRanking(userId);
    }

}