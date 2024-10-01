<template>
  <div>
    <TradeImage />
    <TradeNavigationBar />

    <h1>환전 페이지</h1>

    <div class="conversion-wrapper">
      <div class="conversion-container">
        <!-- 포인트에서 모의투자금 전환 -->
        <div v-if="currentView === 'pointsToMoney'" class="conversion-card">
          <h2>포인트</h2>
          <input
            v-model="pointAmount"
            placeholder="환전할 포인트 입력"
            type="number"
          />
          <div class="arrow-container">
            <span>↓</span>
          </div>
          <h2>환전 금액</h2>
          <div class="exchange-amount">
            <p>{{ formatCurrency(calculateMoney(pointAmount)) }} 원</p>
          </div>
          <button @click="executeConversion('pointsToMoney')">전환하기</button>
          <p v-if="conversionMessage" class="conversion-message">
            {{ conversionMessage }}
          </p>
        </div>

        <!-- 모의투자금에서 포인트 전환 -->
        <div
          v-else-if="currentView === 'moneyToPoints'"
          class="conversion-card"
        >
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
          <h2>환전 포인트</h2>
          <div class="exchange-amount">
            <p>{{ formatCurrency(calculatePoints(moneyAmount)) }} 포인트</p>
          </div>
          <button @click="executeConversion('moneyToPoints')">전환하기</button>
          <p v-if="conversionMessage" class="conversion-message">
            {{ conversionMessage }}
          </p>
        </div>
      </div>

      <!-- 전환 방식 선택 버튼 -->
      <div class="toggle-container">
        <button @click="toggleView('pointsToMoney')">
          포인트 → 모의투자금
        </button>
        <button @click="toggleView('moneyToPoints')">
          모의투자금 → 포인트
        </button>
      </div>
    </div>

    <div>
      <h4>현재 환전 비율: {{ conversionRate }} 원/포인트</h4>
      <p>1 포인트는 {{ formatCurrency(calculateMoney(1)) }} 원입니다.</p>
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
} from '../../../../../../새 폴더/finterest/front-end/src/api/conversionApi.js';

export default {
  setup() {
    const conversionRate = ref(0);
    const pointAmount = ref(0);
    const moneyAmount = ref(0);
    const conversionMessage = ref('');
    const currentView = ref('pointsToMoney'); // 현재 보여줄 화면 설정

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

    const toggleView = (viewType) => {
      currentView.value = viewType;
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
      currentView,
      toggleView,
    };
  },
  components: {
    TradeImage,
    TradeNavigationBar,
  },
};
</script>

<style>
.conversion-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.conversion-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  width: 500px;
}

.conversion-card {
  background-color: #ffffff;
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
  font-size: 2em;
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
  width: 250px;
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
  background-color: #f9f9f9;
  border-radius: 5px;
  padding: 10px;
  margin-top: 10px;
}

.toggle-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.toggle-container button {
  margin: 0 10px;
  background-color: #ddd;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.toggle-container button:hover {
  background-color: #bbb;
}
</style>
