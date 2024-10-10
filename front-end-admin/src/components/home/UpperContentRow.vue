<script setup>
import LatestUpdates from './LatestUpdates.vue';
import PopularCategories from './PopularCategories.vue';
import PopularVideos from './PopularVideos.vue';
</script>

<template>
  <section class="content-row">
    <LatestUpdates class="content-column latest-updates" />
    <PopularCategories class="content-column popular-categories" />
    <PopularVideos class="content-column popular-videos" />
  </section>
</template>

<style scoped>
.content-row {
  display: flex;
  justify-content: space-between;
  align-items: stretch;
  padding: 2rem;
  background-color: #f5f5f5;
  min-height: 300px; /* 최소 높이 설정 */
}

.content-column {
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.latest-updates {
  flex: 5;
  margin-right: 1rem;
}

.popular-categories {
  flex: 2;
  margin-right: 1rem;
}

.popular-videos {
  flex: 3;
}

:deep(h2) {
  font-size: 1.25rem;
  margin-bottom: 1rem;
  font-weight: bold;
}

:deep(.content-body) {
  flex-grow: 1;
  overflow-y: auto;
}
</style>