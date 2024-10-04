package org.finterest.invest.conversion.controller;

import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.finterest.invest.conversion.dto.ConversionRateDTO;
import org.finterest.invest.conversion.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    /**
     * 포인트를 가상 자금으로 환전하는 API
     *
     * @param username    사용자 이름
     * @param pointAmount 환전할 포인트 양
     * @return 환전 성공 여부 메시지
     */
    @PostMapping("/points-to-money")
    public ResponseEntity<String> convertPointsToMoney(@RequestParam String username, @RequestParam int pointAmount) {
        String message = conversionService.convertPointsToMoney(username, pointAmount);
        return ResponseEntity.ok(message);
    }

    /**
     * 가상 자금을 포인트로 환전하는 API
     *
     * @param username    사용자 이름
     * @param moneyAmount 환전할 가상 자금 양
     * @return 환전 성공 여부 메시지
     */
    @PostMapping("/money-to-points")
    public ResponseEntity<String> convertMoneyToPoints(@RequestParam String username, @RequestParam double moneyAmount) {
        String message = conversionService.convertMoneyToPoints(username, BigDecimal.valueOf(moneyAmount));
        return ResponseEntity.ok(message);
    }

    /**
     * 특정 사용자의 환전 거래 내역을 조회하는 API
     *
     * @param username 사용자 이름
     * @return 환전 거래 내역 리스트
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<ConversionTransactionDTO>> getConversionTransactions(@RequestParam String username) {
        List<ConversionTransactionDTO> transactions = conversionService.getConversionTransactions(username);
        return ResponseEntity.ok(transactions);
    }


    /**
     * 최신 환전 비율을 조회하는 API
     */
    @GetMapping("/rate/latest")
    public ResponseEntity<ConversionRateDTO> getLatestConversionRate() {
        ConversionRateDTO rate = conversionService.getLatestConversionRate();
        return ResponseEntity.ok(rate);
    }


    /** 환전 비율을 업데이트하는 API*/
    @PutMapping("/rate/update")
    public ResponseEntity<String> updateConversionRate(@RequestBody ConversionRateDTO rateDTO) {
        conversionService.updateConversionRate(rateDTO);
        return ResponseEntity.ok("환전 비율이 업데이트되었습니다.");
    }

    /**
     * 모든 환전 비율 기록을 조회하는 API
     *
     * @return 환전 비율 리스트
     */
    @GetMapping("/rates")
    public ResponseEntity<List<ConversionRateDTO>> getAllConversionRates() {
        List<ConversionRateDTO> rates = conversionService.getAllConversionRates();
        return ResponseEntity.ok(rates);
    }
}
