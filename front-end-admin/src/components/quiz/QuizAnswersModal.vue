<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <h2>퀴즈 상세 답변</h2>
      <hr>
      <div class="legend">
        <div class="legend-item">
          <span class="legend-color selected"></span> 사용자 선택
        </div>
        <div class="legend-item">
          <span class="legend-color correct-answer"></span> 정답
        </div>
      </div>
      <div v-if="answers" class="answers-container">
        <div v-for="(answer, index) in sortedAnswers" :key="answer.quizId" class="answer-card" :class="{ 'correct': answer.correct, 'incorrect': !answer.correct }">
          <div class="question-number">문제 {{ index + 1 }}</div>
          <div class="question">{{ answer.question }}</div>
          <div class="choices">
            <div v-for="i in 4" :key="i" class="choice" :class="{ 'selected': answer.selectedChoice === i, 'correct-answer': answer.correctChoice === i }">
              {{ i }}. {{ answer['choice' + i] }}
            </div>
          </div>
          <div class="result">
            결과: <span :class="{ 'correct-text': answer.correct, 'incorrect-text': !answer.correct }">{{ answer.correct ? '정답' : '오답' }}</span>
          </div>
        </div>
      </div>
      <div class="button-container">
        <button @click="goToQuizList" class="close-button">목록으로</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuizAnswersModal',
  props: {
    show: Boolean,
    answers: Array
  },
  emits: ['close', 'go-to-quiz-list'],
  computed: {
    sortedAnswers() {
      return [...this.answers].sort((a, b) => a.quizId - b.quizId);
    }
  },
  methods: {
    goToQuizList() {
      this.$emit('go-to-quiz-list');
    }
  }
}
</script>

<style scoped>
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
  border-radius: 10px;
  max-width: 80%;
  width: 100%;
  max-height: 80vh;
  overflow-y: auto;
}

.answers-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.answer-card {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.answer-card.correct {
  background-color: #d4edda;
}

.answer-card.incorrect {
  background-color: #f8d7da;
}

.question-number {
  font-weight: bold;
  margin-bottom: 10px;
}

.question {
  font-size: 1.1em;
  margin-bottom: 15px;
}

.choices {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.choice {
  padding: 8px;
  border-radius: 4px;
  background-color: #e9ecef;
}

.choice.selected {
  background-color: #007bff;
  color: white;
}

.choice.correct-answer {
  background-color: #28a745;
  color: white;
}

.result {
  margin-top: 15px;
  font-weight: bold;
}

.correct-text {
  color: #28a745;
}

.incorrect-text {
  color: #dc3545;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.close-button {
  padding: 10px 20px;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.close-button:hover {
  background-color: #5a6268;
}

.legend {
  display: flex;
  justify-content: left;
  margin-bottom: 20px;
}

.legend-item {
  display: flex;
  align-items: center;
  margin: 0 10px;
}

.legend-color {
  width: 20px;
  height: 20px;
  margin-right: 5px;
  border-radius: 4px;
}

.legend-color.selected {
  background-color: #007bff;
}

.legend-color.correct-answer {
  background-color: #28a745;
}

</style>
