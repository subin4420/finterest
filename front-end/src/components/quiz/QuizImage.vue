<script setup>
import { ref, onMounted } from 'vue';

const currentSlide = ref(0);
const slides = ref([
{
    image: '/src/assets/images/quizpage.png',
    title: '문제를 한 번 풀어봐요.',
    subtitle: '퀴즈페이지',
    description: '배웠던 내용으로 퀴즈를 풀어봐요.',
    page: '/quiz/QuizPage' // 퀴즈 페이지 경로
  }
  // 필요한 만큼 슬라이드를 추가할 수 있습니다
  // 추가 슬라이드를 여기에 넣으세요
]);
</script>

<template>
  <div class="slider-container">
    <div class="slider" :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
      <div v-for="(slide, index) in slides" :key="index" class="slide">
        <img :src="slide.image" :alt="slide.title">
        <div class="slide-content">
          <h2>{{ slide.title }}</h2>
          <h3>{{ slide.subtitle }}</h3>
          <p>{{ slide.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.slider-container {
  position: relative;
  width: 100vw;
  height: 60vh; /* 고정된 높이 설정 (원하는 높이로 조정 가능) */
  overflow: hidden;
  margin-top: -60px; /* 헤더 높이만큼 위로 올림 */
}

.slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
  height: 100%; /* 슬라이더의 높이를 100%로 설정 */
}

.slide {
  flex: 0 0 100%;
  position: relative;
  width: 100vw; /* 각 슬라이드의 너비를 뷰포트 너비로 설정 */
  height: 60vh; /* 슬라이드의 높이를 고정된 값으로 설정 */
}

.slide img {
  width: 100%;
  height: 115%; /* 높이를 100%로 설정하여 슬라이드에 맞춤 */
  object-fit: cover; /* 비율 유지하며 잘림 */
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
  bottom: 30;
  left: 10%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
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

/* 작은 화면에서 글자 크기 조정 */
@media (max-width: 768px) {
  .slide-content h2 {
    font-size: 20px; /* 작은 화면에서 크기 줄임 */
  }

  .slide-content h3 {
    font-size: 18px; /* 작은 화면에서 크기 줄임 */
  }

  .slide-content p {
    font-size: 14px; /* 작은 화면에서 크기 줄임 */
  }
}
</style>