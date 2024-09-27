package org.finterest.achieve.controller;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.service.AdminAchieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/achieve")
public class AdminAchieveController {
    private final AdminAchieveService adminAchieveService;

    @Autowired
    public AdminAchieveController(AdminAchieveService adminAchieveService) {
        System.out.println("AdminAchieveController created");
        this.adminAchieveService = adminAchieveService;
    }

    // 학습 자료 생성
    @PostMapping
    public ResponseEntity<Map<String, String>> createAchieve(
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {
        // Request Body에서 데이터 가져오기
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String link = requestBody.get("link");
        String categoryName = requestBody.get("categoryName");
        String materialImg = requestBody.get("materialImg");

        // 학습 자료 생성
        adminAchieveService.insertAchieve(title, description, content, link, categoryName, materialImg);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "새로운 학습 자료가 생성되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 전체 학습 자료 목록 조회 (카테고리 및 즐겨찾기 및 userId 필터링 가능)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAchieveList(
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "favorites", required = false) Boolean favorites,
            @RequestParam(value = "userId", required = false) Integer userId,  // userId 필터 추가
            @RequestHeader("Authorization") String token) {

        List<AchieveVO> materials;

        // 즐겨찾기 필터가 적용된 경우
        if (favorites != null && favorites && userId != null) {
            // 특정 사용자의 즐겨찾기 자료 조회
            materials = adminAchieveService.selectFavoritesByUser(userId);
        } else {
            // 카테고리 및 일반 자료 조회
            materials = adminAchieveService.selectAchieveByCategoryAndFavorites(categoryName, favorites);
        }

        // 응답 구성
        Map<String, Object> response = new HashMap<>();
        response.put("materials", materials);
        return ResponseEntity.ok(response);
    }

    // 학습 자료 수정
    @PutMapping("/{materialId}")
    public ResponseEntity<Map<String, String>> updateAchieve(
            @PathVariable int materialId,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {
        // Request Body에서 데이터 가져오기
        String title = requestBody.get("title");
        String description = requestBody.get("description");
        String content = requestBody.get("content");
        String link = requestBody.get("link");
        String categoryName = requestBody.get("categoryName");  // 카테고리명으로 수정
        String materialImg = requestBody.get("materialImg");

        // 학습 자료 수정
        adminAchieveService.updateAchieve(materialId, categoryName, title, materialImg, link, description, content);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "학습 자료가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 학습 자료 삭제
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Map<String, String>> deleteAchieve(
            @PathVariable int materialId,
            @RequestHeader("Authorization") String token) {

        // 학습 자료 삭제
        adminAchieveService.deleteAchieve(materialId);

        // 성공 응답
        Map<String, String> response = new HashMap<>();
        response.put("message", "학습 자료가 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }

}
