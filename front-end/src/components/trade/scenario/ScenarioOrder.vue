<template>
  <div class="order-container">
    <form class="order-form" @submit.prevent="handleOrder">
      <h4>주문하기</h4>
      <div class="turn-progress">
        <div class="turn-info">
          <!-- 현재 턴과 전체 턴을 표시 -->
          <span class="current-turn">{{
            String(currentTurn).padStart(2, '0')
          }}</span>
          /<span class="total-turn">{{ totalTurn }}턴</span>
        </div>

        <!-- 프로그레스 바 -->
        <div class="progress-bar">
          <div
            class="progress"
            :style="{ width: progressPercentage + '%' }"
          ></div>
        </div>

        <!-- 다음 턴 버튼 -->
        <div @click="nextTurn" class="next-turn-button">
          <h6>다음 턴</h6>
        </div>
      </div>
      <div class="order-form__togglebox">
        <div class="order-form__togglebox--section">
          <div
            class="order-form__togglebox--purchase"
            :class="{ selected: isBuying }"
            @click="toggleBuySell(true)"
          >
            <b>구매</b>
          </div>
          <div
            class="order-form__togglebox--sell"
            :class="{ selected: !isBuying }"
            @click="toggleBuySell(false)"
          >
            <b>판매</b>
          </div>
        </div>
      </div>
      <label for="purchase-price">거래 가격</label>
      <input
        class="order-form__price"
        :value="
          isNaN(Number(price.stck_oprc))
            ? '0'
            : Number(price.stck_oprc).toLocaleString()
        "
        readonly
      />

      <label for="purchase-amount">거래 수량</label>
      <div class="order-form__input">
        <input
          class="no-border no-arrow"
          v-model.number="amount"
          type="number"
          :max="isBuying ? null : holdingStockAcount"
        />
        <span> 주 </span>
        <div class="order-form__amountbox">
          <div class="order-form__amountbox--minus" @click="decreaseAmount">
            -
          </div>
          <div class="order-form__amountbox--plus" @click="increaseAmount">
            +
          </div>
        </div>
      </div>

      <div class="order-form__buttonbox">
        <input
          class="order-form__button"
          type="button"
          value="10%"
          @click="setPercentage(10)"
        />
        <input
          class="order-form__button"
          type="button"
          value="25%"
          @click="setPercentage(25)"
        />
        <input
          class="order-form__button"
          type="button"
          value="50%"
          @click="setPercentage(50)"
        />
        <input
          class="order-form__button"
          type="button"
          value="최대"
          @click="setMaxAmount"
        />
      </div>

      <div class="order-form__state">
        <div class="order-form__state--line">
          <p>거래가능 금액</p>
          <p>
            {{ isNaN(availableFunds) ? 0 : availableFunds.toLocaleString() }} 원
          </p>
        </div>
        <div class="order-form__state--line">
          <p>총 금액</p>
          <p>{{ isNaN(totalAmount) ? 0 : totalAmount.toLocaleString() }} 원</p>
        </div>
      </div>

      <button type="submit">거래하기</button>
    </form>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineProps, watch } from 'vue';
import axios from 'axios';
import { useToast } from 'vue-toastification';
import StockSummary from './StockSummary.vue'; // StockSummary 컴포넌트 import

const emit = defineEmits(['next-turn', 'update-stock-data']);
const turn = ref(0);
const currentTurn = ref(1);
const totalTurn = ref(50);
const isBuying = ref(true); // 구매/판매 토글 상태
const amount = ref(1); // 구매 수량
const availableFunds = ref(10000000); // 구매 가능 금액 (예시)
const holdingStockAcount = ref(0); //보유 주식 수
const holdingAmount = ref(0); //보유 주식 자산

const props = defineProps({
  stockData: {
    type: Array,
    required: true,
  },
});

const price = ref({
  stck_bsop_date: '',
  stck_oprc: '',
  stck_clpr: '',
  stck_lwpr: '',
  stck_hgpr: '',
});
console.log(price.value.stck_oprc); // 시가를 출력

// 게이지를 계산 (현재 턴 / 총 턴 수 * 100)
const progressPercentage = computed(
  () => (currentTurn.value / totalTurn.value) * 100
);

// 총 평가 자산 계산
const assessedAssets = computed(() => {
  return (
    holdingStockAcount.value * price.value.stck_oprc + availableFunds.value
  );
});

const currentData = async () => {
  try {
    const response = await axios.get('/api/scenario/current');
    const currentData = response.data;
    return {
      stck_bsop_date: currentData.stck_bsop_date,
      stck_oprc: currentData.stck_oprc,
      stck_clpr: currentData.stck_clpr, // 종가
      stck_lwpr: currentData.stck_lwpr,
      stck_hgpr: currentData.stck_hgpr,
    };
  } catch (error) {
    console.error('Error fetching stock data:', error);
    return {
      stck_bsop_date: 'Unknown Date',
      stck_oprc: 'Unknown Price',
      stck_clpr: 'Unknown Price',
      stck_lwpr: 'Unknown Price',
      stck_hgpr: 'Unknown Price',
    };
  }
};

// 구매/판매 토글
const toggleBuySell = (buying) => {
  isBuying.value = buying;
};

// 총 금액 계산
const totalAmount = computed(() => {
  const validPrice = isNaN(price.value.stck_oprc) ? 0 : price.value.stck_oprc;
  const validAmount = isNaN(amount.value) ? 0 : amount.value;
  return validPrice * validAmount;
});

// 수량 증가/감소 수정
const increaseAmount = () => {
  if (isBuying.value) {
    amount.value++; // 구매 시 수량 증가
  } else {
    // 판매 시 보유 주식 수를 초과하지 않도록 설정
    amount.value = Math.min(amount.value + 1, holdingStockAcount.value);
  }
};

const decreaseAmount = () => {
  if (amount.value > 1) {
    amount.value--; // 수량 감소
  }
};

// 퍼센트로 구매 수량 설정 (10%, 25%, 50%, 최대)
const setPercentage = (percentage) => {
  const calculatedAmount = Math.floor(
    (availableFunds.value * (percentage / 100)) / price.value.stck_oprc
  );
  amount.value = calculatedAmount;
};

// 최대 금액 설정
const setMaxAmount = () => {
  const maxAmount = Math.floor(availableFunds.value / price.value.stck_oprc);
  amount.value = maxAmount;
};

// 수익률 계산 수정
const profitRate = computed(() => {
  if (holdingStockAcount.value === 0) return 0; // 보유 주식이 없으면 수익률은 0
  const purchasePrice = price.value.stck_oprc; // 거래 가격
  const currentPrice = price.value.stck_clpr; // 현재 주가
  if (purchasePrice && currentPrice) {
    return (((currentPrice - purchasePrice) / purchasePrice) * 100).toFixed(2); // 수익률 계산
  }
  return 0;
});

const toast = useToast(); // toast 인스턴스 생성

// 주문 처리 수정
const handleOrder = async () => {
  const total = totalAmount.value; // computed 속성은 .value로 접근
  if (isBuying.value) {
    console.log('Buy');
    if (availableFunds.value > total) {
      holdingAmount.value += total;
      availableFunds.value -= total;
      holdingStockAcount.value += amount.value;
      // 구매 성공 메시지 추가
      toast.success('구매가 완료되었습니다.', {
        timeout: 5000,
        closeOnClick: true,
        position: 'top-right',
      });
    } else {
      // 구매 실패 메시지 추가
      toast.error('구매 가능 금액이 부족합니다.', {
        timeout: 5000,
        closeOnClick: true,
        position: 'top-right',
      });
    }
  } else {
    console.log('sell');
    if (holdingStockAcount.value >= amount.value) {
      // 판매할 수 있는 주식 수 제한
      holdingAmount.value -= total;
      availableFunds.value += total;
      holdingStockAcount.value -= amount.value;
      // 판매 성공 메시지 추가
      toast.success('판매가 완료되었습니다.', {
        timeout: 5000,
        closeOnClick: true,
        position: 'top-right',
      });
    } else {
      console.error('판매할 수 있는 주식 수가 부족합니다.'); // 에러 메시지
      // 판매 실패 메시지 추가
      toast.error('판매할 수 있는 주식 수가 부족합니다.', {
        timeout: 5000,
        closeOnClick: true,
        position: 'top-right',
      });
    }
  }

  // 주문 처리 후 데이터 업데이트
  emit('update-stock-data', {
    holdingStockAcount: holdingStockAcount.value,
    profitRate: profitRate.value,
    assessedAssets: assessedAssets.value,
  });
};
const currentStockPrice = () => {
  console.log('현재 시가:', price.value.stck_oprc); // 다른 메서드에서 접근 가능
};

const nextTurn = () => {
  console.log('다음 턴');
  if (currentTurn.value < totalTurn.value) {
    currentTurn.value++;
    console.log(currentTurn.value);
    emit('next-turn', currentTurn.value); // 부모에게 이벤트 전달
  }
};

onMounted(async () => {
  const fetchedData = await currentData(); // 데이터 가져오기

  if (fetchedData) {
    price.value = fetchedData; // price에 가져온 데이터 할당
    console.log(price.value.stck_oprc); // 시가 출력
    currentStockPrice();
  }
});
watch(
  () => props.stockData,
  (newData) => {
    if (newData && newData.length >= 5) {
      console.log('Received stockData:', newData);
      price.value.stck_bsop_date = newData[0];
      price.value.stck_oprc = newData[1];
      price.value.stck_clpr = newData[2];
      price.value.stck_lwpr = newData[3];
      price.value.stck_hgpr = newData[4];
    }
  },
  { immediate: true } // 컴포넌트가 마운트될 때도 즉시 실행되도록 설정
);
</script>

<style lang="scss" scoped>
input[type='number']::-webkit-outer-spin-button,
input[type='number']::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type='number'] {
  -moz-appearance: textfield; /* Firefox용 */
}
/* 테두리 제거 */
input.no-border {
  border: none; /* 테두리 제거 */
  outline: none; /* 선택 시 외곽선 제거 */
  height: 14px;
}
.order-form {
  width: 100px;
  border-radius: 15px;
  background-color: lightblue;
  padding: 14px 8px;
  display: flex;
  flex-direction: column;
  gap: 12px;

  h4 {
    font-size: 17px;
    font-weight: 700;
  }

  label {
    font-size: 13px;
    color: #020202;
    opacity: 0.53;
    font-weight: 600;
  }

  input {
    width: 100%;
  }
  &__price {
    border-radius: 4px;
    border: 1px solid gray;
    height: 28px;
  }

  button {
    width: 100%;
    background-color: red;
    font-size: 18px;
    font-weight: 600;
    color: white;
    border-radius: 8px;
    border: none;
  }

  &__togglebox {
    display: flex;
    justify-content: center;
    align-items: center;
    &--section {
      display: flex;
      flex-direction: row;
      width: 128px;
      height: 34px;
      border-radius: 17px;
      background-color: #dcdcdc;
      justify-content: space-between;
      align-items: center;
      font-size: 14px;
      padding: 0px 8px;
    }

    &--purchase {
      color: red;
    }

    &--sell {
      color: blue;
    }

    &--purchase,
    &--sell {
      width: fit-content;
      height: 24px;
      padding: 0px 4px;
      border-radius: 11px;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }

  &__button,
  &__input {
    width: 100%;
    height: 28px;
    background-color: white;
    border: 1px solid gray;
    border-radius: 4px;
    margin-bottom: 8px;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0 8px;

    p {
      font-size: 12px;
      margin-bottom: 0px;
    }
  }

  &__amountbox {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    font-size: 22px;

    &--plus,
    &--minus {
      display: flex;
      justify-content: center;
      align-items: center;

      width: 24px;
      height: 24px;
    }
  }

  &__buttonbox {
    display: grid;
    flex-direction: row;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    justify-content: space-between;
    align-items: center;
    gap: 4px;
  }

  &__button {
    padding: 0;
  }

  &__state {
    display: flex;
    flex-direction: column;
    border-top: 1px solid gray;
    margin: 12px 0px 32px 0px;
    padding-top: 12px;
    gap: 8px;

    &--line {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;

      p {
        font-size: 13px;
        font-weight: 700;
        opacity: 0.53;
        margin-bottom: 0px;
      }
    }
  }
}

.selected {
  background-color: white;
}
.turn-progress {
  width: 100%;
}

.turn-info {
  font-size: 20px;
  margin-bottom: 5px;
}

.current-turn {
  color: red;
  font-size: 24px;
  font-weight: bold;
}

.total-turn {
  color: gray;
  font-size: 16px;
}

.progress-bar {
  position: relative;
  width: 100%;
  height: 5px;
  background-color: #eee;
  border-radius: 5px;
}

.progress {
  height: 100%;
  background-color: red;
  border-radius: 5px;
  transition: width 0.3s ease;
}

.next-turn-button {
  cursor: pointer;
  text-align: center;
  margin-top: 10px;
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 5px;
  user-select: none;
}

.next-turn-button:hover {
  background-color: #e0e0e0;
}

.order-container {
  display: flex;
  gap: 20px; /* 주문 폼과 요약 사이의 간격 */
  width: 100%; /* 전체 너비 사용 */
}

.order-form {
  flex: 1; /* 주문 폼이 가능한 공간을 차지하도록 설정 */
  background-color: #f9f9f9; /* 폼 배경 색상 */
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.order-summary {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.summary-card {
  background-color: #ffffff;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.summary-card:hover {
  transform: scale(1.02); /* 카드에 마우스 오버 시 확대 효과 */
}

.summary-item {
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
}

.highlight {
  font-weight: normal;
  font-size: 18px;
  color: #333; /* 텍스트 색상 */
}

/* 기존 스타일 ... */
</style>
