package org.finterest.quiz.controller;

import org.finterest.quiz.domain.QuizSetsVO;
import org.finterest.quiz.service.QuizSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz-sets")
public class QuizSetsController {
    private final QuizSetsService quizSetsService;

    @Autowired
    public QuizSetsController(QuizSetsService quizSetsService) {
        System.out.println("QuizSetsController created");
        this.quizSetsService = quizSetsService;
    }

    // 전체 퀴즈 세트 조회 (category_id가 없는 경우)
    @GetMapping
    public Map<String, List<QuizSetsVO>> selectAllQuizSets(){
        List<QuizSetsVO> quizSetsVOList = quizSetsService.selectAllQuizSets();
        System.out.println("------------->>" + quizSetsVOList);

        // quiz_sets 키로 응답을 감싸서 반환
        Map<String, List<QuizSetsVO>> response = new HashMap<>();
        response.put("quiz_sets", quizSetsVOList);
        return response;
    }

    // 카테고리별 퀴즈 세트 조회 (category_id가 있는 경우)
    @GetMapping(params = "category_id")
    public Map<String, List<QuizSetsVO>> selectQuizSets(@RequestParam("category_id") int categoryId){
        List<QuizSetsVO> quizSetsVOList = quizSetsService.selectQuizSetsByCategory(categoryId);
        System.out.println("------------->>" + quizSetsVOList);

        // quiz_sets 키로 응답을 감싸서 반환
        Map<String, List<QuizSetsVO>> response = new HashMap<>();
        response.put("quiz_sets", quizSetsVOList);
        return response;
    }
}
