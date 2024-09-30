package org.finterest.invest.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.comment.domain.CommentVO;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long commentId;      // 댓글 ID
    @NotNull
    private String content;      // 댓글 내용
    private Long userId;         // 회원 ID
    private Long boardId;        // 게시물 ID
    private LocalDateTime createdAt;   // 작성 시간
    private LocalDateTime updatedAt;   // 수정 시간

    // VO에서 DTO로 변환
    public static CommentDTO of(CommentVO vo) {
        return CommentDTO.builder()
                .commentId(vo.getCommentId())
                .content(vo.getContent())
                .userId(vo.getUserId())
                .boardId(vo.getBoardId())
//                .createdAt(vo.getCreatedAt())
//                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    // DTO에서 VO로 변환
    public CommentVO toVo() {
        return CommentVO.builder()
                .commentId(commentId)
                .content(content)
                .userId(userId)
                .boardId(boardId)
//                .createdAt(createdAt)
//                .updatedAt(updatedAt)
                .build();
    }
}
