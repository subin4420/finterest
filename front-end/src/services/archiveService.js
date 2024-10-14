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

// Authorization 헤더 추가 및 에러 핸들링 추가
export const getArchive = async (params = {}) => {
  const token = getToken();
  
  // API 요청 (토큰이 있으면 Authorization 헤더 추가)
  const config = {
    params,
  };
  
  if (token) {
    config.headers = {
      Authorization: `Bearer ${token}`,
    };
  }

  // API 요청
  const response = await api.get('/api/archive', config);

  console.log("in service response.data: ", response.data);
  return response.data;
};



// 학습 진행 상태 조회 API 호출 함수 
export const getArchiveProgress = async (params = {}) => {
  try {
    const token = getToken();
    const response = await api.get('/api/archive/progress', {
      params,
      headers: {
        Authorization: `Bearer ${token}`,
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
  const token = getToken();
  const response = await api.post('/api/archive', {}, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  
  return response.data;
};

// 즐겨찾기 추가
export const addFavorite = async (materialId) => {
  const token = getToken();
  const response = await api.post(`/api/archive/${materialId}/favorite`, {}, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

// 즐겨찾기 삭제
export const removeFavorite = async (materialId) => {
  const token = getToken();
  const response = await api.delete(`/api/archive/${materialId}/favorite`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

// 학습 상태(incomplete) 추가
export const insertArchiveStatus = async(materialId, statusData) => {
  const token = getToken();
  try {
    const response = await api.post(`/api/archive/${materialId}/progress`, statusData,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Server response:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error in insertArchiveStatus:', error.response?.data || error.message);
    throw error; // 에러 발생 시 다시 throw
  }
};

// 학습 상태(completed) 업데이트 함수
export const updateArchiveStatus = async (materialId, statusData) => {
  const token = getToken();
  try {
    const response = await api.put(`/api/archive/${materialId}/progress`, statusData,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Server response:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error in updateArchiveStatus:', error.response?.data || error.message);
    throw error; // 에러 발생 시 다시 throw
  }
};

// 최근 업데이트된 텍스트 자료 8개 가져오기
export const getRecentTextArchives = async () => {
  try {
    const response = await api.get('/api/archive/recentText'); // JWT 토큰 없이 호출
    console.log('Fetched recent text archives:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching recent text archives:', error.response?.data || error.message);
    throw error; // 에러 발생 시 throw
  }
};

// 완료 횟수가 많은 영상 자료 8개 가져오기
export const getPopularVideoArchives = async () => {
  try {
    const response = await api.get('/api/archive/popularVideo'); // JWT 토큰 없이 호출
    console.log('Fetched popular video archives:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching popular video archives:', error.response?.data || error.message);
    throw error; // 에러 발생 시 throw
  }
};
