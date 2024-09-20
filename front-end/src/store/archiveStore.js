import { defineStore } from 'pinia';
import { getArchive } from '@/services/archiveService';

export const useArchiveStore = defineStore('archive', {
  state: () => ({
    archives: [], // archives 배열
  }),
  actions: {
    async fetchArchive() {
      try {
        this.archives = await getArchive(); // API에서 데이터 가져오기
      } catch (error) {
        console.error('Error fetching archives:', error);
      }
    },
  },
});
