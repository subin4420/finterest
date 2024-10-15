<template>
  <div>
    <!-- 모의투자 관련 콘텐츠 렌더링 -->
    <!-- 모의투자 목록을 여기에 표시 -->
    <div>
      <div class="asd">
        <DaliyProfitChart
          style="width: 400px; height: 450px; margin-right: 20px"
        />
        <HoldStockDomChart style="width: 320px; height: 450px" />
      </div>
    </div>
  </div>
</template>

<script>
import DaliyProfitChart from "../trade/portfolioChart/DaliyProfitChart.vue";
import HoldStockDomChart from "../trade/portfolioChart/HoldStockDomChart.vue";
export default {
  name: "InvestmentList",
  components: {
    DaliyProfitChart,
    HoldStockDomChart,
  },
  setup() {
    // 모의투자 관련 데이터 불러오기
    return {};
  },
};
</script>

<style scoped>
.asd {
  display: flex;
  flex-direction: row;
}
</style>
