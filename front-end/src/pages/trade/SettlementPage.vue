<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">결산 페이지</h1>
        <p>여기는 결산 페이지입니다.</p>
        <div>
          <h2>거래 기록</h2>
          <ul>
            <li v-for="(record, index) in tradeHistory" :key="index">
              {{ record.details }}
              <div>
                <strong>주식 코드:</strong> {{ record.stockCode }}<br />
                <strong>거래 가격:</strong> {{ record.tradePrice }}<br />
                <strong>거래 수량:</strong> {{ record.quantity }}<br />
                <strong>거래 날짜:</strong>
                {{
                  isValidDate(record.tradeDate)
                    ? new Date(record.tradeDate).toLocaleDateString()
                    : '유효하지 않은 날짜'
                }}<br />
                <strong>거래 타입:</strong> {{ record.tradeType }}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import TradeService from '@/services/TradeService'; // TradeService import 추가
import { ref, onMounted } from 'vue';

export default {
  name: 'SettlementPage',
  components: {
    SideTradeNavigationBar,
  },
  setup() {
    const tradeHistory = ref([]); // 거래 기록을 저장할 ref

    // 유효한 날짜인지 확인하는 메서드
    const isValidDate = (dateString) => {
      const date = new Date(dateString);
      return !isNaN(date.getTime()); // 유효한 날짜인지 체크
    };

    // 컴포넌트가 마운트될 때 거래 기록을 가져옴
    const fetchUserTradeHistory = async () => {
      try {
        tradeHistory.value = await TradeService.getUserTradeHistory(); // 거래 기록 가져오기
      } catch (error) {
        console.error('거래 기록을 가져오는 데 실패했습니다:', error.message);
      }
    };

    onMounted(() => {
      fetchUserTradeHistory(); // 마운트 시 거래 기록 가져오기
    });

    return {
      tradeHistory, // 거래 기록 반환
      isValidDate, // 날짜 유효성 확인 메서드 반환
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
  margin-top: 60px;
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
</style>
