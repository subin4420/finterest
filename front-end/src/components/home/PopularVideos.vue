<script setup>
import { ref, computed } from 'vue';

const popularVideos = ref([
  { id: 1, title: '인기 영상 1', category: '웹 개발', youtubeId: 'dQw4w9WgXcQ' },
  { id: 2, title: '인기 영상 2', category: '모바일 앱', youtubeId: 'WnTCZola7Nc' },
  { id: 3, title: '인기 영상 3', category: '데이터 사이언스', youtubeId: 'eAt72E0N8ro' },
  { id: 4, title: '인기 영상 4', category: '인공지능', youtubeId: 'CSjTlB6uQT0' },
]);

const currentIndex = ref(0);

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 2) % popularVideos.value.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 2 + popularVideos.value.length) % popularVideos.value.length;
};

const getYoutubeThumbnail = (youtubeId) => {
  return `https://img.youtube.com/vi/${youtubeId}/0.jpg`;
};
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
        <div class="video-list" :style="{ transform: `translateX(-${currentIndex * 50}%)` }">
          <div v-for="video in popularVideos" :key="video.id" class="video-item">
            <div class="video-thumbnail-container">
              <img :src="getYoutubeThumbnail(video.youtubeId)" :alt="video.title" class="video-thumbnail">
            </div>
            <div class="video-info">
              <div class="video-title">{{ video.title }}</div>
              <div class="video-category">{{ video.category }}</div>
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
  transition: transform 0.3s ease;
}

.video-item {
  flex: 0 0 50%;
  padding: 0 0.5rem;
  box-sizing: border-box;
}

.video-thumbnail {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
}

.video-info {
  padding: 1rem;
}

.video-title {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.video-category {
  font-size: 0.9rem;
  color: #666;
  background-color: #f0f0f0;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  display: inline-block;
}

</style>
