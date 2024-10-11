package org.finterest.invest.portfolio.mapper;

import org.finterest.invest.portfolio.domain.PortfolioDTO;

import java.util.List;

public interface PortfolioMapper {
    public String getPreviousDayClosePrice(String stockCode);

    List<PortfolioDTO> selectTransactionHistory(Integer userId);
}
