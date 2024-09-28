package org.finterest.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.notice.domain.NoticeVO;

import java.util.List;

@Mapper
public interface AdminNoticeMapper {
    List<NoticeVO> getNotices();
    boolean addNotice(NoticeVO notice);
    boolean updateNotice(NoticeVO notice);
    boolean deleteNotice(@Param("noticeId")int noticeId);
}
