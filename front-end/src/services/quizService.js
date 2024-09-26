import api from './api';

// 새로운 함수 추가
export const getQuizSets = async () => {
  const response = await api.get('/api/quiz-sets'); // 퀴즈 세트 요청
  return response.data.quiz_sets; // quiz_sets 배열 반환
};
