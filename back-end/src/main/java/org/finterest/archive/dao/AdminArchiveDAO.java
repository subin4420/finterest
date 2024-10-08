package org.finterest.archive.dao;


import org.finterest.archive.domain.ArchiveDetailVO;
import org.finterest.archive.domain.ArchiveVO;
import org.finterest.archive.mapper.AdminArchiveMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminArchiveDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminArchiveDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public int selectCategoryIdByName(String categoryName){
        return sqlSessionTemplate.getMapper(AdminArchiveMapper.class).selectCategoryIdByName(categoryName);
    }

    public void insertArchive(int categoryId,
                              String title,
                              String materialImg,
                              String link,
                              String description,
                              String content){
        sqlSessionTemplate.getMapper(AdminArchiveMapper.class).insertArchive(categoryId, title, materialImg, link, description, content);
    }

    public List<ArchiveVO> selectAllArchive(){
        return sqlSessionTemplate.getMapper(AdminArchiveMapper.class).selectAllArchive();
    }


    public List<ArchiveVO> selectFavoritesByUser(int userId){
        return sqlSessionTemplate.getMapper(AdminArchiveMapper.class).selectFavoritesByUser(userId);
    }


    public List<ArchiveVO> selectArchiveByCategoryAndFavorites(String categoryName,
                                                                     Boolean favorites){
        return sqlSessionTemplate.getMapper(AdminArchiveMapper.class).selectArchiveByCategoryAndFavorites(categoryName, favorites);
    }

    public void updateArchive(int materialId, String categoryName, String title, String materialImg, String link, String description, String content) {
        sqlSessionTemplate.getMapper(AdminArchiveMapper.class).updateArchive(materialId, categoryName, title, materialImg, link, description, content);
    }

    public void deleteArchive(int materialId){
        sqlSessionTemplate.getMapper(AdminArchiveMapper.class).deleteArchive(materialId);
    }

    public void deleteProgress(int materialId){
        sqlSessionTemplate.getMapper(AdminArchiveMapper.class).deleteProgress(materialId);
    }

    public void deleteFavorites(int materialId){
        sqlSessionTemplate.getMapper(AdminArchiveMapper.class).deleteFavorites(materialId);
    }
}