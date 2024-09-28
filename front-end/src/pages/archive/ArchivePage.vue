<template>
  <div id="app">
    <ArchiveImage />
    <ArchiveNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />
    
    <div class="content-section">
      <h3>학습 자료</h3>
      <div class="content-grid">
        <ArchiveCard 
          v-for="archive in filteredTextArchives" 
          :key="archive.materialId" 
          :cardData="archive" 
          @click.native="openModal(archive)" 
        />
      </div>
    </div>

    <div class="content-section">
      <h3>영상 자료</h3>
      <div class="content-grid">
        <ArchiveCard 
          v-for="archive in filteredVideoArchives" 
          :key="archive.materialId" 
          :cardData="archive" 
          @click.native="openModal(archive)" 
        />
      </div>
    </div>

    <ArchiveModal 
      :isVisible="isModalVisible" 
      :cardData="selectedCard" 
      @update:isVisible="isModalVisible = $event" 
    />
  </div>
</template>

<script>
import ArchiveImage from '@/components/archive/ArchiveImage.vue';
import ArchiveNavigationBar from '@/components/archive/ArchiveNavigationBar.vue';
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
import { onMounted, ref, computed } from "vue";
import { useArchiveStore } from "@/stores/archiveStore";

export default {
  name: 'ArchivePage',
  components: {
    ArchiveImage,
    ArchiveCard,
    ArchiveModal,
    ArchiveNavigationBar
  },
  setup() {
    const archiveStore = useArchiveStore();
    const isModalVisible = ref(false);
    const selectedCard = ref({});
    const selectedCategory = ref(null); // 선택된 카테고리 상태 추가

    const textArchives = ref([]);
    const videoArchives = ref([]);

    onMounted(async () => {
      await archiveStore.fetchArchive('text'); // 텍스트 자료 가져오기
      const Tarchives = archiveStore.archives.value; // .value를 통해 배열에 접근
      console.log('Text Archives:', Tarchives); // 추가된 로그
      await archiveStore.fetchArchive('video'); // 영상 자료 가져오기
      const Varchives = archiveStore.archives.value; // .value를 통해 배열에 접근
      console.log('Video Archives:', Varchives); // 추가된 로그

      // 자료를 구분하여 저장
      textArchives.value = Tarchives.filter(archive => archive.link === null);
      videoArchives.value = Varchives.filter(archive => archive.link !== null);
    });

    // 카테고리 필터링 함수
    const filterByCategory = (category) => {
      selectedCategory.value = category;
    };

    // 필터링된 텍스트 아카이브
    const filteredTextArchives = computed(() => {
      if (selectedCategory.value === '즐겨찾기') {
        return textArchives.value.filter(archive => archive.favorite); // 즐겨찾기된 카드만 필터링
      }
      return selectedCategory.value 
        ? textArchives.value.filter(archive => archive.categoryName === selectedCategory.value) 
        : textArchives.value;
    });

    // 필터링된 영상 아카이브
    const filteredVideoArchives = computed(() => {
      if (selectedCategory.value === '즐겨찾기') {
        return videoArchives.value.filter(archive => archive.favorite); // 즐겨찾기된 카드만 필터링
      }
      return selectedCategory.value 
        ? videoArchives.value.filter(archive => archive.categoryName === selectedCategory.value) 
        : videoArchives.value;
    });

    function openModal(archive) {
      selectedCard.value = archive;
      isModalVisible.value = true;
    }

    return { filteredTextArchives, filteredVideoArchives, isModalVisible, selectedCard, openModal, filterByCategory, selectedCategory };
  }
}
</script>

<style scoped>
h3 {
  font-size: 1.8rem; /* 폰트 크기를 조금 더 키워서 눈에 띄게 */
  font-weight: bold; /* 텍스트를 굵게 */
  color: #333; /* 짙은 회색으로 시각적으로 부드럽게 */
  text-align: left; /* 텍스트를 중앙에 배치 */
  margin-bottom: 1.5rem; /* 제목 아래쪽에 간격 추가 */
  padding-bottom: 0.5rem; /* 제목과 카드 사이에 간격 */
  border-bottom: 2px solid #ccc; /* 제목 아래에 구분선 추가 */
}
.content-section {
  margin-bottom: 2rem; /* 섹션 간의 간격 */
  width: 80%; /* 전체 화면의 80%만 차지 */
  margin: 0 auto; /* 화면 중앙 정렬 */
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
</style>
