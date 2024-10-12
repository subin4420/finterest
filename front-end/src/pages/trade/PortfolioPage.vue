<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">포트폴리오 페이지</h1>
        <p>여기는 포트폴리오 페이지입니다.</p>
        <HoldStockDomChart />
        <!-- <HoldTotalAssets /> -->
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from "@/components/trade/SideTradeNavigationBar.vue";
import HoldStockDomChart from "@/components/trade/portfolioChart/HoldStockDomChart.vue";
// import HoldTotalAssets from "@/components/trade/portfolioChart/HoldTotalAssets.vue";

export default {
  name: "PortfolioPage",
  components: {
    SideTradeNavigationBar,
    HoldStockDomChart,
    // HoldTotalAssets,
  },
};
</script>

<style scoped>
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
</style>
