package org.finterest.point.controller;

import org.finterest.point.domain.PointRulesVO;
import org.finterest.point.service.AdminPointRulesService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/point-rules")
public class AdminPointRulesController {

    private final AdminPointRulesService adminPointRulesService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminPointRulesController(AdminPointRulesService adminPointRulesService, TokenUtil tokenUtil) {
        this.adminPointRulesService = adminPointRulesService;
        this.tokenUtil = tokenUtil;
    }

    // 1. 포인트 정책 전체 조회 API
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPointRules(
            @RequestHeader("Authorization") String adminToken) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        List<PointRulesVO> pointRules = adminPointRulesService.getAllPointRules();

        // 응답 데이터를 Map으로 감싸서 "pointRules"로 리턴
        Map<String, Object> response = new HashMap<>();
        response.put("pointRules", pointRules);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2. 포인트 정책 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addPointRule(
            @RequestHeader("Authorization") String adminToken,
            @RequestBody PointRulesVO pointRule) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("관리자 권한이 없습니다.");
        }

        boolean isAdded = adminPointRulesService.addPointRule(pointRule);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isAdded) {
            return new ResponseEntity<>("포인트 규칙이 성공적으로 추가되었습니다.", headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("포인트 규칙 추가에 실패했습니다.", headers, HttpStatus.BAD_REQUEST);
        }
    }

    // 3. 포인트 정책 수정 API
    @PutMapping("/{ruleId}")
    public ResponseEntity<String> updatePointRule(
            @RequestHeader("Authorization") String adminToken,
            @PathVariable("ruleId") int ruleId,
            @RequestBody PointRulesVO pointRule) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("관리자 권한이 없습니다.");
        }

        pointRule.setRuleId(ruleId);  // Path variable을 VO 객체에 설정
        boolean isUpdated = adminPointRulesService.updatePointRule(pointRule);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isUpdated) {
            return new ResponseEntity<>("포인트 규칙이 성공적으로 수정되었습니다.", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("포인트 규칙 수정에 실패했습니다.", headers, HttpStatus.BAD_REQUEST);
        }
    }

    // 4. 포인트 정책 삭제 API
    @DeleteMapping("/{ruleId}")
    public ResponseEntity<String> deletePointRule(
            @RequestHeader("Authorization") String adminToken,
            @PathVariable("ruleId") int ruleId) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("관리자 권한이 없습니다.");
        }

        boolean isDeleted = adminPointRulesService.deletePointRule(ruleId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isDeleted) {
            return new ResponseEntity<>("포인트 규칙이 성공적으로 삭제되었습니다.", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("포인트 규칙 삭제에 실패했습니다.", headers, HttpStatus.NOT_FOUND);
        }
    }

}
