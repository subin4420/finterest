package org.scoula.invest.domain.conversion.domain;

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
public class ConversionTransactionVO {
    private int transactionId;              // 거래 고유 ID
    private int userId;                     // 사용자 ID
    private int pointsUsed;                 // 사용된 포인트
    private BigDecimal moneyUsed;           // 사용된 가상 자금
    private int pointsReceived;             // 획득한 포인트
    private BigDecimal moneyReceived;       // 획득한 가상 자금
    private String conversionType;          // 환전 방향 ('points_to_money' or 'money_to_points')
    private Date conversionDate;            // 거래 시간
    private int rateId;                     // 환전 비율 ID
}