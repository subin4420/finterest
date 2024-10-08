import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api';

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true, // 인증 관련 쿠키를 포함하여 요청 보냄
});

// 토큰을 가져오는 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    // 토큰이 없으면 에러 발생
    console.error('Authentication token is missing.');
    throw new Error('Authentication token is missing. Please log in.');
  }
  return token;
};

// 요청 인터셉터 설정
api.interceptors.request.use(
  (config) => {
    try {
      const token = getToken(); // 토큰을 가져옴
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`; // Authorization 헤더 설정
      }
    } catch (error) {
      console.error('Error setting authentication token:', error.message); // 에러 메시지 출력
      return Promise.reject(error); // 요청 실패로 처리
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// TradeService 클래스 정의
class TradeService {
  // 거래 기록 조회
  async getUserTradeHistory() {
    try {
      const response = await api.get(`/trade/history`);
      return response.data;
    } catch (error) {
      console.error(
        'Error fetching trade history:',
        error.response ? error.response.data : error.message
      );
      throw new Error(
        'Failed to fetch trade history: ' +
          (error.response?.data?.message || error.message)
      );
    }
  }

  // 보유 주식 조회
  async viewStockHeld() {
    try {
      const response = await api.get(`/trade/held`);
      return response.data;
    } catch (error) {
      console.error(
        'Error fetching held stocks:',
        error.response ? error.response.data : error.message
      );
      throw new Error(
        'Failed to fetch held stocks: ' +
          (error.response?.data?.message || error.message)
      );
    }
  }

  // 주식 구매
  async buyStock(record) {
    try {
      const response = await api.post(`/trade/stock/buy`, record);
      console.log('Buy stock response:', response.data);
      return response.data;
    } catch (error) {
      console.error(
        'Error buying stock:',
        error.response ? error.response.data : error.message
      );
      throw new Error(
        'Failed to buy stock: ' +
          (error.response?.data?.message || error.message)
      );
    }
  }

  // 주식 판매
  async sellStock(record) {
    try {
      const response = await api.post(`/trade/stock/sell`, record);
      console.log('Sell stock response:', response.data);
      return response.data;
    } catch (error) {
      console.error(
        'Error selling stock:',
        error.response ? error.response.data : error.message
      );
      throw new Error(
        'Failed to sell stock: ' +
          (error.response?.data?.message || error.message)
      );
    }
  }
}

export default new TradeService();
