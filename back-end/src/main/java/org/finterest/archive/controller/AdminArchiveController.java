package org.finterest.archive.controller;

import org.finterest.archive.domain.ArchiveDetailVO;
import org.finterest.archive.domain.ArchiveVO;
import org.finterest.archive.service.AdminArchiveService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/archive")
public class AdminArchiveController {
    private final AdminArchiveService adminArchiveService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminArchiveController(AdminArchiveService adminArchiveService, TokenUtil tokenUtil) {
        System.out.println("AdminArchiveController created");
        this.adminArchiveService = adminArchiveService;
        this.tokenUtil = tokenUtil;
    }

    // 학습 자료 생성
    @PostMapping
    public ResponseEntity<Map<String, String>> createArchive(
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {

        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // Request Body에서 데이터 가져오기
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String link = requestBody.get("link");
        String categoryName = requestBody.get("categoryName");
        String materialImg = requestBody.get("materialImg");

        // 학습 자료 생성
        adminArchiveService.insertArchive(title, description, content, link, categoryName, materialImg);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "새로운 학습 자료가 생성되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 전체 학습 자료 목록 조회 (카테고리 및 즐겨찾기 및 userId 필터링 가능)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getArchiveList(
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "favorites", required = false) Boolean favorites,
            @RequestParam(value = "userId", required = false) Integer userId,  // userId 필터 추가
            @RequestHeader("Authorization") String token) {

        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        List<ArchiveVO> materials;

        // 즐겨찾기 필터가 적용된 경우
        if (favorites != null && favorites && userId != null) {
            // 특정 사용자의 즐겨찾기 자료 조회
            materials = adminArchiveService.selectFavoritesByUser(userId);
        }
        // 카테고리 또는 일반 자료 조회
        else if (categoryName != null || favorites != null) {
            materials = adminArchiveService.selectArchiveByCategoryAndFavorites(categoryName, favorites);
        }
        // 전체 조회
        else {
            // 필터 없이 모든 자료 조회
            materials = adminArchiveService.selectAllArchive();
        }

        // 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("archives", materials);
        return ResponseEntity.ok(response);
    }

    // 학습 자료 수정
    @PutMapping("/{materialId}")
    public ResponseEntity<Map<String, String>> updateArchive(
            @PathVariable int materialId,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {

        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }

        // Request Body에서 데이터 가져오기
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String link = requestBody.get("link");
        String categoryName = requestBody.get("categoryName");  // 카테고리명으로 수정
        String materialImg = requestBody.get("materialImg");

        // 학습 자료 수정
        adminArchiveService.updateArchive(materialId, categoryName, title, materialImg, link, description, content);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "학습 자료가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 학습 자료 삭제
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Map<String, String>> deleteArchive(
            @PathVariable int materialId,
            @RequestHeader("Authorization") String token) {

        if (!tokenUtil.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "관리자 권한이 없습니다."));
        }


        // 학습 자료 삭제
        adminArchiveService.deleteArchive(materialId);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "학습 자료가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }

}
