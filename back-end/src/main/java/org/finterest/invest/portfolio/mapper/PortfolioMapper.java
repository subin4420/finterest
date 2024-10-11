package org.finterest.invest.portfolio.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finterest.invest.portfolio.domain.PortfolioDTO;

import java.util.List;

@Mapper
public interface PortfolioMapper {
    public String getPreviousDayClosePrice(String stockCode);

    List<PortfolioDTO> selectTransactionHistory(Integer userId);
}
