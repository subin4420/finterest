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
public class ConversionRateVO {
    private int rateId;                    // 환전 규칙 고유 ID
    private BigDecimal conversionRate;     // 포인트와 가상돈 간의 환전 비율
    private Date createdAt;                // 환전 규칙 생성일
    private Date updatedAt;                // 환전 규칙 수정일
}