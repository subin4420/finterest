<template>
  <div class="chart-container">
    <h2 class="chart-title">날짜별 주식 거래 횟수</h2>
    <canvas id="sellAndBuyChart" width="600" height="400"></canvas>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import { onMounted } from 'vue';
import { useTradeStore } from '@/stores/tradeStore'; // TradeStore import

Chart.register(...registerables);

export default {
  name: 'SellandBuyChart',
  setup() {
    const tradeStore = useTradeStore(); // TradeStore 사용

    const createSellAndBuyChart = () => {
      const tradeSummary = {};

      tradeStore.tradeHistory.forEach((trade) => {
        const date = new Date(trade.createdAt).toLocaleDateString(); // 거래 날짜 추출

        if (!tradeSummary[date]) {
          tradeSummary[date] = { buy: 0, sell: 0 }; // 날짜별 초기화
        }

        if (trade.tradeType === 'buy') {
          tradeSummary[date].buy += trade.quantity;
        } else if (trade.tradeType === 'sell') {
          tradeSummary[date].sell += trade.quantity;
        }
      });

      // 날짜를 오른쪽으로 갈수록 최근으로 정렬
      const sortedDates = Object.keys(tradeSummary).sort(
        (a, b) => new Date(a) - new Date(b)
      );
      const labels = sortedDates; // 날짜 배열
      const buyData = labels.map((date) => tradeSummary[date].buy); // 매수 수량 배열
      const sellData = labels.map((date) => tradeSummary[date].sell); // 매도 수량 배열

      const ctx = document.getElementById('sellAndBuyChart').getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: labels,
          datasets: [
            {
              label: '매수 수량',
              data: buyData,
              backgroundColor: 'rgba(75, 192, 192, 0.2)',
              borderColor: 'rgba(75, 192, 192, 1)',
              borderWidth: 1,
            },
            {
              label: '매도 수량',
              data: sellData,
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 1,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
            x: {
              ticks: {
                autoSkip: false,
              },
            },
          },
        },
      });
    };

    onMounted(async () => {
      await tradeStore.fetchUserTradeHistory(1); // 사용자 ID를 전달하여 거래 기록 가져오기
      createSellAndBuyChart(); // 차트 생성
    });
  },
};
</script>

<style scoped>
/* 스타일 추가 */
.chart-container {
  width: 500px;
  height: 470px;
  margin: 20px 0;
  border: 1px solid #ffffff;
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0 2px 4px rgb(254, 254, 254);
  padding-bottom: 10px;
}

.chart-title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 10px;
}
</style>
