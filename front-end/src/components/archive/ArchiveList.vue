<template>
  <div>
    <div class="cards">
      <!-- 데이터를 렌더링할 때 filteredArchives를 참조 -->
      <ArchiveCard 
        v-for="archive in filteredArchives" 
        :key="archive.materialId" 
        :cardData="archive" 
        :showFavoriteButton="false" 
        @click.native="openModal(archive)"  
      />
    </div>
    <ArchiveModal 
      v-if="selectedCard && !isVideoContent(selectedCard)"
      :isVisible="isArchiveModalVisible" 
      :cardData="selectedCard" 
      @update:isVisible="isArchiveModalVisible = $event" 
    />
    <VideoModal 
      v-if="selectedCard && isVideoContent(selectedCard)"
      :isVisible="isVideoModalVisible" 
      :cardData="selectedCard" 
      @close="closeVideoModal"
      @status-updated="handleStatusUpdate"
    />
  </div>
</template>

<script>
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
import VideoModal from '@/components/archive/VideoModal.vue';
import { ref, watch, onMounted, computed } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore';

export default {
  name: 'ArchiveList',
  props: {
    learningStatus: {
      type: String,
      required: true,
    }
  },
  components: {
    ArchiveCard,
    ArchiveModal,
    VideoModal
  },
  setup(props) {
    const archiveStore = useArchiveStore();
    const isArchiveModalVisible = ref(false);
    const isVideoModalVisible = ref(false);
    const selectedCard = ref(null);

    // 현재 학습 상태에 따라 필터링된 아카이브 목록을 설정
    const filteredArchives = computed(() => {
      if (props.learningStatus === 'incomplete') {
        return archiveStore.inProgressArchives.value; // 진행 중인 학습 자료
      } else if (props.learningStatus === 'completed') {
        return archiveStore.completedArchives.value; // 완료된 학습 자료
      }
      return [];
    });

    const isVideoContent = (card) => {
      return card.type === 'video' || (card.materialImg && card.materialImg.match(/^[a-zA-Z0-9_-]{11}$/));
    };

    const openModal = (card) => {
      selectedCard.value = card;
      if (isVideoContent(card)) {
        isVideoModalVisible.value = true;
      } else {
        isArchiveModalVisible.value = true;
      }
    };

    const closeVideoModal = () => {
      isVideoModalVisible.value = false;
      selectedCard.value = null;
    };

    const handleStatusUpdate = (updatedCard) => {
      console.log('Status updated:', updatedCard);
      // 여기에 상태 업데이트 로직을 추가하세요
    };

    onMounted(async () => {
      try {
        // 페이지 로딩 시 기본적으로 진행 중인 학습 자료와 완료된 학습 자료를 불러옴
        await archiveStore.fetchInProgressArchives();
        await archiveStore.fetchCompletedArchives();
      } catch (error) {
        console.error('Error fetching archives:', error);
      }
    });

    // 학습 상태가 변경될 때 필터링된 아카이브 목록을 다시 가져옴
    watch(() => props.learningStatus, async () => {
      try {
        if (props.learningStatus === 'incomplete') {
          await archiveStore.fetchInProgressArchives();
        } else if (props.learningStatus === 'completed') {
          await archiveStore.fetchCompletedArchives();
        }
      } catch (error) {
        console.error('Error fetching archives on status change:', error);
      }
    });

    return {
      filteredArchives,
      isArchiveModalVisible,
      isVideoModalVisible,
      selectedCard,
      openModal,
      closeVideoModal,
      handleStatusUpdate,
      isVideoContent
    };
  }
};
</script>

<style scoped>
.cards {
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 250px)); /* 최소 크기를 200px로 조정 */
  gap: 20px; /* 카드 간격을 10px로 유지 */
}
</style>
