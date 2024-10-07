import api from './api';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  // if (!token) {
  //   throw new Error('인증 토큰이 없습니다. 로그인이 필요합니다.');
  // }
  // return token;
  console.log("Fetched token:", token);
  return token || null;
};

// 퀴즈 세트 가져오기
// export const getQuizSets = async () => {
//   const response = await api.get('/api/quiz-sets'); // 퀴즈 세트 요청
//   //console.log("API Response:", response); // 전체 응답을 출력하여 데이터 구조 확인
//   return response.data; // 필요한 데이터를 반환
// };

// Authorization 헤더 추가
export const getQuizSets = async (params = {}) => {
  const token = getToken();
  
  const response = await api.get('/api/quiz-sets', { 
    params,
    headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
   }); // params를 쿼리로 전달
  console.log("in service response.data: ", response.data);
  return response.data;
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

// 퀴즈 답변 제출
export const submitQuizAnswers = async (setId, answers) => {
  const token = getToken();
  
  const response = await api.post(`/api/quiz-sets/${setId}/submit`, answers, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  console.log("Quiz submission response:", response.data);
  return response.data;
};

// 퀴즈 결과 조회 API 호출
export const fetchQuizResults = async (userId) => {
  const token = getToken();
  
  const response = await api.get('/api/quiz-sets/results', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    params: { userId },  // userId를 쿼리 파라미터로 전달
  });
  
  console.log("Quiz results response:", response.data);
  return response.data;
};

// 퀴즈 결과 답변 가져오기
export const getQuizAnswers = async (setId, resultId) => {
  const token = getToken();
  
  const response = await api.get(`/api/quiz-sets/${setId}/results/${resultId}/answers`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  console.log("Quiz answers response:", response.data);
  return response.data;
};