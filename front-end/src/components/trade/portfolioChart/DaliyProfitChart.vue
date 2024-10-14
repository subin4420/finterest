<template>
  <div style="width: 75%">
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import Chart from "chart.js/auto";
import TradeService from "@/services/tradeService";

export default {
  setup() {
    const chartCanvas = ref(null);
    let myChart = null;

    const Utils = {
      dates(config) {
        const dates = [];
        return dates.slice(0, config.count);
      },
      CHART_COLORS: {
        red: "rgb(255, 99, 132)",
        blue: "rgb(54, 162, 235)",
      },
      transparentize(color, opacity) {
        return color.replace("rgb", "rgba").replace(")", `, ${opacity})`);
      },
    };

    const DATA_COUNT = 7;
    const labels = Utils.dates({ count: DATA_COUNT });

    const data = {
      labels: labels,
      datasets: [
        {
          label: "수익률",
          data: [], // 데이터를 나중에 설정할 수 있도록 빈 배열로 초기화
          borderColor: Utils.CHART_COLORS.red,
          backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red, 0.5),
          order: 1,
        },
        {
          label: "5일 평균",
          data: [], // 데이터를 나중에 설정할 수 있도록 빈 배열로 초기화
          borderColor: Utils.CHART_COLORS.blue,
          backgroundColor: Utils.transparentize(Utils.CHART_COLORS.blue, 0.5),
          type: "line",
          order: 0,
        },
      ],
    };

    const config = {
      type: "bar",
      data: data,
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: "top",
          },
          title: {
            display: true,
            text: "일 별 수익률",
          },
        },
      },
    };

    onMounted(() => {
      myChart = new Chart(chartCanvas.value, config);
      fetchDailyPortfolioData();
    });

    const fetchDailyPortfolioData = async () => {
      try {
        const { data } = await TradeService.fetchDailyPortfolioData();

        // 날짜와 수익률 데이터를 각각 배열로 분리
        const dates = data.map((item) => {
          const dateStr = item[0]; // '20241010'과 같은 형식
          const formattedDate =
            dateStr.substring(4, 6) + "-" + dateStr.substring(6, 8); // 'MM-DD' 형식으로 변환
          return formattedDate;
        });
        const returns = data.map((item) => parseFloat(item[1])); // 문자열을 숫자로 변환

        const movingAverages = calculateMovingAverage(returns, 5); // 5일 평균치

        receiveData(dates, returns, movingAverages); // 받아온 데이터를 처리하는 함수 호출
      } catch (error) {
        console.error("Error fetching daily portfolio data:", error);
      }
    };

    const calculateMovingAverage = (data, windowSize) => {
      const movingAverages = [];
      for (let i = 0; i < data.length; i++) {
        if (i < windowSize - 1) {
          // 5일 이전의 데이터는 이동 평균이 없으므로 NaN으로 처리
          movingAverages.push(NaN);
        } else {
          // 최근 5일 동안의 평균 계산
          const windowData = data.slice(i - windowSize + 1, i + 1);
          const sum = windowData.reduce((acc, val) => acc + val, 0);
          const average = sum / windowSize;
          movingAverages.push(average);
        }
      }
      return movingAverages;
    };

    const calculateCumulativeReturns = (data) => {
      const cumulativeReturns = [];
      let cumulativeSum = 0;

      data.forEach((value) => {
        cumulativeSum += value; // 수익률을 누적
        cumulativeReturns.push(cumulativeSum); // 누적된 수익률을 배열에 저장
      });
      console.log(cumulativeReturns);
      return cumulativeReturns;
    };

    const receiveData = (dataset1Data, dataset2Data, movingAverages) => {
      // 받아온 데이터를 각 데이터셋에 할당
      myChart.data.labels = dataset1Data; // 라벨로 날짜 사용
      myChart.data.datasets[0].data = dataset2Data; // 수익률 데이터를 첫 번째 데이터셋에 반영
      myChart.data.datasets[1].data = movingAverages; // 5일 평균 데이터를 두 번째 데이터셋에 반영

      myChart.update(); // 차트를 업데이트하여 데이터 반영
    };

    return {
      chartCanvas,
      fetchDailyPortfolioData,
    };
  },
};
</script>

<style scoped></style>
