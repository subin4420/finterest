<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />
    <!-- TradeNavigationBar 추가 -->

    <BoardList />
    <BoardCreate />
  </div>
</template>

<script>
import BoardCreate from '@/components/trade/BoardCreate.vue';
import BoardList from '@/components/trade/BoardList.vue';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue'; // TradeNavigationBar 임포트 추가

export default {
  name: 'CommunityPage',
  components: {
    TradeImage,
    TradeNavigationBar,
    BoardCreate,
    BoardList, // TradeNavigationBar 컴포넌트 등록
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
