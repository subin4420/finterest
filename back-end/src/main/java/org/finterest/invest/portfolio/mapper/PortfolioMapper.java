package org.finterest.invest.portfolio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.invest.portfolio.domain.PortfolioDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface PortfolioMapper {
    // 특정 주식, 날짜 종가 추출
    String getPreviousDayClosePrice(@Param("srtnCd") String srtnCd, @Param("basDt") String basDt);

    // 거래 내역
    List<PortfolioDTO> selectTransactionHistory(Integer userId);

    // 사용자 보유 주식별 가격 추출
    List<List<String>> getPreviousDayTradeData(@Param("date") Date date, @Param("userId") Integer userId);

    // 사용자 보유 자산 추출
    BigDecimal getUserMoney(Integer userId);

    // 보유 자산 베이스 차트 데이터 추출
    List<PortfolioDTO> viewTotalAssets(Integer userId);

    // 일 별 수익률 계산을 위한 거래내역 추출
    List<PortfolioDTO> getUserTradeData(Integer userId);
}
