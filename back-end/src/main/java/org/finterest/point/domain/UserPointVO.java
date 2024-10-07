package org.finterest.point.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null인 필드는 직렬화하지 않음
public class UserPointVO {
    private int userId;         // 사용자 ID

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private int totalPoints;     // 누적 포인트

    @JsonInclude(JsonInclude.Include.NON_EMPTY)  // null 또는 빈 리스트일 경우 직렬화하지 않음
    private BigDecimal totalMoney;      // 누적 가상자금
}
