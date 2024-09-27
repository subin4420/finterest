import api from '@/api'; // 인터셉터 사용(index.js)

const BASE_URL = '/api/board';

export default {
  // 게시글 전체 목록
  async getList() {
    // 서버에 요청을 보내고 전체 게시글 목록을 받아온다
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

    // API로 JSON 데이터를 POST 요청
    const { data } = await api.post(`${BASE_URL}/create`, payload);
    console.log('BOARD POST: ', data);
    return data;
  },

  // 게시글 상세 보기
  async get(boardId) {
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

    // API로 JSON 데이터를 PUT 요청
    const { data } = await api.put(`${BASE_URL}/update`, payload);
    console.log('BOARD PUT: ', data);
    return data;
  },
};
