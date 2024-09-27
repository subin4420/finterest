package org.finterest.achieve.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.achieve.domain.AchieveVO;

import java.util.List;

@Mapper
public interface AdminAchieveMapper {
    // 카테고리 이름으로 category_id 가져오기
    int selectCategoryIdByName(@Param("categoryName") String categoryName);

    // 학습 자료 삽입
    void insertAchieve(@Param("categoryId") int categoryId,
                       @Param("title") String title,
                       @Param("materialImg") String materialImg,
                       @Param("link") String link,
                       @Param("description") String description,
                       @Param("content") String content);

    // 전체 학습 자료 목록 조회
    List<AchieveVO> selectAllAchieve();

    // 특정 사용자의 즐겨찾기된 학습 자료 조회
    List<AchieveVO> selectFavoritesByUser(@Param("userId") int userId);

    // 카테고리 및 즐겨찾기 필터링이 적용된 학습 자료 조회
    List<AchieveVO> selectAchieveByCategoryAndFavorites(@Param("categoryName") String categoryName,
                                                        @Param("favorites") Boolean favorites);

    // 학습 자료 수정 (카테고리명을 사용)
    void updateAchieve(@Param("materialId") int materialId,
                       @Param("categoryName") String categoryName,
                       @Param("title") String title,
                       @Param("materialImg") String materialImg,
                       @Param("link") String link,
                       @Param("description") String description,
                       @Param("content") String content);

    void deleteAchieve(@Param("materialId") int materialId);
}
