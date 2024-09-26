<template>
  <div id="app">
    <QuizImage />
    <QuizNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />

    <div class="content-section">
      <h2>퀴즈 자료</h2>
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
.content-section {
  margin-bottom: 2rem; /* 섹션 간의 간격 */
}

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}
</style>