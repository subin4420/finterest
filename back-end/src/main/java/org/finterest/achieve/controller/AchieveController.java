package org.finterest.achieve.controller;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.domain.ProgressVO;
import org.finterest.achieve.service.AchieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/achieve")
public class AchieveController {
    private final AchieveService achieveService;

    @Autowired
    public AchieveController(AchieveService achieveService) {
        System.out.println("AchieveController created");
        this.achieveService = achieveService;
    }

    // 모든 자료 조회
    @GetMapping
    public Map<String, List<AchieveVO>> selectAllAchieve(@RequestParam(value = "type", required = false) String type) {
        List<AchieveVO> achieveVOList;

        if (type != null) {
            if (type.equals("text")) {
                achieveVOList = achieveService.selectTextAchieve(); // 텍스트 자료만
            } else if (type.equals("video")) {
                achieveVOList = achieveService.selectVideoAchieve(); // 영상 자료만
            } else {
                throw new IllegalArgumentException("Invalid type: " + type);
            }
        } else {
            achieveVOList = achieveService.selectAllAchieve(); // 모든 자료
        }

        Map<String, List<AchieveVO>> response = new HashMap<>();
        response.put("achieves", achieveVOList);
        return response;
    }

    // 특정 ID로 자료 조회
    @GetMapping("/{id}")
    public AchieveVO one(@PathVariable int id) {
        AchieveVO achieveVO = achieveService.selectAchieveById(id);
        System.out.println("---------------->>" + achieveVO);
        return achieveVO;
    }

    // 특정 카테고리 ID로 자료 조회
    @GetMapping(params = "categoryId")
    public Map<String, List<AchieveVO>> findByCategoryId(@RequestParam int categoryId) {
        List<AchieveVO> achieveVOList = achieveService.selectAchieveByCategory(categoryId);
        System.out.println("------------->>" + achieveVOList);

        Map<String, List<AchieveVO>> response = new HashMap<>();
        response.put("achieves", achieveVOList);
        return response;
    }

    // 학습 자료 즐겨찾기 추가
    @PostMapping("/{materialId}/favorite")
    public Map<String, String> addFavorite(@PathVariable int materialId, @RequestHeader("Authorization") String token) {
        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;  // 테스트용 userId 하드코딩
        Map<String, String> response = new HashMap<>();

        try {
            achieveService.insertFavorite(userId, materialId);
            response.put("message", "아카이브에 즐겨찾기가 추가되었습니다.");
        } catch (DuplicateKeyException e) {
            response.put("message", "이미 해당 학습 자료가 즐겨찾기에 추가되어 있습니다.");
        }

        return response;
    }


    // 학습 자료 즐겨찾기 삭제
    @DeleteMapping("/{materialId}/favorite")
    public Map<String, String> removeFavorite(@PathVariable int materialId, @RequestHeader("Authorization") String token) {
        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;     // 테스트용
        achieveService.deleteFavorite(userId, materialId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "아카이브에 즐겨찾기가 삭제되었습니다.");
        return response;
    }


    // 학습 진행 상태 조회
    @GetMapping("/progress")
    public Map<String, List<ProgressVO>> getProgress(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "status", required = false) String status) {

        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;     // 테스트용
        List<ProgressVO> progressList;

        if (status == null) {
            progressList = achieveService.selectAllProgress(userId);
        } else {
            progressList = achieveService.selectProgressByStatus(userId, status);
        }

        Map<String, List<ProgressVO>> response = new HashMap<>();
        response.put("progress", progressList);
        return response;
    }

    // 학습 진행 상태 업데이트
    @PostMapping("/{materialId}/progress")
    public Map<String, String> updateProgress(
            @PathVariable int materialId,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {

        //int userId = getUserIdFromToken(token); // 토큰에서 사용자 ID 추출
        int userId = 2;

        String status = requestBody.get("status");
        if (status == null || (!status.equals("completed") && !status.equals("incomplete"))) {
            throw new IllegalArgumentException("Invalid status value");
        }

        // 학습 진행 상태 업데이트
        int affectedRows = achieveService.updateProgressStatus(userId, materialId, status);

        Map<String, String> response = new HashMap<>();
        if (affectedRows > 0) {
            response.put("message", status.equals("completed") ? "학습이 완료되었습니다." : "학습 상태가 미완료로 변경되었습니다.");
        } else {
            response.put("message", "업데이트에 실패했습니다.");
        }
        return response;
    }

    // 토큰에서 사용자 ID를 추출하는 예시 메서드
    private int getUserIdFromToken(String token) {
        // 실제 토큰에서 사용자 ID를 추출하는 로직 필요
        return 1; // 테스트용으로 사용자 ID 1을 반환
    }
}
