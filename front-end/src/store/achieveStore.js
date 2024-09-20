import { defineStore } from 'pinia';
import { getAchieve } from '@/services/achieveService';

export const useAchieveStore = defineStore('achieve', {
  state: () => ({
    achieves: [], // archives 배열
    
  }),
  actions: {
    async fetchAchieve() {
      try {
        this.achieves = await getAchieve(); // API에서 데이터 가져오기
      } catch (error) {
        console.error('Error fetching archives:', error);
      }
    },
  },
});
