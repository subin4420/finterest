package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultVO {
    private int resultId;
    private int setId;  // 퀴즈 세트 ID 추가
    private int userId;  // 사용자 ID 추가
    private String setName;
    private String setImg;
    private String categoryName;
    private int totalScore;
    private int maxScore;
    private String completedAt;  // 퀴즈 완료 날짜 필드 추가
}
