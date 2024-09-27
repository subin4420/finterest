<script setup>
import { ref } from 'vue';

const currentSlide = ref(0);
const slides = ref([
  {
    image: '/src/assets/images/모의투자페이지.png',
    title: '모의투자, 연습도 실전처럼',
  },
]);

// title에 쉼표를 기준으로 줄바꿈 처리를 위한 함수
const formatTitle = (title) => {
  return title.replace(',', '<br/>'); // 쉼표를 <br> 태그로 변경하여 줄바꿈
};
</script>

<template>
  <div class="slider-container">
    <div
      class="slider"
      :style="{ transform: `translateX(-${currentSlide * 100}%)` }"
    >
      <div v-for="(slide, index) in slides" :key="index" class="slide">
        <img :src="slide.image" :alt="slide.title" />
        <div class="slide-content">
          <!-- v-html을 사용해 HTML 태그를 렌더링 -->
          <h2 v-html="formatTitle(slide.title)"></h2>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 이전 스타일 유지 */
.slider-container {
  position: relative;
  width: 100vw;
  height: 60vh;
  overflow: hidden;
  margin-top: -60px;
}

.slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
  height: 100%;
}

.slide {
  flex: 0 0 100%;
  position: relative;
  width: 100vw;
  height: 60vh;
}

.slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.slide-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-align: left;
  width: 80%;
}

/* 기타 스타일은 필요에 따라 추가 */
</style>
