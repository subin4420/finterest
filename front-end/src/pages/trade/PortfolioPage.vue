<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />
    <h1>포트폴리오 페이지</h1>
    <p>여기는 포트폴리오 페이지입니다.</p>
    <HoldStockDomChart />
    <!-- HoldStockDomChart 컴포넌트 추가 -->
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import HoldStockDomChart from '@/components/trade/portfoliochart/HoldStockDomChart.vue'; // HoldStockDomChart 컴포넌트 임포트

export default {
  name: 'PortfolioPage',
  components: {
    TradeImage,
    TradeNavigationBar,
    HoldStockDomChart, // HoldStockDomChart 컴포넌트 등록
  },
};
</script>

<style>
.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}
.community-page {
  padding: 20px;
}
</style>
