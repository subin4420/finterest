package org.finterest.quiz.controller;

import org.finterest.quiz.domain.vo.QuizSetsVO;
import org.finterest.quiz.service.AdminQuizService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/quizSets")
public class AdminQuizSetsController {
    private final AdminQuizService adminQuizService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminQuizSetsController(AdminQuizService adminQuizService, TokenUtil tokenUtil) {
        this.adminQuizService = adminQuizService;
        this.tokenUtil = tokenUtil;
    }

    // 1. 퀴즈 세트 생성
    @PostMapping
    public ResponseEntity<?> createQuizSet(
            @RequestHeader("Authorization") String token,
            @RequestBody QuizSetsVO quizSetsVO) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 퀴즈 세트 생성 처리
        try {
            adminQuizService.createQuizSet(quizSetsVO, quizSetsVO.getQuestions());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "퀴즈 세트가 성공적으로 생성되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "퀴즈 세트 생성 중 오류가 발생했습니다."));
        }
    }


    // 2. 퀴즈 세트 조회
    @GetMapping
    public ResponseEntity<?> getQuizSets(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "categoryId", required = false) Integer categoryId) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 퀴즈 세트 조회
        List<QuizSetsVO> quizSets = adminQuizService.getAllQuizSets(categoryId);
        return ResponseEntity.ok(Map.of("quizSets", quizSets));
    }

    // 3. 퀴즈 세트 수정
    @PutMapping("/{setId}")
    public ResponseEntity<?> updateQuizSet(
            @RequestHeader("Authorization") String token,
            @PathVariable("setId") int setId,
            @RequestBody QuizSetsVO quizSetsVO) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 퀴즈 세트 수정 처리
        try {
            quizSetsVO.setSetId(setId); // 경로 파라미터로 받은 setId 설정
            adminQuizService.updateQuizSet(quizSetsVO, quizSetsVO.getQuestions());
            return ResponseEntity.ok(Map.of("message", "퀴즈 세트가 성공적으로 수정되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "퀴즈 세트 수정 중 오류가 발생했습니다."));
        }
    }


    // 4. 퀴즈 세트 삭제
    @DeleteMapping("/{setId}")
    public ResponseEntity<?> deleteQuizSet(
            @RequestHeader("Authorization") String token,
            @PathVariable("setId") int setId) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        adminQuizService.deleteQuizSet(setId);
        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "퀴즈 세트가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}
