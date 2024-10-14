<script setup>
import { ref, onMounted } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore';

const { videoArchives, fetchVideoArchive } = useArchiveStore();

const popularVideos = ref([]);

onMounted(async () => {
  await fetchVideoArchive();
  popularVideos.value = videoArchives.value.slice(0, 4);
  console.log('인기 비디오:', popularVideos.value);
  
  // 각 비디오의 youtubeId 확인
  popularVideos.value.forEach(video => {
    console.log('Video ID:', video.materialImg);
    console.log('Thumbnail URL:', getYoutubeThumbnail(video.materialImg));
  });
});

const currentIndex = ref(0);

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 2) % popularVideos.value.length;
};

const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 2 + popularVideos.value.length) % popularVideos.value.length;
};

const getYoutubeThumbnail = (youtubeId) => {
  return `https://img.youtube.com/vi/${youtubeId}/hqdefault.jpg`;
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
          <div v-for="video in popularVideos" :key="video.materialId" class="video-item">
            <div class="video-thumbnail-container">
              <img :src="getYoutubeThumbnail(video.materialImg)" :alt="video.title" class="video-thumbnail" @error="console.error('이미지 로딩 실패:', video.youtubeId)">
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
  margin-top: 1rem; /* 상단 여백 추가 */
}

.video-list {
  display: flex;
  transition: transform 0.3s ease;
}

.video-item {
  flex: 0 0 50%;
  padding: 0 0.5rem;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}

.video-thumbnail-container {
  position: relative;
  padding-top: 56.25%; /* 16:9 비율 유지 */
  overflow: hidden;
  border-radius: 8px;
}

.video-thumbnail {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.video-info {
  padding: 0.5rem 0; /* 상하 패딩만 적용 */
  flex-grow: 1; /* 남은 공간 채우기 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.video-title {
  font-weight: bold;
  margin-bottom: 0.25rem;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  line-height: 1.2em;
  max-height: 2.4em;
}

.video-category {
  font-size: 0.8rem; /* 글자 크기 약간 축소 */
  color: #666;
  background-color: #f0f0f0;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  display: inline-block;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  align-self: flex-start; /* 왼쪽 정렬 */
}
</style>
