package org.finterest.invest.stock.simulator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.invest.stock.simulator.domain.SimulatorVO;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SimulatorMapper {

    Long selectTotalQuantity(@Param("stockCode") String stockCode, @Param("userId") Integer userId);
    BigDecimal selectTotalPrice(@Param("stockCode") String stockCode, @Param("userId") Integer userId);

    BigDecimal getUserMoney(Integer userId);

    List<SimulatorVO> viewStockHeld(Integer userId);

    void tradeStock(SimulatorVO vo);

    String updateOauthToken(String token);

    String getOauthToken();

    int updateUserMoney(@Param("userId") Integer userId, @Param("money") BigDecimal money);

    // @Param을 추가하여 메서드 정의
    List<SimulatorVO> getUserTradeHistory(@Param("userId") Integer userId);
}