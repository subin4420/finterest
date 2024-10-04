<template>
  <div class="quiz-problem">
    <h2>{{ quizSet.title }} 문제 풀기</h2>
    <div class="question-list">
      <!-- 여기에 퀴즈 문제를 표시하는 로직 추가 -->
      <div v-for="(question, index) in quizSet.questions" :key="index">
        <p>문제 {{ index + 1 }}: {{ question.question }}</p>
        <ul>
          <li v-for="choice in question.choices" :key="choice">{{ choice }}</li>
        </ul>
      </div>
    </div>
    <button @click="goBack">뒤로 가기</button>
  </div>
</template>

<script>
export default {
  name: 'QuizProblem',
  props: {
    quizSet: {
      type: Object,
      required: true
    }
  },
  methods: {
    goBack() {
      this.$emit('goBack'); // 부모 컴포넌트로 돌아가기 이벤트 전송
    }
  }
}
</script>

<style scoped>
.quiz-problem {
  padding: 2rem;
}
.question-list {
  margin-bottom: 2rem;
}
</style>
