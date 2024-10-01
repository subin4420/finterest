``
<template>
  <div>
    <h2>선택한 주식 코드: {{ selectedStockCode }}</h2>
  </div>
</template>

<script>
import { useTradeStore } from "@/stores/tradeStore"; // Pinia 스토어 가져오기

export default {
  setup() {
    const tradeStore = useTradeStore(); // Pinia 스토어 인스턴스 가져오기
    return { selectedStockCode: tradeStore.selectedStockCode }; // 선택한 주식 코드 가져오기
  },
};
</script>
