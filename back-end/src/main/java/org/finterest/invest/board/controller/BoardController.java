package org.finterest.invest.board.controller;

import lombok.extern.log4j.Log4j;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
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
    public ResponseEntity<Map<String, String>> createBoard(@RequestBody BoardDTO boardDTO) {
        boardService.createBoard(boardDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "게시물이 성공적으로 생성되었습니다.");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> boardList = boardService.getAllBoards();
        log.info("Returned Boards Count: " + boardList.size());
        log.info("Boards: " + boardList);  // 리스트의 내용을 출력
        return ResponseEntity.ok(boardList);
    }


    // 게시물 조회 (ID로, 댓글 포함)
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDTO> getBoardWithComments(@PathVariable Long boardId) {
        BoardDTO boardWithComments = boardService.getBoardWithComments(boardId);
        if (boardWithComments != null) {
            return ResponseEntity.ok(boardWithComments);
        } else {
            return ResponseEntity.notFound().build();
        }
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
