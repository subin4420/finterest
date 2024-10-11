package org.finterest.invest.stock.simulator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.stock.simulator.domain.SimulatorVO;
import org.finterest.invest.stock.simulator.service.SimulatorService;
import org.finterest.security.util.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class SimulatorController {

    private final SimulatorService service;
    private final TokenUtil tokenUtil;

    @GetMapping("/held")
    public ResponseEntity<Map<String, Object>> viewStockHeld(@RequestHeader("Authorization") String authToken) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        Map<String, Object> response = service.viewStockHeld(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history")  // /trade/tradeHistory에 대한 매핑
    public ResponseEntity<List<SimulatorVO>> getUserTradeHistory(@RequestHeader("Authorization") String authToken) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        List<SimulatorVO> response = service.getUserTradeHistory(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/stock/buy")
    public ResponseEntity<String> buyStock(@RequestHeader("Authorization") String authToken, @RequestBody SimulatorVO vo) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        vo.setUserId(userId);
        service.buyStock(vo);
        return new ResponseEntity<>("Trade buy stock successfully", HttpStatus.OK);
    }

    @PostMapping("/stock/sell")
    public ResponseEntity<String> sellStock(@RequestHeader("Authorization") String authToken, @RequestBody SimulatorVO vo) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        vo.setUserId(userId);
        service.sellStock(vo);
        return new ResponseEntity<>("Trade sell stock successfully", HttpStatus.OK);
    }
}
