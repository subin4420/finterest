package org.finterest.archive.dao;

import org.finterest.archive.domain.ArchiveDetailVO;
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

    public List<ArchiveDetailVO> selectAllArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectAllArchive(userId);
    }

    public ArchiveDetailVO selectArchiveById(int id){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectArchiveById(id);
    }

    // 로그인 안 한 사용자의 카테고리 필터링
    public List<ArchiveDetailVO> selectArchiveByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectArchiveByCategory(categoryId);
    }

    // 로그인한 사용자의 카테고리 필터링
    public List<ArchiveDetailVO> selectArchiveByCategoryWithFavorites(Integer userId, int categoryId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectArchiveByCategoryWithFavorites(userId, categoryId);
    }

    public List<ArchiveDetailVO> selectTextArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectTextArchive(userId);
    }

    public List<ArchiveDetailVO> selectVideoArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectVideoArchive(userId);
    }

    public ProgressVO getProgressForMaterial(int materialId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).getProgressForMaterial(materialId);
    }
    public ProgressVO getProgressForUserId(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).getProgressForUserId(userId);
    }

    // 즐겨찾기한 자료 조회
    public List<ArchiveDetailVO> selectFavoritesArchive(Integer userId){
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).selectFavoritesArchive(userId);
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

    // 학습 진행 상태(incomplete) 추가
    public void insertProgressStatus(int userId, int materialId) {
        sqlSessionTemplate.getMapper(ArchiveMapper.class).insertProgressStatus(userId, materialId);
    }

    // 학습 진행 상태(completed) 업데이트 (반환값을 int로 설정)
    public int updateProgressStatus(int userId, int materialId, String status) {
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).updateProgressStatus(userId, materialId, status);
    }

    // 특정 학습 자료와 사용자의 학습 진행 상태 조회
    public ProgressVO getProgressForMaterialAndUser(int userId, int materialId) {
        return sqlSessionTemplate.getMapper(ArchiveMapper.class).getProgressForUserAndMaterial(userId, materialId);
    }

    // 학습 진행 상태 추가
    public void insertProgress(int userId, int materialId, String status, String startedAt) {
        sqlSessionTemplate.getMapper(ArchiveMapper.class)
                .insertProgress(userId, materialId, status, startedAt);
    }

}
