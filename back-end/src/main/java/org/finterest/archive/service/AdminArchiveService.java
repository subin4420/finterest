package org.finterest.archive.service;

import org.finterest.archive.dao.AdminArchiveDAO;
import org.finterest.archive.domain.ArchiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminArchiveService {
    private final AdminArchiveDAO adminArchiveDAO;

    @Autowired

    public AdminArchiveService(AdminArchiveDAO adminArchiveDAO) {
        this.adminArchiveDAO = adminArchiveDAO;
    }

    public int selectCategoryIdByName(String categoryName){
        return adminArchiveDAO.selectCategoryIdByName(categoryName);
    }

    // 학습 자료 생성
    public void insertArchive(String title, String description, String content, String link, String categoryName, String materialImg) {
        // 카테고리 이름으로 category_id 가져오기
        int categoryId = adminArchiveDAO.selectCategoryIdByName(categoryName);

        // 학습 자료 삽입
        adminArchiveDAO.insertArchive(categoryId, title, materialImg, link, description, content);
    }

    public List<ArchiveVO> selectAllArchive(){
        return adminArchiveDAO.selectAllArchive();
    }


    public List<ArchiveVO> selectFavoritesByUser(int userId){
        return adminArchiveDAO.selectFavoritesByUser(userId);
    }


    public List<ArchiveVO> selectArchiveByCategoryAndFavorites(String categoryName,
                                                               Boolean favorites){
        return adminArchiveDAO.selectArchiveByCategoryAndFavorites(categoryName, favorites);
    }

    // 학습 자료 업데이트 메서드
    public void updateArchive(int materialId, String categoryName, String title, String materialImg, String link, String description, String content) {
        // 카테고리 이름으로 category_id 조회
        Integer categoryId = adminArchiveDAO.selectCategoryIdByName(categoryName);

        if (categoryId == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryName);
        }

        // 학습 자료 업데이트
        adminArchiveDAO.updateArchive(materialId, categoryName, title, materialImg, link, description, content);
    }

    public void deleteArchive(int materialId){
        adminArchiveDAO.deleteArchive(materialId);
    }
}
