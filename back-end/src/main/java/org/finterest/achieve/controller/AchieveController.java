package org.finterest.achieve.controller;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.service.AchieveService;
import org.finterest.quiz.domain.QuizSetsVO;
import org.finterest.quiz.service.QuizSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/achieve")
public class AchieveController {
    private final AchieveService achieveService;
    private final QuizSetsService quizSetsService;

    @Autowired
    public AchieveController(AchieveService achieveService, QuizSetsService quizSetsService) {
        System.out.println("AchieveController created");
        this.achieveService = achieveService;
        this.quizSetsService = quizSetsService;
    }

    @GetMapping
    public Map<String, List<AchieveVO>> selectAllAchieve() {
        List<AchieveVO> achieveVOList = achieveService.selectAllAchieve();
        System.out.println("------------->>" + achieveVOList);

        Map<String, List<AchieveVO>> response = new HashMap<>();
        response.put("achieves", achieveVOList);
        return response;
    }

    @GetMapping("/{id}")
    public AchieveVO one(@PathVariable int id) {
        AchieveVO achieveVO = achieveService.selectAchieveById(id);
        System.out.println("---------------->>" + achieveVO);
        return achieveVO;
    }

    @GetMapping(params = "category_id")
    public Map<String, List<AchieveVO>> findByCategoryId(@RequestParam int category_id) {
        List<AchieveVO> achieveVOList = achieveService.selectAchieveByCategory(category_id);
        System.out.println("------------->>" + achieveVOList);

        Map<String, List<AchieveVO>> response = new HashMap<>();
        response.put("achieves", achieveVOList);
        return response;
    }
}
