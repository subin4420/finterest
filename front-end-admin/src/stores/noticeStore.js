import { defineStore } from 'pinia';
import noticeService from '@/services/noticeService';

export const useNoticeStore = defineStore('notice', {
  state: () => ({
    notices: [],
    currentNotice: null
  }),
  actions: {
    async fetchNotices() {
      try {
        this.notices = await noticeService.getNotices();
      } catch (error) {
        console.error('공지사항을 가져오는 중 오류 발생:', error);
      }
    },
    async fetchNoticeDetail(noticeId) {
      try {
        this.currentNotice = await noticeService.getNoticeDetail(noticeId);
      } catch (error) {
        console.error('공지사항 상세 정보를 가져오는 중 오류 발생:', error);
      }
    }
  }
});
