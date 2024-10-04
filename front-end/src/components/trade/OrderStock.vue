<template>
  <form class="order-form" @submit.prevent="handleOrder">
    <h4>주문하기</h4>
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
    <input class="order-form__price" :value="price" readonly />

    <label for="purchase-amount">거래 수량</label>
    <div class="order-form__input">
      <input class="no-border no-arrow" v-model.number="amount" type="number" />
      <span> 주 </span>
      <div class="order-form__amountbox">
        <div class="order-form__amountbox--minus" @click="decreaseAmount">
          -
        </div>
        <div class="order-form__amountbox--plus" @click="increaseAmount">+</div>
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
        <p>{{ availableFunds }} 원</p>
      </div>
      <div class="order-form__state--line">
        <p>총 금액</p>
        <p>{{ isNaN(totalAmount) ? 0 : totalAmount.toLocaleString() }} 원</p>
      </div>
    </div>

    <button type="submit">거래하기</button>
  </form>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useTradeStore } from '@/stores/tradeStore';
import axios from 'axios';

const tradeStore = useTradeStore();
const selectStockcode = computed(() => tradeStore.getSelectedStockCode);
const price = computed(() => tradeStore.getStockPrice);

const isBuying = ref(true); // 구매/판매 토글 상태
const amount = ref(1); // 구매 수량
const availableFunds = ref(10000000); // 구매 가능 금액 (예시)
const userId = 1; // 조회할 사용자 ID

// API 요청 함수
const viewStockHeld = async () => {
  try {
    const response = await axios.post(`/api/trade/userId/${userId}`);
    const data = response.data; // API에서 받은 전체 데이터
    availableFunds.value = data.money;
    console.log('조회 성공:', data);
  } catch (error) {
    console.error('조회 실패:', error);
  }
};

// 구매/판매 토글
const toggleBuySell = (buying) => {
  isBuying.value = buying;
};

// 총 금액 계산
const totalAmount = computed(() => {
  const validPrice = isNaN(price.value) ? 0 : price.value;
  const validAmount = isNaN(amount.value) ? 0 : amount.value;
  return validPrice * validAmount;
});

// 수량 증가/감소
const increaseAmount = () => {
  amount.value++;
};

const decreaseAmount = () => {
  if (amount.value > 1) {
    amount.value--;
  }
};

// 퍼센트로 구매 수량 설정 (10%, 25%, 50%, 최대)
const setPercentage = (percentage) => {
  const calculatedAmount = Math.floor(
    (availableFunds.value * (percentage / 100)) / price.value
  );
  amount.value = calculatedAmount;
};

// 최대 금액 설정
const setMaxAmount = () => {
  const maxAmount = Math.floor(availableFunds.value / price.value);
  amount.value = maxAmount;
};

// 주문 처리
const handleOrder = async () => {
  if (isBuying.value) {
    console.log(`종목 코드: ${selectStockcode.value}`);
    console.log(
      `구매: ${price.value}원, ${amount.value}주, 총 금액: ${totalAmount.value}`
    );
    try {
      const response = await axios.post("/api/trade/stock/buy", {
        stockCode: selectStockcode.value,
        price: price.value,
        quantity: amount.value,
        totalPrice: totalAmount.value,
      });
      console.log("서버 응답:", response.data);
    } catch (error) {
      console.error("구매 요청 중 오류 발생:", error);
    }
  } else {
    console.log(`종목 코드: ${selectStockcode}`);
    console.log(
      `판매: ${price.value}원, ${amount.value}주, 총 금액: ${totalAmount.value}`
    );
    try {
      const response = await axios.post("/api/trade/stock/sell", {
        stockCode: selectStockcode.value,
        price: price.value,
        quantity: amount.value,
        totalPrice: totalAmount.value,
      });

      console.log("서버 응답:", response.data);
    } catch (error) {
      console.error("구매 요청 중 오류 발생:", error);
    }
  }
};
onMounted(() => {
  viewStockHeld(); // 컴포넌트가 로드되면 API 요청을 실행
});
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
  width: 200px;
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
</style>
