<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">거래내역</h1>

        <div class="filter-container">
          <!-- 필터 컨테이너 추가 -->

          <button
            @click="filterType = '매수'"
            :class="{ active: filterType === '매수' }"
          >
            매수
          </button>
          <button
            @click="filterType = '매도'"
            :class="{ active: filterType === '매도' }"
          >
            매도
          </button>
          <button @click="filterType = ''">전체</button>
        </div>

        <table class="trade-history-table">
          <thead>
            <tr>
              <th>주식 코드</th>
              <th>주식 이름</th>
              <th class="text-right">거래 가격</th>
              <th>거래 수량</th>
              <th>거래 날짜</th>
              <th>거래 타입</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(record, index) in filteredTradeHistory" :key="index">
              <td>{{ record.stockCode }}</td>
              <td>{{ record.stockName }}</td>
              <td class="text-right">
                {{ formatCurrency(record.totalPrice) }} 원
              </td>
              <td>{{ record.quantity }}</td>
              <td>
                {{
                  isValidDate(record.createdAt)
                    ? new Date(record.createdAt).toLocaleDateString()
                    : '유효하지 않은 날짜'
                }}
              </td>
              <td :class="record.tradeType === '매수' ? 'buy' : 'sell'">
                {{ record.tradeType }}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import TradeService from '@/services/tradeService';
import { ref, onMounted, computed } from 'vue'; // computed 추가

export default {
  name: 'SettlementPage',
  components: {
    SideTradeNavigationBar,
  },
  setup() {
    const tradeHistory = ref([]);
    const filterType = ref(''); // 필터 타입을 저장할 ref

    const isValidDate = (dateString) => {
      const date = new Date(dateString);
      return !isNaN(date.getTime());
    };

    const formatCurrency = (value) => {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    const fetchUserTradeHistory = async () => {
      try {
        tradeHistory.value = await TradeService.getUserTradeHistory();
      } catch (error) {
        console.error('거래 기록을 가져오는 데 실패했습니다:', error.message);
      }
    };

    const filteredTradeHistory = computed(() => {
      return tradeHistory.value.filter((record) => {
        return filterType.value === '' || record.tradeType === filterType.value;
      });
    });

    onMounted(() => {
      fetchUserTradeHistory();
    });

    return {
      tradeHistory,
      isValidDate,
      formatCurrency,
      filterType,
      filteredTradeHistory, // 필터링된 거래 기록 반환
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

.filter-container {
  display: flex;
  justify-content: flex-end; /* 오른쪽 정렬 */
  margin-bottom: 20px; /* 아래 여백 추가 */
}

.filter-container h2 {
  margin-right: 20px; /* 제목과 버튼 사이 여백 추가 */
}

.filter-container button {
  margin-left: 10px; /* 버튼 사이 여백 추가 */
  padding: 10px 15px; /* 버튼 패딩 추가 */
  border: none;
  border-radius: 5px;
  background-color: #2e78e0; /* 버튼 배경색 */
  color: white; /* 버튼 글씨 색상 */
  cursor: pointer;
  transition: background-color 0.3s; /* 호버 효과 */
}

.filter-container button:hover {
  background-color: #1a5bb8; /* 호버 시 배경색 변경 */
}

.filter-container .active {
  background-color: #1a5bb8; /* 활성화된 버튼 색상 */
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

.text-right {
  text-align: right; /* 오른쪽 정렬 클래스 추가 */
}

.buy {
  color: blue;
}

.sell {
  color: red;
}
</style>
