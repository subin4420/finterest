package org.finterest.quiz.controller;

import org.finterest.quiz.domain.QuizSetsVO;
import org.finterest.quiz.service.QuizSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetsController {
    private final QuizSetsService quizService;

    @Autowired
    public QuizSetsController(QuizSetsService quizService) {
        System.out.println("QuizSetsController created");
        this.quizService = quizService;
    }

    @GetMapping()
    public List<QuizSetsVO> findAll(){
        List<QuizSetsVO> quizVOList = quizService.quizSetsAll();
        System.out.println("------------->>" + quizVOList);
        return quizVOList;
    }
}
