<template>
  <div id="app">
    <QuizImage />
    <div :class="{'navigation-container': !isQuizSelected, 'quiz-top-container': isQuizSelected}">
      <QuizNavigationBar v-if="!isQuizSelected" @category-selected="filterByCategory" :selectedCategory="selectedCategory" />
      <QuizTopBar v-else 
        :currentQuestionIndex="currentQuestionIndex" 
        :totalQuestions="totalQuestions" 
        :score="score"
        :allQuestionsAnswered="allQuestionsAnswered"
        @end-quiz="goBackToQuizList"
        @submit-quiz="submitQuiz"
      />
    </div>
    <!-- 퀴즈가 선택되지 않았을 때 -->
    <div v-if="!isQuizSelected" class="content-section">
      <h3>퀴즈 자료</h3>
      <div class="content-grid">
        <QuizCard 
          v-for="quizSet in filteredQuizSets" 
          :key="quizSet.setId" 
          :cardData="quizSet" 
          @click.native="handleQuizCardClick(quizSet)" 
        />
      </div>
    </div>

    <!-- 퀴즈가 선택되었을 때 -->
    <div v-else class="content-section">
      <QuizSubmit 
        ref="quizSubmitRef"
        :quizSet="selectedQuizSet" 
        @goBack="goBackToQuizList"
        @changeQuizSet="changeQuizSet"
        @quizSubmitted="handleQuizSubmitted"
        @updateQuizProgress="updateQuizProgress"
      />
    </div>

    <!-- LoginRequiredModal 추가 -->
    <LoginRequiredModal 
      v-if="showLoginModal" 
      @close="showLoginModal = false"
      @login="handleLogin"
    />
  </div>
</template>

<script>
import { useRouter, useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import QuizImage from '@/components/quiz/QuizImage.vue';
import QuizNavigationBar from '@/components/quiz/QuizNavigationBar.vue';
import QuizCard from '@/components/quiz/QuizCard.vue';
import QuizModal from '@/components/quiz/QuizModal.vue';
import QuizSubmit from '@/components/quiz/QuizSubmit.vue';
import QuizTopBar from '@/components/quiz/QuizTopBar.vue';
import LoginRequiredModal from '@/components/common/LoginRequiredModal.vue';
import { onMounted, ref, computed } from "vue";
import { useQuizStore } from "@/stores/quizStore";
import { useAuthStore } from "@/stores/auth";

export default {
  name: 'QuizPage',
  components: {
    QuizImage,
    QuizCard,
    QuizModal,
    QuizNavigationBar,
    QuizSubmit,
    QuizTopBar,
    LoginRequiredModal // LoginRequiredModal 컴포넌트 추가
  },
  setup() {
    const quizStore = useQuizStore();
    const authStore = useAuthStore();
    const { isLogin } = storeToRefs(authStore);
    const isModalVisible = ref(false);
    const selectedCard = ref({});
    const selectedCategory = ref(null);
    const quizSets = ref([]);
    const isQuizSelected = ref(false);
    const selectedQuizSet = ref(null);

    // QuizTopBar에 필요한 상태들
    const currentQuestionIndex = ref(1);
    const totalQuestions = ref(0);
    const score = ref(0);
    const allQuestionsAnswered = ref(false);

    const quizSubmitRef = ref(null);
    const showLoginModal = ref(false);

    const router = useRouter(); // Vue Router 인스턴스를 가져옵니다.
    const route = useRoute();

    onMounted(async () => {
      await quizStore.fetchQuizSets();
      const fetchedQuizSets = quizStore.quizSets.value;
      console.log('Fetched Quiz Sets:', fetchedQuizSets);
      quizSets.value = fetchedQuizSets;
    });

    const filterByCategory = (category) => {
      selectedCategory.value = category;
      if (category === '즐겨찾기') {
        // 페이지 새로고침 등 처리
      }
    };

    const filteredQuizSets = computed(() => {
      if (selectedCategory.value === '즐겨찾기') {
        return quizSets.value.filter(quizSet => quizSet.favorite);
      }
      return selectedCategory.value 
        ? quizSets.value.filter(quizSet => quizSet.categoryName === selectedCategory.value) 
        : quizSets.value;
    });

    const openQuizSet = (quiz) => {
      selectedQuizSet.value = quiz;
      isQuizSelected.value = true;
      totalQuestions.value = quiz.questions ? quiz.questions.length : 0;
      currentQuestionIndex.value = 1;
      score.value = 0;
    };

    const goBackToQuizList = () => {
      isQuizSelected.value = false;
      selectedQuizSet.value = null;
    };

    const changeQuizSet = (newQuizSet) => {
      selectedQuizSet.value = newQuizSet;
      totalQuestions.value = newQuizSet.questions ? newQuizSet.questions.length : 0;
      currentQuestionIndex.value = 1;
      score.value = 0;
    };

    const bookmarkQuestion = () => {
      // 북마크 기능 구현
    };

    const showHint = () => {
      // 힌트 기능 구현
    };

    const handleQuizSubmitted = (result) => {
  console.log('퀴즈가 성공적으로 제출되었습니다.');

  // 점수 업데이트
  score.value = result.score || 0; // 서버에서 받은 점수를 업데이트
};


    const updateQuizProgress = (progress) => {
      currentQuestionIndex.value = progress.currentQuestionIndex;
      totalQuestions.value = progress.totalQuestions;
      allQuestionsAnswered.value = progress.allQuestionsAnswered;
    };

    const submitQuiz = () => {
      if (quizSubmitRef.value) {
        quizSubmitRef.value.submitQuiz();
      }
    };

    const handleQuizCardClick = (quiz) => {
      console.log('로그인 상태:', isLogin.value);
      if (isLogin.value) {
        openQuizSet(quiz);
      } else {
        showLoginModal.value = true;
      }
    };

    const handleLogin = () => {
      showLoginModal.value = false;
      const currentPath = route.fullPath;
      router.push({ 
        path: '/auth/login', 
        query: { redirect: currentPath } 
      });
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
      goBackToQuizList,
      changeQuizSet,
      currentQuestionIndex,
      totalQuestions,
      score,
      allQuestionsAnswered,
      updateQuizProgress,
      submitQuiz,
      bookmarkQuestion,
      showHint,
      handleQuizSubmitted,
      quizSubmitRef,
      showLoginModal,
      handleQuizCardClick,
      handleLogin,
      isLogin // isLogin을 반환에 추가
    };
  }
}
</script>

<style scoped>
.navigation-container {
  width: 80%;
  margin: -20px auto 0; /* 원래 네비바에 대해서는 마이너스 마진 제거 */
  max-width: 1200px;
  position: relative;
  z-index: 1002;
  border-radius: 10px;
}

.quiz-top-container {
  width: 80%;
  margin: -20px auto 0; /* 퀴즈 탑바에 대해서만 마이너스 마진 적용 */
  max-width: 1200px;
  position: relative;
  z-index: 1002;
  border-radius: 10px;
  overflow: hidden;
}

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
  max-width: 1200px; /* 최대 너비 설정 */
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