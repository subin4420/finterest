<script setup>
import { ref } from 'vue';

const topCourses = ref([
  {
    id: 1,
    title: '웹 프로그래밍(풀스택)',
    category: '[코스설계] Front-End 윤지수 부스트코스',
    image: '/src/assets/images/가로예시1.jpg',
  },
  {
    id: 2,
    title: '머신러닝을 위한 파이썬',
    category: '최성철 부스트코스',
    image: '/src/assets/images/가로예시2.jpg',
  },
  {
    id: 3,
    title: '텐서플로우로 시작하는 딥러닝 기초',
    category: 'Deep Learning Zero To All 부스트코스',
    image: '/src/assets/images/가로예시3.jpg',
  },
]);
</script>

<template>
  <section class="lower-content-row">
    <h2>부스트코스 최고 인기 강의 TOP 3</h2>
    <div class="course-list">
      <div v-for="course in topCourses" :key="course.id" class="course-card">
        <img :src="course.image" :alt="course.title" class="course-image" />
        <h3>{{ course.title }}</h3>
        <p class="course-category">{{ course.category }}</p>
        <div class="course-stats"></div>
        <p class="course-tags">
          <span v-for="tag in course.tags" :key="tag">{{ tag }}</span>
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.lower-content-row {
  padding: 2rem;
  background-color: #fff;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.course-list {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.course-card {
  flex: 1;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.course-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

h3 {
  font-size: 1.2rem;
  margin: 1rem;
}

.course-category {
  font-size: 0.9rem;
  color: #666;
  margin: 0 1rem;
}

.course-stats {
  display: flex;
  justify-content: space-between;
  margin: 1rem;
  font-size: 0.9rem;
}

.course-stats span {
  display: flex;
  align-items: center;
}

.course-stats img {
  width: 16px;
  height: 16px;
  margin-right: 0.5rem;
}

.course-tags {
  margin: 1rem;
}

.course-tags span {
  display: inline-block;
  background-color: #f0f0f0;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.8rem;
  margin-right: 0.5rem;
  margin-bottom: 0.5rem;
}
</style>
