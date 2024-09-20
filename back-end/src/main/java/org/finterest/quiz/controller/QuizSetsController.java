package org.finterest.quiz.controller;

import org.finterest.quiz.domain.QuizVO;
import org.finterest.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetsController {
    private final QuizService quizService;

    @Autowired
    public QuizSetsController(QuizService quizService) {
        System.out.println("QuizSetsController created");
        this.quizService = quizService;
    }

    @GetMapping()
    public List<QuizVO> findAll(){
        List<QuizVO> quizVOList = quizService.quizSetsAll();
        System.out.println("------------->>" + quizVOList);
        return quizVOList;
    }
}
