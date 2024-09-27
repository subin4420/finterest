<template>
  <div>
    <h2>선택한 주식 코드: {{ selectedStockCode }}</h2>
  </div>
</template>

<script>
import { useStockStore } from '@/stores/tradestock'; // Pinia 스토어 가져오기

export default {
  setup() {
    const stockStore = useStockStore(); // Pinia 스토어 인스턴스 가져오기
    return { selectedStockCode: stockStore.selectedStockCode }; // 선택한 주식 코드 가져오기
  },
};
</script>
