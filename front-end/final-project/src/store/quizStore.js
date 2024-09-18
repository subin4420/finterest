import { defineStore } from 'pinia';
import { getQuizList } from '@/services/quizService';

export const useQuizStore = defineStore('quiz', {
  state: () => ({
    quizzes: [],
  }),
  actions: {
    async fetchQuizzes() {
      try {
        this.quizzes = await getQuizList();
      } catch (error) {
        console.error('Error fetching quizzes:', error);
      }
    },
  },
});
