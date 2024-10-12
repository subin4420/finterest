<template>
  <div>
    <div class="page-container">
      <div class="trade-header"></div>
      <div class="content-wrapper">
        <SideTradeNavigationBar />
        <div class="content">
          <div class="community-page">
            <ScenarioChart @loaded="onChartLoaded" :stockData="stockData" />

            <!-- ScenarioChart가 완료된 후에 ScenarioOrder를 렌더링 -->
            <ScenarioOrder
              v-if="isChartLoaded"
              @next-turn="handleNextTurn"
              :stockData="stockData"
            />

            <!--모달 슈웃~  -->
            <ScenarioModal
              :isVisible="isModalVisible"
              :data="modalData"
              @close="isModalVisible = false"
            />
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
import { ref, onMounted } from 'vue';
import axios from 'axios';

// 모달
const isModalVisible = ref(false);
const modalData = ref({});

// ScenarioChart 로딩 상태 추적
const isChartLoaded = ref(false);
// 컴포넌트에 보낼 데이터 반응형으로 선언
const stockData = ref([]);
const processData = (result) => {
  stockData.value = [
    result.stck_bsop_date,
    result.stck_oprc,
    result.stck_clpr,
    result.stck_lwpr,
    result.stck_hgpr,
  ];
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
      // 응답 데이터를 로그로 출력
      const result = response.data;
      console.log('description:', result.description);
      console.log('stck_bsop_date:', result.stck_bsop_date);
      console.log('stck_oprc:', result.stck_oprc);
      console.log('stck_clpr:', result.stck_clpr);
      console.log('stck_lwpr:', result.stck_lwpr);
      console.log('stck_hgpr:', result.stck_hgpr);
      processData(result);

      // 모달모달모달
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

.page-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
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

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}

.conversion-container {
  margin-top: 20px;
}

h2 {
  margin-bottom: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 5px;
}
</style>
