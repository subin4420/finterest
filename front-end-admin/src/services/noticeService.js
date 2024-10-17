import api from './api';

export default {
  async getNotices() {
    try {
      const response = await api.get('/api/notices');
      return response.data;
    } catch (error) {
      console.error('공지사항을 가져오는 중 오류 발생:', error);
      throw error;
    }
  },
  async getNoticeDetail(noticeId) {
    try {
      const response = await api.get(`/api/notices/${noticeId}`);
      return response.data;
    } catch (error) {
      console.error('공지사항 상세 정보를 가져오는 중 오류 발생:', error);
      throw error;
    }
  }
};
