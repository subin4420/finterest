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
// export const getArchive = async (params = {}) => {
//   const token = getToken();
//   const response = await api.get('/api/archive', { 
//     params,
//     headers: {
//       Authorization: `Bearer ${token}`,
//     },
//   });
//   console.log("in service response.data: ", response.data);
//   return response.data;
// };

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

// 주별/월별 학습자료 조회수
export const getArchiveChart = async ({ period, year, month }) => {
  try {
    const token = getToken();
    const url = `/api/admin/chart/archive-completion/${period}`; // 경로 수정
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
    console.log('Response data from getArchiveChart:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching archive chart data:', error);
    throw error;
  }
};


