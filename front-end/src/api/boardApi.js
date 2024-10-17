import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // 프록시 설정에 맞춰 baseURL 설정
});

export default {
  async getList(params) {
    try {
      const { data } = await api.get('/board', { params });
      console.log('BOARD GET LIST: ', data);
      return data;
    } catch (error) {
      console.error('Error fetching board list:', error);
      throw error;
    }
  },

  async create(boardData, files) {
    const formData = new FormData();
    formData.append('writer', boardData.writer); // writer 필드 추가
    formData.append('title', boardData.title);
    formData.append('content', boardData.content);

    if (files && Array.isArray(files)) {
      files.forEach((file) => {
        formData.append('files', file);
      });
    }

    return api.post('/board', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  get: async (no) => {
    try {
      const response = await api.get(`/board/${no}`);
      console.log('게시글 상세 응답:', response.data); // 로그 추가
      return response.data;
    } catch (error) {
      console.error('게시글 가져오기 오류:', error);
      throw error;
    }
  },

  async delete(no) {
    const { data } = await api.delete(`/board/${no}`);
    console.log('BOARD DELETE: ', data);
    return data;
  },

  // 게시글 수정
  async update(article) {
    const formData = new FormData();
    formData.append('no', article.no);
    formData.append('title', article.title);
    formData.append('writer', article.writer);
    formData.append('content', article.content);
    if (article.files) {
      // 첨부파일이 있는 경우
      for (let i = 0; i < article.files.length; i++) {
        formData.append('files', article.files[i]);
      }
    }
    const { data } = await api.put(`/board/${article.no}`, formData);
    console.log('BOARD PUT: ', data);
    return data;
  },

  // 첨부파일 삭제
  async deleteAttachment(no) {
    const { data } = await api.delete(`/board/deleteAttachment/${no}`);
    console.log('ATTACHMENT DELETE: ', data);
    return data;
  },

  async getWithComments(no) {
    const response = await api.get(`/board/${no}/with-comments`);
    return response.data;
  },

  async addComment(boardNo, commentData) {
    const response = await axios.post(
      `/api/board/${boardNo}/comments`,
      commentData
    );
    return response.data;
  },

  async getComments(bno) {
    try {
      const response = await api.get(`/comments/board/${bno}`);
      console.log(`게시글 ${bno}의 댓글:`, response.data);
      return response.data;
    } catch (error) {
      console.error('댓글 조회 중 오류:', error);
      if (error.response) {
        console.error('응답 데이터:', error.response.data);
        console.error('응답 상태:', error.response.status);
        console.error('응답 헤더:', error.response.headers);
      }
      throw error;
    }
  },

  async addComment(bno, commentData) {
    try {
      const response = await api.post(`/comments/create`, commentData);
      return response.data;
    } catch (error) {
      console.error('댓글 추가 중 오류:', error);
      throw error;
    }
  },
};
