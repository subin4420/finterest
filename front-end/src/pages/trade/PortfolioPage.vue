<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">포트폴리오 페이지</h1>
        <!-- SellandBuyChart와 HoldStockDomChart를 따로 배치 -->
        <div class="charts-container">
          <div class="chart-row">
            <SellandBuyChart :tradeData="heldStockData" class="chart" />
          </div>
          <div class="chart-row" style="margin-left: 20px">
            <HoldStockDomChart class="chart" />
          </div>
        </div>
        <div>
          <div>
            <DaliyProfitChart class="chart" />
            <CumulativeProfitChart class="chart" />
          </div>
        </div>

        <div class="trade-history-container">
          <div class="trade-history-table-container">
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
                <tr
                  v-for="(record, index) in tradeHistory"
                  :key="index"
                  class="trade-record"
                >
                  <td>{{ record.stockCode }}</td>
                  <td>{{ record.stockName }}</td>
                  <td>{{ record.totalPrice }}</td>
                  <td>{{ record.quantity }}</td>
                  <td>
                    {{
                      isValidDate(record.createdAt)
                        ? new Date(record.createdAt).toLocaleDateString()
                        : "유효하지 않은 날짜"
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
  </div>
</template>

<script>
import SideTradeNavigationBar from "@/components/trade/SideTradeNavigationBar.vue";
import HoldStockDomChart from "@/components/trade/portfolioChart/HoldStockDomChart.vue";
import SellandBuyChart from "@/components/trade/portfolioChart/SellandBuyChart.vue";
import DaliyProfitChart from "@/components/trade/portfolioChart/DaliyProfitChart.vue";
import CumulativeProfitChart from "@/components/trade/portfolioChart/CumulativeProfitChart.vue";
import { reactive, onMounted, ref } from "vue";
import axios from "axios";
import TradeService from "@/services/tradeService";

export default {
  name: "PortfolioPage",
  components: {
    SideTradeNavigationBar,
    HoldStockDomChart,
    DaliyProfitChart,
    CumulativeProfitChart,
    SellandBuyChart,
  },
  setup() {
    const state = reactive({
      money: 0,
      heldStockData: [],
    });

    const tradeHistory = ref([]); // 거래 기록을 저장할 ref 추가

    const fetchData = async () => {
      try {
        const response = await axios.get("/api/portfolio/assets");
        state.money = response.data.money;
        state.heldStockData = response.data.heldStockData;
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    const isValidDate = (dateString) => {
      const date = new Date(dateString);
      return !isNaN(date.getTime()); // 유효한 날짜인지 확인
    };

    const fetchUserTradeHistory = async () => {
      try {
        tradeHistory.value = await TradeService.getUserTradeHistory(); // 거래 기록 가져오기
      } catch (error) {
        console.error("거래 기록을 가져오는 데 실패했습니다:", error.message);
      }
    };

    onMounted(() => {
      fetchData();
      fetchUserTradeHistory(); // 거래 기록 가져오기
    });

    return {
      ...state,
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

.charts-container {
  display: flex;
  flex-direction: row; /* 가로 방향으로 정렬 */
  padding-left: 20px; /* 아래 여백 추가 */
}

.small-chart {
  max-width: 50%; /* 차트 크기 조정 */
  height: 300px; /* 높이 조정 */
  margin-bottom: 10px; /* 차트 간격 조정 */
}

.trade-history-container {
  margin-top: 20px; /* 매매 기록 컨테이너 여백 추가 */
}

.trade-history-table-container {
  width: 100%; /* 테이블 컨테이너 너비 설정 */
  border: 1px solid #ddd; /* 테이블 컨테이너 경계선 설정 */
  border-radius: 10px; /* 테이블 컨테이너 경계 반지름 설정 */
  padding: 10px; /* 테이블 컨테이너 패딩 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 테이블 컨테이너 그림자 설정 */
}

.trade-history-table {
  width: 100%; /* 테이블 너비 설정 */
  border-collapse: collapse; /* 테이블 경계선 설정 */
}

.trade-history-table th {
  background-color: #f0f0f0; /* 테이블 헤더 배경색 */
  padding: 10px; /* 테이블 헤더 패딩 */
  text-align: left; /* 텍스트 정렬 */
}

.trade-history-table td {
  padding: 10px; /* 테이블 데이터 패딩 */
  border-bottom: 1px solid #ddd; /* 테이블 데이터 경계선 */
}

.trade-record {
  &:nth-child(even) {
    background-color: #f9f9f9; /* 짝수 행 배경색 */
  }
}
</style>
