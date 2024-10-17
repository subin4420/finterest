import axios from 'axios';
import { useAuthStore } from '@/stores/auth'; // auth 스토어 import

// 백엔드 서버 주소를 환경 변수로 설정
const BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api';

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true, // 쿠키를 포함한 요청을 위해
});

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    throw new Error('인증 토큰이 없습니다. 로그인이 필요합니다.');
  }
  console.log('Fetched token:', token);
  return token;
};

// API 요청 전에 인증 헤더를 추가하는 인터셉터
api.interceptors.request.use(
    (config) => {
      try {
        const token = getToken();
        if (token) {
          config.headers['Authorization'] = `Bearer ${token}`;
        }
        console.log('요청 헤더:', config.headers);
      } catch (error) {
        console.error('인증 토큰 설정 중 오류 발생:', error);
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
);

export const getLatestConversionRate = async () => {
  try {
    const token = getToken();
    const response = await api.get('/conversion/rate/latest', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('환전 비율을 가져오는 중 오류 발생:', error);
    throw error;
  }
};

// 포인트를 모의투자금으로 전환하는 함수
export const convertPointsToMoney = async (pointAmount) => {
  try {
    const token = getToken();
    const response = await api.post(
        '/conversion/points-to-money',
        { pointAmount },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
    );
    return response.data;
  } catch (error) {
    console.error('포인트를 모의투자금으로 전환하는 중 오류 발생:', error);
    throw error;
  }
};

// 모의투자금을 포인트로 전환하는 함수
export const convertMoneyToPoints = async (moneyAmount) => {
  try {
    const token = getToken();
    const response = await api.post(
        '/conversion/money-to-points',
        { moneyAmount },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
    );
    return response.data;
  } catch (error) {
    console.error('모의투자금을 포인트로 전환하는 중 오류 발생:', error);
    throw error;
  }
};

export const getConversionTransactions = async () => {
  try {
    const response = await api.get('/conversion/transactions');
    return response.data;
  } catch (error) {
    console.error('환전 거래 내역을 가져오는 중 오류 발생:', error);
    if (error.response) {
      console.error('에러 응답:', error.response.data);
    }
    throw error;
  }
};

export const getUserBalance = async () => {
  try {
    const response = await api.get('/conversion/balance');
    console.log('API Response (getUserBalance):', response);
    console.log('API Response data:', response.data);
    return response.data;
  } catch (error) {
    console.error('잔액 조회 중 오류 발생:', error);
    throw error;
  }
};
