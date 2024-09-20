package org.finterest.achieve.controller;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.service.AchieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

 @GetMapping
 public List<AchieveVO> all() {
  System.out.println("--------->> " + achieveService.all());
  return achieveService.all();
 }

 @GetMapping("/{id}")
 public AchieveVO one(@PathVariable int id) {
  AchieveVO achieveVO = achieveService.one(id);
  System.out.println("--------->> " + achieveVO);
  return achieveVO;
 }

 // 카테고리별 조회 추가
 @GetMapping(params = "category_id")
 public List<AchieveVO> findByCategoryId(@RequestParam("category_id") int categoryId) {
  System.out.println("Fetching data for category: " + categoryId);
  return achieveService.categoryFilter(categoryId);
 }

 @GetMapping(value = "/all")
 public List<AchieveVO> all1() {
  System.out.println("--------->> " + achieveService.all());
  return achieveService.all();
 }
}
