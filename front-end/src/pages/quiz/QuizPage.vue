<template>
  <div id="app">
      <QuizImage />
      <QuizNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />
      <!-- 퀴즈가 선택되지 않았을 때 -->
      <div v-if="!isQuizSelected">
        <div class="content-section">
          <h3>퀴즈 자료</h3>
          <div class="content-grid">
            <QuizCard 
              v-for="quizSet in filteredQuizSets" 
              :key="quizSet.setId" 
              :cardData="quizSet" 
              @click.native="openQuizSet(quizSet)" 
            />
          </div>
        </div>
      </div>


    <!-- 퀴즈가 선택되었을 때 -->
    <div v-else>
      <QuizSubmit :quizSet="selectedQuizSet" @goBack="goBackToQuizList" />
    </div>
  </div>
</template>

<script>
import QuizImage from '@/components/quiz/QuizImage.vue';
import QuizNavigationBar from '@/components/quiz/QuizNavigationBar.vue';
import QuizCard from '@/components/quiz/QuizCard.vue';
import QuizModal from '@/components/quiz/QuizModal.vue';
import QuizSubmit from '@/components/quiz/QuizSubmit.vue'; // 새로 추가할 퀴즈 문제 컴포넌트
import { onMounted, ref, computed } from "vue";
import { useQuizStore } from "@/stores/quizStore";

export default {
  name: 'QuizPage',
  components: {
    QuizImage,
    QuizCard,
    QuizModal,
    QuizNavigationBar,
    QuizSubmit // 퀴즈 문제 컴포넌트 추가
  },
  setup() {
    const quizStore = useQuizStore();
    const isModalVisible = ref(false);
    const selectedCard = ref({});
    const selectedCategory = ref(null); // 선택된 카테고리 상태 추가
    const quizSets = ref([]);
    const isQuizSelected = ref(false); // 퀴즈 세트가 선택되었는지 여부
    const selectedQuizSet = ref(null); // 선택된 퀴즈 세트 저장

    onMounted(async () => {
      await quizStore.fetchQuizSets(); // 퀴즈 세트 가져오기
      const fetchedQuizSets = quizStore.quizSets.value; // .value를 통해 배열에 접근
      console.log('Fetched Quiz Sets:', fetchedQuizSets); // 가져온 데이터 확인
      quizSets.value = fetchedQuizSets; // 퀴즈 세트 저장
    });

    // 카테고리 필터링 함수
    const filterByCategory = (category) => {
      selectedCategory.value = category;
      if (category === '즐겨찾기') {
        // 페이지 새로고침 등 처리
      }
    };

    // 필터링된 퀴즈 세트
    const filteredQuizSets = computed(() => {
      if (selectedCategory.value === '즐겨찾기') {
        return quizSets.value.filter(quizSet => quizSet.favorite); // 즐겨찾기된 퀴즈만 필터링
      }
      return selectedCategory.value 
        ? quizSets.value.filter(quizSet => quizSet.categoryName === selectedCategory.value) 
        : quizSets.value;
    });

    // 퀴즈 세트 선택 시 호출
    const openQuizSet = (quiz) => {
      selectedQuizSet.value = quiz; // 선택된 퀴즈 세트 저장
      isQuizSelected.value = true; // 퀴즈 세트가 선택되었음을 표시
    };

    // 퀴즈 문제 풀이 영역에서 퀴즈 세트 목록으로 돌아가기
    const goBackToQuizList = () => {
      isQuizSelected.value = false; // 퀴즈 세트 선택 해제
      selectedQuizSet.value = null; // 선택된 퀴즈 세트 초기화
    };

    return { 
      filteredQuizSets, 
      isModalVisible, 
      selectedCard, 
      filterByCategory, 
      selectedCategory, 
      openQuizSet, 
      isQuizSelected, 
      selectedQuizSet, 
      goBackToQuizList 
    };
  }
}
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