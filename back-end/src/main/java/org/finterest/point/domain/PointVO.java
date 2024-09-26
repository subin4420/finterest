package org.finterest.point.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointVO {
    private int pointId;          // 포인트 ID
    private int userId;           // 사용자 ID
    private String activityName;  // 활동명
    private int pointsChange;     // 포인트 변화 (적립 또는 차감)
    private String createdAt;     // 포인트 발생 날짜
}
