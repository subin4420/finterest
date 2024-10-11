<template>
  <div class="recommended-archives">
    <div v-for="archive in archives" :key="archive.id" class="archive-item">
      <img :src="archive.image" :alt="archive.title" class="archive-image">
      <div class="archive-info">
        <h4>{{ archive.title }}</h4>
        <p>{{ archive.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'RecommendedArchives',
  props: {
    archives: {
      type: Array,
      required: true
    }
  }
});
</script>

<style scoped>
.recommended-archives {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.archive-item {
  display: flex;
  background-color: #f9f9f9;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.archive-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}
.archive-info {
  padding: 10px;
  flex-grow: 1;
}
.archive-info h4 {
  margin: 0 0 5px;
  font-size: 1.1em;
}
.archive-info p {
  margin: 0;
  font-size: 0.9em;
  color: #666;
}
</style>
