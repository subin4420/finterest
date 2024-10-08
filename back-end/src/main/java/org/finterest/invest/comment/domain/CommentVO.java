package org.finterest.invest.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVO {

    private Long no;      // 댓글 ID
    private String content;      // 댓글 내용
    private String writer;      // 회원 ID
    private Long bno;        // 게시물 ID

}
