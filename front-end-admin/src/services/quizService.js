import api from './api';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  return authStore.getToken() || null;
};

// 퀴즈 세트 가져오기
export const getQuizSets = async (params = {}) => {
  const token = getToken();
  
  try {
    const config = { params };
    if (token) {
      config.headers = { Authorization: `Bearer ${token}` };
    }
    
    const response = await api.get('/api/quiz-sets', config);
    console.log("in service response.data: ", response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching quiz sets:", error);
    return { quizSets: [] };
  }
};

// 특정 퀴즈 세트의 문제들 가져오기
export const getQuizQuestions = async (setId) => {
  const token = getToken();
  
  const response = await api.get(`/api/quiz-sets/${setId}/questions`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  console.log("Quiz questions response:", response.data);
  return response.data;
};

// 주별/월별 학습자료 조회수
export const getQuizChart = async ({ period, year, month }) => {
  try {
    const token = getToken();
    const url = `/api/admin/chart/quiz-completion/${period}`; // 경로 수정
    const params = { year };
    if (period === 'weekly') {
      params.month = month; // 주별인 경우, 월을 추가
    }

    const response = await api.get(url, {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
      params, // 쿼리 파라미터 추가
    });
    console.log('Response data from getQuizChart:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching quiz chart data:', error);
    throw error;
  }
};

