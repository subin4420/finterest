<template>
  <div class="order-summary">
    <div class="summary-card">
      <div
        class="summary-item"
        style="
          display: flex;
          justify-content: space-between;
          margin-bottom: 12px;
        "
      >
        <div style="flex: 1; text-align: center">
          보유 주식 수:
          <span class="highlight">{{ holdingStockAcount }} 주</span>
        </div>
        <div style="flex: 1; text-align: center">
          수익률:
          <span
            :style="{ color: profitRate >= 0 ? 'red' : 'blue' }"
            class="highlight"
            >{{ profitRate }}%</span
          >
        </div>
        <div style="flex: 1; text-align: center">
          총 평가 금액:
          <span class="highlight"
            >{{ assessedAssets.toLocaleString() }} 원</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  holdingStockAcount: Number,
  profitRate: Number,
  assessedAssets: Number,
});
</script>

<style scoped>
.summary-card {
  background-color: #ffffff; /* 카드 배경색 */
  border: 1px solid #ffffff; /* 카드 테두리 */
  border-radius: 8px; /* 모서리 둥글게 */
  padding: 16px; /* 내부 여백 */
}

.summary-item {
  padding: 8px; /* 항목 내부 여백 추가 */
  background-color: #ffffff; /* 항목 배경색 */
  border-radius: 4px; /* 항목 모서리 둥글게 */
  boarder: 2px solid #000000;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.5); /* 항목 그림자 효과 */
}

.highlight {
  font-weight: bold; /* 강조 텍스트 두껍게 */
  font-size: 1.2em; /* 강조 텍스트 크기 증가 */
}
</style>
