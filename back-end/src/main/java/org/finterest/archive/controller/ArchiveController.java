package org.finterest.archive.controller;

import org.finterest.archive.domain.ArchiveDetailVO;

import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;
import org.finterest.archive.service.ArchiveService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/archive")
public class ArchiveController {
    private final ArchiveService archiveService;
    private final TokenUtil tokenUtil;
    @Autowired
    public ArchiveController(ArchiveService archiveService, TokenUtil tokenUtil) {
        System.out.println("ArchiveController created");
        this.archiveService = archiveService;
        this.tokenUtil = tokenUtil;

    }


    // 전체 자료 조회
    @GetMapping
    public Map<String, List<ArchiveDetailVO>> selectAllArchive(
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "favorites", required = false) Boolean favorites,  // 즐겨찾기 여부 추가
            @RequestParam(value = "categoryId", required = false) Integer categoryId,  // 카테고리 ID 추가
            @RequestHeader(value = "Authorization", required = false) String authToken) {

        List<ArchiveDetailVO> archiveVOList;
        Integer userId = null; // 토큰이 없는 경우를 고려하여 null로 설정

        if (authToken != null && !authToken.isEmpty()) {
            // 토큰이 있는 경우에만 사용자 ID를 추출
            System.out.println("@@@@@@@@@@@토큰 있는 사용자의 아카이브 조회@@@@@@@@@@@@");
            userId = tokenUtil.getUserIdFromToken(authToken);
        }
        System.out.println("token: " + authToken);
        System.out.println("UserID: " + userId);

        // 즐겨찾기 여부를 확인하고, 즐겨찾기 자료 조회
        if (favorites != null && favorites) {
            if (userId == null) {
                throw new IllegalArgumentException("로그인 후 즐겨찾기 자료를 조회할 수 있습니다.");
            }
            System.out.println("즐겨찾기한 자료만 조회");
            archiveVOList = archiveService.getArchives(userId, favorites); // 즐겨찾기 자료만 조회
        } else if (categoryId != null) {
            // 카테고리 ID로 자료 조회
            System.out.println("카테고리 ID로 자료 조회");
            archiveVOList = archiveService.selectArchiveByCategory(userId, categoryId);;
        } else {
            // 자료 유형별 조회
            if (type != null) {
                if (type.equals("text")) {
                    archiveVOList = archiveService.selectTextArchive(userId); // 텍스트 자료만
                } else if (type.equals("video")) {
                    archiveVOList = archiveService.selectVideoArchive(userId); // 영상 자료만
                } else {
                    throw new IllegalArgumentException("Invalid type: " + type);
                }
            } else {
                archiveVOList = archiveService.selectAllArchive(userId); // 모든 자료
            }
        }

        boolean isAuthenticated = (userId != null); // 사용자 ID가 있을 때만 인증된 상태로 간주

        Map<String, List<ArchiveDetailVO>> response = new HashMap<>();
        response.put("archives", applyProgressData(archiveVOList, isAuthenticated, userId)); // 학습 진행 상태 추가

        return response;
    }


    // 특정 ID로 자료 조회
    @GetMapping("/{id}")
    public ArchiveDetailVO one(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String authToken) {
        ArchiveDetailVO archiveVO = archiveService.selectArchiveById(id);
        boolean isAuthenticated = (authToken != null && !authToken.isEmpty());
        return applyProgressDataToSingle(archiveVO, isAuthenticated); // 학습 진행 상태 추가
    }


    // JWT 토큰이 있는 경우 학습 진행 데이터를 추가하는 메서드
    private List<ArchiveDetailVO> applyProgressData(List<ArchiveDetailVO> archives, boolean isAuthenticated, Integer userId) {
        if (isAuthenticated && userId != null) {
            for (ArchiveDetailVO archive : archives) {
                // 학습 자료 ID와 사용자 ID로 학습 진행 정보를 조회
                ProgressVO progress = archiveService.getProgressForUserAndMaterial(userId, archive.getMaterialId());
                if (progress != null) {
                    archive.setStatus(progress.getStatus());
                    archive.setStartedAt(progress.getStartedAt());
                    archive.setCompletedAt(progress.getCompletedAt());
                }
            }
        } else {
            for (ArchiveDetailVO archive : archives) {
                // 토큰이 없는 경우 null로 설정
                archive.setStatus(null);
                archive.setStartedAt(null);
                archive.setCompletedAt(null);
            }
        }
        return archives;
    }

    // JWT 토큰이 있는 경우 학습 진행 데이터를 추가하는 메서드 (단일 자료용)
    private ArchiveDetailVO applyProgressDataToSingle(ArchiveDetailVO archive, boolean isAuthenticated) {
        if (isAuthenticated) {
            ProgressVO progress = archiveService.getProgressForMaterial(archive.getMaterialId());
            if (progress != null) {
                archive.setStatus(progress.getStatus());
                archive.setStartedAt(progress.getStartedAt());
                archive.setCompletedAt(progress.getCompletedAt());
            }
        } else {
            archive.setStatus(null);
            archive.setStartedAt(null);
            archive.setCompletedAt(null);
        }
        return archive;
    }



    // 학습 자료 즐겨찾기 추가
    @PostMapping("/{materialId}/favorite")
    public Map<String, String> addFavorite(
            @PathVariable int materialId,
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);

        Map<String, String> response = new HashMap<>();

        try {
            archiveService.insertFavorite(userId, materialId);
            response.put("message", "아카이브에 즐겨찾기가 추가되었습니다.");
        } catch (DuplicateKeyException e) {
            response.put("message", "이미 해당 학습 자료가 즐겨찾기에 추가되어 있습니다.");
        }

        return response;
    }


    // 학습 자료 즐겨찾기 삭제
    @DeleteMapping("/{materialId}/favorite")
    public Map<String, String> removeFavorite(
            @PathVariable int materialId,
            @RequestHeader("Authorization") String token) {
        int userId = tokenUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        archiveService.deleteFavorite(userId, materialId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "아카이브에 즐겨찾기가 삭제되었습니다.");
        return response;
    }


    // 학습 진행 상태 조회
    @GetMapping("/progress")
    public Map<String, List<ProgressDetailVO>> getProgress(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "status", required = false) String status) {

        int userId = tokenUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        List<ProgressDetailVO> progressList;

        if (status == null) {
            progressList = archiveService.selectAllProgress(userId);
        } else {
            progressList = archiveService.selectProgressByStatus(userId, status);
        }

        Map<String, List<ProgressDetailVO>> response = new HashMap<>();
        response.put("progress", progressList);
        return response;
    }

    // 학습 진행 상태 추가 (incomplete 상태로 학습 시작)
    @PostMapping("/{materialId}/progress")
    public Map<String, String> insertProgress(
            @PathVariable int materialId,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드


        String status = requestBody.get("status");
        if (status == null || !status.equals("incomplete")) {
            throw new IllegalArgumentException("Invalid status value. Only 'incomplete' is allowed.");
        }

        // 학습 진행 상태(incomplete) 추가 로직
        archiveService.insertProgressStatus(userId, materialId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "학습 진행 상태가 추가되었습니다.");
        return response;
    }


    // 학습 진행 상태(completed) 업데이트
    @PutMapping("/{materialId}/progress")
    public Map<String, String> updateProgress(
            @PathVariable int materialId,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader("Authorization") String token) {

        int userId = tokenUtil.getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드

        String status = requestBody.get("status");
        if (status == null || (!status.equals("completed") && !status.equals("incomplete"))) {
            throw new IllegalArgumentException("Invalid status value");
        }

        // 학습 진행 상태 업데이트
        int affectedRows = archiveService.updateProgressStatus(userId, materialId, status);

        Map<String, String> response = new HashMap<>();
        if (affectedRows > 0) {
            response.put("message", status.equals("completed") ? "학습이 완료되었습니다." : "학습 상태가 미완료로 변경되었습니다.");
        } else {
            response.put("message", "업데이트에 실패했습니다.");
        }
        return response;
    }

}
