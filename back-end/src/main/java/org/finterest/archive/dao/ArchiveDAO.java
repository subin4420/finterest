package org.finterest.archive.dao;

import org.finterest.archive.domain.ArchiveVO;
import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;
import org.finterest.archive.mapper.ArchiveMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArchiveDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ArchiveDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<ArchiveVO> selectAllArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectAllArchive(userId);
    }

    public ArchiveVO selectArchiveById(int id){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectArchiveById(id);
    }

    public List<ArchiveVO> selectArchiveByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectArchiveByCategory(categoryId);
    }

    public List<ArchiveVO> selectTextArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectTextArchive(userId);
    }

    public List<ArchiveVO> selectVideoArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectVideoArchive(userId);
    }

    public ProgressVO getProgressForMaterial(int materialId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).getProgressForMaterial(materialId);
    }


    public void insertFavorite(int userId, int materialId){
        sqlSessionTemplate.getMapper(ArchiveMapper.class).insertFavorite(userId, materialId);
    }

    public void deleteFavorite(int userId, int materialId) {
        sqlSessionTemplate.getMapper(ArchiveMapper.class).deleteFavorite(userId, materialId);
    }

    public List<ProgressDetailVO> selectAllProgress(int userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectAllProgress(userId);
    }

    public List<ProgressDetailVO> selectProgressByStatus(int userId, String status){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectProgressByStatus(userId, status);
    }

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).updateProgressStatus(userId, materialId, status);
    }
    // 특정 학습 자료와 사용자의 학습 진행 상태 조회
    public ProgressVO getProgressForMaterialAndUser(int materialId, int userId) {
        return sqlSessionTemplate.getMapper(ArchiveMapper.class)
                .getProgressForMaterialAndUser(materialId, userId);
    }

    // 학습 진행 상태 추가
    public void insertProgress(int userId, int materialId, String status, String startedAt) {
        sqlSessionTemplate.getMapper(ArchiveMapper.class)
                .insertProgress(userId, materialId, status, startedAt);
    }

}
