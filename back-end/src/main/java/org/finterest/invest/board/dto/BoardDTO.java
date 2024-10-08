package org.finterest.invest.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.finterest.invest.board.domain.BoardAttachmentVO;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.comment.dto.CommentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {
    private Long no;
    private String title;
    private String content;
    private String writer;
    private Date regDate;
    private Date updateDate;
    private List<CommentDTO> comments; 

    // 첨부 파일
    private List<BoardAttachmentVO> attaches;

    List<MultipartFile> files = new ArrayList<>(); // 실제 업로드된 파일(Multipart) 목록


    // VO  DTO 변환
    public static BoardDTO of(BoardVO vo) {
        return vo == null ? null : BoardDTO.builder()
                .no(vo.getNo())
                .title(vo.getTitle())
                .content(vo.getContent())
                .writer(vo.getWriter())
                .attaches(vo.getAttaches())
                .regDate(vo.getRegDate())
                .updateDate(vo.getUpdateDate())
                .comments(vo.getComments() != null ? 
                    vo.getComments().stream().map(CommentDTO::of).collect(Collectors.toList()) : 
                    null)
                .build();
    }

    // DTO  VO 변환
    public BoardVO toVo() {
        return BoardVO.builder()
                .no(no)
                .title(title)
                .content(content)
                .writer(writer)
                .attaches(attaches)
                .regDate(regDate)
                .updateDate(updateDate)
                .comments(comments != null ? 
                    comments.stream().map(CommentDTO::toVo).collect(Collectors.toList()) : 
                    null)
                .build();
    }
}
