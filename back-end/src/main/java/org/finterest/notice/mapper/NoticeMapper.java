package org.finterest.notice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.notice.domain.NoticeVO;

import java.util.List;

@Mapper
public interface NoticeMapper {

    // 공지사항 목록 조회
    List<NoticeVO> getNotices();

    // 공지사항 상세 조회
    NoticeVO getNoticeById(@Param("noticeId") int noticeId);

    // 조회수 증가
    int incrementViewCount(@Param("noticeId") int noticeId);
}
