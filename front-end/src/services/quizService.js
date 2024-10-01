import api from './api';

// 퀴즈 세트 가져오기
export const getQuizSets = async () => {
  const response = await api.get('/api/quiz-sets'); // 퀴즈 세트 요청
  //console.log("API Response:", response); // 전체 응답을 출력하여 데이터 구조 확인
  return response.data; // 필요한 데이터를 반환
};
