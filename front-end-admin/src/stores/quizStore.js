import { reactive, toRefs } from 'vue';
import { getQuizSets, getQuizQuestions, getQuizChart } from '../services/quizService';

const state = reactive({
  quizSets: [], // 퀴즈 세트를 저장할 상태
  currentQuizQuestions: null,
  quizResults: [],  // 퀴즈 결과를 저장할 상태

  weeklyQuizCounts: [], // 주별 학습 자료 조회수
  monthlyQuizCounts: [], // 월별 학습 자료 조회수
});

// 퀴즈 세트 가져오기
const fetchQuizSets = async () => {
  try {
    const data = await getQuizSets(); // 퀴즈 세트 가져오기
    console.log("Fetched Data:", data); // 전체 데이터를 먼저 로그로 확인
    if (data && Array.isArray(data.quizSets)) {
      state.quizSets = data.quizSets; // 퀴즈 세트 저장
    } else {
      console.error("Quiz sets not found in the response or invalid format.");
      state.quizSets = [];
    }
  } catch (error) {
    console.error("Failed to fetch quiz sets:", error);
    state.quizSets = [];
  }
};

// 특정 퀴즈 세트의 문제들 가져오기
const fetchQuizQuestions = async (setId) => {
  try {
    const data = await getQuizQuestions(setId);
    state.currentQuizQuestions = data;
  } catch (error) {
    console.error("Failed to fetch quiz questions:", error);
  }
};

// 주별 퀴즈 조회수 가져오기
const fetchWeeklyQuizCounts = async (year, month) => {
  try {
    const data = await getQuizChart({ period: 'weekly', year, month });
    state.weeklyQuizCounts = data.quizCompletionCounts || []; // 주별 퀴즈 조회수 할당
  } catch (error) {
    console.error('Error fetching weekly quiz counts:', error);
  }
};

// 월별 퀴즈 조회수 가져오기
const fetchMonthlyQuizCounts = async (year) => {
  try {
    const data = await getQuizChart({ period: 'monthly', year });
    state.monthlyQuizCounts = data.quizCompletionCounts || []; // 월별 퀴즈 조회수 할당
  } catch (error) {
    console.error('Error fetching monthly quiz counts:', error);
  }
};




export const useQuizStore = () => {
  return {
    ...toRefs(state), // state를 toRefs로 반환하여 반응성 유지
    fetchQuizSets,
    fetchQuizQuestions,
    fetchWeeklyQuizCounts,
    fetchMonthlyQuizCounts
  };
};
