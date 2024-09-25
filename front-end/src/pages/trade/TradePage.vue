<template>
    <div>
      <h1>TradePage</h1>
      <router-link to="/trade/stocksimulator">Go to Stock Simulator</router-link>
      <router-view></router-view>
    </div>
  </template>
  
  <script>
  export default {
    name: 'TradePage',
    methods: {
      goToStockSimulator() {
        // '/trade/stock-simulator' 경로로 이동
        this.$router.push('stocksimulator');
      }
    }
  };
  </script>
  
  <style lang="scss" scoped></style>
  