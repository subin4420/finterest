<script setup>
import { ref, onMounted } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore'; // ArchiveStore 가져오기
import ArchiveCard from '@/components/archive/ArchiveCard.vue'; // ArchiveCard 컴포넌트 임포트

// fetchRecentTextArchives를 사용하기 위해 ArchiveStore 가져오기
const { fetchRecentTextArchives, recentText } = useArchiveStore();

const latestUpdates = ref([]); // 빈 배열로 초기화
const currentIndex = ref(0);

// 슬라이드 이동 함수
const nextSlide = () => {
  const totalSlides = Math.ceil(latestUpdates.value.length / 2);
  currentIndex.value = (currentIndex.value + 1) % totalSlides;
};

const prevSlide = () => {
  const totalSlides = Math.ceil(latestUpdates.value.length / 2);
  currentIndex.value = (currentIndex.value - 1 + totalSlides) % totalSlides;
};

// 컴포넌트가 마운트될 때 최신 텍스트 학습 자료 가져오기
onMounted(async () => {
  await fetchRecentTextArchives(); // API 호출로 최신 자료 가져오기
  latestUpdates.value = recentText.value.map(item => ({
    id: item.materialId,
    title: item.title,
    categoryName: item.categoryName,
    materialImg: item.materialImg,
  }));
});
</script>

<template>
  <div>
    <div class="header">
      <h2>최신 업데이트 학습자료</h2>
      <div class="slider-buttons">
        <button @click="prevSlide" class="slider-button prev">&lt;</button>
        <button @click="nextSlide" class="slider-button next">&gt;</button>
      </div>
    </div>

    <div class="content-body">
      <div class="update-slider">
        <div class="update-list" :style="{ transform: `translateX(-${currentIndex * 100}%)` }">
          <!-- 2개씩 한 슬라이드에 표시 -->
          <div v-for="i in Math.ceil(latestUpdates.length / 2)" :key="i" class="slide">
            <div class="card-row">
              <ArchiveCard
                v-for="update in latestUpdates.slice((i - 1) * 2, i * 2)"
                :key="update.id"
                :cardData="update"
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

.update-slider {
  position: relative;
  overflow: hidden;
}

.update-list {
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

.update-slider .card-row > * {
  flex: 1; /* 모든 카드를 같은 높이로 설정 */
}
</style>
