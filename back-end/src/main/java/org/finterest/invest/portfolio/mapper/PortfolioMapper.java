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
    String getPreviousDayClosePrice(@Param("srtnCd") String srtnCd, @Param("basDt") Date basDt);

    // 거래 내역
    List<PortfolioDTO> selectTransactionHistory(Integer userId);

    // 사용자 보유 주식별 가격 추출
    BigDecimal getPreviousDayTotalPrice(@Param("srtnCd") String srtnCd, @Param("date") Date date, @Param("userId") Integer userId);

    // 사용자 보유 자산 추출
    BigDecimal getUserMoney(Integer userId);

    //
    List<PortfolioDTO> viewStockHeld(Integer userId);
}
