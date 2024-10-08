<template>
  <div id="app" class="trade-page">
    <TradeImage />
    <TradeNavigationBar />
    <div class="content-wrapper">
      <div v-if="authStore.isLogin" class="user-balance">
        <h2 class="balance-title">현재 잔액</h2>
        <div class="balance-container">
          <div class="balance-item">
            <i class="fas fa-coins"></i>
            <span class="balance-label">포인트</span>
            <span class="balance-value">{{
              formatCurrency(conversionStore.userBalance.totalPoints)
            }}</span>
          </div>
          <div class="balance-item">
            <i class="fas fa-dollar-sign"></i>
            <span class="balance-label">모의투자금</span>
            <span class="balance-value"
              >{{ formatCurrency(conversionStore.userBalance.money) }} 원</span
            >
          </div>
        </div>
      </div>
      <div class="content-grid">
        <ArchiveCard
          v-for="archive in archives"
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
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import ArchiveCard from '@/components/archive/ArchiveCard.vue';
import ArchiveModal from '@/components/archive/ArchiveModal.vue';
import DefaultLayout from '@/components/layouts/DefaultLayout.vue';
import { onMounted, ref } from 'vue';
import { useArchiveStore } from '@/stores/archiveStore';
import { useAuthStore } from '@/stores/auth';
import { useConversionStore } from '@/stores/conversionStore';

export default {
  name: 'ArchivePage',
  components: {
    TradeImage,
    ArchiveCard,
    ArchiveModal,
    DefaultLayout,
    TradeNavigationBar,
  },
  setup() {
    const archiveStore = useArchiveStore();
    const authStore = useAuthStore();
    const conversionStore = useConversionStore();
    const isModalVisible = ref(false);
    const selectedCard = ref({});

    onMounted(async () => {
      archiveStore.fetchArchive();
      if (authStore.isLogin) {
        await conversionStore.fetchUserBalance();
      }
      console.log('archiveStore.archives: ' + archiveStore.archives);
    });

    const archives = archiveStore.archives;

    function openModal(archive) {
      selectedCard.value = archive;
      isModalVisible.value = true;
    }

    function formatCurrency(value) {
      return new Intl.NumberFormat('ko-KR').format(value);
    }

    return {
      archives,
      isModalVisible,
      selectedCard,
      openModal,
      authStore,
      conversionStore,
      formatCurrency,
    };
  },
};
</script>

<style scoped>
.trade-page {
  background-color: #f5f5f5;
}

.content-wrapper {
  min-height: calc(100vh - 60px);
  padding: 20px;
  margin-top: 60px;
}

.user-balance {
  background: linear-gradient(135deg, #3498db, #8e44ad);
  border-radius: 12px;
  padding: 15px;
  margin-bottom: 20px;
  text-align: center;
  color: #ffffff;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.balance-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 15px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.balance-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 10px;
}

.balance-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
}

.balance-item i {
  font-size: 1.8em;
  margin-bottom: 10px;
  color: #ffffff;
}

.balance-label {
  font-size: 0.9em;
  margin-bottom: 5px;
  font-weight: 600;
}

.balance-value {
  font-size: 1.1em;
  font-weight: bold;
}

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}

:deep(.archive-card) {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
