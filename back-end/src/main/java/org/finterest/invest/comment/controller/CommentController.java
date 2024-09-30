package org.finterest.invest.comment.controller;

import org.finterest.invest.comment.dto.CommentDTO;
import org.finterest.invest.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return ResponseEntity.ok("댓글이 성공적으로 생성되었습니다.");
    }
    // 특정 게시물의 댓글 조회
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBoardId(@PathVariable Long boardId) {
        List<CommentDTO> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.updateComment(commentDTO);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
