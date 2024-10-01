<template>
  <div>
    <div class="cards">
      <!-- 데이터를 렌더링할 때 filteredArchives를 참조 -->
      <ArchiveCard 
        v-for="archive in filteredArchives" 
        :key="archive.materialId" 
        :cardData="archive" 
        @click.native="openModal(archive)"  
      />
    </div>
    <ArchiveModal 
      v-if="selectedCard"
      :isVisible="isModalVisible" 
      :cardData="selectedCard" 
      :status="selectedCard?.status"
      @update:isVisible="isModalVisible = $event" 
    />
  </div>
</template>

<script>
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
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
    ArchiveModal
  },
  setup(props) {
    const archiveStore = useArchiveStore();
    const isModalVisible = ref(false);
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

    const openModal = (card) => {
      selectedCard.value = card;
      isModalVisible.value = true;
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
      isModalVisible,
      selectedCard,
      openModal
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
