package org.finterest.quiz.controller;

import org.finterest.quiz.domain.vo.QuizVO;
import org.finterest.quiz.service.AdminQuizService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/quizzes")
public class AdminQuizController {

    private final AdminQuizService adminQuizService;
    private final TokenUtil tokenUtil; // 토큰 유틸리티 클래스 주입

    @Autowired
    public AdminQuizController(AdminQuizService adminQuizService, TokenUtil tokenUtil) {
        this.adminQuizService = adminQuizService;
        this.tokenUtil = tokenUtil;
    }


    // 5. 모든 퀴즈 문제 조회 또는 카테고리별 퀴즈 문제 조회
    @GetMapping
    public ResponseEntity<?> getAllQuizzes(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "categoryId", required = false) Integer categoryId) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // 응답 구성
        Map<String, Object> response = new HashMap<>();

        if (categoryId != null) {
            response.put("quizzes", adminQuizService.getQuizzesByCategoryId(categoryId));
            return ResponseEntity.ok(response);
        } else {
            response.put("quizzes", adminQuizService.getAllQuizzes());
            return ResponseEntity.ok(response);
        }
    }

    // 6. 새로운 퀴즈 문제 생성
    @PostMapping
    public ResponseEntity<?> createQuiz(
            @RequestHeader("Authorization") String token,
            @RequestBody QuizVO quizVO) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        adminQuizService.createQuiz(quizVO);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "새로운 퀴즈가 생성되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 7. 특정 퀴즈 문제 수정
    @PutMapping("/{quizId}")
    public ResponseEntity<?> updateQuiz(
            @RequestHeader("Authorization") String token,
            @PathVariable("quizId") int quizId,
            @RequestBody QuizVO quizVO) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        quizVO.setQuizId(quizId);
        adminQuizService.updateQuiz(quizVO);
        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "퀴즈가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 8. 특정 퀴즈 문제 삭제
    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> deleteQuiz(
            @RequestHeader("Authorization") String token,
            @PathVariable("quizId") int quizId) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        adminQuizService.deleteQuiz(quizId);
        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "퀴즈가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}
