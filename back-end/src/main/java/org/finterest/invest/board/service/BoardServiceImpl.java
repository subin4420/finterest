package org.finterest.invest.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.finterest.common.pagination.Page;
import org.finterest.common.pagination.PageRequest;
import org.finterest.common.util.UploadFiles;
import org.finterest.invest.board.domain.BoardAttachmentVO;
import org.finterest.invest.board.domain.BoardVO;
import org.finterest.invest.board.dto.BoardDTO;
import org.finterest.invest.board.mapper.BoardMapper;
import org.finterest.invest.comment.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final static String BASE_DIR = "c:/upload/board";

    //    @Autowired
    final private BoardMapper mapper;
    
    private final CommentService commentService;

    @Override
    public List<BoardDTO> getList() {
        log.info("getList..........");

        List<BoardDTO> boardDTOList = mapper.getList().stream() // BoardVO의 스트림
                .map(BoardDTO::of) // BoardDTO의 스트림
                .toList(); // List<BoardDTO> 변환

        return boardDTOList;

//        return mapper.getList().stream() // BoardVO의 스트림
//                .map(BoardDTO::of) // BoardDTO의 스트림
//                .toList(); // List<BoardDTO> 변환
    }

    @Override
    public BoardDTO get(Long no) { // no=44
        log.info("get......" + no);

        BoardVO boardVO = mapper.get(no);
        BoardDTO board = BoardDTO.of(boardVO); // BoardVO no=44

//        BoardDTO board2 = BoardDTO.of(mapper.get(no)); // BoardVO no=44

//        return board;

        return Optional.ofNullable(board)
                .orElseThrow(NoSuchElementException::new); //
    }

//    @Override
//    public BoardDTO create(BoardDTO board) {
//        mapper.create(board.toVo());
//        log.info("board.toVo():" + board.getNo());
//        return board;
//    }

    // 2개 이상의 insert 문이 실행될 수 있으므로 트랜잭션 처리 필요
    // RuntimeException인 경우만 자동 rollback.
    @Transactional
    @Override
    public BoardDTO create(BoardDTO board) {
        log.info("create......" + board);
        BoardVO boardVO = board.toVo();
        mapper.create(boardVO); // no == 47

        // 파일 업로드 처리
        List<MultipartFile> files = board.getFiles();
        if (files != null && !files.isEmpty()) { // 첨부 파일이 있는 경우
            upload(boardVO.getNo(), files);
        }

        return get(boardVO.getNo());

    }

    private void upload(Long bno, List<MultipartFile> files) { // 47, files
        for (MultipartFile part : files) {
            if (part.isEmpty()) continue;
            try {
                String uploadPath = UploadFiles.upload(BASE_DIR, part);
                BoardAttachmentVO attach = BoardAttachmentVO.of(part, bno, uploadPath);
                mapper.createAttachment(attach);
            } catch (IOException e) {
                throw new RuntimeException(e); // @Transactional에서 감지, 자동 rollback
            }
        }
    }


    @Override
    public BoardDTO update(BoardDTO board) {
        log.info("update......" + board);
        mapper.update(board.toVo());
//        return mapper.update(board.toVo()) == 1;
        return get(board.getNo());
    }

    @Override
    public BoardDTO delete(Long no) {
        log.info("delete...." + no);
        BoardDTO board = get(no);

        mapper.delete(no);
        return board;

//        return mapper.delete(no) == 1;
    }

    // 첨부파일 한 개 얻기
    @Override
    public BoardAttachmentVO getAttachment(Long no) {
        return mapper.getAttachment(no);
    }

    // 첨부파일 삭제
    @Override
    public boolean deleteAttachment(Long no) {
        return mapper.deleteAttachment(no) == 1;
    }

    @Override
    public Page<BoardDTO> getPage(PageRequest pageRequest) {

        List<BoardVO> boards = mapper.getPage(pageRequest);
        int totalCount = mapper.getTotalCount();

        log.info("currentPage : " + pageRequest.getPage());

        return Page.of(pageRequest, totalCount,
                boards.stream().map(BoardDTO::of).toList());
    }

    @Override
    public BoardDTO getWithComments(Long no) {
        log.info("getWithComments......" + no);

        BoardVO boardVO = mapper.get(no);
        if (boardVO == null) {
            throw new NoSuchElementException("게시글을 찾을 수 없습니다.");
        }

        BoardDTO boardDTO = BoardDTO.of(boardVO);
        boardDTO.setComments(commentService.getCommentsByBno(no));

        return boardDTO;
    }
}
