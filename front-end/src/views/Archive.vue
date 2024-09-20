<template>
  <div>
    <h1>나는 학습</h1>
    <div v-if="archives.length === 0">데이터를 불러오는 중...</div>
    <ul v-else>
      <li v-for="archive in archives" :key="archive.materialId">
        <strong>{{ archive.title }}</strong> - {{ archive.description }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { useArchiveStore } from "@/store/archiveStore";

const archiveStore = useArchiveStore();

// 컴포넌트가 마운트될 때 데이터를 가져옵니다.
onMounted(() => {
  archiveStore.fetchArchive();
});

// archives를 컴포넌트에서 사용할 수 있도록 가져옵니다.
const archives = archiveStore.archives;
</script>

<style scoped></style>
