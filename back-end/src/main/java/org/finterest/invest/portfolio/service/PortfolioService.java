package org.finterest.invest.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.portfolio.domain.PortfolioDTO;
import org.finterest.invest.portfolio.mapper.PortfolioMapper;
import org.finterest.invest.stock.simulator.mapper.SimulatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {


    private final PortfolioMapper mapper;

    private final SimulatorMapper simulatorMapper;


    public List<Map<String, Object>> viewTransactionHistory(Integer userId) {
        List<PortfolioDTO> portfolioList = mapper.selectTransactionHistory(userId);
        List<Map<String, Object>> resultList = new ArrayList<>();

        if (!portfolioList.isEmpty()) {
            for (PortfolioDTO vo : portfolioList) {
                // 각 PortfolioVO 객체의 createdAt 값 포맷팅
                Date date = vo.getCreatedAt();
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                String createYear = yearFormat.format(date);
                String createMonthDay = monthDayFormat.format(date);
                String createTime = timeFormat.format(date);

                // 각 PortfolioVO의 필드를 Map에 저장
                Map<String, Object> viewData = new HashMap<>();
                viewData.put("createdYear", createYear);
                viewData.put("createdMonthDay", createMonthDay);
                viewData.put("createdTime", createTime);
                viewData.put("stockName", vo.getStockName());
                viewData.put("tradeType", vo.getTradeType());
                viewData.put("price", vo.getTotalPrice());
                viewData.put("money", vo.getMoney());

                // 각 Map을 resultList에 추가
                resultList.add(viewData);
            }
        }
        return resultList;
    }

    public String getPreviousDayClosePrice(String stockCode) {
        return mapper.getPreviousDayClosePrice(stockCode);
    }
}