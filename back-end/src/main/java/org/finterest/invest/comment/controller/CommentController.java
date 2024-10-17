package org.finterest.invest.comment.controller;

import lombok.RequiredArgsConstructor;
import org.finterest.invest.comment.dto.CommentDTO;
import org.finterest.invest.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
        return ResponseEntity.ok("댓글이 성공적으로 생성되었습니다.");
    }

    @GetMapping("/board/{bno}")
    public ResponseEntity<List<CommentDTO>> getCommentsByBno(@PathVariable Long bno) {
        List<CommentDTO> comments = commentService.getCommentsByBno(bno);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{no}")
    public ResponseEntity<String> updateComment(@PathVariable Long no, @RequestBody CommentDTO commentDTO) {
        commentDTO.setNo(no);
        commentService.updateComment(commentDTO);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteComment(@PathVariable Long no) {
        commentService.deleteComment(no);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
