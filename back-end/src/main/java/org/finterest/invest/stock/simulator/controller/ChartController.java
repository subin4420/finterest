package org.finterest.invest.stock.simulator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.stock.simulator.service.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/chart")
@RequiredArgsConstructor
@Slf4j
public class ChartController {

    private final ChartService service;

    @RequestMapping(value = "/data/{stockCode}", method = RequestMethod.GET)
    @ResponseBody
    public List<List<String>> sendChartData(@PathVariable String stockCode) throws IOException {
        List<List<String>> chartData = service.sendChartData(stockCode);
        return chartData;  // JSON 형식으로 변환되어 반환
    }
}