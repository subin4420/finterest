package org.finterest.point.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRulesVO {
    private int ruleId;              // 규칙 ID
    private String activityName;     // 활동명
    private int pointsAwarded;       // 포인트 금액
}
