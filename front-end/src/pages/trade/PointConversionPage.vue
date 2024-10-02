<template>
  <div>
    <TradeImage />
    <TradeNavigationBar />

    <h1>환전 페이지</h1>

    <div class="conversion-container">
      <div class="conversion-card">
        <h2>포인트</h2>
        <input
          v-model="pointAmount"
          placeholder="환전할 포인트 입력"
          type="number"
        />
        <div class="arrow-container">
          <span>↓</span>
        </div>
        <p>환전 금액: {{ formatCurrency(calculateMoney(pointAmount)) }} 원</p>
        <button @click="executeConversion('pointsToMoney')">전환하기</button>
        <p v-if="conversionMessage" class="conversion-message">
          {{ conversionMessage }}
        </p>
      </div>

      <div class="conversion-card">
        <h2>모의투자금</h2>
        <input
          v-model="moneyAmount"
          placeholder="환전할 가상 자금 입력"
          type="number"
          @input="formatMoneyAmount"
        />
        <div class="arrow-container">
          <span>↓</span>
        </div>

        <p>
          환전 포인트:{{ formatCurrency(calculatePoints(moneyAmount)) }} 포인트
        </p>

        <button @click="executeConversion('moneyToPoints')">전환하기</button>
        <p v-if="conversionMessage" class="conversion-message">
          {{ conversionMessage }}
        </p>
        <div></div>
      </div>
    </div>
    <div>
      <h2>현재 환전 비율: {{ conversionRate }} 원/포인트</h2>
      <h3>100 포인트는 {{ formatCurrency(calculateMoney(100)) }} 원입니다.</h3>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import {
  getLatestConversionRate,
  convertPointsToMoney,
  convertMoneyToPoints,
} from '@/api/conversionApi.js';

export default {
  setup() {
    const conversionRate = ref(0);
    const pointAmount = ref(0);
    const moneyAmount = ref(0);
    const conversionMessage = ref('');

    onMounted(async () => {
      const rateData = await getLatestConversionRate();
      conversionRate.value = rateData.conversionRate;
    });

    const formatCurrency = (amount) => {
      return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    const calculateMoney = (points) => {
      return Math.floor(points * conversionRate.value);
    };

    const calculatePoints = (money) => {
      return Math.floor(money / conversionRate.value);
    };

    const formatMoneyAmount = () => {
      moneyAmount.value = moneyAmount.value.replace(
        /\B(?=(\d{3})+(?!\d))/g,
        ''
      );
      moneyAmount.value = parseFloat(moneyAmount.value.replace(/,/g, ''));
    };

    const executeConversion = async (type) => {
      let response;
      if (type === 'pointsToMoney') {
        response = await convertPointsToMoney(pointAmount.value);
        conversionMessage.value =
          '환전 완료: ' +
          formatCurrency(calculateMoney(pointAmount.value)) +
          ' 원';
      } else if (type === 'moneyToPoints') {
        response = await convertMoneyToPoints(moneyAmount.value);
        conversionMessage.value =
          '환전 완료: ' +
          formatCurrency(calculatePoints(moneyAmount.value)) +
          ' 포인트';
      }
    };

    return {
      conversionRate,
      pointAmount,
      moneyAmount,
      conversionMessage,
      executeConversion,
      calculateMoney,
      calculatePoints,
      formatCurrency,
      formatMoneyAmount,
    };
  },
  components: {
    TradeImage,
    TradeNavigationBar,
  },
};
</script>

<style>
.conversion-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.conversion-card {
  background-color: #ffffff; /* 흰색 배경 */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  flex: 1;
  margin: 0 10px;
  transition: transform 0.3s;
}

.conversion-card:hover {
  transform: translateY(-5px);
}

input[type='number'] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
}

.arrow-container {
  text-align: center;
  font-size: 2em; /* 화살표 크기 조정 */
  margin: 10px 0;
}

button {
  background-color: #7b57ff;
  border: none;
  color: white;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100%;
}

button:hover {
  background-color: #9173ff;
}

.conversion-message {
  margin-top: 10px;
  font-size: 0.9em;
  color: green;
}

.exchange-amount {
  background-color: #f9f9f9; /* 환전금액 박스 흰색 배경 */
  border-radius: 5px;
  padding: 10px;
  margin-top: 10px;
}
</style>
