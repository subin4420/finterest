import api from './api';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    throw new Error('인증 토큰이 없습니다. 로그인이 필요합니다.');
  }
  console.log('Fetched token:', token);
  return token;
};

// 1. 대시보드 정보 조회
export const getDashboard = async () => {
  try {
    const token = getToken();
    const response = await api.get('/api/admin/chart/dashboard', {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });
    console.log('Response data from getTodaySignUps:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching today sign-ups:', error);
    throw error;
  }
};
