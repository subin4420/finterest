package org.finterest.archive.service;

import org.finterest.archive.dao.ArchiveDAO;
import org.finterest.archive.domain.ArchiveVO;
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
    public List<ArchiveVO> selectAllArchive(Integer userId){
        return archiveDAO.selectAllArchive(userId);
    }

    // 특정 ID로 조회
    public ArchiveVO selectArchiveById(int id){
        return archiveDAO.selectArchiveById(id);
    }

    // 특정 카테고리로 조회
    public List<ArchiveVO> selectArchiveByCategory(int categoryId){
        return archiveDAO.selectArchiveByCategory(categoryId);
    }

    // 즐겨찾기된 자료만 조회
    public List<ArchiveVO> selectFavoriteArchive(int userId){
        return archiveDAO.selectFavoriteArchive(userId);
    }

    // 텍스트 자료만 조회
    public List<ArchiveVO> selectTextArchive(Integer userId){
        return archiveDAO.selectTextArchive(userId);
    }

    // 영상 자료만 조회
    public List<ArchiveVO> selectVideoArchive(Integer userId){
        return archiveDAO.selectVideoArchive(userId);
    }

    // 학습 진행 상태 조회 (materialId 기준)
    public ProgressVO getProgressForMaterial(int materialId) {
        return archiveDAO.getProgressForMaterial(materialId);
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

    public void insertProgressStatus(int userId, int materialId){
        archiveDAO.insertProgressStatus(userId, materialId);
    }

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return archiveDAO.updateProgressStatus(userId, materialId, status);
    }


}
