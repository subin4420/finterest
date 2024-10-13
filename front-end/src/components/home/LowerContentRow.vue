<script setup>
import { ref, onMounted } from 'vue';
import { useQuizStore } from '@/stores/quizStore'; // 퀴즈 세트 API 함수 및 상태 가져오기
import QuizCard from '@/components/quiz/QuizCard.vue'; // QuizCard 컴포넌트 가져오기

// 상태 및 함수 사용
const { fetchCompletedQuizSets, completedQuizSets } = useQuizStore(); // quizStore에서 상태 가져오기

// 컴포넌트가 마운트될 때 fetchCompletedQuizSets 호출
onMounted(() => {
  fetchCompletedQuizSets();
});
</script>

<template>
  <div>
    <!-- header -->
    <div class="header">
      <h2>완료된 퀴즈 세트 TOP 3</h2>
    </div>
    
    <!-- content-body -->
    <div class="content-body">
      <div class="quiz-set-list">
        <!-- 상위 3개의 완료된 퀴즈 세트를 출력 -->
        <QuizCard
          v-for="quizSet in completedQuizSets.slice(0, 3)"
          :key="quizSet.setId"
          :cardData="quizSet"
          @click="handleQuizCardClick"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handleQuizCardClick(cardData) {
      console.log('Quiz card clicked:', cardData);
      // 클릭 시 원하는 동작을 수행 (예: 상세 페이지로 이동)
    }
  }
};
</script>

<style scoped>
/* header 스타일 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 20px;
  margin-top: 1rem;
  padding-top: 20px;
}

h2 {
  font-size: 2rem;
  margin: 0;
}

/* content-body 스타일 */
.content-body {
  width: 100%;
  padding: 20px;
  margin-left: 10px;
  margin-right: 10px;
}

.quiz-set-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center; /* 카드들이 가운데 정렬되도록 설정 */
  gap: 20px;
  width: 100%; /* 부모 요소 너비를 100%로 설정 */
}

.quiz-set-list > * {
  flex-grow: 1; /* 상위 컨테이너의 너비를 따르도록 설정 */
  flex-basis: calc(33.333% - 20px); /* 한 줄에 3개의 카드가 나오도록 설정, 20px은 gap */
  max-width: calc(33.333% - 20px); /* 최대 너비를 동일하게 설정 */
  box-sizing: border-box; /* 박스 크기를 맞추기 위해 box-sizing 설정 */
}

/* 미디어 쿼리 */
@media (max-width: 768px) {
  .quiz-set-list > * {
    flex-basis: calc(50% - 20px); /* 화면 크기가 작을 때는 한 줄에 2개씩 배치 */
    max-width: calc(50% - 20px);
  }
}

@media (max-width: 480px) {
  .quiz-set-list > * {
    flex-basis: 100%; /* 더 작은 화면에서는 한 줄에 하나씩 배치 */
    max-width: 100%;
  }
}
</style>
