import { reactive, toRefs } from 'vue';
import { getQuizSets, getQuizQuestions, submitQuizAnswers as submitQuizAnswersService, getQuizAnswers, fetchQuizResults as fetchQuizResultsService } from '../services/quizService';

const state = reactive({
  quizSets: [], // 퀴즈 세트를 저장할 상태
  currentQuizQuestions: null,
  quizResults: [],  // 퀴즈 결과를 저장할 상태
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

// 퀴즈 답변 제출
const submitQuizAnswers = async (setId, answers) => {
  try {
    const response = await submitQuizAnswersService(setId, answers);
    return response;
  } catch (error) {
    console.error("Failed to submit quiz answers:", error);
    throw error;
  }
};

// 퀴즈 결과 조회하기
const fetchQuizResults = async (userId) => {
  try {
    const data = await fetchQuizResultsService(userId); // API 호출
    state.quizResults = data.quizResults || []; // 퀴즈 결과 상태에 저장
    console.log("Fetched quiz results:", state.quizResults);
  } catch (error) {
    console.error("Failed to fetch quiz results:", error);
  }
};

// 퀴즈 답변 상세 결과 가져오기
const fetchQuizAnswers = async (setId, resultId) => {
  try {
    const response = await getQuizAnswers(setId, resultId);
    return response.answers;
  } catch (error) {
    console.error("Failed to fetch quiz answers:", error);
    throw error;
  }
};

export const useQuizStore = () => {
  return {
    ...toRefs(state), // state를 toRefs로 반환하여 반응성 유지
    fetchQuizSets,
    fetchQuizQuestions,
    submitQuizAnswers,
    fetchQuizAnswers,
    fetchQuizResults
  };
};
