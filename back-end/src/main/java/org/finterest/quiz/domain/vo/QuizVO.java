package org.finterest.quiz.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizVO {
    private int quizId;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
}
