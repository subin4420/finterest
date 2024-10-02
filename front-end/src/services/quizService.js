import api from './api';

// 퀴즈 세트 가져오기
// export const getQuizSets = async () => {
//   const response = await api.get('/api/quiz-sets'); // 퀴즈 세트 요청
//   //console.log("API Response:", response); // 전체 응답을 출력하여 데이터 구조 확인
//   return response.data; // 필요한 데이터를 반환
// };

// Authorization 헤더 추가
export const getQuizSets = async (params = {}) => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.
  const response = await api.get('/api/quiz-sets', { 
    params,
    headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
   }); // params를 쿼리로 전달
  console.log("in service response.data: ", response.data);
  return response.data;
};