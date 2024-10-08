package org.finterest.invest.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.invest.comment.domain.CommentVO;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 댓글 생성
    void insertComment(CommentVO commentVO);

    // 특정 게시물의 모든 댓글 조회
    List<CommentVO> selectCommentsByBno(@Param("bno") Long bno);

    // 댓글 수정
    void updateComment(CommentVO commentVO);

    // 댓글 삭제
    void deleteComment(@Param("no") Long no);

    // 댓글 조회
    List<CommentVO> getCommentsByBno(@Param("bno") Long bno);
}
