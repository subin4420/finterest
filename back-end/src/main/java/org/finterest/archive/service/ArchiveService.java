package org.finterest.archive.service;

import org.finterest.archive.dao.ArchiveDAO;
import org.finterest.archive.domain.ArchiveDetailVO;
import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveService {
    private final ArchiveDAO archiveDAO;

    @Autowired
    public ArchiveService(ArchiveDAO archiveDAO) {
        this.archiveDAO = archiveDAO;
    }

    // 모든 자료 조회
    public List<ArchiveDetailVO> selectAllArchive(Integer userId){
        return archiveDAO.selectAllArchive(userId);
    }

    // 특정 ID로 조회
    public ArchiveDetailVO selectArchiveById(int id){
        return archiveDAO.selectArchiveById(id);
    }

    // 특정 카테고리로 조회
//    public List<ArchiveVO> selectArchiveByCategory(int categoryId){
//        return archiveDAO.selectArchiveByCategory(categoryId);
//    }

    // 로그인 여부로 특정 카테고리 필터링
    public List<ArchiveDetailVO> selectArchiveByCategory(Integer userId, Integer categoryId) {
        if (userId != null) {
            return archiveDAO.selectArchiveByCategoryWithFavorites(userId, categoryId);
        } else {
            return archiveDAO.selectArchiveByCategory(categoryId);
        }
    }




    // 텍스트 자료만 조회
    public List<ArchiveDetailVO> selectTextArchive(Integer userId){
        return archiveDAO.selectTextArchive(userId);
    }

    // 영상 자료만 조회
    public List<ArchiveDetailVO> selectVideoArchive(Integer userId){
        return archiveDAO.selectVideoArchive(userId);
    }

    // 학습 진행 상태 조회 (materialId 기준)
    public ProgressVO getProgressForMaterial(int materialId) {
        return archiveDAO.getProgressForMaterial(materialId);
    }
    // 학습 진행 상태 조회 (materialId 기준)
    public ProgressVO getProgressForUserId(Integer UserId) {
        return archiveDAO.getProgressForUserId(UserId);
    }

    // 즐겨찾기한 자료 조회
    public List<ArchiveDetailVO> getArchives(Integer userId, Boolean favorites) {
        if (Boolean.TRUE.equals(favorites)) {
            return archiveDAO.selectFavoritesArchive(userId);
        } else {
            return archiveDAO.selectAllArchive(userId);
        }
    }

    public void insertFavorite(int userId, int materialId){
        archiveDAO.insertFavorite(userId, materialId);
    }

    public void deleteFavorite(int userId, int materialId){
        archiveDAO.deleteFavorite(userId, materialId);
    }

    public List<ProgressDetailVO> selectAllProgress(int userId){
        return archiveDAO.selectAllProgress(userId);
    }

    public List<ProgressDetailVO> selectProgressByStatus(int userId, String status){
        return archiveDAO.selectProgressByStatus(userId, status);
    }

    // 사용자 ID와 자료 ID로 학습 진행 정보 조회
    public ProgressVO getProgressForUserAndMaterial(int userId, int materialId) {
        return archiveDAO.getProgressForMaterialAndUser(userId, materialId);
    }

    // 학습 진행 상태 추가
    public void insertProgress(int userId, int materialId, String status, String startedAt) {
        archiveDAO.insertProgress(userId, materialId, status, startedAt);
    }

    // 학습 진행 상태(incomplete) 추가
    public void insertProgressStatus(int userId, int materialId){
        archiveDAO.insertProgressStatus(userId, materialId);
    }

    // 학습 진행 상태(completed) 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        archiveDAO.insertPoint(userId);
        return archiveDAO.updateProgressStatus(userId, materialId, status);
    }


}
