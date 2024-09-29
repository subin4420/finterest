import api from './api';

// Authorization 헤더 추가 X
// export const getArchive = async (params = {}) => {
//   const response = await api.get('/api/archive', { params }); // params를 쿼리로 전달
//   console.log("in service response.data: ", response.data);
//   return response.data;
// };

// Authorization 헤더 추가
export const getArchive = async (params = {}) => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.
  const response = await api.get('/api/archive', { 
    params,
    headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
   }); // params를 쿼리로 전달
  console.log("in service response.data: ", response.data);
  return response.data;
};

// 학습 진행 상태 조회 API 호출 함수 (테스트용 Authorization 헤더 추가)
export const getArchiveProgress = async (params = {}) => {
  try {
    // 테스트용 토큰
    const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.

    const response = await api.get('/api/archive/progress', {
      params,
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
      },
    });

    return response.data;
  } catch (error) {
    console.error('Error fetching archive progress:', error);
    throw error;
  }
};

// 포스트 요청에도 Authorization 헤더 추가
export const postArchive = async () => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.
  
  const response = await api.post('/api/archive', {}, {
    headers: {
      Authorization: `Bearer ${token}`, // Authorization 헤더에 토큰 추가
    },
  });
  
  return response.data;
};

// 즐겨찾기 추가
export const addFavorite = async (materialId) => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // JWT 토큰
  const response = await api.post(`/api/archive/${materialId}/favorite`, {}, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

// 즐겨찾기 삭제
export const removeFavorite = async (materialId) => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // JWT 토큰
  const response = await api.delete(`/api/archive/${materialId}/favorite`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

// 학습 상태 업데이트 함수
export const updateArchiveStatus = async (materialId, data) => {
  const token = 'eyJhbGciOiJIUzI1NiIsInR...'; // 여기에 실제 테스트용 JWT 토큰을 추가하세요.
  const response = await api.put(`/api/archive/${materialId}/progress`, data,{    
      headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};