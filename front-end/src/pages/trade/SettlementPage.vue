<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />
    <!-- TradeNavigationBar 추가 -->
    <h1>결산 페이지</h1>
    <p>여기는 포트폴리오 페이지입니다.</p>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue'; // TradeNavigationBar 임포트 추가

export default {
  name: 'SettlementPage',
  components: {
    TradeImage,
    TradeNavigationBar, // TradeNavigationBar 컴포넌트 등록
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
</style>
