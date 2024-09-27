package org.finterest.invest.conversion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.conversion.domain.ConversionRateVO;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionRateDTO {
    private int rateId;
    private BigDecimal conversionRate;
    private Date createdAt;
    private Date updatedAt;

    // VO -> DTO 변환
    public static ConversionRateDTO of(ConversionRateVO vo) {
        return vo == null ? null : ConversionRateDTO.builder()
                .rateId(vo.getRateId())
                .conversionRate(vo.getConversionRate())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    // DTO -> VO 변환
    public ConversionRateVO toVo() {
        return ConversionRateVO.builder()
                .rateId(rateId)
                .conversionRate(conversionRate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}