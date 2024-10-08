package org.finterest.invest.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.comment.dto.CommentDTO;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

    private Long boardId;                  // 게시판 글 ID

    @NotNull
    private String title;                  // 글 제목

    @NotNull
    private String content;                // 글 내용

    private Long userId;                   // 회원 ID

    // createdAt과 updatedAt 필드 제거
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;

    private List<CommentDTO> comments;     // 댓글 목록

    // VO에서 DTO로 변환
    public static BoardDTO of(BoardVO vo) {
        return BoardDTO.builder()
                .boardId(vo.getBoardId())
                .title(vo.getTitle())
                .content(vo.getContent())
                .userId(vo.getUserId())
                // .createdAt(vo.getCreatedAt())  // 필드 제거
                // .updatedAt(vo.getUpdatedAt())  // 필드 제거
                .build();
    }
}