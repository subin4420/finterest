<template>
  <div>
    <div class="cards">
      <QuizCard 
        v-for="quiz in filteredQuizSets" 
        :key="quiz.setId" 
        :cardData="quiz" 
        @click="openQuizModal(quiz)"  
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
    const quizStore = useQuizStore();
    const isQuizModalVisible = ref(false); // 모달 상태 관리
    const selectedQuizSet = ref(null); // 선택된 퀴즈 세트 저장

    // 컴포넌트가 마운트될 때 퀴즈 세트를 가져오는 로직
    onMounted(async () => {
      try {
        await quizStore.fetchQuizSets(); // 스토어의 fetchQuizSets 호출하여 데이터를 가져옴
      } catch (error) {
        console.error('Error fetching quizSets', error);
      }
    });

    // userScore가 null이 아닌 퀴즈 세트만 필터링
    const filteredQuizSets = computed(() => {
      return quizStore.quizSets.value;  // 테스트용: 모든 퀴즈 세트 반환
    });

    // 모달 열기
    const openQuizModal = (quiz) => {
      // 중복 호출 확인을 위해 이벤트 발생 지점 단일화
      //if (!isQuizModalVisible.value) {
        console.log('Selected Quiz:', quiz);  // 선택한 퀴즈 로그 확인
        selectedQuizSet.value = quiz;
        isQuizModalVisible.value = true;
      //}
    };

    return {
      filteredQuizSets, // 필터링된 퀴즈 세트를 반환
      isQuizModalVisible,
      selectedQuizSet,
      openQuizModal,
    };
  }
};
</script>
<style scoped>
.cards {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 250px)); /* 최소 크기를 200px로 조정 */
  gap: 20px; /* 카드 간격을 10px로 유지 */
}
</style>
