package org.finterest.invest.board.controller;

import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    // 생성자 주입
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시물 생성
    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody BoardDTO boardDTO) {
        boardService.createBoard(boardDTO);
        return ResponseEntity.ok("게시물이 성공적으로 생성되었습니다.");
    }

    // 게시물 조회 (ID로)
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardVO> getBoardById(@PathVariable Long boardId) {
        BoardVO board = boardService.getBoardById(boardId);
        if (board != null) {
            return ResponseEntity.ok(board);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardVO>> getAllBoards() {
        List<BoardVO> boardList = boardService.getAllBoards();
        return ResponseEntity.ok(boardList);
    }

    // 게시물 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDTO boardDTO) {
        boardService.updateBoard(boardDTO);
        return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");
    }

    // 게시물 삭제
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok("게시물이 성공적으로 삭제되었습니다.");
    }
}
