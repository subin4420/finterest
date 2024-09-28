package org.finterest.notice.service;

import org.finterest.notice.dao.NoticeDAO;
import org.finterest.notice.domain.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeDAO noticeDAO;

    @Autowired
    public NoticeService(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

    public List<NoticeVO> getNotices() {
        return noticeDAO.getNotices();
    }

    public NoticeVO getNoticeById(int noticeId) {
        // 조회수 증가 로직 추가
        noticeDAO.incrementViewCount(noticeId);
        return noticeDAO.getNoticeById(noticeId);
    }

    public int incrementViewCount(int noticeId) {
        return noticeDAO.incrementViewCount(noticeId);
    }
}
