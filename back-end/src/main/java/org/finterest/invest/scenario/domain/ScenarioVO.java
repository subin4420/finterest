package org.finterest.invest.scenario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenarioVO {
    private int scenarioId;             // 시나리오 고유 ID
    private String description;          // 시나리오 설명
    private String marketSector;         // 시장 부문
    private double stockEffect;          // 특정 주식에 미치는 영향
    private double globalEffect;         // 전체 시장에 미치는 영향

}