<template>
  <div>
    <TradeImage />
    <TradeNavigationBar />
    <div class="community-page">
      <StockSearch :kospiStockList="kospiStockList" />
      <StockChart />
      <OrderStock />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TradeImage from "@/components/trade/TradeImage.vue";
import TradeNavigationBar from "@/components/trade/TradeNavigationBar.vue";
import StockSearch from "@/components/trade/StockSearch.vue";
import StockChart from "@/components/trade/StockChart.vue";
import OrderStock from "@/components/trade/OrderStock.vue";

export default {
  components: {
    TradeImage,
    TradeNavigationBar,
    StockSearch,
    StockChart,
    OrderStock,
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
        const response = await axios.get("/api/stock/list/KOSPI");
        this.kospiStockList = Array.isArray(response.data) ? response.data : [];
      } catch (error) {
        console.error("Error fetching KOSPI Stock List:", error);
      }
    },
  },
};
</script>

<style scoped>
.community-page {
  display: flex;
  justify-content: space-between; /* 필요에 따라 컴포넌트들 사이 간격 조정 */
  align-items: flex-start; /* 필요에 따라 컴포넌트들 수직 정렬 */
  gap: 10px; /* 컴포넌트들 사이에 간격을 줄 수 있음 */
}
StockSearch,
StockChart,
OrderStock {
  flex-grow: 1; /* 모든 컴포넌트가 같은 너비를 차지하도록 */
}
</style>
