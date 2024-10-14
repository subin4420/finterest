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
        <div
          class="bar-segment segment-1"
          :style="{ width: segment1Width }"
        ></div>
        <div
          class="bar-segment segment-2"
          :style="{ width: segment2Width }"
        ></div>
        <div
          class="bar-segment segment-3"
          :style="{ width: segment3Width }"
        ></div>
        <div
          class="bar-segment segment-4"
          :style="{ width: segment4Width }"
        ></div>
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
import { reactive, onMounted, watch, computed } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const data = reactive({
      money: 0,
      heldStockData: [],
    });

    const fetchData = async () => {
      try {
        const response = await axios.get('/api/trade/held', {
          headers: {
            'Content-Type': 'application/json', // Content-Type을 명시적으로 설정
            Authorization: 'Bearer YOUR_TOKEN_HERE', // 필요한 경우 Authorization 헤더 추가
          },
        });
        data.money = response.data.money;
        data.heldStockData = response.data.heldStockData;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    onMounted(() => {
      fetchData();
    });

    // 차트 세그먼트 너비 계산
    const segment1Width = computed(() => {
      return (data.heldStockData[0]?.totalPrice / data.money) * 100 + '%';
    });
    const segment2Width = computed(() => {
      return (data.heldStockData[1]?.totalPrice / data.money) * 100 + '%';
    });
    const segment3Width = computed(() => {
      return (data.heldStockData[2]?.totalPrice / data.money) * 100 + '%';
    });
    const segment4Width = computed(() => {
      return (data.heldStockData[3]?.totalPrice / data.money) * 100 + '%';
    });

    const formatAmount = (value) => {
      return new Intl.NumberFormat().format(value);
    };

    return {
      ...data,
      formatAmount,
      segment1Width,
      segment2Width,
      segment3Width,
      segment4Width,
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
