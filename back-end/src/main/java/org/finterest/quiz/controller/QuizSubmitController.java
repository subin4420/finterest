package org.finterest.quiz.controller;

import org.finterest.quiz.dto.QuizResultDTO;
import org.finterest.quiz.dto.QuizSubmissionDTO;
import org.finterest.quiz.service.QuizSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSubmitController {
    private final QuizSubmitService quizSubmitService;

    @Autowired
    public QuizSubmitController(QuizSubmitService quizSubmitService) {
        this.quizSubmitService = quizSubmitService;
    }

    @PostMapping("/{setId}/submit")
    public ResponseEntity<QuizResultDTO> submitQuizSet(
            @PathVariable int setId,
            @RequestBody QuizSubmissionDTO submissionDTO) {
        // Debugging: 출력하여 전달된 값을 확인
        System.out.println("User ID: " + submissionDTO.getUserId());
        submissionDTO.getAnswers().forEach(answer ->
                System.out.println("Quiz ID: " + answer.getQuizId() + ", Selected Choice: " + answer.getSelectedChoice())
        );

        QuizResultDTO result = quizSubmitService.submitQuizSet(setId, submissionDTO.getUserId(), submissionDTO.getAnswers());
        return ResponseEntity.ok(result);
    }
}

