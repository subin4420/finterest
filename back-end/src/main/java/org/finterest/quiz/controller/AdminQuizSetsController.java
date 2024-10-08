package org.finterest.quiz.controller;

import org.finterest.quiz.service.AdminQuizService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
