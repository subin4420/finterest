package org.finterest.invest.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.portfolio.mapper.PortfolioMapper;
import org.finterest.invest.portfolio.service.PortfolioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<Map<String, Object>> viewTransactionHistory(@PathVariable Integer userId) {
        return service.viewTransactionHistory(userId);
    }
}
