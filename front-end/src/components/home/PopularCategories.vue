<script setup>
import { ref } from 'vue';

const popularCategories = ref([
  '경제상식', '투자', '세금', 
  '재무관리', '기타', '카테고리1'
]);
</script>

<template>
  <div class="popular-categories">
    <h2>인기 카테고리</h2>
    <div class="category-grid">
      <div v-for="(category, index) in popularCategories" :key="index" class="category-item">
        {{ category }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.popular-categories {
  padding: 1rem;
}

h2 {
  margin-bottom: 1rem;
  font-size: 1.25rem;
  font-weight: bold;
}

.category-grid {
  margin-top: 16px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 0.5rem;
}

.category-item {
  background-color: #f0f0f0;
  border-radius: 20px;
  padding: 0.5rem 1rem;
  text-align: center;
  font-size: 0.9rem;
  color: #333;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
