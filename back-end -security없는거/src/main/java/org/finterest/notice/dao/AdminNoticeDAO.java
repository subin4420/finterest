package org.finterest.notice.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.notice.domain.NoticeVO;
import org.finterest.notice.mapper.AdminNoticeMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminNoticeDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminNoticeDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<NoticeVO> getNotices(){
        return sqlSessionTemplate.getMapper(AdminNoticeMapper.class).getNotices();
    }
    public boolean addNotice(NoticeVO notice){
        return sqlSessionTemplate.getMapper(AdminNoticeMapper.class).addNotice(notice);
    }
    public boolean updateNotice(NoticeVO notice){
        return sqlSessionTemplate.getMapper(AdminNoticeMapper.class).updateNotice(notice);
    }
    public boolean deleteNotice(int noticeId){
        return sqlSessionTemplate.getMapper(AdminNoticeMapper.class).deleteNotice(noticeId);
    }
}
