package org.finterest.archive.mapper;

import org.apache.ibatis.annotations.Param;
import org.finterest.archive.domain.ArchiveVO;
import org.apache.ibatis.annotations.Mapper;
import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ArchiveMapper {
    List<ArchiveVO> selectAllArchive(@Param("userId") Integer userId);
    ArchiveVO selectArchiveById(int id);
    List<ArchiveVO> selectArchiveByCategory(int category_id);
    List<ArchiveVO> selectFavoriteArchive(@Param("userId") int userId);
    List<ArchiveVO> selectTextArchive(@Param("userId") Integer userId);
    List<ArchiveVO> selectVideoArchive(@Param("userId") Integer userId);
    ProgressVO getProgressForMaterial(int materialId);

    //void insertFavorite(int userId, int materialId);
    //void deleteFavorite(int userId, int materialId);

    // 즐겨찾기 추가
    void insertFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 즐겨찾기 삭제
    void deleteFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 학습 진행 상태 조회
    List<ProgressDetailVO> selectAllProgress(@Param("userId") int userId);
    List<ProgressDetailVO> selectProgressByStatus(@Param("userId") int userId, @Param("status") String status);

    // 학습 진행 상태(incomplete) 추가
    int insertProgressStatus(@Param("userId") int userId, @Param("materialId") int materialId);

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    int updateProgressStatus(@Param("userId") int userId, @Param("materialId") int materialId, @Param("status") String status);

}
