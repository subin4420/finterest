<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />

    <StockSearch :kospiStockList="kospiStockList" />
  </div>
</template>

<script>
import axios from 'axios';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import StockSearch from '@/components/trade/StockSearch.vue';

export default {
  components: {
    TradeImage,
    TradeNavigationBar,
    StockSearch, // 등록
  },
  data() {
    return {
      kospiStockList: [],
    };
  },
  async mounted() {
    await this.fetchKOSPIStockList();
  },
  methods: {
    async fetchKOSPIStockList() {
      try {
        const response = await axios.get('/api/stock/list/KOSPI');
        this.kospiStockList = Array.isArray(response.data) ? response.data : [];
      } catch (error) {
        console.error('Error fetching KOSPI Stock List:', error);
      }
    },
  },
};
</script>

<style scoped></style>
