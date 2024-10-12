package org.finterest.invest.stock.simulator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.apache.ibatis.annotations.Param;
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
        log.info("authToken");
        // Long 변환 대신 BigDecimal 그대로 사용
        BigDecimal price = vo.getPrice();
        Long quantity = vo.getQuantity();
        BigDecimal money = mapper.getUserMoney(vo.getUserId());

        // 현재 총 거래 금액 계산
        BigDecimal totalPrice = selectTotalPrice(vo.getStockCode(), vo.getUserId());
        BigDecimal currentTotalPrice = price.multiply(BigDecimal.valueOf(quantity));

        // 현재 보유 수량 계산
        Long currentQuantity = selectTotalQuantity(vo.getStockCode(), vo.getUserId());

        if (money.compareTo(currentTotalPrice) < 0) {
            throw new IllegalArgumentException("보유 금액이 부족합니다.");
        }

        // BigDecimal과 Long 연산
        vo.setTradeType("매수");
        vo.setTotalPrice(totalPrice.add(currentTotalPrice));  // 가격 * 수량
        vo.setTotalStockHoldings(currentQuantity + quantity);  // 총 보유량 갱신
        vo.setCreatedAt(new Date());
        vo.setUpdatedAt(new Date());

        mapper.tradeStock(vo);
    }

    public void sellStock(SimulatorVO vo) {
        // Long 변환 대신 BigDecimal 그대로 사용
        BigDecimal price = vo.getPrice();
        Long quantity = vo.getQuantity();

        // 현재 총 거래 금액 계산
        BigDecimal totalPrice = selectTotalPrice(vo.getStockCode(), vo.getUserId());
        BigDecimal currentTotalPrice = price.multiply(BigDecimal.valueOf(quantity));

        // 현재 보유 수량 계산
        Long currentQuantity = selectTotalQuantity(vo.getStockCode(), vo.getUserId());

        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("매도하려는 수량이 현재 보유 수량보다 많습니다.");
        }

        // BigDecimal과 Long 연산
        vo.setTradeType("매도");
        vo.setTotalPrice(totalPrice.subtract(currentTotalPrice));  // 가격 * 수량
        vo.setTotalStockHoldings(currentQuantity - quantity);  // 총 보유량 갱신
        vo.setCreatedAt(new Date());
        vo.setUpdatedAt(new Date());

        mapper.tradeStock(vo);
    }

    public Long selectTotalQuantity(String stockCode, Integer userId) {
        Long result = mapper.selectTotalQuantity(stockCode, userId);
        if (result == null) {
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
        response.put("stock", result);
        return response;
    }

    public List<SimulatorVO> getUserTradeHistory(Integer userId) {
        // userId를 매개변수로 받아서 Mapper를 통해 거래 기록을 가져온다.
        return mapper.getUserTradeHistory(userId);
    }
}