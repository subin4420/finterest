import { defineStore } from 'pinia';
import tradeService from '@/services/tradeService'; // TradeService import

export const useTradeStore = defineStore('trade', {
  state: () => ({
    selectedStockCode: null, // 선택된 주식 코드
    stockPrice: 0, // 주식 가격
    tradeHistory: [], // 거래 기록
    stockHeld: {}, // 보유 주식 정보
    loading: false, // 로딩 상태
    error: null, // 에러 메시지
  }),
  getters: {
    getSelectedStockCode(state) {
      return state.selectedStockCode;
    },
    getStockPrice(state) {
      return state.stockPrice;
    },
    getTradeHistory(state) {
      return state.tradeHistory;
    },
    getStockHeld(state) {
      return state.stockHeld;
    },
    isLoading(state) {
      return state.loading;
    },
    getError(state) {
      return state.error;
    },
  },
  actions: {
    setSelectedStockCode(code) {
      this.selectedStockCode = code;
    },
    setStockPrice(price) {
      this.stockPrice = price;
    },
    addTradeRecord(record) {
      this.tradeHistory.push(record);
    },

    // 사용자 거래 기록을 백엔드에서 가져오는 액션
    async fetchUserTradeHistory() {
      this.loading = true;
      this.error = null;
      try {
        const history = await tradeService.getUserTradeHistory();
        this.tradeHistory = history; // 거래 기록을 스토어에 저장
      } catch (error) {
        this.error = '거래 기록을 가져오는 데 실패했습니다.';
        console.error('거래 기록을 가져오는 데 실패했습니다:', error.message);
      } finally {
        this.loading = false;
      }
    },

    // 사용자 보유 주식 정보를 백엔드에서 가져오는 액션
    async fetchStockHeld() {
      this.loading = true;
      this.error = null;
      try {
        const stockHeld = await tradeService.viewStockHeld();
        this.stockHeld = stockHeld; // 보유 주식 정보를 스토어에 저장
      } catch (error) {
        this.error = '보유 주식 정보를 가져오는 데 실패했습니다.';
        console.error(
          '보유 주식 정보를 가져오는 데 실패했습니다:',
          error.message
        );
      } finally {
        this.loading = false;
      }
    },

    // 새로운 거래 기록을 생성하는 액션
    async createTradeRecord(record) {
      this.loading = true;
      this.error = null;
      try {
        const newRecord = await tradeService.addTradeRecord(record);
        this.addTradeRecord(newRecord); // 추가된 거래 기록을 스토어에 추가
      } catch (error) {
        this.error = '거래 기록을 추가하는 데 실패했습니다.';
        console.error('거래 기록을 추가하는 데 실패했습니다:', error.message);
      } finally {
        this.loading = false;
      }
    },

    // 주식 판매 액션
    async sellTradeRecord(record) {
      this.loading = true;
      this.error = null;
      try {
        const sellResult = await tradeService.sellTradeRecord(record);
        console.log('주식 판매 성공:', sellResult);
        await this.fetchStockHeld(); // 주식 판매 후 보유 주식 정보 갱신
      } catch (error) {
        this.error = '주식을 판매하는 데 실패했습니다.';
        console.error('주식을 판매하는 데 실패했습니다:', error.message);
      } finally {
        this.loading = false;
      }
    },
  },
});
