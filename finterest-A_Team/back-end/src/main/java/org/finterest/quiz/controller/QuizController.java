package org.finterest.quiz.controller;

import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.service.QuizSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes/{quizId}")
public class QuizController {
    private final QuizSetsService quizSetsService;

    @Autowired
    public QuizController(QuizSetsService quizSetsService) {
        this.quizSetsService = quizSetsService;
    }

    @GetMapping
    public QuizVO getQuiz(@PathVariable int quizId) {
        return quizSetsService.selectQuizById(quizId);
    }
}
