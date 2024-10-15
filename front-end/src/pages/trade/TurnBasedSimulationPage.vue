<template>
  <div>
    <div class="page-container">
      <div class="trade-header"></div>
      <div class="content-wrapper">
        <SideTradeNavigationBar />

        <div class="content">
          <StockSummary
            :holdingStockAcount="holdingStockAcount"
            :profitRate="profitRate"
            :assessedAssets="assessedAssets"
          />

          <div class="community-page">
            <ScenarioChart @loaded="onChartLoaded" :stockData="stockData" />
            <ScenarioOrder
              v-if="isChartLoaded"
              @next-turn="handleNextTurn"
              @update-stock-data="updateStockData"
              :stockData="stockData"
            />
            <ScenarioModal
              class="modal-background"
              :isVisible="isModalVisible"
              :data="modalData"
              @close="isModalVisible = false"
            />
            <!-- StockSummary 컴포넌트 추가 -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import ScenarioModal from '@/components/trade/scenario/ScenarioModal.vue';
import ScenarioChart from '@/components/trade/scenario/ScenarioChart.vue';
import ScenarioOrder from '@/components/trade/scenario/ScenarioOrder.vue';
import StockSummary from '@/components/trade/scenario/StockSummary.vue';
import { ref } from 'vue';
import axios from 'axios';

// 모달
const isModalVisible = ref(false);
const modalData = ref({});

// ScenarioChart 로딩 상태 추적
const isChartLoaded = ref(false);
const stockData = ref([]);

// 필요한 데이터 정의
const holdingStockAcount = ref(0);
const profitRate = ref(0);
const assessedAssets = ref(0);

// 데이터 업데이트 함수
const updateStockData = (data) => {
  holdingStockAcount.value = data.holdingStockAcount;
  profitRate.value = data.profitRate;
  assessedAssets.value = data.assessedAssets;
};

// ScenarioChart 로딩 완료 후 호출될 메서드
const onChartLoaded = () => {
  isChartLoaded.value = true; // 차트가 로딩된 후 isChartLoaded 값을 true로 설정
};
const handleNextTurn = (newTurnValue) => {
  console.log('부모에서 감지된 턴:', newTurnValue);
  axios
    .post(`/api/scenario/next/${newTurnValue}`)
    .then((response) => {
      const result = response.data;
      stockData.value = [
        result.stck_bsop_date,
        result.stck_oprc,
        result.stck_clpr,
        result.stck_lwpr,
        result.stck_hgpr,
      ];
      modalData.value = result;
      isModalVisible.value = true;
    })
    .catch((error) => {
      console.error('Error during next turn request:', error);
    });
};
</script>

<style>
.page-container {
  display: flex;
  flex-direction: column;
}

.trade-header {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  display: flex;
  margin-top: 20px;
}

.content {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
}

.community-page {
  display: flex;
  justify-content: left; /* 필요에 따라 컴포넌트들 사이 간격 조정 */
  align-items: flex-start; /* 필요에 따라 컴포넌트들 수직 정렬 */
  margin: 0; /* 간격 제거 */
}

ScenarioChart,
ScenarioOrder {
  flex-grow: 1; /* 모든 컴포넌트가 같은 너비를 차지하도록 */
  margin: 0; /* 각 컴포넌트의 마진 제거 */
}

.modal-background {
  background: url('/src/assets/images/quiz_img/newspaper.png') no-repeat center
    center;
  background-size: contain; /* 이미지 크기를 contain으로 설정 */
  max-width: 80%; /* 최대 너비를 80%로 제한 */
  max-height: 80%; /* 최대 높이를 80%로 제한 */
  margin: 0 auto; /* 중앙 정렬 */
}
</style>
