import { reactive, toRefs } from 'vue';
import { getQuizSets } from '../services/quizService';

const state = reactive({
  quizSets: [], // 퀴즈 세트를 저장할 상태
});

// 퀴즈 세트 가져오기
const fetchQuizSets = async () => {
  try {
    const data = await getQuizSets(); // 퀴즈 세트 가져오기
    console.log("Fetched Data:", data); // 전체 데이터를 먼저 로그로 확인
    if (data && data.quizSets) {
      state.quizSets = data.quizSets; // 퀴즈 세트 저장
    } else {
      console.error("Quiz sets not found in the response.");
    }
  } catch (error) {
    console.error("Failed to fetch quiz sets:", error);
  }
};

// 추가적인 store 메서드들 필요 시 여기에 추가 가능

export const useQuizStore = () => {
  return {
    ...toRefs(state), // state를 toRefs로 반환하여 반응성 유지
    fetchQuizSets,
  };
};
