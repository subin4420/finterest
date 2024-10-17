package org.finterest.archive.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.archive.domain.ArchiveDetailVO;
import org.finterest.archive.domain.ArchiveVO;

import java.util.List;

@Mapper
public interface AdminArchiveMapper {
    // 카테고리 이름으로 category_id 가져오기
    int selectCategoryIdByName(@Param("categoryName") String categoryName);

    // 학습 자료 삽입
    void insertArchive(@Param("categoryId") int categoryId,
                       @Param("title") String title,
                       @Param("materialImg") String materialImg,
                       @Param("link") String link,
                       @Param("description") String description,
                       @Param("content") String content);

    // 전체 학습 자료 목록 조회
    List<ArchiveVO> selectAllArchive();

    // 특정 사용자의 즐겨찾기된 학습 자료 조회
    List<ArchiveVO> selectFavoritesByUser(@Param("userId") int userId);

    // 카테고리 및 즐겨찾기 필터링이 적용된 학습 자료 조회
    List<ArchiveVO> selectArchiveByCategoryAndFavorites(@Param("categoryName") String categoryName,
                                                              @Param("favorites") Boolean favorites);

    // 학습 자료 수정 (카테고리명을 사용)
    void updateArchive(@Param("materialId") int materialId,
                       @Param("categoryName") String categoryName,
                       @Param("title") String title,
                       @Param("materialImg") String materialImg,
                       @Param("link") String link,
                       @Param("description") String description,
                       @Param("content") String content);

    void deleteArchive(@Param("materialId") int materialId);
    void deleteProgress(@Param("materialId") int materialId);
    void deleteFavorites(@Param("materialId") int materialId);
}
