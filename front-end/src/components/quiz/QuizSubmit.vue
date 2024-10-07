<!-- <QuizSubmit :quizSet="selectedQuizSet" @goBack="goBackToQuizList" /> -->
<!-- {
  "setId": 1,
  "setName": "경제 기본 상식 세트 1",
  "setImg": "img1.png",
  "categoryName": "경제 기본 상식",
  "createdAt": "2024-10-04"
} -->

<template>
  <div class="quiz-container">
    <div class="quiz-sidebar">
      <h3>퀴즈 세트</h3>
      <div v-for="category in categories" :key="category" class="category">
        <h4>{{ category }}</h4>
        <ul>
          <li v-for="set in quizSetsByCategory(category)" :key="set.setId" 
              :class="{ 'current-set': set.setId === quizSet.setId }"
              @click="selectQuizSet(set)">
            <span class="quiz-set-name">{{ set.setName }}</span>
            <span v-if="set.setId === quizSet.setId" class="current-indicator">*</span>
          </li>
        </ul>
      </div>
    </div>
    
    <div class="quiz-problem">
      <div class="quiz-header">
        <h2>{{ quizSet.setName }}</h2>
      </div>
      <div v-if="questions.length > 0" class="question-card">
        <div class="question-number">문제 {{ currentQuestionIndex + 1 }} / {{ questions.length }}</div>
        <div class="question">
          <h3>{{ currentQuestion.question }}</h3>
          <ul class="choices">
            <li v-for="i in 4" :key="i" 
                @click="selectAnswer(i)" 
                :class="{ selected: userAnswers[currentQuestionIndex] === i }">
              {{ i }}. {{ currentQuestion[`choice${i}`] }}
            </li>
          </ul>
        </div>
        <div class="navigation-buttons">
          <button @click="previousQuestion" :disabled="currentQuestionIndex === 0">이전 문제</button>
          <button @click="nextQuestion" :disabled="currentQuestionIndex === questions.length - 1">다음 문제</button>
        </div>
      </div>
      <div v-else class="loading">
        문제를 불러오는 중입니다...
      </div>
    </div>
    
    <!-- 모달 컨테이너를 body에 추가 -->
    <teleport to="body">
      <QuizResultModal 
        :show="showResultModal"
        :result="quizResult"
        @close="closeResultModal"
        @view-answers="openAnswersModal"
        @go-to-quiz-list="goToQuizList"
      />

      <QuizAnswersModal
        :show="showAnswersModal"
        :answers="detailedAnswers"
        @close="closeAnswersModal"
        @go-to-quiz-list="goToQuizList"
      />
    </teleport>
  </div>
</template>

<script>
import { ref, computed, onMounted, watch, toRef } from 'vue';
import { useQuizStore } from '@/stores/quizStore';
import QuizResultModal from './QuizResultModal.vue';
import QuizAnswersModal from './QuizAnswersModal.vue';

export default {
  components: {
    QuizResultModal,
    QuizAnswersModal
  },
  props: {
    quizSet: Object,
  },
  emits: ['goBack', 'changeQuizSet', 'quizSubmitted', 'updateQuizProgress'],
  setup(props, { emit }) {
    const quizStore = useQuizStore();
    const questions = ref([]);
    const allQuizSets = ref([]);
    const currentQuestionIndex = ref(0);
    const userAnswers = ref([]);

    // props.quizSet을 반응형 참조로 변환
    const currentQuizSet = toRef(props, 'quizSet');

    const allQuestionsAnswered = computed(() => {
      return userAnswers.value.every(answer => answer !== null);
    });

    const updateQuizProgress = () => {
      const answeredQuestions = userAnswers.value.filter(answer => answer !== null).length;
      emit('updateQuizProgress', {
        currentQuestionIndex: currentQuestionIndex.value + 1,
        totalQuestions: questions.value.length,
        allQuestionsAnswered: allQuestionsAnswered.value,
        answeredQuestions: answeredQuestions
      });
    };

    watch([userAnswers, currentQuestionIndex], updateQuizProgress);

    const loadQuizQuestions = async (setId) => {
      try {
        await quizStore.fetchQuizQuestions(setId);
        questions.value = quizStore.currentQuizQuestions.value.questions;
        console.log("Fetched questions:", JSON.stringify(questions.value, null, 2));
        userAnswers.value = new Array(questions.value.length).fill(null);
        currentQuestionIndex.value = 0; // 첫 번째 문제로 리셋
        updateQuizProgress();
      } catch (error) {
        console.error('데이터를 불러오는 데 실패했습니다:', error);
      }
    };

    onMounted(async () => {
      await loadQuizQuestions(currentQuizSet.value.setId);
      await quizStore.fetchQuizSets();
      allQuizSets.value = quizStore.quizSets.value;
    });

    // quizSet이 변경될 때마다 새로운 퀴즈 문제를 불러옵니다.
    watch(currentQuizSet, (newQuizSet) => {
      loadQuizQuestions(newQuizSet.setId);
    });

    const categories = computed(() => {
      return [...new Set(allQuizSets.value.map(set => set.categoryName))];
    });

    const quizSetsByCategory = (category) => {
      return allQuizSets.value.filter(set => set.categoryName === category);
    };

    const currentQuestion = computed(() => questions.value[currentQuestionIndex.value] || {});

    const nextQuestion = () => {
      if (currentQuestionIndex.value < questions.value.length - 1) {
        currentQuestionIndex.value++;
      }
    };

    const previousQuestion = () => {
      if (currentQuestionIndex.value > 0) {
        currentQuestionIndex.value--;
      }
    };

    const selectAnswer = (answerIndex) => {
      userAnswers.value[currentQuestionIndex.value] = answerIndex;
      updateQuizProgress();
    };

    const selectQuizSet = (set) => {
      emit('changeQuizSet', set);
    };

    const goBack = () => {
      emit('goBack');
    };

    // 결과 모달 관련 상태 추가
    const showResultModal = ref(false);
    const quizResult = ref(null);
    const showAnswersModal = ref(false);
    const detailedAnswers = ref(null);

    const submitQuiz = async () => {
      if (!allQuestionsAnswered.value) return;

      console.log("Questions before submission:", JSON.stringify(questions.value, null, 2));

      const answers = questions.value.map((question, index) => {
        console.log("Processing question:", question);
        return {
          quizId: question.id || question.quizId, // id나 quizId 중 존재하는 것을 사용
          selectedChoice: userAnswers.value[index]
        };
      });

      const submitData = {
        answers: answers
      };

      console.log("Final submit data:", JSON.stringify(submitData, null, 2));

      try {
        const result = await quizStore.submitQuizAnswers(props.quizSet.setId, submitData);
        console.log("Quiz submission result:", result);
        quizResult.value = result;
        showResultModal.value = true;
        emit('quizSubmitted', result);
      } catch (error) {
        console.error('퀴즈 출 실패:', error);
      }
    };

    const closeResultModal = () => {
      showResultModal.value = false;
    };

    const openAnswersModal = async (resultId) => {
      try {
        const answers = await quizStore.fetchQuizAnswers(props.quizSet.setId, resultId);
        detailedAnswers.value = answers;
        showResultModal.value = false; // 결과 모달 닫기
        showAnswersModal.value = true; // 답변 모달 열기
      } catch (error) {
        console.error('퀴즈 답변 조회 실패:', error);
      }
    };

    const closeAnswersModal = () => {
      showAnswersModal.value = false;
    };

    const goToQuizList = () => {
      emit('goBack');
    };

    return {
      questions,
      goBack,
      categories,
      quizSetsByCategory,
      selectQuizSet,
      currentQuestion,
      currentQuestionIndex,
      nextQuestion,
      previousQuestion,
      selectAnswer,
      userAnswers,
      allQuestionsAnswered,
      submitQuiz,
      updateQuizProgress,
      showResultModal,
      quizResult,
      closeResultModal,
      showAnswersModal,
      detailedAnswers,
      openAnswersModal,
      closeAnswersModal,
      goToQuizList
    };
  }
}
</script>

<style scoped>
.quiz-container {
  display: flex;
  padding: 2rem;
}

.quiz-sidebar {
  min-width: 200px;
  max-width: 300px;
  width: auto;
  margin-right: 2rem;
  padding: 1rem;
  background-color: #f5f5f5;
  border-radius: 8px;
  align-self: flex-start;
}

.quiz-problem {
  flex: 1;
}

.category {
  margin-bottom: 1rem;
}

.category h4 {
  margin-bottom: 0.5rem;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

li {
  margin-bottom: 0.5rem;
  cursor: pointer;
  padding: 5px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

li:hover {
  background-color: #e0e0e0;
}

.current-set {
  font-weight: bold;
  color: #007bff;
  background-color: #e6f2ff;
}

.quiz-set-name {
  display: inline-block;
  width: calc(100% - 20px);
}

.current-indicator {
  display: inline-block;
  width: 20px;
  text-align: center;
}

.quiz-header {
  margin-bottom: 1rem;
  padding: 1rem;
  background-color: #f0f0f0;
  border-radius: 8px;
}

.quiz-header h2 {
  margin: 0;
  color: #333;
}

.question-card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 2rem;
  margin-bottom: 2rem;
}

.question-number {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 1rem;
}

.question h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 1.5rem;
}

.choices {
  list-style-type: none;
  padding-left: 0;
}

.choices li {
  margin-bottom: 1rem;
  padding: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.choices li:hover {
  background-color: #f5f5f5;
}

.choices li.selected {
  background-color: #e6f2ff;
  border-color: #007bff;
  color: #007bff;
}

.navigation-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 2rem;
}

.navigation-buttons button {
  padding: 0.5rem 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.navigation-buttons button:hover:not(:disabled) {
  background-color: #0056b3;
}

.navigation-buttons button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.submit-button {
  display: block;
  width: 100%;
  padding: 1rem;
  margin-top: 2rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.submit-button:hover:not(:disabled) {
  background-color: #218838;
}

.submit-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.loading {
  text-align: center;
  padding: 2rem;
  font-style: italic;
  color: #666;
}

/* 모달 스타일 추가 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 80%;
  max-height: 80%;
  overflow-y: auto;
}
</style>