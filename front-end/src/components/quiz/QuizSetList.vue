<template>
  <div>
    <div class="cards">
      <QuizCard 
        v-for="quiz in filteredQuizSets" 
        :key="quiz.setId" 
        :cardData="quiz" 
        @click.native="openQuizModal(quiz)"  
      />
    </div>
    <QuizResultModal 
      v-if="selectedQuizSet"
      :isVisible="isQuizModalVisible" 
      :cardData="selectedQuizSet" 
      @update:isVisible="isQuizModalVisible = $event" 
    />
  </div>
</template>

<script>
import QuizCard from '@/components/quiz/QuizCard.vue';
import QuizResultModal from '@/components/quiz/QuizResultModal.vue';
import { ref, onMounted, computed } from 'vue';
import { useQuizStore } from '@/stores/quizStore';

export default {
  name: 'QuizSetList',
  components: {
    QuizCard,
    QuizResultModal
  },
  setup() {
    const { quizSets, fetchQuizSets } = useQuizStore();
    const isQuizModalVisible = ref(false);
    const selectedQuizSet = ref(null);

    // 컴포넌트가 마운트될 때 퀴즈 세트를 가져오는 로직
    onMounted(async () => {
      try {
        await fetchQuizSets(); // 스토어의 fetchQuizSets 호출하여 데이터를 가져옴
      } catch (error) {
        console.error('Error fetching quizSets', error);
      }
    });

    // userScore가 null이 아닌 퀴즈 세트만 필터링
    const filteredQuizSets = computed(() => {
      //return quizSets.value.filter(quiz => quiz.userScore !== null);
      return quizSets.value;  // 테스트용
    });

    const openQuizModal = (quiz) => {
      console.log('Selected Quiz:', quiz);  // 선택한 퀴즈 로그 확인
      if (quiz) {
        selectedQuizSet.value = quiz;
        isQuizModalVisible.value = true;
      }
    };

    return {
      filteredQuizSets, // 필터링된 퀴즈 세트를 반환
      isQuizModalVisible,
      selectedQuizSet,
      openQuizModal
    };
  }
};
</script>

<style scoped>
.cards {
  width: 80%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 10px; /* 간격을 30px로 늘림 */
}

</style>