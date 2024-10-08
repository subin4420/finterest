import api from './api';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    throw new Error('인증 토큰이 없습니다. 로그인이 필요합니다.');
  }
  console.log("Fetched token:", token);
  return token;
};

// 1. 포인트 내역 조회 (필터와 날짜 범위 적용 가능)
export const getAllPoints = async (params = {}) => {
  try {
    const token = getToken();
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
    const token = getToken();
    const response = await api.get('/api/total_points', { // params 제거
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });
    console.log('Response data from getTotalPoints:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching total points:', error);
    throw error;
  }
};

// 3. 사용자 누적 가상자금 조회
export const getTotalMoney = async () => {
  try {
    const token = getToken();
    const response = await api.get('/api/total_money', { // params 제거
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });
    console.log('Response data from getTotalPoints:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching total points:', error);
    throw error;
  }
};
