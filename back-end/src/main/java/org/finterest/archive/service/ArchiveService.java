package org.finterest.archive.service;

import org.finterest.archive.dao.ArchiveDAO;
import org.finterest.archive.domain.ArchiveVO;
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

    public List<ArchiveVO> selectAllArchive(){
        return archiveDAO.selectAllArchive();
    }

    public ArchiveVO selectArchiveById(int id){
        return archiveDAO.selectArchiveById(id);
    }

    public List<ArchiveVO> selectArchiveByCategory(int categoryId){
        return archiveDAO.selectArchiveByCategory(categoryId);
    }

    public List<ArchiveVO> selectTextArchive(){
        return archiveDAO.selectTextArchive();
    }

    public List<ArchiveVO> selectVideoArchive(){
        return archiveDAO.selectVideoArchive();
    }

    public void insertFavorite(int userId, int materialId){
        archiveDAO.insertFavorite(userId, materialId);
    }

    public void deleteFavorite(int userId, int materialId){
        archiveDAO.deleteFavorite(userId, materialId);
    }

    public List<ProgressVO> selectAllProgress(int userId){
        return archiveDAO.selectAllProgress(userId);
    }

    public List<ProgressVO> selectProgressByStatus(int userId, String status){
        return archiveDAO.selectProgressByStatus(userId, status);
    }

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return archiveDAO.updateProgressStatus(userId, materialId, status);
    }


}
