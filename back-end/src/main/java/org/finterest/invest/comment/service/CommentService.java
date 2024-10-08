package org.finterest.invest.comment.service;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.comment.domain.CommentVO;
import org.finterest.invest.comment.dto.CommentDTO;
import org.finterest.invest.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    // 댓글 생성
    @Transactional
    public void createComment(CommentDTO commentDTO) {
        CommentVO commentVO = commentDTO.toVo();
        commentMapper.insertComment(commentVO);
    }

    // 특정 게시물의 댓글 조회
    public List<CommentDTO> getCommentsByBoardId(Long boardId) {
        List<CommentVO> commentVOList = commentMapper.selectCommentsByBoardId(boardId);
        return commentVOList.stream().map(CommentDTO::of).collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        CommentVO commentVO = commentDTO.toVo();
        commentMapper.updateComment(commentVO);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
