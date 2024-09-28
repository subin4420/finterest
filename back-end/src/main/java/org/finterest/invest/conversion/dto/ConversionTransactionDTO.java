package org.finterest.invest.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.conversion.domain.ConversionTransactionVO;
import org.finterest.invest.conversion.enums.ConversionType;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionTransactionDTO {
    private int transactionId;          // 거래 ID
    private int userId;                 // 사용자 ID
    private BigDecimal moneyChange;      // 가상 자금 변화량
    private int pointChange;            // 포인트 변화량
    private ConversionType conversionType;       // 환전 방향
    private Date conversionDate;        // 거래 시간
    private int rateId;                 // 환전 비율 ID
    // VO -> DTO 변환


    // VO -> DTO 변환
    public static ConversionTransactionDTO of(ConversionTransactionVO vo) {
        if (vo == null) {
            return null;
        }
        return ConversionTransactionDTO.builder()
                .transactionId(vo.getTransactionId())
                .userId(vo.getUserId())
                .moneyChange(vo.getMoneyChange())
                .pointChange(vo.getPointChange())
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
                .moneyChange(moneyChange)
                .pointChange(pointChange)
                .conversionType(conversionType)
                .conversionDate(conversionDate)
                .rateId(rateId)
                .build();
    }
}