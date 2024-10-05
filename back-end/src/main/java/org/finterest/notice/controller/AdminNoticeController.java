package org.finterest.notice.controller;

import org.finterest.notice.domain.NoticeVO;
import org.finterest.notice.service.AdminNoticeService;
import org.finterest.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notices")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;
    private final TokenUtil tokenUtil;

    @Autowired
    public AdminNoticeController(AdminNoticeService adminNoticeService, TokenUtil tokenUtil) {
        this.adminNoticeService = adminNoticeService;
        this.tokenUtil = tokenUtil;
    }

    // 1. 공지사항 목록 조회
    @GetMapping
    public ResponseEntity<List<NoticeVO>> getNotices(@RequestHeader("Authorization") String adminToken) {
        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // 권한이 없으면 403 Forbidden 응답
        }

        List<NoticeVO> notices = adminNoticeService.getNotices();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        return new ResponseEntity<>(notices, headers, HttpStatus.OK);
    }

    // 2. 공지사항 작성
    @PostMapping
    public ResponseEntity<String> addNotice(
            @RequestHeader("Authorization") String adminToken,
            @RequestBody NoticeVO notice) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return new ResponseEntity<>("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);  // 권한이 없으면 403 Forbidden 응답
        }

        boolean isAdded = adminNoticeService.addNotice(notice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isAdded) {
            return new ResponseEntity<>("공지사항이 성공적으로 작성되었습니다.", headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("공지사항 추가에 실패했습니다.", headers, HttpStatus.BAD_REQUEST);
        }
    }

    // 3. 공지사항 수정
    @PutMapping("/{noticeId}")
    public ResponseEntity<String> updateNotice(
            @RequestHeader("Authorization") String adminToken,
            @PathVariable int noticeId,
            @RequestBody NoticeVO notice) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return new ResponseEntity<>("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);  // 권한이 없으면 403 Forbidden 응답
        }

        notice.setNoticeId(noticeId);
        boolean isUpdated = adminNoticeService.updateNotice(notice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isUpdated) {
            return new ResponseEntity<>("공지사항이 성공적으로 수정되었습니다.", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("공지사항 수정에 실패했습니다.", headers, HttpStatus.BAD_REQUEST);
        }
    }

    // 4. 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotice(
            @RequestHeader("Authorization") String adminToken,
            @PathVariable int noticeId) {

        // 관리자 권한 확인
        if (!tokenUtil.isAdmin(adminToken)) {
            return new ResponseEntity<>("관리자 권한이 없습니다.", HttpStatus.FORBIDDEN);  // 권한이 없으면 403 Forbidden 응답
        }

        boolean isDeleted = adminNoticeService.deleteNotice(noticeId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // UTF-8 설정

        if (isDeleted) {
            return new ResponseEntity<>("공지사항이 성공적으로 삭제되었습니다.", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("공지사항 삭제에 실패했습니다.", headers, HttpStatus.NOT_FOUND);
        }
    }
}
