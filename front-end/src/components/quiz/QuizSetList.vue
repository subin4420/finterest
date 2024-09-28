<template>
  <div>
    <div class="cards">
      <QuizCard 
        v-for="quiz in quizSets" 
        :key="quiz.setId" 
        :cardData="quiz" 
        @click.native="openQuizModal(quiz)"  
      />
    </div>
    <QuizModal 
      :isVisible="isQuizModalVisible" 
      :cardData="selectedQuizSet" 
      @update:isVisible="isQuizModalVisible = $event" 
    />
  </div>
</template>

<script>
import QuizCard from '@/components/quiz/QuizCard.vue';
import QuizModal from '@/components/quiz/QuizModal.vue';
import { ref, onMounted } from 'vue';
import { useQuizStore } from '@/stores/quizStore';

export default {
  name: 'QuizSetList',
  components: {
    QuizCard,
    QuizModal
  },
  setup() {
    const quizStore = useQuizStore();
    const quizSets = ref([]);
    const isQuizModalVisible = ref(false);
    const selectedQuizSet = ref({});

    onMounted(async () => {
      const quizSetData = await quizStore.fetchQuizSets();
      console.log('Fetched Quiz Sets:', quizSetData);  // 데이터 확인
      quizSets.value = quizSetData;
    });


    const openQuizModal = (quiz) => {
      console.log('Selected Quiz:', quiz);  // 선택한 퀴즈 로그 확인
      if (quiz) {
        selectedQuizSet.value = quiz;
        isQuizModalVisible.value = true;
      }
    };


    return {
      quizSets,
      isQuizModalVisible,
      selectedQuizSet,
      openQuizModal
    };
  }
};
</script>
