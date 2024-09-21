package org.finterest.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDTO {
    private int resultId;
    private int totalScore;
    private int maxScore;
    private List<CorrectAnswerDTO> correctAnswers;
    private List<WrongAnswerDTO> wrongAnswers;
}
