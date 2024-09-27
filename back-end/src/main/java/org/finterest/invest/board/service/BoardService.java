package org.finterest.invest.board.service;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.mapper.BoardMapper;
import org.scoula.invest.domain.board.dto.BoardDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    // 생성자 주입
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    // 게시물 생성
    @Transactional
    public void createBoard(BoardDTO boardDTO) {
        log.info("create");
        boardMapper.insertBoard(boardDTO);
    }

    // 게시물 조회
    public BoardVO getBoardById(Long boardId) {
        return boardMapper.selectBoardById(boardId);
    }

    // 게시물 목록 조회
    public List<BoardVO> getAllBoards() {
        return boardMapper.selectAllBoards();
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
