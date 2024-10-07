<template>
  <div class="notice-detail-page container mt-5">
    <h1 class="mb-4">공지사항 상세</h1>
    <div v-if="currentNotice" class="card">
      <div class="card-body">
        <h2 class="card-title">{{ currentNotice.title }}</h2>
        <p class="card-text">
          <small class="text-muted">
            작성자: {{ currentNotice.writer }} | 작성일: {{ formatDate(currentNotice.createdAt) }} | 조회수: {{ currentNotice.viewCount }}
          </small>
        </p>
        <p class="card-text">{{ currentNotice.content }}</p>
      </div>
    </div>
    <button @click="goBack" class="btn btn-primary mt-3">목록으로 돌아가기</button>
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useNoticeStore } from '@/stores/noticeStore';

const route = useRoute();
const router = useRouter();
const noticeStore = useNoticeStore();

const currentNotice = computed(() => noticeStore.currentNotice);

onMounted(async () => {
  const noticeId = parseInt(route.params.id);
  await noticeStore.fetchNoticeDetail(noticeId);
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR');
};

const goBack = () => {
  router.push({ name: 'NoticeList' });
};
</script>

<style scoped>
.notice-detail-page {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card {
  border: 1px solid #e0e0e0;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.card-title {
  font-weight: bold;
  font-size: 1.5rem;
  color: #333;
}

.card-text {
  font-size: 1rem;
  margin-top: 15px;
}
</style>
