import api from '@/api'; // 인터셉터 사용(index.js)

const BASE_URL = '/api/board';

export default {
  // 게시글 전체 목록
  async getList() {
    const { data } = await api.get(`${BASE_URL}/list`);
    console.log('BOARD GET LIST : ', data);
    return data;
  },

  // 게시글 생성
  async create(article) {
    const payload = {
      title: article.title,
      writer: article.writer,
      content: article.content,
    };
    const { data } = await api.post(`${BASE_URL}/create`, payload);
    console.log('BOARD POST: ', data);
    return data;
  },

  // 게시글 상세 보기
  async getBoard(boardId) {
    const { data } = await api.get(`${BASE_URL}/${boardId}`);
    console.log('BOARD GET', data);
    return data;
  },

  // 게시글 삭제
  async delete(boardId) {
    const { data } = await api.delete(`${BASE_URL}/delete/${boardId}`);
    console.log('BOARD DELETE', data);
    return data;
  },

  // 게시글 수정
  async update(article) {
    const payload = {
      boardId: article.boardId,
      title: article.title,
      writer: article.writer,
      content: article.content,
    };
    const { data } = await api.put(`${BASE_URL}/update`, payload);
    console.log('BOARD PUT: ', data);
    return data;
  },

  // 사용자 목록 가져오기
  async getUsers() {
    const { data } = await api.get('/api/users'); // 사용자 목록 가져오기
    console.log('USERS GET', data);
    return data;
  },

  // 댓글 목록 가져오기
  async getComments(boardId) {
    const { data } = await api.get(`/api/comments/board/${boardId}`);
    console.log('COMMENTS GET', data);
    return data;
  },

  // 댓글 생성
  async createComment(comment) {
    const payload = {
      content: comment.content,
      userId: comment.userId,
      boardId: comment.boardId,
    };
    const { data } = await api.post(`/api/comments/create`, payload);
    console.log('COMMENT POST', data);
    return data;
  },

  // 댓글 삭제
  async deleteComment(commentId) {
    try {
      const response = await api.delete(`/api/comments/delete/${commentId}`);
      console.log(response.data);
    } catch (error) {
      console.error('댓글 삭제 실패:', error);
    }
  },
};
