package org.finterest.achieve.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.achieve.domain.ProgressVO;
import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.mapper.AchieveMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AchieveDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AchieveDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<AchieveVO> selectAllAchieve(){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAllAchieve();
    }

    public AchieveVO selectAchieveById(int id){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAchieveById(id);
    }

    public List<AchieveVO> selectAchieveByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAchieveByCategory(categoryId);
    }

    public List<AchieveVO> selectTextAchieve(){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectTextAchieve();
    }

    public List<AchieveVO> selectVideoAchieve(){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectVideoAchieve();
    }

    public void insertFavorite(int userId, int materialId){
        sqlSessionTemplate.getMapper(AchieveMapper.class).insertFavorite(userId, materialId);
    }

    public void deleteFavorite(int userId, int materialId) {
        sqlSessionTemplate.getMapper(AchieveMapper.class).deleteFavorite(userId, materialId);
    }

    public List<ProgressVO> selectAllProgress(int userId){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAllProgress(userId);
    }

    public List<ProgressVO> selectProgressByStatus(int userId, String status){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectProgressByStatus(userId, status);
    }

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return sqlSessionTemplate.getMapper(AchieveMapper.class).updateProgressStatus(userId, materialId, status);
    }

}
