import axios from "axios";
import { useAuthStore } from "@/stores/auth"; // auth 스토어 import

// 백엔드 서버 주소를 환경 변수로 설정
const BASE_URL = import.meta.env.VITE_API_BASE_URL || "/api";

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true, // 쿠키를 포함한 요청을 위해
});

// 토큰을 가져오는 헬퍼 함수
const getToken = () => {
  const authStore = useAuthStore();
  const token = authStore.getToken();
  if (!token) {
    throw new Error("인증 토큰이 없습니다. 로그인이 필요합니다.");
  }
  return token;
};

// API 요청 전에 인증 헤더를 추가하는 인터셉터
api.interceptors.request.use(
  (config) => {
    try {
      const token = getToken();
      if (token) {
        config.headers["Authorization"] = `Bearer ${token}`;
      }
      console.log("요청 헤더:", config.headers);
    } catch (error) {
      console.error("인증 토큰 설정 중 오류 발생:", error);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

class TradeService {
  // 사용자 거래 기록을 가져오는 메서드
  async getUserTradeHistory() {
    try {
      const response = await api.get(`/trade/history`); // 경로 수정
      return response.data; // 거래 기록 반환
    } catch (error) {
      throw new Error("거래 기록을 가져오는 데 실패했습니다: " + error.message);
    }
  }

  // 새로운 거래 기록을 추가하는 메서드
  async addTradeRecord(record, tradeType) {
    try {
      const endpoint =
        tradeType === "buy" ? `/trade/stock/buy` : `/trade/stock/sell`; // 거래 유형에 따라 URL 변경
      const response = await api.post(endpoint, record);
      return response.data; // 추가된 거래 기록 반환
    } catch (error) {
      throw new Error("거래 기록을 추가하는 데 실패했습니다: " + error.message);
    }
  }

  async buyStock(stockData) {
    const token = getToken(); // 토큰 가져오기
    const response = await api.post("/trade/stock/buy", stockData, {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더 추가
        "Content-Type": "application/json", // Content-Type 헤더 추가
      },
    });
    return response;
  }

  async sellStock(stockData) {
    const token = getToken(); // 토큰 가져오기
    const response = await api.post("/trade/stock/sell", stockData, {
      headers: {
        Authorization: `Bearer ${token}`, // Authorization 헤더 추가
        "Content-Type": "application/json", // Content-Type 헤더 추가
      },
    });
    return response;
  }

  // 사용자 자산 정보를 가져오는 메서드
  async getUserFunds() {
    try {
      const response = await api.get("/trade/held"); // 자산 정보 API 호출
      return response.data; // 자산 정보 반환
    } catch (error) {
      throw new Error("자산 정보를 가져오는 데 실패했습니다: " + error.message);
    }
  }

  // 포트폴리오 일별 수익률을 가져오는 메서드
  async fetchDailyPortfolioData() {
    try {
      const response = await api.get("/portfolio/daily"); // 기존 api 객체 사용
      const data = response.data; // 데이터 받아오기
      console.log(data);
      return { data }; // 데이터를 반환
    } catch (error) {
      throw new Error("포트폴리오 데이터를 가져오는 데 실패했습니다.");
    }
  }
}

export default new TradeService();
