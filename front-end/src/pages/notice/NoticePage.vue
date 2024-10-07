<template>
  <div class="notice-page container mt-5">
    <h1 class="mb-4">공지사항</h1>
    <div class="row">
      <div class="col-12" v-for="notice in notices" :key="notice.noticeId">
        <div class="card mb-3 rounded" @click="goToNoticeDetail(notice.noticeId)">
          <div class="card-body d-flex align-items-center">
            <div class="flex-grow-1">
              <h5 class="card-title mb-0">{{ notice.title }}</h5>
              <p class="card-text mb-0">
                <small class="text-muted">
                  {{ notice.writer }} | {{ formatDate(notice.createdAt) }} | 조회수: {{ notice.viewCount }}
                </small>
              </p>
            </div>
            <i class="fas fa-chevron-right text-muted"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { useNoticeStore } from '@/stores/noticeStore';

const router = useRouter();
const noticeStore = useNoticeStore();
const notices = ref([]);

onMounted(async () => {
  await noticeStore.fetchNotices();
  notices.value = noticeStore.notices;
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('ko-KR');
};

const goToNoticeDetail = (noticeId) => {
  router.push({ name: 'NoticeDetail', params: { id: noticeId } });
};
</script>

<style scoped>
.notice-page {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card {
  border: 1px solid #e0e0e0;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: box-shadow 0.3s ease;
}

.card:hover {
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.card-body {
  padding: 0.75rem 1.25rem;
}

.card-title {
  font-weight: bold;
  font-size: 1.1rem;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.card-text {
  font-size: 0.9rem;
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #b3b3b3;
  padding-bottom: 10px;
  margin-bottom: 30px;
}
</style>