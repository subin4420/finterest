<template>
  <!-- isVisible와 cardData가 존재하고 quizzes가 있을 때만 모달을 렌더링 -->
  <div v-if="isVisible && cardData && cardData.quizzes" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <div class="modal-header">
        <button class="close-button" @click="closeModal">
          <i class="fas fa-times"></i>
        </button>
        <!-- 퀴즈 결과 -->
        <span class="userScore" v-if="cardData.totalScore && cardData.maxScore">
          {{ cardData.totalScore }}/{{ cardData.maxScore }}
        </span>
      </div>

      <!-- 네비게이션 버튼들 -->
      <div class="quiz-navigation">
        <button
          v-for="(quiz, index) in cardData.quizzes"
          :key="index"
          :class="['quiz-nav-button', { 'answered': answeredQuizzes[index], 'current': currentQuizIndex === index }]"
          @click="setCurrentQuiz(index)"
        >
          {{ index + 1 }}
        </button>
      </div>

      <!-- 퀴즈 컨텐츠 -->
      <div class="quiz-content" v-if="currentQuiz">
        <p>{{ currentQuiz.question }}</p>
        <div class="quiz-options">
          <label v-for="(option, index) in currentQuiz.options" :key="index">
            <input
              type="radio"
              :name="'quiz-option'"
              :value="index"
              v-model="currentQuiz.selectedAnswer"
            />
            {{ option }}
          </label>
        </div>
      </div>

      <!-- 이전, 다음 버튼 -->
      <div class="quiz-nav-buttons">
        <button @click="previousQuiz" :disabled="currentQuizIndex === 0">이전</button>
        <button @click="nextQuiz" :disabled="currentQuizIndex === cardData.quizzes.length - 1">다음</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: Boolean,
    cardData: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      currentQuizIndex: 0, // 현재 문제 번호
    };
  },
  computed: {
    currentQuiz() {
        console.log("currentQuiz-----------------------------");
      return this.cardData && this.cardData.quizzes && this.cardData.quizzes.length > 0
        ? this.cardData.quizzes[this.currentQuizIndex]
        : null;
    },
  },
  methods: {
    closeModal() {
      this.$emit('update:isVisible', false);
    },
    setCurrentQuiz(index) {
      this.currentQuizIndex = index;
    },
    previousQuiz() {
      if (this.currentQuizIndex > 0) {
        this.currentQuizIndex--;
      }
    },
    nextQuiz() {
      if (this.currentQuizIndex < this.cardData.quizzes.length - 1) {
        this.currentQuizIndex++;
      }
    }
  }
};
</script>
<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    padding: 30px;
    border-radius: 15px;
    max-width: 80%;
    max-height: 80%;
    overflow-y: auto;
    z-index: 1001;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.close-button {
    color: #333;
    background: none;
    border: none;
    cursor: pointer;
    font-size: 24px;
    transition: color 0.3s ease;
}

.close-button:hover {
    color: #00c4d1; /* 마우스를 올렸을 때 색상 변경 */
}

.quiz-navigation {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.quiz-nav-button {
    width: 30px;
    height: 30px;
    margin: 0 5px;
    border: none;
    border-radius: 50%;
    background-color: #ddd;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.quiz-nav-button.answered {
    background-color: #2ecc71;
    color: white;
}

.quiz-nav-button.current {
    background-color: #3498db;
    color: white;
}

.quiz-content {
    margin-bottom: 20px;
}

.quiz-options {
    display: flex;
    flex-direction: column;
}

.quiz-options label {
    margin: 10px 0;
    cursor: pointer;
}

.quiz-options input[type="radio"] {
    margin-right: 10px;
}

.quiz-nav-buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

button {
    background-color: #00c4d1;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 20px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover:not(:disabled) {
    background-color: #00a8b3;
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

</style>