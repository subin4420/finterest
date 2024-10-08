import { defineStore } from "pinia";

export const useTradeStore = defineStore("trade", {
  state: () => ({
    selectedStockCode: null, // 선택된 주식 코드
    stockPrice: 0, // 주식 가격 추가
  }),
  getters: {
    getSelectedStockCode(state) {
      return state.selectedStockCode;
    },
    getStockPrice(state) {
      return state.stockPrice;
    },
  },
  actions: {
    // 주식 코드를 설정하는 메서드
    setSelectedStockCode(code) {
      this.selectedStockCode = code;
    },
    // 주식 가격을 설정하는 메서드
    setStockPrice(price) {
      this.stockPrice = price;
    },
  },
});
