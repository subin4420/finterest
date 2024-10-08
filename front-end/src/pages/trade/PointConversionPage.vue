<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">환전 페이지</h1>
        <div v-if="authStore.isLogin" class="user-balance">
          <h2>현재 잔액</h2>
          <div class="balance-container">
            <div class="balance-item">
              <i class="fas fa-coins"></i>
              <span class="balance-label">포인트</span>
              <span class="balance-value">{{
                formatCurrency(conversionStore.userBalance.totalPoints)
              }}</span>
            </div>
            <div class="balance-item">
              <i class="fas fa-dollar-sign"></i>
              <span class="balance-label">모의투자금</span>
              <span class="balance-value"
                >{{
                  formatCurrency(conversionStore.userBalance.money)
                }}
                원</span
              >
            </div>
          </div>
        </div>

        <div class="conversion-rate-info">
          <h2>현재 환전 비율</h2>
          <div class="rate-container">
            <div class="rate-item">
              <i class="fas fa-exchange-alt"></i>
              <span class="rate-value"
                >1 포인트 = {{ formatCurrency(getConversionRate()) }} 원</span
              >
            </div>
          </div>
        </div>

        <div class="main-content">
          <div class="conversion-section">
            <div v-if="authStore.isLogin" class="conversion-container">
              <div class="conversion-card">
                <h2>P → ₩</h2>
                <input
                  v-model="pointAmount"
                  placeholder="환전할 포인트 입력"
                  type="text"
                  @input="validateInput('point')"
                  :max="conversionStore.userBalance.totalPoints"
                />
                <div class="arrow-container">
                  <i class="fas fa-arrow-down"></i>
                </div>
                <p class="conversion-result">
                  환전 금액:
                  {{ formatCurrency(calculateMoney(pointAmount)) }} 원
                </p>
                <button @click="executeConversion('pointsToMoney')">
                  전환하기
                </button>
              </div>

              <div class="conversion-card">
                <h2>₩ → P</h2>
                <input
                  v-model="moneyAmount"
                  placeholder="환전할 모의 자금 입력 (최소 1,000원)"
                  type="text"
                  @input="validateInput('money')"
                  :max="conversionStore.userBalance.money"
                />
                <div class="arrow-container">
                  <i class="fas fa-arrow-down"></i>
                </div>
                <p class="conversion-result">
                  환전 포인트:
                  {{ formatCurrency(calculatePoints(moneyAmount)) }} 포인트
                </p>
                <button @click="executeConversion('moneyToPoints')">
                  전환하기
                </button>
                <p v-if="moneyInputError" class="error-message">
                  {{ moneyInputError }}
                </p>
              </div>
            </div>

            <p v-else class="login-message">
              환전 기능을 사용하려면 로그인이 필요합니다.
            </p>

            <p v-if="conversionMessage" class="conversion-message">
              {{ conversionMessage }}
            </p>
          </div>

          <div v-if="authStore.isLogin" class="transaction-history">
            <table>
              <thead>
                <tr>
                  <th>환전 유형</th>
                  <th>포인트</th>
                  <th>모의투자금</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(transaction, index) in displayedTransactions"
                  :key="index"
                >
                  <td>
                    {{
                      transaction.conversionType === 'points_to_money'
                        ? 'P → ₩'
                        : '₩ → P'
                    }}
                  </td>
                  <td>
                    {{ formatCurrency(Math.abs(transaction.pointChange)) }} P
                  </td>
                  <td>
                    {{ formatCurrency(Math.abs(transaction.moneyChange)) }} ₩
                  </td>
                </tr>
              </tbody>
            </table>
            <div
              v-if="conversionStore.transactions.length > transactionsPerPage"
              class="show-more"
            >
              <button @click="toggleShowAllTransactions">
                {{ showAllTransactions ? '닫기' : '더 보기' }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, watch, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useConversionStore } from '@/stores/conversionStore';
import TradeImage from '@/components/trade/TradeImage.vue';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';

export default {
  components: {
    TradeImage,
    SideTradeNavigationBar,
  },
  setup() {
    const authStore = useAuthStore();
    const conversionStore = useConversionStore();

    const pointAmount = ref('');
    const moneyAmount = ref('');
    const conversionMessage = ref('');
    const moneyInputError = ref('');

    onMounted(async () => {
      if (authStore.isLogin) {
        await conversionStore.fetchConversionRate();
        await conversionStore.fetchUserBalance();
        await conversionStore.fetchTransactions();
        console.log(
          'Component mounted, userBalance:',
          conversionStore.userBalance
        );
      }
    });

    watch(
      () => conversionStore.userBalance,
      (newBalance) => {
        console.log('userBalance changed in component:', newBalance);
      },
      { deep: true }
    );

    const formatCurrency = (value) => {
      console.log('Formatting currency value:', value);
      if (value === undefined || value === null || isNaN(value)) {
        return '0';
      }
      return Number(value).toLocaleString('ko-KR');
    };

    const calculateMoney = (points) => {
      const rate = getConversionRate();
      return isNaN(points) || isNaN(rate) ? 0 : Math.floor(points * rate);
    };

    const calculatePoints = (money) => {
      const rate = getConversionRate();
      return isNaN(money) || isNaN(rate) || rate === 0
        ? 0
        : Math.floor(money / rate);
    };

    const formatMoneyAmount = () => {
      moneyAmount.value = moneyAmount.value.replace(
        /\B(?=(\d{3})+(?!\d))/g,
        ''
      );
      moneyAmount.value = parseFloat(moneyAmount.value.replace(/,/g, ''));
    };

    const executeConversion = async (type) => {
      if (!authStore.isLogin) {
        conversionMessage.value = '환전을 위해서는 로그인이 필요합니다.';
        return;
      }

      if (type === 'pointsToMoney' && parseInt(pointAmount.value) <= 0) {
        conversionMessage.value = '유효한 포인트 금액을 입력해주세요.';
        return;
      }

      if (type === 'moneyToPoints') {
        const inputMoney = parseInt(moneyAmount.value);
        if (inputMoney < 1000) {
          conversionMessage.value = '최소 1,000원부터 환전 가능합니다.';
          return;
        }
      }

      try {
        console.log(`환전 시작: ${type}`);
        console.log(`환전 전 잔액:`, conversionStore.userBalance);

        let result;
        if (type === 'pointsToMoney') {
          result = await conversionStore.executePointsToMoney(
            pointAmount.value
          );
        } else {
          result = await conversionStore.executeMoneyToPoints(
            moneyAmount.value
          );
        }

        console.log('환전 결과:', result);
        console.log('환전 후 잔액:', conversionStore.userBalance);

        conversionMessage.value = '환전이 성공적으로 완료되었습니다.';
        pointAmount.value = 0;
        moneyAmount.value = 0;
      } catch (error) {
        console.error('환전 중 오류 발생:', error);
        conversionMessage.value =
          '환전 중 오류가 발생했습니다: ' + error.message;
      }
    };

    const getConversionRate = () => {
      const rate = conversionStore.conversionRate;
      if (typeof rate === 'object' && rate !== null) {
        return Number(rate.conversionRate);
      }
      return Number(rate);
    };

    watch(
      () => conversionStore.conversionRate,
      (newRate) => {
        console.log('New conversion rate:', newRate);
      }
    );

    const transactionsPerPage = 5;
    const showAllTransactions = ref(false);

    const displayedTransactions = computed(() => {
      if (showAllTransactions.value) {
        return conversionStore.transactions;
      }
      return conversionStore.transactions.slice(0, transactionsPerPage);
    });

    const toggleShowAllTransactions = () => {
      showAllTransactions.value = !showAllTransactions.value;
    };

    const validateInput = (type) => {
      if (type === 'point') {
        pointAmount.value = pointAmount.value.replace(/[^0-9]/g, '');
        const maxPoints = conversionStore.userBalance.totalPoints;
        if (parseInt(pointAmount.value) > maxPoints) {
          pointAmount.value = maxPoints.toString();
        }
      } else {
        moneyAmount.value = moneyAmount.value.replace(/[^0-9]/g, '');
        const maxMoney = conversionStore.userBalance.money;
        const inputMoney = parseInt(moneyAmount.value);

        if (inputMoney > maxMoney) {
          moneyAmount.value = maxMoney.toString();
          moneyInputError.value = '';
        } else if (inputMoney < 1000 && inputMoney !== 0) {
          moneyInputError.value = '최소 1,000원부터 환전 가능합니다.';
        } else {
          moneyInputError.value = '';
        }
      }
    };

    return {
      authStore,
      conversionStore,
      pointAmount,
      moneyAmount,
      conversionMessage,
      executeConversion,
      calculateMoney,
      calculatePoints,
      formatCurrency,
      formatMoneyAmount,
      getConversionRate,
      displayedTransactions,
      toggleShowAllTransactions,
      showAllTransactions,
      transactionsPerPage,
      validateInput,
      moneyInputError,
    };
  },
};
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
}

.trade-header {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 60px; /* 헤더 높이 */
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  display: flex;
  margin-top: 60px; /* 헤더 높이만큼 아래로 */
}

.content {
  flex: 1;
  margin-left: 250px; /* SideTradeNavigationBar의 너비와 같게 설정 */
  padding: 20px;
}

.page-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
}

.user-balance,
.conversion-rate-info {
  background-color: #f0f0f0;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.user-balance h2,
.conversion-rate-info h2 {
  margin-bottom: 15px;
  color: #333;
}

.balance-container,
.rate-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.balance-item,
.rate-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  background-color: #ffffff;
  border-radius: 8px;
  min-width: 150px;
  transition: transform 0.3s ease;
}

.balance-item:hover,
.rate-item:hover {
  transform: translateY(-5px);
}

.balance-item i,
.rate-item i {
  font-size: 2em;
  margin-bottom: 10px;
  color: #7b57ff;
}

.balance-label,
.rate-value {
  font-size: 0.9em;
  color: #666;
  margin-bottom: 5px;
}

.balance-value,
.rate-description {
  font-size: 1.2em;
  font-weight: bold;
  color: #333;
}

.main-content {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.conversion-section {
  flex: 1;
  margin-right: 20px;
}

.conversion-container {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
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

.conversion-card h2 {
  color: #333;
  margin-bottom: 15px;
}

input[type='text'] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
  font-size: 1em;
}

.arrow-container {
  text-align: center;
  font-size: 2em;
  margin: 10px 0;
  color: #7b57ff;
}

.conversion-result {
  font-size: 1.1em;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
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
  font-size: 1em;
}

button:hover {
  background-color: #9173ff;
}

.login-message,
.conversion-message {
  margin-top: 10px;
  font-size: 0.9em;
  color: #666;
  text-align: center;
}

.conversion-message {
  color: #4caf50;
}

.transaction-history {
  flex: 1;
  margin-top: 30px;
}

.transaction-history h2 {
  color: #333;
  margin-bottom: 15px;
}

.transaction-history table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
  background-color: #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  overflow: hidden;
}

.transaction-history th,
.transaction-history td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.transaction-history th {
  background-color: #7b57ff;
  color: white;
  font-weight: bold;
}

.transaction-history tr:last-child td {
  border-bottom: none;
}

.transaction-history tr:nth-child(even) {
  background-color: #f8f8f8;
}

.transaction-history tr:hover {
  background-color: #f0f0f0;
}

.conversion-rate-info {
  background-color: #f0f0f0;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 20px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.conversion-rate-info h2 {
  margin-bottom: 10px;
  color: #333;
}

.rate-container {
  display: flex;
  justify-content: center;
}

.rate-item {
  display: flex;
  align-items: center;
  background-color: #ffffff;
  border-radius: 8px;
  padding: 10px 15px;
}

.rate-item i {
  font-size: 1.5em;
  margin-right: 10px;
  color: #7b57ff;
}

.rate-value {
  font-size: 1.2em;
  font-weight: bold;
  color: #333;
}

.show-more {
  text-align: center;
  margin-top: 15px;
}

.show-more button {
  background-color: #f0f0f0;
  color: #333;
  border: none;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.show-more button:hover {
  background-color: #e0e0e0;
}

.error-message {
  color: red;
  font-size: 0.9em;
  margin-top: 5px;
}

@media (max-width: 768px) {
  .content {
    margin-left: 60px; /* 작은 화면에서의 SideTradeNavigationBar 너비 */
  }
}
</style>
