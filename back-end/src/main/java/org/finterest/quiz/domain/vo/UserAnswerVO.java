package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerVO {
    private int quizId;
    private String question;       // 문제 내용
    private String choice1;        // 선택지 1
    private String choice2;        // 선택지 2
    private String choice3;        // 선택지 3
    private String choice4;        // 선택지 4
    private int selectedChoice;    // 사용자가 선택한 답변
    private int correctChoice;     // 정답
    private boolean isCorrect;     // 정답 여부
}
