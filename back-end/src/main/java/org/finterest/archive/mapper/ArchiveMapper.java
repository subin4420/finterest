package org.finterest.archive.mapper;

import org.apache.ibatis.annotations.Param;
import org.finterest.archive.domain.ArchiveDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.finterest.archive.domain.ArchiveVO;
import org.finterest.archive.domain.ProgressDetailVO;
import org.finterest.archive.domain.ProgressVO;

import java.util.List;

@Mapper
public interface ArchiveMapper {
    List<ArchiveDetailVO> selectAllArchive(@Param("userId") Integer userId);
    ArchiveDetailVO selectArchiveById(int id);

    List<ArchiveDetailVO> selectArchiveByCategory(int categoryId);
    List<ArchiveDetailVO> selectArchiveByCategoryWithFavorites(@Param("userId") Integer userId, @Param("categoryId") int categoryId);

    List<ArchiveDetailVO> selectTextArchive(@Param("userId") Integer userId);
    List<ArchiveDetailVO> selectVideoArchive(@Param("userId") Integer userId);
    ProgressVO getProgressForMaterial(int materialId);
    ProgressVO getProgressForUserId(Integer userId);

    // 즐겨찾기한 자료 조회
    List<ArchiveDetailVO> selectFavoritesArchive(@Param("userId") Integer userId);

    // 즐겨찾기 추가
    void insertFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 즐겨찾기 삭제
    void deleteFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 학습 진행 상태 조회
    List<ProgressDetailVO> selectAllProgress(@Param("userId") int userId);
    List<ProgressDetailVO> selectProgressByStatus(@Param("userId") int userId, @Param("status") String status);

    // 학습 진행 상태(incomplete) 추가
    int insertProgressStatus(@Param("userId") int userId, @Param("materialId") int materialId);


    // 학습 진행 상태(completed) 업데이트 (반환값을 int로 설정)
    int updateProgressStatus(@Param("userId") int userId, @Param("materialId") int materialId, @Param("status") String status);

    void insertPoint(@Param("userId")int userId);

    // 학습 진행 상태 조회
    ProgressVO getProgressForUserAndMaterial(@Param("userId") int userId, @Param("materialId") int materialId);
    void insertProgress(@Param("userId") int userId, @Param("materialId") int materialId, @Param("status") String status, @Param("startedAt") String startedAt);


    // 최근 업데이트된 텍스트 자료 8개 조회
    List<ArchiveVO> selectRecentTextArchives();

    // 완료 횟수가 많은 영상 자료 8개 조회
    List<ArchiveVO> selectMostCompletedVideoArchives();

}
