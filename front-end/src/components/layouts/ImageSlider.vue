<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'; // Vue Router 사용

const router = useRouter(); // Router 인스턴스 가져오기

const currentSlide = ref(0);
const slides = ref([
  {
    image: '/src/assets/images/홈페이지.png',
    title: 'Z세대를 위한 경제 교육 플랫폼',
    subtitle: '우리의 서비스 소개',
    description: '우리의 서비스 소개',
    page: '/' // 홈 페이지 경로
  },
  {
    image: '/src/assets/images/학습페이지.png',
    title: '경제 상식을 배워봐요',
    subtitle: '학습페이지',
    description: '카테고리별로 학습 할 수 있어요.',
    page: '/archive/ArchivePage' // 아카이브 페이지 경로
  },
  {
    image: '/src/assets/images/퀴즈페이지.png',
    title: '문제를 한 번 풀어봐요.',
    subtitle: '퀴즈페이지',
    description: '배웠던 내용으로 퀴즈를 풀어봐요.',
    page: '/quiz/QuizPage' // 퀴즈 페이지 경로
  },
  {
    image: '/src/assets/images/모의투자페이지.png',
    title: '이제는 실전에서 해봐요',
    subtitle: '모의투자 페이지',
    description: '리스크 없이 투자 경험을 쌓아보세요',
    page: '/trade/TradePage' // 거래 페이지 경로
  },
  {
    image: '/src/assets/images/쳇봇페이지.png',
    title: '저에게 모르는 걸 물어보세요.',
    subtitle: '쳇봇 서비스',
    description: '학습 중 모르는 것이 있으면 질문할 수 있어요.',
    page: '/myLearning/MyLearningPage' // 내 학습 페이지 경로
  },
]);

const goToSlide = (index) => {
  currentSlide.value = index;
};

const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % slides.value.length;
};

const prevSlide = () => {
  currentSlide.value = (currentSlide.value - 1 + slides.value.length) % slides.value.length;
};

const goToPage = (page) => {
  router.push(page); // 해당 페이지로 이동
};

onMounted(() => {
  setInterval(nextSlide, 5000); // 5초마다 자동 슬라이드
});
</script>

<template>
  <div class="slider-container">
    <div class="slider" :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
      <div v-for="(slide, index) in slides" :key="index" class="slide" @click="goToPage(slide.page)" >
        <img :src="slide.image" :alt="slide.title">
        <div class="slide-content">
          <h2>{{ slide.title }}</h2>
          <h3>{{ slide.subtitle }}</h3>
          <p>{{ slide.description }}</p>
        </div>
      </div>
    </div>
    <button class="prev" @click="prevSlide">&lt;</button>
    <button class="next" @click="nextSlide">&gt;</button>
    <div class="dots">
      <span 
        v-for="(slide, index) in slides" 
        :key="index" 
        :class="['dot', { active: currentSlide === index }]"
        @click="goToSlide(index)"
        :style="{ backgroundColor: currentSlide === index ? 'gray' : 'rgba(255, 255, 255, 0.5)' }" >
      </span>
    </div>
  </div>
</template>

<style scoped>
.slider-container {
  position: relative;
  width: 100vw;
  height: 100vh; /* 뷰포트 높이의 100%로 설정 */
  overflow: hidden;
  margin-top: -60px; /* 헤더 높이만큼 위로 올림 */
}

.slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
  height: 100%;
}

.slide {
  flex: 0 0 100%;
  position: relative;
  width: 100vw; /* 각 슬라이드의 너비를 뷰포트 너비로 설정 */
  height: 100vh; /* 각 슬라이드도 전체 높이로 설정 */
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.slide-content {
  position: absolute;
  top: 50%;
  left: 50%; /* 왼쪽 정렬을 위해 변경 */
  transform: translate(-50%, -50%); /* 중앙 정렬을 위해 변경 */
  color: white;
  text-align: left; /* 텍스트 중앙 정렬 */
  width: 65%; /* 내용의 최대 너비 설정 */
}

.prev, .next {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: transparent; /* 배경을 투명하게 설정 */
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  z-index: 10;
  font-size: 24px; /* 버튼 텍스트 크기 증가 */
  transition: all 0.3s ease; /* 부드러운 전환 효과 */
}

.prev:hover, .next:hover {
  color: rgba(255, 255, 255, 0.8); /* 호버 시 약간 투명해지는 효과 */
}

.prev { left: 20px; }
.next { right: 20px; }

.dots {
  position: absolute;
  bottom: 30px; /* px 단위를 추가하여 위치를 명확히 설정 */
  left: 50%; /* 중앙 정렬을 위해 50%로 설정 */
  transform: translateX(-50%); /* 중앙 정렬을 위해 변환 */
  display: flex;
  gap: 10px; /* 점 사이의 간격 */
  z-index: 10; /* 다른 요소 위에 표시되도록 z-index 설정 */
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.dot.active {
  background-color: white;
}

.dot:hover {
  background-color: rgba(255, 255, 255, 0.8);
}
</style>