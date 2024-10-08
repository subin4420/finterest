package org.finterest.invest.scenario.controller;

import org.finterest.invest.scenario.domain.ScenarioConversionVO;
import org.finterest.invest.scenario.domain.ScenarioVO;
import org.finterest.invest.scenario.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    @Autowired
    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    // 모든 시나리오 가져오기
    @GetMapping("/all")
    public List<ScenarioVO> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    // 모든 시나리오 변환 가져오기
    @GetMapping("/conversions")
    public List<ScenarioConversionVO> getAllScenarioConversions() {
        return scenarioService.getAllScenarioConversions();
    }
}
