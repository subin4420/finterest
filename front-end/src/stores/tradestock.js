import { defineStore } from 'pinia';

export const useStockStore = defineStore('stock', {
  state: () => ({
    selectedStockCode: null,
  }),
  actions: {
    setSelectedStockCode(code) {
      this.selectedStockCode = code;
    },
  },
});
