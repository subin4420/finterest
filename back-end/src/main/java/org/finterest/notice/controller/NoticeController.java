package org.finterest.notice.controller;

import org.finterest.notice.domain.NoticeVO;
import org.finterest.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<List<NoticeVO>> getNotices() {
        List<NoticeVO> notices = noticeService.getNotices();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }

    // 공지사항 상세 조회 및 조회수 증가
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeVO> getNoticeById(@PathVariable int noticeId) {
        NoticeVO notice = noticeService.getNoticeById(noticeId);
        if (notice != null) {
            return new ResponseEntity<>(notice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
