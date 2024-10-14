<template>
  <div class="portfolio-chart-container">
    <h2 class="chart-title">보유 자산</h2>
    <p class="total-assets">
      총자산
      <span class="highlight total-assets-highlight"
        >{{ formatCurrency(totalAssets) }} 원</span
      >
    </p>
    <!-- 총자산 차트 추가 -->
    <canvas
      id="totalAssetsChart"
      width="250"
      height="200"
      style="margin-top: 0px"
    ></canvas>
    <p class="money">
      주식 총합:
      <span class="highlight">{{ formatCurrency(totalStockValue) }} 원</span>
    </p>
    <!-- 주식별 보유량 추가 -->
    <!-- <ul class="funds-list">
      <li v-for="(fund, index) in sortedFunds" :key="index" class="fund-item">
        <span>{{ fund.stockName }}</span>
        <span class="total-price"
          >{{ formatCurrency(fund.totalPrice) }} 원</span
        >
      </li>
    </ul> -->
  </div>
</template>

<script>
import { defineComponent, onMounted, ref, computed } from 'vue';
import { useTradeStore } from '@/stores/tradeStore';
import TradeService from '@/services/tradeService';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

export default defineComponent({
  setup() {
    const tradeStore = useTradeStore();
    const funds = ref([]); // 자산 정보를 저장할 ref 생성
    const money = ref(0); // 보유 머니를 저장할 ref 생성

    const fetchUserFunds = async () => {
      try {
        const userFunds = await TradeService.getUserFunds();
        funds.value = userFunds.stock;
        money.value = userFunds.money;
      } catch (error) {
        console.error('자산 정보를 가져오는 데 실패했습니다:', error.message);
      }
    };

    // 총자산 계산
    const totalAssets = computed(() => {
      const totalStockValue = funds.value.reduce(
        (sum, fund) => sum + fund.totalPrice,
        0
      );
      return money.value + totalStockValue; // 보유 머니와 보유 주식의 총합
    });

    // 주식 총합 계산
    const totalStockValue = computed(() => {
      return funds.value.reduce((sum, fund) => sum + fund.totalPrice, 0);
    });

    // 보유 주식을 가격이 높은 순으로 정렬
    const sortedFunds = computed(() => {
      return funds.value.sort((a, b) => b.totalPrice - a.totalPrice);
    });

    const createTotalAssetsChart = () => {
      const ctx = document.getElementById('totalAssetsChart').getContext('2d');
      new Chart(ctx, {
        type: 'bar', // 막대그래프 설정
        data: {
          labels: [''],
          datasets: [
            {
              label: '보유 머니',
              data: [money.value],
              backgroundColor: '#36A2EB',
              barThickness: 20, // 막대그래프의 두께 조정
              borderColor: '#1E90FF', // 테두리 색상 추가
              borderWidth: 2, // 테두리 두께 추가
            },
            {
              label: '주식 총합',
              data: [
                funds.value.reduce((sum, fund) => sum + fund.totalPrice, 0),
              ],
              backgroundColor: '#FF6384',
              barThickness: 20, // 막대그래프의 두께 조정
              borderColor: '#FF4500', // 테두리 색상 추가
              borderWidth: 2, // 테두리 두께 추가
            },
          ],
        },
        options: {
          indexAxis: 'y', // 가로 막대그래프 설정
          scales: {
            x: {
              beginAtZero: true,
              stacked: true, // 스택형 막대그래프 설정
              ticks: {
                display: true, // x축 눈금 표시
                color: '#333', // x축 눈금 색상
              },
              grid: {
                display: true, // x축 격자 표시
                color: '#e0e0e0', // 격자 색상
              },
            },
            y: {
              stacked: true, // 스택형 막대그래프 설정
              ticks: {
                display: true, // y축 눈금 표시
                color: '#333', // y축 눈금 색상
              },
              grid: {
                display: true, // y축 격자 표시
                color: '#e0e0e0', // 격자 색상
              },
            },
          },
          layout: {
            padding: 10, // 그래프 여백 추가
          },
          background: {
            color: '#d3d3d3', // 그래프 배경색을 회색으로 변경
          },
        },
      });
    };

    // 금액 포맷팅 함수
    const formatCurrency = (value) => {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    onMounted(() => {
      fetchUserFunds().then(() => {
        createTotalAssetsChart();
      });
    });

    return {
      funds,
      totalAssets,
      money,
      sortedFunds,
      totalStockValue,
      formatCurrency,
    }; // formatCurrency 추가
  },
});
</script>

<style scoped>
/* 일 추가 */
.portfolio-chart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #ffffff; /* 배경색을 흰색으로 변경 */
  border-radius: 12px; /* 모서리 둥글게 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2); /* 그림자 강도 증가 */
}

.chart-title {
  font-size: 30px; /* 제목 크기 증가 */
  color: #2c3e50; /* 제목 색상 변경 */
  margin-bottom: 20px; /* 제목과 차트 사이의 여백 증가 */
  font-weight: bold; /* 제목 두껍게 */
  text-align: center; /* 제목 중앙 정렬 */
}

.total-assets,
.money {
  font-size: 22px; /* 텍스트 크기 증가 */
  color: #34495e; /* 텍스트 색상 변경 */
  margin-bottom: 15px; /* 텍스트 사이의 여백 증가 */
  font-family: 'Arial', sans-serif; /* 폰트 변경 */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); /* 텍스트 그림자 추가 */
}

.funds-list {
  list-style-type: none; /* 목록 스타일 제거 */
  padding: 0; /* 목록 여백 제거 */
  width: 100%; /* 목록 너비 100%로 설정 */
}

.fund-item {
  font-size: 18px; /* 목록 텍스트 크기 증가 */
  color: #000000; /* 목록 텍스트 색상 변경 */
  margin-bottom: 15px; /* 목록 항목 사이의 여백 증가 */
  padding: 12px; /* 항목 패딩 증가 */
  border-radius: 8px; /* 항목 모서리 둥글게 */
  background-color: #ecf0f1; /* 항목 배경색 추가 */
  transition: background-color 0.3s, transform 0.3s; /* 배경색 및 크기 전환 효과 */
}

.fund-item .total-price {
  float: right; /* fund.totalPrice를 오른쪽 정렬 */
}

.highlight {
  font-weight: bold; /* 강조된 텍스트 두껍게 */
  color: #e74c3c; /* 강조된 텍스트 색상 변경 */
  font-size: 24px; /* 강조된 텍스트 크기 증가 */
}

.total-assets-highlight {
  color: #2ecc71; /* 보유 자산 색상 변경 (예: 초록색) */
}
</style>
