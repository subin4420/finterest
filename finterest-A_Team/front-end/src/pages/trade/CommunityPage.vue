<template>
  <div class="community-page">
    <TradeImage/>
    <TradeNavigationBar />
    <h1>커뮤니티 페이지</h1>
    <p>여기는 커뮤니티 페이지입니다.</p>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';

export default {
  name: 'CommunityPage',
  component: {
    TradeImage
  }
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
