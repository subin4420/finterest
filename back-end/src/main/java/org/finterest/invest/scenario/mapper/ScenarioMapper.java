package org.finterest.invest.scenario.mapper;


import org.finterest.invest.scenario.domain.ScenarioConversionVO;
import org.finterest.invest.scenario.domain.ScenarioVO;

import java.util.List;

public interface ScenarioMapper {

    // 모든 시나리오 가져오기
    List<ScenarioVO> getAllScenarios();

    // 모든 시나리오 변환 가져오기
    List<ScenarioConversionVO> getAllScenarioConversions();
}
