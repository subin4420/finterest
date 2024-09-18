import { defineStore } from 'pinia';
import { getPortfolio } from '@/services/tradingService';

export const useTradingStore = defineStore('trading', {
  state: () => ({
    portfolio: [],
  }),
  actions: {
    async fetchPortfolio() {
      try {
        this.portfolio = await getPortfolio();
      } catch (error) {
        console.error('Error fetching portfolio:', error);
      }
    },
  },
});
