package org.finterest.quiz.controller;

import org.finterest.quiz.domain.QuizSetDetailsVO;
import org.finterest.quiz.service.QuizSetDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetDetailController {

    private final QuizSetDetailsService quizSetDetailsService;

    @Autowired
    public QuizSetDetailController(QuizSetDetailsService quizSetDetailsService) {
        this.quizSetDetailsService = quizSetDetailsService;
        System.out.println("QuizSetDetailController created");
    }

    // 특정 퀴즈 세트의 문제 목록 조회
    @GetMapping("/{set_id}/questions")
    public QuizSetDetailsVO getQuizSetDetails(@PathVariable("set_id") int setId) {
        QuizSetDetailsVO quizSetDetailsVO = quizSetDetailsService.getQuizSetDetails(setId);
        System.out.println("Retrieved QuizSetDetails: " + quizSetDetailsVO);
        return quizSetDetailsVO;
    }
}
