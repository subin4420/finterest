package org.finterest.invest.conversion.controller;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.conversion.dto.*;
import org.finterest.invest.conversion.service.ConversionService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/points-to-money")
    public ResponseEntity<String> convertPointsToMoney(@RequestBody ConversionRequestDTO request, @RequestHeader("Authorization") String authToken) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        return ResponseEntity.ok(conversionService.convertPointsToMoney(userId, request.getPointAmount()));
    }

    @PostMapping("/money-to-points")
    public ResponseEntity<String> convertMoneyToPoints(@RequestBody ConversionRequestDTO request, @RequestHeader("Authorization") String authToken) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        return ResponseEntity.ok(conversionService.convertMoneyToPoints(userId, request.getMoneyAmount()));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<ConversionTransactionDTO>> getConversionTransactions(@RequestHeader("Authorization") String authToken) {
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        return ResponseEntity.ok(conversionService.getConversionTransactions(userId));
    }

    @GetMapping("/rate/latest")
    public ResponseEntity<ConversionRateDTO> getLatestConversionRate() {
        return ResponseEntity.ok(conversionService.getLatestConversionRate());
    }

    @PutMapping("/rate/update")
    public ResponseEntity<String> updateConversionRate(@RequestBody ConversionRateDTO rateDTO) {
        conversionService.updateConversionRate(rateDTO);
        return ResponseEntity.ok("환전 비율이 업데이트되었습니다.");
    }

    @GetMapping("/rates")
    public ResponseEntity<List<ConversionRateDTO>> getAllConversionRates() {
        return ResponseEntity.ok(conversionService.getAllConversionRates());
    }

    // 사용자 잔액 조회 엔드포인트 추가
    @GetMapping("/balance")
    public ResponseEntity<UserBalanceDTO> getUserBalance(@RequestHeader("Authorization") String authToken) {
        log.info(authToken);
        Integer userId = tokenUtil.getUserIdFromToken(authToken);
        return ResponseEntity.ok(conversionService.getUserBalance(userId));
    }
}
