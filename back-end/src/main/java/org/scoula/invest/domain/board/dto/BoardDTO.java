package org.scoula.invest.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.scoula.invest.domain.board.domain.BoardVO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class BoardDTO {

    private Long boardId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    // VO에서 DTO로 변환
    public static BoardDTO of(BoardVO vo) {  // fromVo를 of로 변경
        return BoardDTO.builder()
                .boardId(vo.getBoardId())
                .title(vo.getTitle())
                .content(vo.getContent())
                .userId(vo.getUserId())
                .createdAt(vo.getCreatedAt())
                .updatedAt(vo.getUpdatedAt())
                .build();
    }

    // DTO에서 VO로 변환
    public BoardVO toVo() {
        return BoardVO.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .userId(userId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

}

