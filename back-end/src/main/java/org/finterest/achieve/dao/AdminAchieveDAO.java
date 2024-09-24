package org.finterest.achieve.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.mapper.AdminAchieveMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminAchieveDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminAchieveDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public int selectCategoryIdByName(String categoryName){
        return sqlSessionTemplate.getMapper(AdminAchieveMapper.class).selectCategoryIdByName(categoryName);
    }

    public void insertAchieve(int categoryId,
                                String title,
                                String materialImg,
                                String link,
                                String description,
                                String content){
        sqlSessionTemplate.getMapper(AdminAchieveMapper.class).insertAchieve(categoryId, title, materialImg, link, description, content);
    }

    public List<AchieveVO> selectAllAchieve(){
        return sqlSessionTemplate.getMapper(AdminAchieveMapper.class).selectAllAchieve();
    }


    public List<AchieveVO> selectFavoritesByUser(int userId){
        return sqlSessionTemplate.getMapper(AdminAchieveMapper.class).selectFavoritesByUser(userId);
    }


    public List<AchieveVO> selectAchieveByCategoryAndFavorites(String categoryName,
                                                        Boolean favorites){
        return sqlSessionTemplate.getMapper(AdminAchieveMapper.class).selectAchieveByCategoryAndFavorites(categoryName, favorites);
    }

    public void updateAchieve(int materialId, String categoryName, String title, String materialImg, String link, String description, String content) {
        sqlSessionTemplate.getMapper(AdminAchieveMapper.class).updateAchieve(materialId, categoryName, title, materialImg, link, description, content);
    }

    public void deleteAchieve(int materialId){
        sqlSessionTemplate.getMapper(AdminAchieveMapper.class).deleteAchieve(materialId);
    }
}
