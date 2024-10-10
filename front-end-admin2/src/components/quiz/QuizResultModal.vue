<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <h2>퀴즈 결과</h2>
      <table class="summary-table">
        <thead>
          <tr>
            <th>총점</th>
            <th>맞은 문제</th>
            <th>틀린 문제</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{ result.totalScore }} / {{ result.maxScore }}</td>
            <td>{{ result.correctAnswers.length }}</td>
            <td>{{ result.wrongAnswers.length }}</td>
          </tr>
        </tbody>
      </table>
      <h3>문제 상세 결과</h3>
      <table class="detail-table">
        <thead>
          <tr>
            <th>문제 번호</th>
            <th>선택한 답</th>
            <th>결과</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(question, index) in allQuestions" :key="question.quizId"
              :class="{ 'correct': isCorrect(question), 'incorrect': !isCorrect(question) }">
            <td>{{ index + 1 }}</td>
            <td>{{ getAnswerText(question) }}</td>
            <td>{{ isCorrect(question) ? '정답' : '오답' }}</td>
          </tr>
        </tbody>
      </table>
      <div class="button-container">
        <button @click="showDetailedAnswers" class="view-answers-button">정답 보기</button>
        <button @click="goToQuizList" class="close-button">목록으로</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuizResultModal',
  props: {
    show: Boolean,
    result: Object
  },
  emits: ['close', 'view-answers', 'go-to-quiz-list'],
  computed: {
    allQuestions() {
      const correctAnswers = this.result.correctAnswers.map(q => ({ ...q, isCorrect: true }));
      const wrongAnswers = this.result.wrongAnswers.map(q => ({ ...q, isCorrect: false }));
      return [...correctAnswers, ...wrongAnswers].sort((a, b) => a.quizId - b.quizId);
    }
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    showDetailedAnswers() {
      this.$emit('view-answers', this.result.resultId);
    },
    isCorrect(question) {
      return question.isCorrect;
    },
    getAnswerText(question) {
      if (this.isCorrect(question)) {
        return question.correctChoice;
      } else {
        return question.selectedChoice || '선택 안함';
      }
    },
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
  border-radius: 5px;
  max-width: 600px;
  width: 100%;
  max-height: 80vh;
  overflow-y: auto;
}

.summary-table, .detail-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f2f2f2;
}

.detail-table .correct {
  background-color: #d4edda;
}

.detail-table .incorrect {
  background-color: #f8d7da;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.view-answers-button, .close-button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.view-answers-button {
  background-color: #2196F3;
  color: white;
}

.view-answers-button:hover {
  background-color: #1976D2;
}

.close-button {
  background-color: #f44336;
  color: white;
}

.close-button:hover {
  background-color: #d32f2f;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>