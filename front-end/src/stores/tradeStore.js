import { defineStore } from 'pinia';
import TradeService from '@/services/tradeService'; // TradeService import

export const useTradeStore = defineStore('trade', {
  state: () => ({
    selectedStockCode: null, // 선택된 주식 코드
    stockPrice: 0, // 주식 가격
    tradeHistory: [], // 거래 기록
  }),
  getters: {
    // 선택된 주식 코드를 반환하는 getter
    getSelectedStockCode(state) {
      return state.selectedStockCode;
    },
    // 주식 가격을 반환하는 getter
    getStockPrice(state) {
      return state.stockPrice;
    },
    // 거래 기록을 반환하는 getter
    getTradeHistory(state) {
      return state.tradeHistory;
    },
  },
  actions: {
    // 주식 코드를 설정하는 액션
    setSelectedStockCode(code) {
      this.selectedStockCode = code;
    },
    // 주식 가격을 설정하는 액션
    setStockPrice(price) {
      this.stockPrice = price;
    },
    // 거래 기록을 추가하는 액션
    addTradeRecord(record) {
      this.tradeHistory.push(record);
    },
    // 사용자 거래 기록을 백엔드에서 가져오는 액션
    async fetchUserTradeHistory(userId) {
      try {
        const history = await TradeService.getUserTradeHistory(userId);
        history.forEach((record) => this.addTradeRecord(record)); // 각 거래 기록을 스토어에 추가
      } catch (error) {
        console.error('거래 기록을 가져오는 데 실패했습니다:', error.message);
      }
    },
    // 새로운 거래 기록을 생성하고 스토어에 추가하는 액션
    async createTradeRecord(userId, record) {
      try {
        const newRecord = await TradeService.addTradeRecord(userId, record);
        this.addTradeRecord(newRecord); // 추가된 거래 기록을 스토어에 추가
      } catch (error) {
        console.error('거래 기록을 추가하는 데 실패했습니다:', error.message);
      }
    },
    // 주식 매수 액션
    async buyStock(stockData) {
      try {
        const newRecord = await TradeService.buyStock(stockData);
        this.addTradeRecord(newRecord); // 매수 거래 기록을 스토어에 추가
      } catch (error) {
        console.error('주식 매수 중 오류 발생:', error.message);
      }
    },
    // 주식 매도 액션
    async sellStock(stockData) {
      try {
        const newRecord = await TradeService.sellStock(stockData);
        this.addTradeRecord(newRecord); // 매도 거래 기록을 스토어에 추가
      } catch (error) {
        console.error('주식 매도 중 오류 발생:', error.message);
      }
    },
  },
});
