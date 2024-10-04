package org.finterest.invest.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.dto.BoardDTO;

import java.util.List;

@Mapper
public interface BoardMapper {
    // 게시물 생성
    void insertBoard(BoardDTO boardDTO);

    // 게시물 조회
    BoardVO selectBoardById(Long boardId);

    // 게시물 목록 조회
    List<BoardVO> selectAllBoards();

    // 게시물 업데이트
    void updateBoard(BoardDTO boardDTO);

    // 게시물 삭제
    void deleteBoard(Long boardId);

    // 게시글에 댓글도 나오게 하는 것
    BoardDTO selectBoardWithComments(Long boardId);
}
