package org.finterest.invest.stock.simulator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.stock.simulator.domain.SimulatorVO;
import org.finterest.invest.stock.simulator.mapper.SimulatorMapper;
import org.finterest.invest.stock.simulator.service.SimulatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class SimulatorController {

    private final SimulatorService service;
    private final SimulatorMapper mapper;

    @GetMapping("/userId/{userId}")
    public ResponseEntity<Map<String, Object>> viewStockHeld(@PathVariable Integer userId) {
        Map<String, Object> response = service.viewStockHeld(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//
//    @PostMapping("/userId/{userId}")
//    public ResponseEntity<Map<String, Object>> viewStockHeld(@PathVariable Integer userId) {
//        Map<String, Object> response = service.viewStockHeld(userId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping("/stock/buy")
    public ResponseEntity<String> buyStock(
            @RequestBody Map<String, Object> payload) {
        log.info("Received payload: {}", payload); // 로그로 받은 데이터 출력
        String stockCode = (String) payload.get("stockCode");
        BigDecimal price = new BigDecimal(payload.get("price").toString());
        Long quantity = Long.parseLong(payload.get("quantity").toString());
        BigDecimal totalPrice = new BigDecimal(payload.get("totalPrice").toString());
//        Integer userId = (Integer) payload.get("userId");
        Integer userId = 1;

        SimulatorVO vo = new SimulatorVO();
        vo.setUserId(userId);
        vo.setStockCode(stockCode);
        vo.setPrice(price);
        vo.setQuantity(quantity);
        vo.setTotalPrice(totalPrice);
//        vo.setUserId(userId);

        service.buyStock(vo);
        return new ResponseEntity<>("Trade buy stock successfully", HttpStatus.OK);
    }

    @PostMapping("/stock/sell")
    public ResponseEntity<String> sellStock(
            @RequestBody Map<String, Object> payload) {
        String stockCode = (String) payload.get("stockCode");
        BigDecimal price = new BigDecimal(payload.get("price").toString());
        Long quantity = Long.parseLong(payload.get("quantity").toString());
        BigDecimal totalPrice = new BigDecimal(payload.get("totalPrice").toString());
//        Integer userId = (Integer) payload.get("userId");
        Integer userId = 1;

        SimulatorVO vo = new SimulatorVO();
        vo.setUserId(userId);
        vo.setStockCode(stockCode);
        vo.setPrice(price);
        vo.setQuantity(quantity);
        vo.setTotalPrice(totalPrice);
//        vo.setUserId(userId);

        service.sellStock(vo);
        return new ResponseEntity<>("Trade sell stock successfully", HttpStatus.OK);
    }
}