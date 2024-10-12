<template>
  <div class="chart-container" style="margin-bottom: 50px">
    <div id="main" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { useTradeStore } from '@/stores/tradeStore'; // tradeStore import
import { useAuthStore } from '@/stores/auth'; // authStore import

export default {
  name: 'HoldStockDomChart',
  data() {
    return {
      chart: null, // 차트 인스턴스를 저장할 변수
      totalMoney: 0, // 총 자산을 저장할 변수 추가
    };
  },
  mounted() {
    this.initChart(); // 차트 초기화
    this.fetchData(); // 데이터 가져오기
  },
  methods: {
    initChart() {
      // ECharts 차트 초기화
      const chartDom = document.getElementById('main');
      this.chart = echarts.init(chartDom);
    },
    async fetchData() {
      try {
        const tradeStore = useTradeStore(); // tradeStore 사용
        const authStore = useAuthStore(); // authStore 사용
        const userId = authStore.getUserId; // getUserId를 호출하지 않고 가져오기

        await tradeStore.fetchUserTradeHistory(userId); // userId를 사용하여 거래 기록 가져오기
        const stockData = tradeStore.getTradeHistory; // 거래 기록 가져오기

        // totalMoney을 data 속성에 저장
        this.totalMoney = tradeStore.totalMoney || 0; // totalMoney이 undefined일 경우 0으로 설정

        // 중복된 주식 이름을 합산하여 새로운 데이터 생성
        const aggregatedData = stockData.reduce((acc, item) => {
          const existing = acc.find(
            (stock) => stock.stockName === item.stockName
          );
          if (existing) {
            existing.totalPrice += item.totalPrice; // 가격 합산
          } else {
            acc.push({
              stockName: item.stockName,
              totalPrice: item.totalPrice,
            });
          }
          return acc;
        }, []);

        // 차트 데이터 변환
        const pieData = aggregatedData.map((item) => ({
          value: item.totalPrice,
          name: item.stockName,
        }));

        const option = {
          title: {
            text: '보유 주식 현황',

            left: 'center',
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)',
          },
          legend: {
            left: 'center',
            top: 'bottom',
            data: aggregatedData.map((item) => item.stockName),
          },
          series: [
            {
              name: '주식',
              type: 'pie',
              radius: [20, 140],
              center: ['50%', '50%'],
              roseType: 'radius',
              itemStyle: {
                borderRadius: 5,
              },
              label: {
                show: false,
              },
              emphasis: {
                label: {
                  show: true,
                },
              },
              data: pieData,
            },
          ],
        };

        // 차트에 데이터 설정
        this.chart.setOption(option);
      } catch (error) {
        console.error('거래 기록을 가져오는 중 오류 발생:', error);
      }
    },
  },
  beforeDestroy() {
    // 컴포넌트가 파괴되기 전에 차트 인스턴스를 해제
    if (this.chart) {
      this.chart.dispose();
      this.chart = null; // 차트 인스턴스를 null로 설정
    }
  },
};
</script>

<style scoped>
/* 스타일 추가 */
.chart-container {
  width: 500px;
  height: 470px;
  margin: 20px 0;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
