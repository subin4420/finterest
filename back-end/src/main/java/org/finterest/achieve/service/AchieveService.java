package org.finterest.achieve.service;

import org.finterest.achieve.domain.ProgressVO;
import org.finterest.achieve.dao.AchieveDAO;
import org.finterest.achieve.domain.AchieveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchieveService {
    private final AchieveDAO achieveDAO;

    @Autowired
    public AchieveService(AchieveDAO achieveDAO) {
        this.achieveDAO = achieveDAO;
    }

    public List<AchieveVO> selectAllAchieve(){
        return achieveDAO.selectAllAchieve();
    }

    public AchieveVO selectAchieveById(int id){
        return achieveDAO.selectAchieveById(id);
    }

    public List<AchieveVO> selectAchieveByCategory(int categoryId){
        return achieveDAO.selectAchieveByCategory(categoryId);
    }

    public List<AchieveVO> selectTextAchieve(){
        return achieveDAO.selectTextAchieve();
    }

    public List<AchieveVO> selectVideoAchieve(){
        return achieveDAO.selectVideoAchieve();
    }

    public void insertFavorite(int userId, int materialId){
        achieveDAO.insertFavorite(userId, materialId);
    }

    public void deleteFavorite(int userId, int materialId){
        achieveDAO.deleteFavorite(userId, materialId);
    }

    public List<ProgressVO> selectAllProgress(int userId){
        return achieveDAO.selectAllProgress(userId);
    }

    public List<ProgressVO> selectProgressByStatus(int userId, String status){
        return achieveDAO.selectProgressByStatus(userId, status);
    }

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return achieveDAO.updateProgressStatus(userId, materialId, status);
    }


}
