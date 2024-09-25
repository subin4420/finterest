<template>
  <div id="app">
    <TradeImage />
    <TradeNavigationBar />
    <div class="content-grid">
      <ArchiveCard
        v-for="archive in archives"
        :key="archive.materialId"
        :cardData="archive"
        @click.native="openModal(archive)"
      />
    </div>
    <ArchiveModal
      :isVisible="isModalVisible"
      :cardData="selectedCard"
      @update:isVisible="isModalVisible = $event"
    />
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
import DefaultLayout from '@/components/layouts/DefaultLayout.vue'; // DefaultLayout 임포트 추가
import { onMounted, ref } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore';

export default {
  name: 'ArchivePage',
  components: {
    TradeImage,
    ArchiveCard,
    ArchiveModal,
    DefaultLayout, // DefaultLayout 컴포넌트 등록
    TradeNavigationBar,
  },
  setup() {
    const archiveStore = useArchiveStore();
    const isModalVisible = ref(false);
    const selectedCard = ref({});

    onMounted(() => {
      archiveStore.fetchArchive();
      console.log('archiveStore.archives: ' + archiveStore.archives);
    });

    const archives = archiveStore.archives;

    function openModal(archive) {
      selectedCard.value = archive;
      isModalVisible.value = true;
    }

    return { archives, isModalVisible, selectedCard, openModal };
  },
};
</script>

<style>
.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}
</style>
