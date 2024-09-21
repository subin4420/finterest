package org.finterest.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorrectAnswerDTO {
    private int quizId;
    private int correctChoice;
}
