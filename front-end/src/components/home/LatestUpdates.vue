<script setup>
import { ref, computed } from 'vue';

const latestUpdates = ref([
  {
    id: 1,
    title: '최신 학습자료 1',
    category: '카테고리1',
    thumbnail: '/src/assets/images/가로예시1.jpg',
  },
  {
    id: 2,
    title: '최신 학습자료 2',
    category: '카테고리2',
    thumbnail: '/src/assets/images/가로예시2.jpg',
  },
  {
    id: 3,
    title: '최신 학습자료 3',
    category: '카테고리3',
    thumbnail: '/src/assets/images/가로예시3.jpg',
  },
  {
    id: 4,
    title: '최신 학습자료 4',
    category: '카테고리4',
    thumbnail: '/src/assets/images/가로예시1.jpg',
  },
]);

const currentIndex = ref(0);

const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 2) % latestUpdates.value.length;
};

const prevSlide = () => {
  currentIndex.value =
    (currentIndex.value - 2 + latestUpdates.value.length) %
    latestUpdates.value.length;
};
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
        <div
          class="update-list"
          :style="{ transform: `translateX(-${currentIndex * 50}%)` }"
        >
          <div
            v-for="update in latestUpdates"
            :key="update.id"
            class="update-item"
          >
            <img
              :src="update.thumbnail"
              alt="학습자료 썸네일"
              class="update-thumbnail"
            />
            <div class="update-info">
              <div class="update-title">{{ update.title }}</div>
              <div class="update-category">{{ update.category }}</div>
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
  transition: transform 0.3s ease;
}

.update-item {
  flex: 0 0 50%;
  padding: 0 0.5rem;
  box-sizing: border-box;
}

.update-thumbnail {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
}

.update-info {
  padding: 1rem;
}

.update-title {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.update-category {
  font-size: 0.9rem;
  color: #666;
}
</style>
