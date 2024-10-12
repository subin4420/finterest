<template>
  <div class="asset-card">
    <div class="total-asset">
      <div class="label">총 자산</div>
      <div class="amount">{{ formatAmount(money) }} 원</div>
      <div class="description">
        지난 달 보다 <span class="highlight">투자</span> 비중이 높아졌어요
      </div>
    </div>
    <div class="asset-chart">
      <div class="bar">
        <div class="bar-segment segment-1"></div>
        <div class="bar-segment segment-2"></div>
        <div class="bar-segment segment-3"></div>
        <div class="bar-segment segment-4"></div>
      </div>
    </div>
    <div class="asset-list">
      <div
        class="asset-item"
        v-for="(item, index) in heldStockData"
        :key="index"
      >
        <img :src="item.logo" class="logo" />
        <div class="company">{{ item.stockName }}</div>
        <div class="amount">{{ formatAmount(item.totalPrice) }} 원</div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, watch } from "vue";
import axios from "axios";

export default {
  setup() {
    const data = reactive({
      money: 0,
      heldStockData: [],
    });

    const fetchData = async () => {
      try {
        const response = await axios.get("/api/portfolio/assets"); // 백엔드에서 받아올 엔드포인트
        // console.log(response.data.heldStockData[0].stockName);
        data.money = response.data.money;
        data.heldStockData = response.data.heldStockData;
        // console.log(data.money);
        // console.log(data.heldStockData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    onMounted(() => {
      fetchData(); // 컴포넌트가 마운트될 때 데이터 가져오기
    });

    // data.heldStockData가 변경될 때마다 실행
    watch(
      () => data.heldStockData,
      (newValue) => {
        newValue.forEach((item) => {
          item.formattedTotalPrice = formatAmount(item.totalPrice);
        });
      }
    );

    const formatAmount = (value) => {
      return new Intl.NumberFormat().format(value);
    };

    return {
      ...data,
      formatAmount,
    };
  },
};
</script>

<style scoped>
.asset-card {
  width: 300px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  background-color: white;
}

.total-asset .label {
  background-color: #00bcd4;
  color: white;
  padding: 5px 10px;
  border-radius: 20px;
  display: inline-block;
}

.total-asset .amount {
  font-size: 24px;
  font-weight: bold;
  margin: 10px 0;
}

.total-asset .description {
  color: #666;
}

.total-asset .highlight {
  color: #00bcd4;
}

.asset-chart .bar {
  height: 10px;
  display: flex;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 20px;
}

.bar-segment {
  height: 100%;
}

.segment-1 {
  width: 50%;
  background-color: #f44336;
}

.segment-2 {
  width: 25%;
  background-color: #3f51b5;
}

.segment-3 {
  width: 5%;
  background-color: #4caf50;
}

.segment-4 {
  width: 20%;
  background-color: #ff9800;
}

.asset-list {
  margin-top: 20px;
}

.asset-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.asset-item .logo {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}

.asset-item .company {
  flex-grow: 1;
  margin-left: 10px;
}

.asset-item .amount {
  font-weight: bold;
}
</style>
