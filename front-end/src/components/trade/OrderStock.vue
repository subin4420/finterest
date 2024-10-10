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
    <input class="order-form__price" :value="currentStockPrice" readonly />

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
    <p v-if="message">{{ message }}</p>
  </form>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useTradeStore } from '@/stores/tradeStore';

export default {
  setup() {
    const tradeStore = useTradeStore();
    const isBuying = ref(true); // 구매/판매 토글 상태
    const amount = ref(1); // 구매 수량
    const availableFunds = ref(9909000); // 예시로 설정된 보유 모의투자금
    const message = ref('');

    // 현재 주식 가격을 가져오는 computed property
    const currentStockPrice = computed(() => {
      return tradeStore.getStockPrice; // tradeStore에서 주식 가격 가져오기
    });

    // 총 금액 계산
    const totalAmount = computed(() => {
      return currentStockPrice.value * amount.value;
    });

    // 구매/판매 토글
    const toggleBuySell = (buying) => {
      isBuying.value = buying;
    };

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
        (availableFunds.value * (percentage / 100)) / currentStockPrice.value
      );
      amount.value = calculatedAmount;
    };

    // 최대 금액 설정
    const setMaxAmount = () => {
      const maxAmount = Math.floor(availableFunds.value / currentStockPrice.value);
      amount.value = maxAmount;
    };

    // 주문 처리
    const handleOrder = async () => {
      const stockData = {
        stockCode: tradeStore.getSelectedStockCode,
        quantity: amount.value,
        price: currentStockPrice.value,
        tradeType: isBuying.value ? 'buy' : 'sell',
      };

      try {
        if (isBuying.value) {
          await tradeStore.buyStock(stockData);
          message.value = '주식 매수 성공!';
        } else {
          await tradeStore.sellStock(stockData);
          message.value = '주식 매도 성공!';
        }
      } catch (error) {
        message.value = '주문 처리 중 오류 발생: ' + error.message;
      }
    };

    onMounted(() => {
      // 주식 가격을 가져오는 로직 추가 필요
      // price.value = ...;
    });

    return {
      isBuying,
      amount,
      availableFunds,
      totalAmount,
      message,
      currentStockPrice,
      toggleBuySell,
      increaseAmount,
      decreaseAmount,
      setPercentage,
      setMaxAmount,
      handleOrder,
    };
  },
};
</script>

<style scoped>
/* 스타일 코드 */
</style>