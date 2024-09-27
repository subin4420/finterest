package org.finterest.invest.board.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BoardVO {
    private Long boardId;       // 게시판 글 ID
    private String title;       // 글 제목
    private String content;     // 글 내용
    private Long userId;        // 회원 ID
    private LocalDateTime createdAt;   // 작성 시간
    private LocalDateTime updatedAt;   // 수정 시간
}
