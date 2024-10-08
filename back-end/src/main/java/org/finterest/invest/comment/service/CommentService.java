package org.finterest.invest.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.finterest.invest.comment.domain.CommentVO;
import org.finterest.invest.comment.dto.CommentDTO;
import org.finterest.invest.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public void createComment(CommentDTO commentDTO) {
        CommentVO commentVO = commentDTO.toVo();
        commentMapper.insertComment(commentVO);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByBno(Long bno) {
        List<CommentVO> commentVOList = commentMapper.getCommentsByBno(bno);
        return commentVOList.stream().map(CommentDTO::of).collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        CommentVO commentVO = commentDTO.toVo();
        commentMapper.updateComment(commentVO);
    }

    @Transactional
    public void deleteComment(Long no) {
        commentMapper.deleteComment(no);
    }
}
