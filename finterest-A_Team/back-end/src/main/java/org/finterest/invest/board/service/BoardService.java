package org.finterest.invest.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Log4j

@Service
public class BoardService {

    private final BoardMapper boardMapper;


    // 게시물 작성

    public void createBoard(BoardDTO boardDTO) {



    }

    // 게시물 업데이트

    public void updateBoard(BoardDTO boardDTO) {
        boardMapper.updateBoard(boardDTO);
    }







}
