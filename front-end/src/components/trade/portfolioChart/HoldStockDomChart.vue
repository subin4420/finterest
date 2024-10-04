<template>
  <div>
    <div id="main" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'HoldStockDomChart',
  data() {
    return {
      chart: null, // 차트 인스턴스를 저장할 변수
    };
  },
  mounted() {
    this.initChart(); // 컴포넌트가 마운트되면 차트를 초기화
    this.fetchData(); // 데이터 가져오기
  },
  methods: {
    initChart() {
      // ECharts 차트 초기화
      const chartDom = document.getElementById('main');
      this.chart = echarts.init(chartDom);
    },
    fetchData() {
      // API 호출
      fetch('/api/trade/userId/1')
        .then((response) => response.json())
        .then((data) => {
          const stockData = data.stock;

          // 차트 데이터 변환
          const pieData = stockData.map((item) => ({
            value: item.totalPrice,
            name: item.stockName,
          }));

          const option = {
            title: {
              text: '주식 포트폴리오',
              subtext: `총 자산: ${data.money}원`,
              left: 'center',
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)',
            },
            legend: {
              left: 'center',
              top: 'bottom',
              data: stockData.map((item) => item.stockName),
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
        })
        .catch((error) => {
          console.error('API 호출 중 오류 발생:', error);
        });
    },
  },
};
</script>

<style>
/* 필요에 따라 CSS 스타일 추가 */
</style>
