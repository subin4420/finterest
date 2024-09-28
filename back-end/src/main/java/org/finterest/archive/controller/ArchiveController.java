package org.finterest.archive.controller;

import org.finterest.archive.domain.ArchiveVO;

import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;
import org.finterest.archive.service.ArchiveService;
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

    @Autowired
    public ArchiveController(ArchiveService archiveService) {
        System.out.println("ArchiveController created");
        this.archiveService = archiveService;
    }

    // 모든 자료 조회
    @GetMapping
    public Map<String, List<ArchiveVO>> selectAllArchive(
            @RequestParam(value = "type", required = false) String type,
            @RequestHeader(value = "Authorization", required = false) String authToken) {

        List<ArchiveVO> archiveVOList;

        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;  // 테스트용 userId 하드코딩


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

        boolean isAuthenticated = (authToken != null && !authToken.isEmpty()); // JWT 토큰이 있는지 확인

        Map<String, List<ArchiveVO>> response = new HashMap<>();
        response.put("archives", applyProgressData(archiveVOList, isAuthenticated)); // 학습 진행 상태 추가

        return response;
    }

    // 특정 ID로 자료 조회
    @GetMapping("/{id}")
    public ArchiveVO one(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String authToken) {
        ArchiveVO archiveVO = archiveService.selectArchiveById(id);

        boolean isAuthenticated = (authToken != null && !authToken.isEmpty());
        return applyProgressDataToSingle(archiveVO, isAuthenticated); // 학습 진행 상태 추가
    }

    // 특정 카테고리 ID로 자료 조회
    @GetMapping(params = "categoryId")
    public Map<String, List<ArchiveVO>> findByCategoryId(
            @RequestParam int categoryId,
            @RequestHeader(value = "Authorization", required = false) String authToken) {

        List<ArchiveVO> archiveVOList = archiveService.selectArchiveByCategory(categoryId);

        boolean isAuthenticated = (authToken != null && !authToken.isEmpty());

        Map<String, List<ArchiveVO>> response = new HashMap<>();
        response.put("archives", applyProgressData(archiveVOList, isAuthenticated));

        return response;
    }

    // JWT 토큰이 있는 경우 학습 진행 데이터를 추가하는 메서드
    private List<ArchiveVO> applyProgressData(List<ArchiveVO> archives, boolean isAuthenticated) {
        if (isAuthenticated) {
            for (ArchiveVO archive : archives) {
                // 여기서 학습 진행 상태(status, startedAt, completedAt)를 추가할 수 있습니다.
                ProgressVO progress = archiveService.getProgressForMaterial(archive.getMaterialId());
                if (progress != null) {
                    archive.setStatus(progress.getStatus());
                    archive.setStartedAt(progress.getStartedAt());
                    archive.setCompletedAt(progress.getCompletedAt());
                }
            }
        } else {
            for (ArchiveVO archive : archives) {
                // 토큰이 없는 경우 null로 설정
                archive.setStatus(null);
                archive.setStartedAt(null);
                archive.setCompletedAt(null);
            }
        }
        return archives;
    }

    // JWT 토큰이 있는 경우 학습 진행 데이터를 추가하는 메서드 (단일 자료용)
    private ArchiveVO applyProgressDataToSingle(ArchiveVO archive, boolean isAuthenticated) {
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
        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;  // 테스트용 userId 하드코딩
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
        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;     // 테스트용
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

        //int userId = getUserIdFromToken(token);  // 토큰에서 사용자 ID를 추출하는 메서드
        int userId = 1;     // 테스트용
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
        int affectedRows = archiveService.updateProgressStatus(userId, materialId, status);

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
