import api from './api';

export const getArchive = async (params = {}) => {
  const response = await api.get('/api/archive', { params }); // params를 쿼리로 전달
  console.log("in service response.data: ", response.data);
  return response.data;
};

// 학습 진행 상태 조회 API 호출 함수 (테스트용 Authorization 헤더 추가)
export const getArchiveProgress = async (params = {}) => {
  try {
    // 테스트용 토큰
    const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.

    const response = await api.get('/api/archive/progress', {
      params,
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });

    return response.data;
  } catch (error) {
    console.error('Error fetching archive progress:', error);
    throw error;
  }
};

export const postArchive = async () => {
  const response = await api.post('/api/archive');
  return response.data;
};
