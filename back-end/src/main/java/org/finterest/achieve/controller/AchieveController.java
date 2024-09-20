package org.finterest.achieve.controller;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.service.AchieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achieve")
public class AchieveController {
    private final AchieveService achieveService;

    @Autowired
    public AchieveController(AchieveService achieveService) {
        System.out.println("AchieveController created");
        this.achieveService = achieveService;
    }

    @GetMapping("/{id}")
    public AchieveVO one(@PathVariable int id) {
        AchieveVO achieveVO = achieveService.one(id);
        System.out.println("---------------->>" + achieveVO);
        return achieveVO;
    }

    @GetMapping(params = "category_id")
    public List<AchieveVO> findByCategoryId(@RequestParam int category_id) {
        System.out.println("AchieveController findByCategoryId" + category_id);
        return achieveService.categoryFilter(category_id);
    }

    @GetMapping(value = "/all")
    public List<AchieveVO> findAll() {
        List<AchieveVO> achieveVOList = achieveService.all();
        System.out.println("------------->>" + achieveVOList);
        return achieveVOList;
    }
}
