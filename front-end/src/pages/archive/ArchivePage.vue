<template>
  <div id="app">
    <ArchiveImage />
    <ArchiveNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />

    <div class="content-container">
      <!-- 탭 컴포넌트 추가 -->
      <div class="tab-container">
        <button 
          :class="['tab-button', { active: activeTab === 'text' }]" 
          @click="activeTab = 'text'"
        >
          학습 자료
        </button>
        <button 
          :class="['tab-button', { active: activeTab === 'video' }]" 
          @click="activeTab = 'video'"
        >
          영상 자료
        </button>
      </div>

      <!-- 학습 자료 탭 -->
      <div v-if="activeTab === 'text'" class="content-section">
        <div class="content-grid">
          <ArchiveCard 
            v-for="archive in sortedAndFilteredTextArchives" 
            :key="archive.materialId" 
            :cardData="archive"
            @click="handleCardClick(archive)" 
          />
        </div>
      </div>

      <!-- 영상 자료 탭 -->
      <div v-if="activeTab === 'video'" class="content-section">
        <div class="content-grid">
          <ArchiveCard 
            v-for="archive in sortedVideoArchives" 
            :key="archive.materialId" 
            :cardData="archive" 
            @click="handleCardClick(archive)" 
          />
        </div>
      </div>
    </div>

    <ArchiveModal 
      v-if="selectedCard && isModalVisible"
      :isVisible="isModalVisible" 
      :cardData="selectedCard" 
      @update:isVisible="isModalVisible = $event" 
    />

    <LoginRequiredModal
      v-if="showLoginModal"
      @close="showLoginModal = false"
      @login="goToLogin"
    />

    <!-- 비디오 모달 수정 -->
    <VideoModal 
      v-if="isVideoModalVisible"
      :isVisible="isVideoModalVisible" 
      :cardData="selectedCard"
      @close="closeVideoModal"
      @status-updated="handleStatusUpdate"
    />
  </div>
</template>

<script>
import ArchiveImage from '@/components/archive/ArchiveImage.vue';
import ArchiveNavigationBar from '@/components/archive/ArchiveNavigationBar.vue';
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
import LoginRequiredModal from '@/components/common/LoginRequiredModal.vue';
import ProgressBar from '@/components/common/ProgressBar.vue';
import RecentActivityList from '@/components/archive/RecentActivityList.vue';
import RecommendedArchives from '@/components/archive/RecommendedArchives.vue';
import VideoModal from '@/components/archive/VideoModal.vue';
import { onMounted, ref, computed, watch } from "vue";
import { useArchiveStore } from "@/stores/archiveStore";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';

export default {
  name: 'ArchivePage',
  components: {
    ArchiveImage,
    ArchiveCard,
    ArchiveModal,
    ArchiveNavigationBar,
    LoginRequiredModal,
    ProgressBar,
    RecentActivityList,
    RecommendedArchives,
    VideoModal,
  },
  setup() {
    const archiveStore = useArchiveStore();
    const authStore = useAuthStore();
    const router = useRouter();
    const isModalVisible = ref(false);
    const selectedCard = ref(null);
    const selectedCategory = ref(null);
    const showLoginModal = ref(false);
    const searchQuery = ref('');
    const sortOption = ref('date');
    const overallProgress = ref(0);
    const recentActivities = ref([]);
    const isVideoModalVisible = ref(false);
    const selectedVideoId = ref('');
    const selectedVideoTitle = ref('');
    const { isLogin } = storeToRefs(authStore);

    const recommendedArchives = ref([
      { id: 1, title: '주식 시장의 이해', description: '주식 시장의 기본 개념을 배웁니다.', image: '/path/to/image1.jpg' },
      { id: 2, title: '투자 전략 수립하기', description: '효과적인 투자 전략을 세우는 방법을 학습합니다.', image: '/path/to/image2.jpg' },
      // ... 더 많은 추천 자료
    ]);

    const { textArchives, videoArchives } = archiveStore;

    onMounted(async () => {
      try {
        await archiveStore.fetchTextArchive();
        await archiveStore.fetchVideoArchive();
        await archiveStore.fetchRecentActivities();
        recentActivities.value = archiveStore.recentActivities;
      } catch (error) {
        console.error('Error during data fetch:', error);
      }
    });

    watch(() => archiveStore.recentActivities, (newActivities) => {
      recentActivities.value = newActivities;
    });

    const filterByCategory = (category) => {
      selectedCategory.value = category;
    };

    const filteredTextArchives = computed(() => {
      if (!textArchives.value) return [];
      if (selectedCategory.value === '즐겨찾기') {
        return textArchives.value.filter(archive => archive.favorite);
      }
      return selectedCategory.value 
        ? textArchives.value.filter(archive => archive.categoryName === selectedCategory.value) 
        : textArchives.value;
    });

    const filteredVideoArchives = computed(() => {
      if (!videoArchives.value) return [];
      if (selectedCategory.value === '즐겨찾기') {
        return videoArchives.value.filter(archive => archive.favorite);
      }
      return selectedCategory.value 
        ? videoArchives.value.filter(archive => archive.categoryName === selectedCategory.value) 
        : videoArchives.value;
    });

    const sortedTextArchives = computed(() => {
      if (!filteredTextArchives.value) return [];
      return [...filteredTextArchives.value].sort((a, b) => {
        if (a.status === 'incomplete' && b.status !== 'incomplete') return -1;
        if (b.status === 'incomplete' && a.status !== 'incomplete') return 1;
        return 0;
      });
    });

    const sortedVideoArchives = computed(() => {
      if (!filteredVideoArchives.value) return [];
      return [...filteredVideoArchives.value].sort((a, b) => {
        if (a.status === 'incomplete' && b.status !== 'incomplete') return -1;
        if (b.status === 'incomplete' && a.status !== 'incomplete') return 1;
        return 0;
      });
    });

    // 검색과 정렬을 적용한 계산된 속성
    const sortedAndFilteredTextArchives = computed(() => {
      let result = sortedTextArchives.value;
      if (searchQuery.value) {
        result = result.filter(archive => 
          archive.title.toLowerCase().includes(searchQuery.value.toLowerCase())
        );
      }
      // 정렬 로직 구현
      // ...
      return result;
    });

    const recentArchives = computed(() => {
      const allArchives = [...sortedTextArchives.value, ...sortedVideoArchives.value];
      return allArchives
        .sort((a, b) => new Date(b.lastAccessedAt) - new Date(a.lastAccessedAt))
        .slice(0, 4); // 최근 4개의 자료만 표시
    });

    const handleCardClick = (card) => {
      if (isLogin.value) {
        console.log('Clicked card:', card);
        selectedCard.value = card;
        if (card.type === 'video' || (card.materialImg && card.materialImg.match(/^[a-zA-Z0-9_-]{11}$/))) {
          console.log('Video card clicked');
          isVideoModalVisible.value = true;
        } else {
          console.log('Text card clicked');
          isModalVisible.value = true;
        }
      } else {
        showLoginModal.value = true;
      }
    };

    const closeVideoModal = () => {
      isVideoModalVisible.value = false;
      selectedCard.value = null;
    };

    const goToLogin = () => {
      showLoginModal.value = false;
      const currentPath = router.currentRoute.value.fullPath;
      router.push({ 
        path: '/auth/login', 
        query: { redirect: currentPath } 
      });
    };

    const activeTab = ref('text'); // 기본 탭을 '학습 자료'로 설정

    const handleStatusUpdate = (updatedCard) => {
      // 상태가 업데이트된 카드를 찾아 업데이트
      const index = sortedVideoArchives.value.findIndex(card => card.materialId === updatedCard.materialId);
      if (index !== -1) {
        sortedVideoArchives.value[index] = { ...sortedVideoArchives.value[index], ...updatedCard };
      }
    };

    return { 
      sortedTextArchives, 
      sortedVideoArchives, 
      isModalVisible, 
      selectedCard, 
      handleCardClick, 
      filterByCategory, 
      selectedCategory,
      showLoginModal,
      goToLogin,
      authStore, // authStore를 템플릿에서 용할 수 있도록 추가
      searchQuery,
      sortOption,
      overallProgress,
      recentActivities,
      recommendedArchives,
      sortedAndFilteredTextArchives,
      recentArchives,
      activeTab,
      isVideoModalVisible,
      selectedVideoId,
      selectedVideoTitle,
      closeVideoModal,
      handleStatusUpdate,
      isLogin,
    };
  }
}
</script>

<style scoped>
h3 {
  font-size: 1.8rem; /* 폰트 크기를 조금 더 키워서 눈에 띄게 */
  font-weight: bold; /* 텍스트를 굵게 */
  color: #333; /* 짙은 회색으로 시각적으로 부드럽게 */
  text-align: left; /* 텍스트 중앙에 배치 */
  margin-bottom: 1.5rem; /* 제목 아래쪽에 간격 추가 */
  padding-bottom: 0.5rem; /* 제목과 카드 사이에 간격 */
  border-bottom: 2px solid #ccc; /* 제목 아래에 구분선 추가 */
}
.content-section {
  margin-bottom: 2rem; /* 섹션 간의 간격 */
  width: 100%; /* 80%에서 100%로 변경 */
  margin: 0 auto;
}
.content-section + .content-section {
  margin-top: 3rem; /* 텍스트 카드들과 영상 카드들 사이에 여백 추가 */
}
.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  gap: 1rem;
}
.content-grid > * {
  flex: 1 1 calc(25% - 1rem); /* 각 카드가 25%의 너비를 차지 */
  max-width: calc(25% - 1rem); /* 최대 너비도 25%로 설정 */
}
@media (max-width: 1200px) {
  .content-grid > * {
    flex: 1 1 calc(33.33% - 1rem); /* 화면이 작아지면 한 행에 3개 */
    max-width: calc(33.33% - 1rem);
  }
}

@media (max-width: 900px) {
  .content-grid > * {
    flex: 1 1 calc(50% - 1rem); /* 더 작은 화면에서 한 행에 2개 */
    max-width: calc(50% - 1rem);
  }
}

@media (max-width: 600px) {
  .content-grid > * {
    flex: 1 1 100%; /* 작은 모바일 화면에서는 한 행에 1개 */
    max-width: 100%;
  }
}

.progress-section, .recent-activity, .recommended-section, .search-bar, .sort-options {
  margin-bottom: 2rem;
  width: 80%;
  margin: 0 auto;
}

.recent-materials {
  margin-bottom: 2rem;
  width: 80%;
  margin: 0 auto;
}

.recent-materials .content-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
}

.recent-materials .content-grid > * {
  flex: 1 1 calc(25% - 1rem);
  max-width: calc(25% - 1rem);
}

@media (max-width: 1200px) {
  .recent-materials .content-grid > * {
    flex: 1 1 calc(50% - 1rem);
    max-width: calc(50% - 1rem);
  }
}

@media (max-width: 600px) {
  .recent-materials .content-grid > * {
    flex: 1 1 100%;
    max-width: 100%;
  }
}

.summary-card {
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 1.5rem;
  margin-bottom: 2rem;
  width: 80%;
  margin: 0 auto;
}

.summary-card h3 {
  font-size: 1.4rem;
  margin-bottom: 1rem;
  border-bottom: none;
}

.recent-materials-list {
  list-style-type: none;
  padding: 0;
}

.recent-materials-list li {
  padding: 0.5rem 0;
  cursor: pointer;
  transition: background-color 0.2s;
}

.recent-materials-list li:hover {
  background-color: #e9ecef;
}

.search-bar {
  margin-top: 1rem;
}

.search-bar input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.tab-container {
  width: 100%; /* 탭 컨테이너의 너비를 100%로 설정 */
  padding: 0 20px; /* 좌우 패딩 추가 */
  box-sizing: border-box; /* 패딩을 너비에 포함 */
}

.tab-button {
  padding: 10px 20px;
  margin-right: 10px;
  border: none;
  background-color: transparent;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  color: #666;
  transition: all 0.3s;
}

.tab-button.active {
  color: #00D18B;
  border-bottom: 2px solid #00D18B;
}

.tab-button:hover {
  color: #00D18B;
}

.content-section {
  margin-top: 20px;
}

.content-container {
  width: 80%;
  margin: 0 auto;
  padding-top: 00px;
}

</style>