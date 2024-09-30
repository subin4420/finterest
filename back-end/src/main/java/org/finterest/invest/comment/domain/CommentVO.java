package org.finterest.invest.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {

    private Long commentId;      // 댓글 ID
    private String content;      // 댓글 내용
    private Long userId;         // 회원 ID
    private Long boardId;        // 게시물 ID
//    private LocalDateTime createdAt;   // 작성 시간
//    private LocalDateTime updatedAt;   // 수정 시간
}
