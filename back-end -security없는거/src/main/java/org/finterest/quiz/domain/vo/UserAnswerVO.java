package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerVO {
    private int quizId;
    private int selectedChoice;
    private int correctChoice;
    private boolean isCorrect;
}
