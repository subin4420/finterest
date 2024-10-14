import api from './api'
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

export const getUserInfo = async () => {
  const response = await api.get('/user/info');
  return response.data;
};

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  console.log("Fetched token:", token);
  return token || null;
};

// 전체 사용자 랭킹 조회 API 호출 함수
export const getAllRankings = async () => {
  try {
    const response = await api.get('/api/rankings');
    console.log('API 응답 데이터:', response.data);
    return response.data.rankings; // rankings 배열만 반환
  } catch (error) {
    console.error('Failed to fetch all rankings:', error);
    throw error;
  }
};



// 특정 사용자의 랭킹 정보 조회 API 호출 함수 (토큰 포함)
export const getUserRanking = async () => {
  try {
    const token = getToken();  // 토큰 가져오기
    const response = await api.get(`/api/rankings/me`, {
      headers: {
        Authorization: `Bearer ${token}`,  // Authorization 헤더에 토큰 추가
      },
    });
    console.log("getUserRanking data:", response.data);
    return response.data;  // 사용자 랭킹 데이터 반환
  } catch (error) {
    console.error(`Failed to fetch ranking for user with ID: `, error);
    throw error;  // 에러 발생 시 throw
  }
};
