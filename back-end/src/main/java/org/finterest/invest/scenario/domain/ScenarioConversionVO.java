package org.finterest.invest.scenario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ScenarioConversionVO {
    private int id;                     // 고유 ID
    private String stockName;           // 주식 이름
    private double initialPrice;        // 초기 주식 가격
    private String marketSector;        // 시장 부문
    private int scenarioId;             // 관련 시나리오 ID
    private String srtnCd;
}
