package org.finterest.invest.board.service;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j
@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    // 게시물 생성
    @Transactional
    public void createBoard(BoardDTO boardDTO) {
        log.info("create");
        boardMapper.insertBoard(boardDTO);
    }

    // 게시물 조회 (ID로, 댓글 포함)
    public BoardDTO getBoardWithComments(Long boardId) {
        return boardMapper.selectBoardWithComments(boardId);
    }

    public List<BoardDTO> getAllBoards() {
        List<BoardVO> boardVOList = boardMapper.selectAllBoards();  // Mapper 호출
        return boardVOList.stream()
                .map(BoardDTO::of)  // VO를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 게시물 업데이트
    @Transactional
    public void updateBoard(BoardDTO boardDTO) {
        boardMapper.updateBoard(boardDTO);
    }

    // 게시물 삭제
    @Transactional
    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }
}