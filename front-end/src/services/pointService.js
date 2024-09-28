import api from './api';

// 1. 포인트 내역 조회 (필터와 날짜 범위 적용 가능)
export const getAllPoints = async (params = {}) => {
  try {
    // 테스트용 토큰
    const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.

    const response = await api.get('/api/points', { 
      params,
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });
    console.log('Response data from getAllPoints:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching point history:', error);
    throw error;
  }
};

// 2. 사용자 누적 포인트 조회
export const getTotalPoints = async () => {
  try {
    const response = await api.get('/api/total_points');
    console.log('Response data from getTotalPoints:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching total points:', error);
    throw error;
  }
};
