package org.finterest.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSetQuestionsVO {
    private int setId;  // 퀴즈 세트 ID
    private int quizId; // 퀴즈 ID
}
