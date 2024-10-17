package org.finterest.invest.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.finterest.common.pagination.Page;
import org.finterest.common.pagination.PageRequest;
import org.finterest.common.util.UploadFiles;
import org.finterest.invest.board.domain.BoardAttachmentVO;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.service.BoardService;
import org.finterest.invest.comment.dto.CommentDTO;
import org.finterest.invest.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService service;
    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<Page> getList(PageRequest pageRequest) {
        return ResponseEntity.ok(service.getPage(pageRequest));
    }

    @GetMapping("/{no}") // 15
    public ResponseEntity<BoardDTO> get(@PathVariable Long no) {
        return ResponseEntity.ok(service.get(no));
    }

    @PostMapping("")
    public ResponseEntity<BoardDTO> create(BoardDTO board) {
        return ResponseEntity.ok(service.create(board));
    }

    @PutMapping("/{no}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long no, BoardDTO board) {
        board.setNo(no);
        return ResponseEntity.ok(service.update(board));
    }

    @DeleteMapping("/{no}") // 11
    public ResponseEntity<BoardDTO> delete(@PathVariable Long no) {
        return ResponseEntity.ok(service.delete(no));
    }

    @GetMapping("/download/{no}") // 7
    public void download(@PathVariable Long no, HttpServletResponse response) throws Exception {
        BoardAttachmentVO attachment = service.getAttachment(no);
        File file = new File(attachment.getPath());
        UploadFiles.download(response, file, attachment.getFilename());
    }

    @DeleteMapping("/deleteAttachment/{no}")
    public ResponseEntity<Boolean> deleteAttachment(@PathVariable Long no) throws Exception {
        return ResponseEntity.ok(service.deleteAttachment(no));
    }

    @GetMapping("/{no}/with-comments")
    public ResponseEntity<BoardDTO> getWithComments(@PathVariable Long no) {
        return ResponseEntity.ok(service.getWithComments(no));
    }

    @PostMapping("/{no}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long no, @RequestBody CommentDTO commentDTO) {
        try {
            commentDTO.setBno(no);
            // writer가 제공되지 않았다면 "익명"으로 설정
            if (commentDTO.getWriter() == null || commentDTO.getWriter().trim().isEmpty()) {
                commentDTO.setWriter("익명");
            }
            commentService.createComment(commentDTO);
            return ResponseEntity.ok("댓글이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            log.error("댓글 추가 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("댓글 추가 중 오류 발생: " + e.getMessage());
        }
    }
}