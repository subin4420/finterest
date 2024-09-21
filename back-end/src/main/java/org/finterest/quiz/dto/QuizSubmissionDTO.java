package org.finterest.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizSubmissionDTO {
    private int userId;
    private List<AnswerSubmissionDTO> answers;
}
