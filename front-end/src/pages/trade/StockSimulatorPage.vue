<template>
  <div class="페이지-컨테이너">
    <div class="거래-헤더"></div>
    <div class="콘텐츠-래퍼">
      <SideTradeNavigationBar />

      <div class="콘텐츠">
        <h1 class="page-title">모의투자 페이지</h1>

        <div class="주식-시뮬레이터-레이아웃">
          <div class="검색-차트-주문-컨테이너">
            <StockSearch :kospiStockList="kospiStockList" class="주식-검색" />
            <div class="차트-주문-컨테이너">
              <StockChart
                :selectStockCode="tradeStore.selectedStockCode"
                v-if="showStockChart"
                :key="chartKey"
                class="주식-차트"
              />
              <OrderStock class="주문-주식" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useTradeStore } from '@/stores/tradeStore';
import axios from 'axios';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import StockSearch from '@/components/trade/StockSearch.vue';
import StockChart from '@/components/trade/StockChart.vue';
import OrderStock from '@/components/trade/OrderStock.vue';

const kospiStockList = ref([]);
const showStockChart = ref(true);
const chartKey = ref(0);
const tradeStore = useTradeStore();

const fetchKOSPIStockList = async () => {
  try {
    const response = await axios.get('/api/stock/list/KOSPI');
    kospiStockList.value = Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    console.error('KOSPI 주식 목록 가져오기 오류:', error);
  }
};

onMounted(async () => {
  await fetchKOSPIStockList();
});

watch(
  () => tradeStore.selectedStockCode,
  (newCode) => {
    console.log('선택된 주식 코드 변경:', newCode);
    showStockChart.value = false;
    setTimeout(() => {
      chartKey.value++;
      showStockChart.value = true;
    }, 0);
  }
);
</script>

<style scoped>
.페이지-컨테이너 {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
}

.거래-헤더 {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.콘텐츠-래퍼 {
  display: flex;
  margin-top: 20px;
  background-color: #ffffff;
}

.콘텐츠 {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
  background-color: #ffffff;
}

.주식-시뮬레이터-레이아웃 {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.검색-차트-주문-컨테이너 {
  display: flex;
  gap: 20px;
}

.주식-검색 {
  width: 200px; /* 너비를 줄임 */
  min-width: 0;
}

.차트-주문-컨테이너 {
  display: flex;
  flex: 1;
  gap: 20px;
  max-width: 100vw; /* 전체 화면을 넘어가지 않도록 설정 */
  overflow: hidden; /* 넘치는 내용 숨기기 */
}

.주식-차트 {
  flex: 2;
  height: 600px;
  max-height: 600px; /* 최대 높이 설정 */
  border-radius: 8px; /* 차트에 둥근 모서리 추가 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 차트에 그림자 추가 */
}

.주문-주식 {
  flex: 1;
  min-width: 200px; /* 최소 너비 조정 */
  max-width: 200px; /* 최대 너비 설정 */
  height: 400px; /* 높이 설정 */
  border-radius: 8px; /* 주문 컴포넌트에 둥근 모서리 추가 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 주문 컴포넌트에 그림자 추가 */
  background-color: #f9f9f9; /* 배경색 추가 */
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
</style>
