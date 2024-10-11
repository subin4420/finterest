package org.finterest.invest.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PortfolioDTO {

    private Long tradeId;             // 거래 ID (PK)
    private String stockCode;         // 주식 코드
    private String stockName;         // 주식 이름
    private BigDecimal price;         // 가격
    private Long quantity;            // 거래 수량
    private String tradeType;         // 거래 유형 (예: BUY, SELL)
    private BigDecimal totalPrice;    // 총 거래 금액
    private Long totalStockHoldings;  // 총 보유 주식 수
    private Integer userId;           // 사용자 ID
    private Date createdAt;           // 거래 생성일
    private Date updatedAt;           // 거래 수정일
    private BigDecimal money;         // 사용자 보유 가상 자산
}
