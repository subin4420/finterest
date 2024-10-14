package org.finterest.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDto {
    private int userId;
    private String username;
    private int totalPoints;
    private int rankPosition;
    private String rankTitle;
}
