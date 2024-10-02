import api from './api';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    throw new Error('인증 토큰이 없습니다. 로그인이 필요합니다.');
  }
  return token;
};

// Authorization 헤더 추가 및 에러 핸들링 추가
export const getArchive = async (params = {}) => {
  const token = getToken();
  const response = await api.get('/api/archive', { 
    params,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  console.log("in service response.data: ", response.data);
  return response.data;
};

// 학습 진행 상태 조회 API 호출 함수 (테스트용 Authorization 헤더 추가)
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

// 학습 상태 업데이트 함수
export const updateArchiveStatus = async (materialId, data) => {
  const token = getToken();
  const response = await api.put(`/api/archive/${materialId}/progress`, data,{    
      headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};