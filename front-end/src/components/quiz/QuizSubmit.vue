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
      <h2>{{ quizSet.setName }} 문제 풀기</h2>
      <div v-if="questions.length > 0" class="question-container">
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
      <div v-else>
        문제를 불러오는 중입니다...
      </div>
      <!-- <button @click="submitQuiz" :disabled="!allQuestionsAnswered" class="submit-button">퀴즈 제출</button> -->
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed, watch } from 'vue';
import { useQuizStore } from '@/stores/quizStore';

export default {
  name: 'QuizSubmit',
  props: {
    quizSet: {
      type: Object,
      required: true
    }
  },
  emits: ['changeQuizSet', 'goBack', 'quizSubmitted', 'updateQuizProgress'],
  setup(props, { emit }) {
    const quizStore = useQuizStore();
    const questions = ref([]);
    const allQuizSets = ref([]);
    const currentQuestionIndex = ref(0);
    const userAnswers = ref([]);

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

    onMounted(async () => {
      try {
        await quizStore.fetchQuizQuestions(props.quizSet.setId);
        questions.value = quizStore.currentQuizQuestions.value.questions;
        console.log("Fetched questions:", JSON.stringify(questions.value, null, 2));
        userAnswers.value = new Array(questions.value.length).fill(null);
        
        await quizStore.fetchQuizSets();
        allQuizSets.value = quizStore.quizSets.value;
        updateQuizProgress();
      } catch (error) {
        console.error('데이터를 불러오는 데 실패했습니다:', error);
      }
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
        await quizStore.submitQuizAnswers(props.quizSet.setId, submitData);
        emit('quizSubmitted');
      } catch (error) {
        console.error('퀴즈 제출 실패:', error);
      }
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
      updateQuizProgress
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

.question {
  margin-bottom: 1.5rem;
}

.question-container {
  margin-bottom: 2rem;
}

.choices {
  list-style-type: none;
  padding-left: 0;
}

.choices li {
  margin-bottom: 0.5rem;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.choices li:hover {
  background-color: #f0f0f0;
}

.choices li.selected {
  background-color: #e6f2ff;
  border-color: #007bff;
}

.navigation-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}

.navigation-buttons button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.navigation-buttons button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.submit-button {
  margin-top: 1rem;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.submit-button:hover:not(:disabled) {
  background-color: #45a049;
}

.submit-button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}
</style>