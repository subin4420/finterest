package org.finterest.invest.scenario.service;

import org.finterest.invest.scenario.domain.ScenarioConversionVO;
import org.finterest.invest.scenario.domain.ScenarioVO;
import org.finterest.invest.scenario.mapper.ScenarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService {

    private final ScenarioMapper scenarioMapper;

    @Autowired
    public ScenarioService(ScenarioMapper scenarioMapper) {
        this.scenarioMapper = scenarioMapper;
    }

    // 모든 시나리오 가져오기
    public List<ScenarioVO> getAllScenarios() {
        return scenarioMapper.getAllScenarios();
    }

    // 모든 시나리오 변환 가져오기
    public List<ScenarioConversionVO> getAllScenarioConversions() {
        return scenarioMapper.getAllScenarioConversions();
    }
}