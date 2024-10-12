package org.finterest.adminChart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVO {
    private String userName; // 사용자 이름
    private int quizParticipationCount; // 퀴즈 참여 수
    private double averageQuizScore; // 평균 퀴즈 점수
    private int learningMaterialCompletionCount; // 학습 자료 완료 횟수
    private String completionRate; // 학습 자료 완료 비율
}
