package org.finterest.achieve.mapper;

import org.apache.ibatis.annotations.Param;
import org.finterest.achieve.domain.ProgressVO;
import org.finterest.achieve.domain.AchieveVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AchieveMapper {
    List<AchieveVO> selectAllAchieve();
    AchieveVO selectAchieveById(int id);
    List<AchieveVO> selectAchieveByCategory(int category_id);
    List<AchieveVO> selectTextAchieve();
    List<AchieveVO> selectVideoAchieve();
    //void insertFavorite(int userId, int materialId);
    //void deleteFavorite(int userId, int materialId);

    // 즐겨찾기 추가
    void insertFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 즐겨찾기 삭제
    void deleteFavorite(@Param("userId") int userId, @Param("materialId") int materialId);

    // 학습 진행 상태 조회
    List<ProgressVO> selectAllProgress(@Param("userId") int userId);
    List<ProgressVO> selectProgressByStatus(@Param("userId") int userId, @Param("status") String status);

    // 학습 진행 상태 업데이트 (반환값을 int로 설정)
    int updateProgressStatus(@Param("userId") int userId, @Param("materialId") int materialId, @Param("status") String status);

}
