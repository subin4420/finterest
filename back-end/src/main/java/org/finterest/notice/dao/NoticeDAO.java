package org.finterest.notice.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.notice.domain.NoticeVO;
import org.finterest.notice.mapper.NoticeMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class NoticeDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public NoticeDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 공지사항 목록 조회
    public List<NoticeVO> getNotices(){
        return sqlSessionTemplate.getMapper(NoticeMapper.class).getNotices();
    }

    // 공지사항 상세 조회
    public NoticeVO getNoticeById(int noticeId){
        return sqlSessionTemplate.getMapper(NoticeMapper.class).getNoticeById(noticeId);
    }

    // 조회수 증가
    public int incrementViewCount(int noticeId){
        return sqlSessionTemplate.getMapper(NoticeMapper.class).incrementViewCount(noticeId);
    }
}
