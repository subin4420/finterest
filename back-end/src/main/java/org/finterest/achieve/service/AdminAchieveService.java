package org.finterest.achieve.service;

import org.apache.ibatis.annotations.Param;
import org.finterest.achieve.dao.AdminAchieveDAO;
import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.mapper.AdminAchieveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAchieveService {
    private final AdminAchieveDAO adminAchieveDAO;

    @Autowired

    public AdminAchieveService(AdminAchieveDAO adminAchieveDAO) {
        this.adminAchieveDAO = adminAchieveDAO;
    }

    public int selectCategoryIdByName(String categoryName){
        return adminAchieveDAO.selectCategoryIdByName(categoryName);
    }

    // 학습 자료 생성
    public void insertAchieve(String title, String description, String content, String link, String categoryName, String materialImg) {
        // 카테고리 이름으로 category_id 가져오기
        int categoryId = adminAchieveDAO.selectCategoryIdByName(categoryName);

        // 학습 자료 삽입
        adminAchieveDAO.insertAchieve(categoryId, title, materialImg, link, description, content);
    }

    public List<AchieveVO> selectAllAchieve(){
        return adminAchieveDAO.selectAllAchieve();
    }


    public List<AchieveVO> selectFavoritesByUser(int userId){
        return adminAchieveDAO.selectFavoritesByUser(userId);
    }


    public List<AchieveVO> selectAchieveByCategoryAndFavorites(String categoryName,
                                                               Boolean favorites){
        return adminAchieveDAO.selectAchieveByCategoryAndFavorites(categoryName, favorites);
    }

    // 학습 자료 업데이트 메서드
    public void updateAchieve(int materialId, String categoryName, String title, String materialImg, String link, String description, String content) {
        // 카테고리 이름으로 category_id 조회
        Integer categoryId = adminAchieveDAO.selectCategoryIdByName(categoryName);

        if (categoryId == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryName);
        }

        // 학습 자료 업데이트
        adminAchieveDAO.updateAchieve(materialId, categoryName, title, materialImg, link, description, content);
    }

    public void deleteAchieve(int materialId){
        adminAchieveDAO.deleteAchieve(materialId);
    }
}
