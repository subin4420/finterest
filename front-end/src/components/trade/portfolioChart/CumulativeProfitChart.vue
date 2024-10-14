<template>
  <div id="main" ref="chartRef" style="width: 628px; height: 418px"></div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import TradeService from "@/services/tradeService";

const chartRef = ref(null);
const data = ref([]);

onMounted(() => {
  const chartDom = chartRef.value;
  const myChart = echarts.init(chartDom);

  // 받아온 데이터를 누적 수익률로 변환하는 함수
  const calculateCumulativeReturns = (rawData) => {
    const cumulativeReturns = [];
    let cumulativeSum = 0;

    rawData.forEach(([date, value]) => {
      cumulativeSum += parseFloat(value); // 수익률을 누적
      cumulativeReturns.push([date, cumulativeSum.toFixed(2)]); // 누적된 수익률을 배열에 저장
    });

    return cumulativeReturns;
  };

  const fetchDailyPortfolioData = async () => {
    try {
      const { data: rawData } = await TradeService.fetchDailyPortfolioData(); // 실제 데이터 fetch
      console.log(rawData);
      // 날짜를 'MM-DD' 형식으로 변환
      const dates = rawData.map((item) => {
        const dateStr = item[0]; // '20241010'과 같은 형식
        const formattedDate =
          dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8); // 'MM-DD' 형식으로 변환
        return formattedDate;
      });

      const cumulativeData = calculateCumulativeReturns(rawData);
      renderChart(cumulativeData, dates); // 차트에 그릴 데이터로 사용
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  };

  const renderChart = (cumulativeData, dates) => {
    const option = {
      title: {
        text: "누적 수익률 차트", // 제목 텍스트
        left: "center", // 제목을 중앙으로 정렬
        style: {
          textAlign: "center", // 추가적으로 텍스트 정렬을 중앙으로 설정 (필요시)
        },
      },
      tooltip: {
        trigger: "axis",
        formatter: (params) => {
          const { axisValue, data } = params[0];
          return `날짜: ${axisValue}<br/>누적 수익률: ${data}%`;
        },
      },
      xAxis: {
        type: "category",
        data: dates, // 변환된 날짜 데이터 사용
        name: "날짜",
      },
      yAxis: {
        type: "value",
        name: "누적 수익률 (%)",
      },
      series: [
        {
          data: cumulativeData.map((item) => item[1]), // 누적 수익률만 그리기
          type: "line",
          smooth: true,
        },
      ],
    };

    myChart.setOption(option);
  };

  // 차트 데이터를 가져와서 렌더링
  fetchDailyPortfolioData();
});
</script>

<style scoped>
#main {
  width: 100%;
  height: 400px;
}
</style>
