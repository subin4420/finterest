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
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

h2 {
  margin-bottom: 1rem;
  font-size: 1.25rem;
  font-weight: bold;
  color: #333;
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 0.75rem;
}

.category-item {
  background-color: #ffffff;
  border-radius: 20px;
  padding: 0.5rem 1rem;
  text-align: center;
  font-size: 0.9rem;
  color: #333;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.category-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}
</style>
