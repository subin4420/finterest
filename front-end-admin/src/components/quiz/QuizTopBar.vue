<template>
  <div class="quiz-top-bar">
    <div class="quiz-info">
      <span class="question-count">{{ currentQuestionIndex }} / {{ totalQuestions }}</span>
      <span class="timer">{{ formattedTime }}</span>
      <span class="score">점수: {{ score }}</span>
    </div>
    <div class="progress-bar">
      <div class="progress" :style="{ width: progressPercentage + '%' }"></div>
    </div>
    <div class="quiz-actions">
      <button @click="submitQuiz" class="action-button submit-quiz" :disabled="!allQuestionsAnswered">퀴즈 제출</button>
      <button @click="endQuiz" class="action-button end-quiz">목록으로</button>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from 'vue';

export default {
  name: 'QuizTopBar',
  props: {
    currentQuestionIndex: Number,
    totalQuestions: Number,
    score: Number,
    allQuestionsAnswered: Boolean,
  },
  emits: ['end-quiz', 'bookmark', 'hint', 'submit-quiz'],
  setup(props, { emit }) {
    const startTime = ref(Date.now());
    const currentTime = ref(Date.now());
    const timer = ref(null);

    const progressPercentage = computed(() => 
      (props.currentQuestionIndex / props.totalQuestions) * 100
    );

    const formattedTime = computed(() => {
      const diff = Math.floor((currentTime.value - startTime.value) / 1000);
      const minutes = Math.floor(diff / 60);
      const seconds = diff % 60;
      return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    });

    const updateTimer = () => {
      currentTime.value = Date.now();
    };

    onMounted(() => {
      timer.value = setInterval(updateTimer, 1000);
    });

    onUnmounted(() => {
      if (timer.value) clearInterval(timer.value);
    });

    const bookmarkQuestion = () => emit('bookmark');
    const showHint = () => emit('hint');
    const submitQuiz = () => emit('submit-quiz');
    const endQuiz = () => emit('end-quiz');

    return {
      progressPercentage,
      formattedTime,
      bookmarkQuestion,
      showHint,
      submitQuiz,
      endQuiz,
    };
  }
}
</script>

<style scoped>
.quiz-top-bar {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px 20px;
  margin-bottom: 20px;
}

.quiz-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.question-count, .timer, .score {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.progress-bar {
  width: 100%;
  height: 6px;
  background-color: #e0e0e0;
  border-radius: 3px;
  overflow: hidden;
  margin-bottom: 10px;
}

.progress {
  height: 100%;
  background-color: #4CAF50;
  transition: width 0.3s ease;
}

.quiz-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.action-button {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  background-color: #2196F3;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.action-button:hover {
  background-color: #1976D2;
}

.submit-quiz {
  background-color: #4CAF50;
}

.submit-quiz:hover:not(:disabled) {
  background-color: #45a049;
}

.submit-quiz:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.end-quiz {
  background-color: #f44336;
}

.end-quiz:hover {
  background-color: #d32f2f;
}
</style>
