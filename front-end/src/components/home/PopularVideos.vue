<script setup>
import { ref, onMounted, computed } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore'; // ArchiveStore 가져오기
import ArchiveCard from '@/components/archive/ArchiveCard.vue'; // ArchiveCard 컴포넌트 임포트

// store에서 popularVideos 관련 데이터 가져오기
const { fetchPopularVideoArchives, popularVideo } = useArchiveStore();

const currentIndex = ref(0);

// 전체 슬라이드 개수 계산 (두 개씩 한 슬라이드로 구성)
const totalSlides = computed(() => Math.ceil(popularVideo.value.length / 2));

// 슬라이드 이동 함수 (무한 스크롤)
const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % totalSlides.value;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + totalSlides.value) % totalSlides.value;
};

// 컴포넌트가 마운트될 때 인기 영상 학습 자료 가져오기
onMounted(async () => {
  await fetchPopularVideoArchives(); // API 호출로 인기 영상 자료 가져오기
});
</script>

<template>
  <div>
    <div class="header">
      <h2>인기 영상</h2>
      <div class="slider-buttons">
        <button @click="prevSlide" class="slider-button prev">&lt;</button>
        <button @click="nextSlide" class="slider-button next">&gt;</button>
      </div>
    </div>

    <div class="content-body">
      <div class="video-slider">
        <div class="video-list" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
          <!-- 2개씩 한 슬라이드에 표시 -->
          <div v-for="i in totalSlides" :key="i" class="slide">
            <div class="card-row">
              <ArchiveCard
                v-for="video in popularVideo.slice((i - 1) * 2, i * 2)"
                :key="video.id"
                :cardData="{
                  title: video.title,
                  categoryName: video.categoryName,
                  materialImg: video.youtubeId,  // 유튜브 썸네일을 materialImg로 전달
                }"
                :showFavoriteButton="false"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.slider-buttons {
  display: flex;
}

.slider-button {
  background-color: #f0f0f0;
  color: #333;
  border: none;
  padding: 5px 10px;
  margin-left: 5px;
  cursor: pointer;
  border-radius: 4px;
}

.video-slider {
  position: relative;
  overflow: hidden;
}

.video-list {
  display: flex;
  transition: transform 0.5s ease;
  width: 100%; /* 각 슬라이드의 너비는 화면의 100%로 설정 */
}

.slide {
  display: flex;
  justify-content: space-between; /* 카드가 가로로 배치되도록 설정 */
  min-width: 100%; /* 슬라이드가 화면의 100%를 차지 */
}

.card-row {
  display: flex;
  justify-content: space-between; /* 카드들이 가로로 나란히 배치되도록 설정 */
  gap: 20px; /* 카드 간격 설정 */
  width: 100%;
}

.content-body {
  overflow: hidden;
  height: 400px; /* 슬라이더 높이 설정 */
}

.video-slider .card-row > * {
  flex: 1; /* 모든 카드를 같은 높이로 설정 */
}
</style>
