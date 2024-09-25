package org.scoula.invest.domain.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.invest.domain.conversion.domain.ConversionTransactionVO;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionTransactionDTO {
    private int transactionId;
    private int userId;
    private int pointsUsed;
    private BigDecimal moneyUsed;
    private int pointsReceived;
    private BigDecimal moneyReceived;
    private String conversionType;  // 'points_to_money' or 'money_to_points'
    private Date conversionDate;
    private int rateId;

    // VO -> DTO 변환
    public static ConversionTransactionDTO of(ConversionTransactionVO vo) {
        return vo == null ? null : ConversionTransactionDTO.builder()
                .transactionId(vo.getTransactionId())
                .userId(vo.getUserId())
                .pointsUsed(vo.getPointsUsed())
                .moneyUsed(vo.getMoneyUsed())
                .pointsReceived(vo.getPointsReceived())
                .moneyReceived(vo.getMoneyReceived())
                .conversionType(vo.getConversionType())
                .conversionDate(vo.getConversionDate())
                .rateId(vo.getRateId())
                .build();
    }

    // DTO -> VO 변환
    public ConversionTransactionVO toVo() {
        return ConversionTransactionVO.builder()
                .transactionId(transactionId)
                .userId(userId)
                .pointsUsed(pointsUsed)
                .moneyUsed(moneyUsed)
                .pointsReceived(pointsReceived)
                .moneyReceived(moneyReceived)
                .conversionType(conversionType)
                .conversionDate(conversionDate)
                .rateId(rateId)
                .build();
    }
}