import { ref } from 'vue';
import { getQuizSets } from '../services/quizService';

const quizSets = ref([]); // 퀴즈 세트를 저장할 상태

export const useQuizStore = () => {
  const fetchQuizSets = async () => {
    try {
      const data = await getQuizSets(); // 퀴즈 세트 가져오기
      quizSets.value = data; // 퀴즈 세트 저장
      console.log("Fetched Quiz Sets:", quizSets.value); // 가져온 데이터 확인
    } catch (error) {
      console.error("Failed to fetch quiz sets:", error);
    }
  };

  return {
    quizSets,
    fetchQuizSets,
  };
};
