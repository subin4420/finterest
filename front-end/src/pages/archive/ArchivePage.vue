<template>
  <div id="app">
    <ArchiveImage />
    <ArchiveNavigationBar @category-selected="filterByCategory" :selectedCategory="selectedCategory" />
    
    <div class="content-section">
      <h2>학습 자료</h2>
      <h3>텍스트 카드들</h3>
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
      // 텍스트와 영상 자료를 모두 가져옵니다.
      await archiveStore.fetchArchive('text'); // 텍스트 자료 가져오기
      const Tarchives = archiveStore.archives.value; // .value를 통해 배열에 접근
      await archiveStore.fetchArchive('video'); // 영상 자료 가져오기
      const Varchives = archiveStore.archives.value; // .value를 통해 배열에 접근

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
      return selectedCategory.value 
        ? textArchives.value.filter(archive => archive.categoryName === selectedCategory.value) 
        : textArchives.value;
    });

    // 필터링된 영상 아카이브
    const filteredVideoArchives = computed(() => {
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

<style>
.content-section {
  margin-bottom: 2rem; /* 섹션 간의 간격 */
}

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}
</style>
