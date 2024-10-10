<template>
  <div>
    <div class="cards">
      <QuizCard 
        v-for="quiz in quizResults" 
        :key="quiz.setId" 
        :cardData="mapQuizToCardData(quiz)"
        @click="openQuizModal(quiz)"  
      />
    </div>

    <!-- 퀴즈 답변 모달 -->
    <QuizAnswersModal 
      v-if="isQuizAnswersModalVisible" 
      :show="isQuizAnswersModalVisible" 
      :answers="quizAnswers" 
      @close="isQuizAnswersModalVisible = false"
      @go-to-quiz-list="closeQuizAnswersModal"
    />
  </div>
</template>

<script>
import QuizCard from '@/components/quiz/QuizCard.vue';
import QuizAnswersModal from '@/components/quiz/QuizAnswersModal.vue';
import { ref, onMounted } from 'vue';
import { useQuizStore } from '@/stores/quizStore';

export default {
  name: 'QuizSetList',
  components: {
    QuizCard,
    QuizAnswersModal
  },
  setup() {
    const quizStore = useQuizStore();
    const isQuizAnswersModalVisible = ref(false); // 퀴즈 답변 모달 상태 관리
    const selectedQuizSet = ref(null); // 선택된 퀴즈 세트 저장
    const quizResults = ref([]); // 퀴즈 세트 결과를 저장할 배열
    const quizAnswers = ref([]); // 퀴즈 답변을 저장할 배열

    // 컴포넌트가 마운트될 때 퀴즈 세트를 가져오는 로직
    onMounted(async () => {
      try {
        // userId를 필요에 따라 동적으로 설정 가능
        await quizStore.fetchQuizResults(1); // 스토어에서 fetchQuizResults 호출
        quizResults.value = quizStore.quizResults.value; // 스토어에서 결과를 가져와 로컬 상태에 저장
      } catch (error) {
        console.error('Error fetching quiz results:', error);
      }
    });

    // `quizResults` 데이터를 `QuizCard`에서 사용할 수 있도록 매핑하는 함수
    const mapQuizToCardData = (quiz) => {
      return {
        ...quiz,
        userScore: quiz.totalScore // totalScore를 userScore로 매핑
      };
    };

    // 퀴즈 답변 모달 열기
    const openQuizModal = async (quiz) => {
      console.log('Selected Quiz:', quiz);  // 선택한 퀴즈 로그 확인
      selectedQuizSet.value = quiz;

      // 퀴즈 답변 가져오기
      try {
        const answers = await quizStore.fetchQuizAnswers(quiz.setId, quiz.resultId); // API 호출로 답변 가져오기
        quizAnswers.value = answers; // 퀴즈 답변을 저장
        isQuizAnswersModalVisible.value = true; // 퀴즈 답변 모달 열기
      } catch (error) {
        console.error('Error fetching quiz answers:', error);
      }
    };

    // 퀴즈 답변 모달 닫기
    const closeQuizAnswersModal = () => {
      isQuizAnswersModalVisible.value = false;
    };

    return {
      quizResults,
      isQuizAnswersModalVisible,
      selectedQuizSet,
      quizAnswers,
      openQuizModal,
      closeQuizAnswersModal,
      mapQuizToCardData
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
