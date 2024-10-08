package org.finterest.invest.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.comment.domain.CommentVO;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardVO {
    private Long no; // 49
    private String title; // 이미지 업로딩
    private String content; //
    private String writer;
    private Date regDate;
    private Date updateDate; //


    private List<CommentVO> comments;
    private List<BoardAttachmentVO> attaches;

    // <setting name="mapUnderscoreToCamelCase" value="true"/>

    // mapUnderscoreToCamelCase ->  스네이크 케이스 == 카멜 케이스

    // reg_date -> regDate
    // update_date -> updateDate


}
