<template>
  <div id="app">
    <QuizImage />
    <QuizNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />

    <div class="content-section">
      <h3>퀴즈 자료</h3>
      <div class="content-grid">
        <QuizCard 
          v-for="quizSet in filteredQuizSets" 
          :key="quizSet.setId" 
          :cardData="quizSet" 
          @click="openQuizModal(quizSet)" 
        />
      </div>
    </div>

    <QuizModal 
      :isVisible="isModalVisible" 
      :cardData="selectedQuiz" 
      @update:isVisible="isModalVisible = $event" 
    />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import QuizImage from '../../components/quiz/QuizImage.vue';
import QuizNavigationBar from '../../components/quiz/QuizNavigationBar.vue';
import QuizCard from '../../components/quiz/QuizCard.vue';
import QuizModal from '../../components/quiz/QuizModal.vue';
import { useQuizStore } from '../../stores/quizStore';

const { quizSets, fetchQuizSets } = useQuizStore();
const isModalVisible = ref(false);
const selectedQuiz = ref(null);
const selectedCategory = ref(null); // 선택된 카테고리 상태 추가

onMounted(async () => {
    await fetchQuizSets(); // 퀴즈 세트 가져오기
    console.log("Quiz Sets on Mount:", quizSets.value); // 초기 상태 확인
});

// 카테고리 필터링 함수
const filterByCategory = (category) => {
    selectedCategory.value = category;
};

// 필터링된 퀴즈 세트
const filteredQuizSets = computed(() => {
    return selectedCategory.value 
        ? quizSets.value.filter(quizSet => quizSet.categoryName === selectedCategory.value) 
        : quizSets.value;
});

function openQuizModal(quiz) {
    selectedQuiz.value = quiz;
    isModalVisible.value = true;
}

// return { filteredQuizSets, isModalVisible, selectedQuiz, openQuizModal, filterByCategory, selectedCategory };
</script>

<style scoped>
h3 {
  font-size: 1.8rem; /* 폰트 크기를 조금 더 키워서 눈에 띄게 */
  font-weight: bold; /* 텍스트를 굵게 */
  color: #333; /* 짙은 회색으로 시각적으로 부드럽게 */
  text-align: left; /* 텍스트를 중앙에 배치 */
  margin-bottom: 1.5rem; /* 제목 아래쪽에 간격 추가 */
  padding-bottom: 0.5rem; /* 제목과 카드 사이에 간격 */
  border-bottom: 2px solid #ccc; /* 제목 아래에 구분선 추가 */
}

.content-section {
  margin-bottom: 2rem; /* 섹션 간의 간격 */
  width: 80%; /* 전체 화면의 80%만 차지 */
  margin: 0 auto; /* 화면 중앙 정렬 */
}

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 1rem;
}
.content-grid > * {
  flex: 1 1 calc(25% - 1rem); /* 각 카드가 25%의 너비를 차지 */
  max-width: calc(25% - 1rem); /* 최대 너비도 25%로 설정 */
}
@media (max-width: 1200px) {
  .content-grid > * {
    flex: 1 1 calc(33.33% - 1rem); /* 화면이 작아지면 한 행에 3개 */
    max-width: calc(33.33% - 1rem);
  }
}

@media (max-width: 900px) {
  .content-grid > * {
    flex: 1 1 calc(50% - 1rem); /* 더 작은 화면에서 한 행에 2개 */
    max-width: calc(50% - 1rem);
  }
}

@media (max-width: 600px) {
  .content-grid > * {
    flex: 1 1 100%; /* 작은 모바일 화면에서는 한 행에 1개 */
    max-width: 100%;
  }
}
</style>