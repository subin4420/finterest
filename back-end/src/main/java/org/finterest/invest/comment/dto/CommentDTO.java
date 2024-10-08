package org.finterest.invest.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.comment.domain.CommentVO;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long no;
    private String content;
    private String writer;
    private Long bno;


    public static CommentDTO of(CommentVO vo) {
        return CommentDTO.builder()
                .no(vo.getNo())
                .content(vo.getContent())
                .writer(vo.getWriter())
                .bno(vo.getBno())

                .build();
    }

    public CommentVO toVo() {
        return CommentVO.builder()
                .no(no)
                .content(content)
                .writer(writer)
                .bno(bno)
                .build();
    }
}
