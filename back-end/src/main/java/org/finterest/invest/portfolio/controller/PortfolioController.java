package org.finterest.invest.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.portfolio.domain.PortfolioDTO;
import org.finterest.invest.portfolio.mapper.PortfolioMapper;
import org.finterest.invest.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService service;
    private final PortfolioMapper mapper;

    @GetMapping("/transactionHistory/{userId}")
    public ResponseEntity<List<Map<String, Object>>> viewTransactionHistory(@PathVariable Integer userId) {
        List<Map<String, Object>> response = service.viewTransactionHistory(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/assets")
    private ResponseEntity<Map<String, Object>> viewTotalAssets() {
        Integer userId = 1;
        Map<String, Object> response = service.viewTotalAssets(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<String[]>> viewDailyIncome() {
        Integer userId = 5;
        List<String[]> response = service.viewDailyIncome(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
