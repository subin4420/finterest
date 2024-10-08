package org.finterest.invest.board.service;

import org.finterest.common.pagination.Page;
import org.finterest.common.pagination.PageRequest;
import org.finterest.invest.board.domain.BoardAttachmentVO;
import org.finterest.invest.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    public List<BoardDTO> getList();

    public BoardDTO get(Long no);

    public BoardDTO create(BoardDTO board);

    public BoardDTO update(BoardDTO board);

    public BoardDTO delete(Long no);

    public BoardAttachmentVO getAttachment(Long no);

    public boolean deleteAttachment(Long no);

    Page<BoardDTO> getPage(PageRequest pageRequest);

    BoardDTO getWithComments(Long no);
}
