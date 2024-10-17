package org.finterest.invest.scenario.controller;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.scenario.domain.ScenarioChartDTO;
import org.finterest.invest.scenario.domain.ScenarioConversionVO;
import org.finterest.invest.scenario.domain.ScenarioVO;
import org.finterest.invest.scenario.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/api/scenario")
@CrossOrigin("http://localhost:5173")
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

    @GetMapping("/chart/{stockCode}")
    public List<List<String>> scenarioChart(@PathVariable String stockCode) {
        return scenarioService.sendScenarioChartData(stockCode);
    }

    @GetMapping("/select")
    public String[] selectStockData() {
        return scenarioService.selectStockData();
    }

    @GetMapping("/current")
    public ScenarioChartDTO currentData() {
        return scenarioService.currentData();
    }

    @PostMapping("/next/{nextTurn}")
    public ResponseEntity<Map<String, Object>> nextTurn(@PathVariable int nextTurn) {
        log.info(nextTurn + "번 턴");
        Map<String, Object> response = scenarioService.nextTurn();
        return ResponseEntity.ok(response);
    }
}
