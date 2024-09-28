package org.finterest.invest.conversion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.conversion.enums.ConversionType;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionTransactionVO {
    private int transactionId;          // 거래 ID
    private int userId;                 // 사용자 ID
    private BigDecimal moneyChange;     // 가상 자금 변화량
    private int pointChange;            // 포인트 변화량
    private ConversionType conversionType;  // 환전 방향
    private Date conversionDate;        // 거래 시간
    private int rateId;                 // 환전 비율 ID
}
