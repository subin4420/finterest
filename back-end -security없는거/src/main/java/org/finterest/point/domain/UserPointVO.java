package org.finterest.point.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPointVO {
    private int userId;         // 사용자 ID
    private int totalPoints;     // 누적 포인트
}
