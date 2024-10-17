package org.finterest.notice.service;

import org.finterest.notice.dao.AdminNoticeDAO;
import org.finterest.notice.domain.NoticeVO;
import org.finterest.notice.mapper.AdminNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminNoticeService {
    private final AdminNoticeDAO adminNoticeDAO;

    @Autowired
    public AdminNoticeService(AdminNoticeDAO adminNoticeDAO) {
        this.adminNoticeDAO = adminNoticeDAO;
    }

    public List<NoticeVO> getNotices(){
        return adminNoticeDAO.getNotices();
    }
    public boolean addNotice(NoticeVO notice){
        return adminNoticeDAO.addNotice(notice);
    }
    public boolean updateNotice(NoticeVO notice){
        return adminNoticeDAO.updateNotice(notice);
    }
    public boolean deleteNotice(int noticeId){
        return adminNoticeDAO.deleteNotice(noticeId);
    }
}
