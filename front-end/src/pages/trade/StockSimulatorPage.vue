<template>
  <div>
    <TradeImage />
    <TradeNavigationBar />
    <div class="community-page">
      <StockSearch :kospiStockList="kospiStockList" />
      <StockChart
        :selectStockCode="tradeStore.selectedStockCode"
        v-if="showStockChart"
        :key="chartKey"
      />
      <OrderStock />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useTradeStore } from "@/stores/tradeStore";
import axios from "axios";
import TradeImage from "@/components/trade/TradeImage.vue";
import TradeNavigationBar from "@/components/trade/TradeNavigationBar.vue";
import StockSearch from "@/components/trade/StockSearch.vue";
import StockChart from "@/components/trade/StockChart.vue";
import OrderStock from "@/components/trade/OrderStock.vue";

// KOSPI stock list 상태
const kospiStockList = ref([]);
const isStockSelected = ref(false);
const showStockChart = ref(true); // StockChart 표시 여부를 제어하는 ref
const chartKey = ref(0); // 컴포넌트의 key 값을 업데이트하여 재렌더링
const tradeStore = useTradeStore();

// 상태 관리
const selectedStockCode = ref(""); // selectStockcode는 ref로 관리

// KOSPI 주식 목록 가져오기 함수
const fetchKOSPIStockList = async () => {
  try {
    const response = await axios.get("/api/stock/list/KOSPI");
    kospiStockList.value = Array.isArray(response.data) ? response.data : [];
  } catch (error) {
    console.error("Error fetching KOSPI Stock List:", error);
  }
};

// 컴포넌트가 마운트될 때 주식 목록을 가져옴
onMounted(async () => {
  await fetchKOSPIStockList();
});

watch(
  () => tradeStore.selectedStockCode,
  (newCode) => {
    console.log("선택된 주식 코드 변경:", newCode);
    // 자식 컴포넌트 새로고침 (v-if를 통해 리렌더링 유도)
    showStockChart.value = false;
    setTimeout(() => {
      chartKey.value++; // key 값 변경하여 컴포넌트 재렌더링
      showStockChart.value = true;
    }, 0);
  }
);
</script>

<style scoped>
.community-page {
  display: flex;
  justify-content: space-between; /* 필요에 따라 컴포넌트들 사이 간격 조정 */
  align-items: flex-start; /* 필요에 따라 컴포넌트들 수직 정렬 */
  gap: 10px; /* 컴포넌트들 사이에 간격을 줄 수 있음 */
}
StockSearch,
StockChart,
OrderStock {
  flex-grow: 1; /* 모든 컴포넌트가 같은 너비를 차지하도록 */
}
</style>
