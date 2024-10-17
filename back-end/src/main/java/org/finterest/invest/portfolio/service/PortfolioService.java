package org.finterest.invest.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.invest.portfolio.domain.PortfolioDTO;
import org.finterest.invest.portfolio.mapper.PortfolioMapper;
import org.finterest.invest.stock.simulator.mapper.SimulatorMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

                // 각 Map을 resultList에 추가
                resultList.add(viewData);
            }
        }
        return resultList;
    }

    public Map<String, Object> viewTotalAssets(Integer userId) {
        Map<String, Object> viewData = new HashMap<>();
        BigDecimal userMoney = mapper.getUserMoney(userId);
        viewData.put("money", userMoney);

        List<PortfolioDTO> heldStockData = mapper.viewTotalAssets(userId);
        viewData.put("heldStockData", heldStockData);

        return viewData;
    }

    public List<String[]> viewDailyIncome(Integer userId) {
        List<String[]> dailyIncomeData = new LinkedList<>();
        List<PortfolioDTO> portfolioList = mapper.getUserTradeData(userId);
        SimpleDateFormat sendFormat = new SimpleDateFormat("yyyyMMdd");

        // 날짜별로 트랜잭션을 그룹화하고 TreeMap을 사용하여 날짜 기준 오름차순으로 정렬
        Map<String, List<PortfolioDTO>> tradesByDate = portfolioList.stream()
                .collect(Collectors.groupingBy(dto -> sendFormat.format(dto.getCreatedAt()), TreeMap::new, Collectors.toList()));

        for (String key : tradesByDate.keySet()) {
            List<PortfolioDTO> userData = tradesByDate.get(key);
            BigDecimal totalProfit = BigDecimal.ZERO;    // 총 수익
            BigDecimal totalInvestment = BigDecimal.ZERO; // 총 투자 금액
            String[] result = new String[2];                // 결과 저장 배열

            for (PortfolioDTO dto : userData) {
                log.info(key);
                BigDecimal closePrice = getPreviousDayClosePrice(dto.getStockCode(), key);  // 전일 종가
                BigDecimal price = dto.getPrice();  // 현재 가격

                // closePrice가 null 또는 0이 아닌지 확인
                if (closePrice == null || closePrice.compareTo(BigDecimal.ZERO) == 0) {
                    log.warn("closePrice is null or zero for stockCode: {}, date: {}", dto.getStockCode(), key);
                    continue;
                }

                // 수량을 BigDecimal로 변환
                BigDecimal amountBigDecimal = new BigDecimal(dto.getQuantity());

                // 주식당 총 수익 = (현재 가격 - 전일 종가) * 수량
                BigDecimal profitForStock = price.subtract(closePrice).multiply(amountBigDecimal);

                // 총 투자 금액 = 전일 종가 * 수량
                BigDecimal investmentForStock = closePrice.multiply(amountBigDecimal);

                // 총 수익과 총 투자 금액을 누적합
                totalProfit = totalProfit.add(profitForStock);
                totalInvestment = totalInvestment.add(investmentForStock);
            }

            // 날짜별 수익률 = (총 수익 / 총 투자 금액) * 100
            BigDecimal dailyReturnRate = BigDecimal.ZERO;
            if (totalInvestment.compareTo(BigDecimal.ZERO) != 0) {
                dailyReturnRate = totalProfit.divide(totalInvestment, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100));
            }

            // 결과 저장
            result[0] = key;  // 날짜
            result[1] = dailyReturnRate.setScale(2, RoundingMode.HALF_UP).toString();  // 날짜별 수익률

            log.info("Date: {}, Daily Return Rate: {}", result[0], result[1]);
            dailyIncomeData.add(result);  // 결과 리스트에 추가
        }
        return dailyIncomeData;
    }


    // 종가 가져오기
    public BigDecimal getPreviousDayClosePrice(String srtnCd, String date) {
        String closePrice = mapper.getPreviousDayClosePrice(srtnCd, date);
        log.info(closePrice);
        return new BigDecimal(closePrice);
    }
}