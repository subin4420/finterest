<template>
    <div>
      <div id="chart">
        <StockChart class="stock-chart" />
        <router-view></router-view>
      </div>
    </div>
  </template>
  
  <script>
  import StockChart from '@/components/trade/StockChart.vue';
  
  export default {
    name: 'StockSimulatorPage',
    components: {
      StockChart,
    },
  };
  </script>
  
  <style scoped>
  #chart {
    height: 400px;
    width: 600px;
  }
  
  .stock-chart {
    height: 400px;
    width: 600px;
  }
  </style>
  