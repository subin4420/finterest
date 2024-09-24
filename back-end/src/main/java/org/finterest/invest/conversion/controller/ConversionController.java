package org.finterest.invest.conversion.controller;

import org.finterest.invest.conversion.dto.ConversionTransactionDTO;
import org.finterest.invest.conversion.service.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/conversion")
public class ConversionController {
    private final ConversionService conversionService;

    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    // 포인트를 가상 돈으로 환전하는 엔드포인트
    @PostMapping("/points-to-money")
    public ConversionTransactionDTO convertPointsToMoney(@RequestParam int userId, @RequestParam int points) {
        return conversionService.convertPointsToMoney(userId, points);
    }

    // 가상 돈을 포인트로 환전하는 엔드포인트
    @PostMapping("/money-to-points")
    public ConversionTransactionDTO convertMoneyToPoints(@RequestParam int userId, @RequestParam BigDecimal money) {
        return conversionService.convertMoneyToPoints(userId, money);
    }

    // 실시간으로 전환 비율을 가져오는 엔드포인트
    @GetMapping("/current-rate")
    public BigDecimal getCurrentConversionRate() {
        return conversionService.getCurrentConversionRate();
    }
}