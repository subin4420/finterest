<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">거래내역</h1>

        <div>
          <h2></h2>
          <table class="trade-history-table">
            <thead>
              <tr>
                <th>주식 코드</th>
                <th>주식 이름</th>
                <th>거래 가격</th>
                <th>거래 수량</th>
                <th>거래 날짜</th>
                <th>거래 타입</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(record, index) in tradeHistory" :key="index">
                <td>{{ record.stockCode }}</td>
                <td>{{ record.stockName }}</td>
                <td>{{ record.totalPrice }}</td>
                <td>{{ record.quantity }}</td>
                <td>
                  {{
                    isValidDate(record.createdAt)
                      ? new Date(record.createdAt).toLocaleDateString()
                      : '유효하지 않은 날짜'
                  }}
                </td>
                <td>{{ record.tradeType }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import TradeService from '@/services/tradeService'; // TradeService import 추가
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
  margin-top: 20px;
}

.content {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
  background-color: #f9f9f9; /* 배경색 추가 */
  border-radius: 8px; /* 모서리 둥글게 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
}

.page-title {
  font-size: 2rem; /* 제목 크기 증가 */
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
}

.trade-history-table {
  width: 100%; /* 테이블 너비 100% */
  border-collapse: collapse; /* 테두리 겹침 제거 */
  margin-top: 20px; /* 상단 여백 추가 */
  border-radius: 8px; /* 테이블 모서리 둥글게 */
  overflow: hidden; /* 모서리 둥글게 하기 위해 overflow hidden */
}

.trade-history-table th,
.trade-history-table td {
  border: 1px solid #ddd; /* 테두리 추가 */
  padding: 12px; /* 패딩 증가 */
  text-align: left; /* 텍스트 왼쪽 정렬 */
}

.trade-history-table th {
  background-color: #2e78e0; /* 헤더 배경색 */
  color: white; /* 헤더 글씨 색상 */
  font-weight: bold; /* 헤더 글씨 두껍게 */
}

.trade-history-table tr:nth-child(even) {
  background-color: #f2f2f2; /* 짝수 행 배경색 */
}

.trade-history-table tr:hover {
  background-color: #e0e0e0; /* 호버 시 배경색 */
}
</style>
