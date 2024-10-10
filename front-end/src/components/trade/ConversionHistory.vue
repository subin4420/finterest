<template>
  <div class="conversion-history">
    <h2>최근 환전 내역</h2>
    <ul v-if="transactions.length > 0">
      <li
        v-for="(transaction, index) in transactions"
        :key="index"
        class="transaction-item"
      >
        <span class="conversion-type">
          {{
            transaction.conversionType === 'points_to_money'
              ? '포인트 → 모의투자금'
              : '모의투자금 → 포인트'
          }}
        </span>
        <span class="amount">
          {{ formatCurrency(Math.abs(transaction.pointChange)) }} 포인트
          {{ transaction.conversionType === 'points_to_money' ? '→' : '←' }}
          {{ formatCurrency(Math.abs(transaction.moneyChange)) }} 원
        </span>
        <span class="date">{{ formatDate(transaction.conversionDate) }}</span>
      </li>
    </ul>
    <p v-else>환전 내역이 없습니다.</p>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import { useConversionStore } from '@/stores/conversionStore';

export default defineComponent({
  name: 'ConversionHistory',
  setup() {
    const conversionStore = useConversionStore();

    const formatCurrency = (value) => {
      return Number(value).toLocaleString('ko-KR');
    };

    const formatDate = (dateString) => {
      const date = new Date(dateString);
      return date.toLocaleString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
      });
    };

    return {
      transactions: conversionStore.transactions,
      formatCurrency,
      formatDate,
    };
  },
});
</script>

<style scoped>
.conversion-history {
  margin-top: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.transaction-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #e9ecef;
}

.transaction-item:last-child {
  border-bottom: none;
}

.conversion-type {
  font-weight: bold;
  color: #495057;
}

.amount {
  color: #6c757d;
}

.date {
  font-size: 0.9em;
  color: #adb5bd;
}
</style>
