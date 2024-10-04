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