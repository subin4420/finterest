<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">환전 페이지</h1>
        <!-- 현재 잔액과 환전율을 가로로 나열 -->
        <div v-if="authStore.isLogin" class="user-info">
          <div class="balance-container">
            <div class="balance-item">
              <i class="fas fa-coins"></i>
              <span class="balance-label">보유 포인트</span>
              <span class="balance-value">{{
                formatCurrency(conversionStore.userBalance.totalPoints)
              }}</span>
            </div>
            <div class="balance-item">
              <i class="fas fa-dollar-sign"></i>
              <span class="balance-label">보유 모의투자금</span>
              <span class="balance-value"
                >{{
                  formatCurrency(conversionStore.userBalance.money)
                }}
                원</span
              >
            </div>
            <div class="balance-item">
              <i class="fas fa-exchange-alt"></i>
              <span class="balance-label">현재 환전율</span>
              <span class="balance-value"
                >1 포인트 = {{ formatCurrency(getConversionRate()) }} 원</span
              >
            </div>
          </div>
        </div>

        <div class="main-content">
          <div class="conversion-section">
            <div v-if="authStore.isLogin" class="conversion-container">
              <div class="conversion-card">
                <h2 class="conversion-title">
                  <i
                    :class="
                      conversionMode === 'pointsToMoney'
                        ? 'fas fa-coins'
                        : 'fas fa-won-sign'
                    "
                  ></i>
                  <i class="fas fa-arrow-right"></i>
                  <i
                    :class="
                      conversionMode === 'pointsToMoney'
                        ? 'fas fa-won-sign'
                        : 'fas fa-coins'
                    "
                  ></i>
                </h2>
                <input
                  v-model="conversionAmount"
                  :placeholder="
                    conversionMode === 'pointsToMoney'
                      ? '환전할 포인트 입력'
                      : '환전할 모의 자금 입력 (최소 1,000원)'
                  "
                  type="text"
                  @input="validateInput(conversionMode)"
                  :max="
                    conversionMode === 'pointsToMoney'
                      ? conversionStore.userBalance.totalPoints
                      : conversionStore.userBalance.money
                  "
                />
                <div class="arrow-container">
                  <i class="fas fa-arrow-down"></i>
                </div>
                <p class="conversion-result">
                  {{
                    conversionMode === 'pointsToMoney'
                      ? '환전 금액:'
                      : '환전 포인트:'
                  }}
                  {{ formatCurrency(calculateConversion()) }}
                  {{ conversionMode === 'pointsToMoney' ? '원' : '포인트' }}
                </p>
                <button @click="executeConversion">전환하기</button>
                <p v-if="conversionInputError" class="error-message">
                  {{ conversionInputError }}
                </p>
                <button
                  @click="toggleConversionMode"
                  class="toggle-mode-button"
                >
                  {{
                    conversionMode === 'pointsToMoney'
                      ? '모의투자금 → 포인트로 전환'
                      : '포인트 → 모의투자금으로 전환'
                  }}
                </button>
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

    const conversionMode = ref('pointsToMoney');
    const conversionAmount = ref('');
    const conversionMessage = ref('');
    const conversionInputError = ref('');

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
      conversionAmount.value = conversionAmount.value.replace(
        /\B(?=(\d{3})+(?!\d))/g,
        ''
      );
      conversionAmount.value = parseFloat(
        conversionAmount.value.replace(/,/g, '')
      );
    };

    const toggleConversionMode = () => {
      conversionMode.value =
        conversionMode.value === 'pointsToMoney'
          ? 'moneyToPoints'
          : 'pointsToMoney';
      conversionAmount.value = '';
      conversionInputError.value = '';
    };

    const calculateConversion = () => {
      if (conversionMode.value === 'pointsToMoney') {
        return calculateMoney(conversionAmount.value);
      } else {
        return calculatePoints(conversionAmount.value);
      }
    };

    const validateInput = (mode) => {
      conversionAmount.value = conversionAmount.value.replace(/[^0-9]/g, '');
      const maxValue =
        mode === 'pointsToMoney'
          ? conversionStore.userBalance.totalPoints
          : conversionStore.userBalance.money;
      const inputValue = parseInt(conversionAmount.value);

      if (inputValue > maxValue) {
        conversionAmount.value = maxValue.toString();
        conversionInputError.value = '';
      } else if (
        mode === 'moneyToPoints' &&
        inputValue < 1000 &&
        inputValue !== 0
      ) {
        conversionInputError.value = '최소 1,000원부터 환전 가능합니다.';
      } else {
        conversionInputError.value = '';
      }
    };

    const executeConversion = async () => {
      if (!authStore.isLogin) {
        conversionMessage.value = '환전을 위해서는 로그인이 필요합니다.';
        return;
      }

      if (
        conversionMode.value === 'pointsToMoney' &&
        parseInt(conversionAmount.value) <= 0
      ) {
        conversionMessage.value = '유효한 포인트 금액을 입력해주세요.';
        return;
      }

      if (conversionMode.value === 'moneyToPoints') {
        const inputMoney = parseInt(conversionAmount.value);
        if (inputMoney < 1000) {
          conversionMessage.value = '최소 1,000원부터 환전 가능합니다.';
          return;
        }
      }

      try {
        console.log(`환전 시작: ${conversionMode.value}`);
        console.log(`환전 전 잔액:`, conversionStore.userBalance);

        let result;
        if (conversionMode.value === 'pointsToMoney') {
          result = await conversionStore.executePointsToMoney(
            conversionAmount.value
          );
        } else {
          result = await conversionStore.executeMoneyToPoints(
            conversionAmount.value
          );
        }

        console.log('환전 결과:', result);
        console.log('환전 후 잔액:', conversionStore.userBalance);

        conversionMessage.value = '환전이 성공적으로 완료되었습니다.';
        conversionAmount.value = 0;
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

    return {
      authStore,
      conversionStore,
      conversionMode,
      conversionAmount,
      conversionMessage,
      conversionInputError,
      toggleConversionMode,
      calculateConversion,
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
      executeConversion,
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
  height: 60px;
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  display: flex;
  margin-top: 20px; /* 헤더 높이만큼만 여백 주기 */
}

.content {
  flex: 1;
  margin-left: 250px;
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

.user-info {
  background-color: #f0f0f0;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.balance-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.balance-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  background-color: #ffffff;
  border-radius: 8px;
  min-width: 150px;
  transition: transform 0.3s ease;
}

.balance-item:hover {
  transform: translateY(-5px);
}

.balance-item i {
  font-size: 2em;
  margin-bottom: 10px;
  color: #7b57ff;
}

.balance-label {
  font-size: 0.9em;
  color: #666;
  margin-bottom: 5px;
}

.balance-value {
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
    margin-left: 60px;
  }
}

.conversion-title {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}

.conversion-title i {
  font-size: 1.5em;
  margin: 0 10px;
}

.conversion-title .fa-coins {
  color: #ffd700;
}

.conversion-title .fa-won-sign {
  color: #4caf50;
}

.conversion-title .fa-arrow-right {
  color: #7b57ff;
}

.toggle-mode-button {
  margin-top: 15px;
  background-color: #f0f0f0;
  color: #333;
}

.toggle-mode-button:hover {
  background-color: #e0e0e0;
}
</style>
