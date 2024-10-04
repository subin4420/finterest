package org.finterest.invest.stock.simulator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.invest.stock.simulator.domain.SimulatorVO;
import org.finterest.invest.stock.simulator.mapper.SimulatorMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@Service
@RequiredArgsConstructor
public class SimulatorService {

    private final SimulatorMapper mapper;

    public void buyStock(SimulatorVO vo) {
        long quantity = vo.getQuantity();
        String stockName = getStockName(vo.getStockCode());

        // 현재 보유 수량 계산
        Long currentQuantity = selectTotalQuantity(vo.getStockCode(), vo.getUserId());
        log.info("현재 총 수량 (currentQuantity): " + currentQuantity);

        // vo에 담기
        vo.setUserId(1);
        vo.setStockName(stockName);
        vo.setTradeType("매수");
        vo.setTotalStockHoldings(currentQuantity + quantity);  // 총 보유량 갱신
        vo.setCreatedAt(new Date());
        vo.setUpdatedAt(new Date());

        mapper.tradeStock(vo);
    }

    public void sellStock(SimulatorVO vo) {
        long quantity = vo.getQuantity();
        String stockName = getStockName(vo.getStockCode());

        // 현재 보유 수량 계산
        Long currentQuantity = selectTotalQuantity(vo.getStockCode(), vo.getUserId());
        log.info("현재 총 수량 (currentQuantity): " + currentQuantity);

        // vo에 담기
        vo.setUserId(1);
        vo.setStockName(stockName);
        vo.setTradeType("매도");
        vo.setTotalStockHoldings(currentQuantity - quantity);  // 총 보유량 갱신
        vo.setCreatedAt(new Date());
        vo.setUpdatedAt(new Date());

        mapper.tradeStock(vo);
    }

    public Long selectTotalQuantity(String stockCode, Integer userId) {
        log.info("stockCode: " + stockCode + ", userId: " + userId);
        Long result = mapper.selectTotalQuantity(stockCode,userId);
        if (result == null){
            return 0L;
        }
        return result;
    }

    public BigDecimal selectTotalPrice(String stockCode, Integer userId) {
        BigDecimal result = mapper.selectTotalPrice(stockCode, userId);
        if (result == null) {
            return BigDecimal.ZERO;
        }
        return result;
    }

    public Map<String, Object> viewStockHeld(Integer userId) {
        List<SimulatorVO> result = mapper.viewStockHeld(userId);
        BigDecimal money = mapper.getUserMoney(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("money", money);
        response.put("stock",result);
        return response;
    }

    public String getStockName(String stockCode) {
        return mapper.getStockName(stockCode);
    }
}
