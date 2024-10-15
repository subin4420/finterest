<template>
  <div>
    <div ref="chartDom" id="main" style="width: 800px; height: 600px"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import axios from "axios";

const chartDom = ref(null);
let myChart = null; // 차트 인스턴스 저장
let data0 = { categoryData: [], values: [], volumes: [] }; // 초기 차트 데이터

const calculateMA = (dayCount, data0) => {
  const result = [];
  for (let i = 0, len = data0.values.length; i < len; i++) {
    if (i < dayCount) {
      result.push("-");
      continue;
    }
    let sum = 0;
    for (let j = 0; j < dayCount; j++) {
      sum += +data0.values[i - j][1];
    }
    result.push(sum / dayCount);
  }
  return result;
};

const renderChart = () => {
  if (chartDom.value) {
    // 기존에 차트 인스턴스가 있으면 삭제
    if (myChart) {
      myChart.dispose();
    }

    myChart = echarts.init(chartDom.value);

    const upColor = "#ed2926";
    const upBorderColor = "#d01411";
    const downColor = "#2679ed";
    const downBorderColor = "#1160d0";

    // 차트 초기 옵션 설정
    const option = {
      title: {
        text: "모의 투자",
        left: 0,
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "cross",
        },
      },
      legend: {
        data: ["Candlestick", "Volume", "MA5", "MA10", "MA20", "MA30"],
      },
      grid: [
        {
          left: "10%",
          right: "8%",
          height: "60%", // 캔들스틱 차트 높이
          top: "10%",
        },
        {
          left: "10%", // 거래량 차트와 캔들 차트의 간격을 동일하게 맞춤
          right: "8%", // 거래량 차트와 캔들 차트의 간격을 동일하게 맞춤
          top: "75%", // 거래량 차트 위치
          height: "10%", // 거래량 차트 높이
        },
      ],
      xAxis: [
        {
          type: "category",
          data: data0.categoryData,
          boundaryGap: true, // 동일하게 설정
          axisLine: { onZero: false },
          splitLine: { show: false },
          min: "dataMin",
          max: "dataMax",
          gridIndex: 0, // 캔들 차트 x축
          axisLabel: {
            formatter: (value) => {
              // 'yyyyMMdd' 형식의 데이터를 'mm-dd' 형식으로 변환
              return value.slice(4, 6) + "-" + value.slice(6, 8);
            },
          },
        },
        {
          type: "category",
          data: data0.categoryData,
          boundaryGap: true, // 동일하게 설정
          gridIndex: 1, // 거래량 차트 x축
          axisLabel: { show: false }, // 거래량 차트에서 x축 라벨 숨김
          axisLine: { onZero: false },
        },
      ],
      yAxis: [
        {
          scale: true,
          splitArea: { show: true },
          gridIndex: 0, // 캔들 차트 y축
          min: function (value) {
            // 최소값을 데이터 최소값보다 약간 낮게 설정하고, 내림으로 처리
            return Math.floor(value.min * 0.999);
          },
          max: function (value) {
            // 최대값을 데이터 최대값보다 약간 높게 설정하고, 올림으로 처리
            return Math.ceil(value.max * 1.001);
          },
        },
        {
          scale: true,
          gridIndex: 1, // 거래량 차트 y축
          axisLabel: { show: true },
          min: 0, // 막대 차트의 최소값 설정 (음수 방지)
          max: function (value) {
            return value.max * 1.1; // 막대 차트 최대값을 자동으로 데이터의 10% 상향
          },
        },
      ],
      dataZoom: [
        {
          type: "inside",
          xAxisIndex: [0, 1], // 두 개의 xAxis에 동일하게 적용
          start: 50,
          end: 100,
        },
        {
          show: true,
          type: "slider",
          xAxisIndex: [0, 1], // 두 개의 xAxis에 동일하게 적용
          top: "90%",
          start: 50,
          end: 100,
        },
      ],
      series: [
        {
          name: "Candlestick",
          type: "candlestick",
          data: data0.values,
          barWidth: "30%", // 캔들스틱 차트의 너비 설정
          itemStyle: {
            color: upColor,
            color0: downColor,
            borderColor: upBorderColor,
            borderColor0: downBorderColor,
          },
        },
        {
          name: "Volume",
          type: "bar",
          xAxisIndex: 1, // 거래량 차트의 x축 인덱스
          yAxisIndex: 1, // 거래량 차트의 y축 인덱스
          data: data0.volumes,
          barWidth: "30%", // 거래량 차트의 너비 설정 (캔들 차트와 동일하게 설정)
          itemStyle: {
            color: function (params) {
              return data0.values[params.dataIndex][1] >
                data0.values[params.dataIndex][0]
                ? upColor
                : downColor;
            },
          },
        },
        {
          name: "MA5",
          type: "line",
          data: calculateMA(5, data0),
          smooth: true,
          lineStyle: { opacity: 0.5 },
        },
        {
          name: "MA10",
          type: "line",
          data: calculateMA(10, data0),
          smooth: true,
          lineStyle: { opacity: 0.5 },
        },
        {
          name: "MA20",
          type: "line",
          data: calculateMA(20, data0),
          smooth: true,
          lineStyle: { opacity: 0.5 },
        },
        {
          name: "MA30",
          type: "line",
          data: calculateMA(30, data0),
          smooth: true,
          lineStyle: { opacity: 0.5 },
        },
      ],
    };

    myChart.setOption(option);
  }
};

// 실시간 데이터를 차트에 업데이트하는 함수
const updateChartData = (newData) => {
  data0.categoryData.push(newData[0]); // 시간
  data0.values.push([newData[1], newData[2], newData[3], newData[4]]); // 시가, 현재가, 저가, 고가
  data0.volumes.push(newData[5]); // 거래량 추가

  if (myChart) {
    myChart.setOption({
      xAxis: [
        { data: data0.categoryData },
        { data: data0.categoryData }, // 거래량 차트의 xAxis 업데이트
      ],
      series: [
        { name: "Candlestick", data: data0.values },
        { name: "Volume", data: data0.volumes },
        { name: "MA5", data: calculateMA(5, data0) },
        { name: "MA10", data: calculateMA(10, data0) },
        { name: "MA20", data: calculateMA(20, data0) },
        { name: "MA30", data: calculateMA(30, data0) },
      ],
    });
  }
};

// 차트에 과거 데이터를 렌더링
const loadChartData = async () => {
  try {
    const pastData = await axios.get(`/api/chart/home`); // 비동기 데터 수신을 기다림
    const formattedData = pastData.data.map((data) => [
      data[0], // 시간
      data[1], // 시가
      data[2], // 종가
      data[3], // 저가
      data[4], // 고가
      data[5], // 거래량
    ]);

    // 가공된 데이터를 updateChartData에 전달하여 차트를 업데이트
    formattedData.forEach((newData) => {
      updateChartData(newData);
    });
  } catch (error) {
    console.error("Error loading chart data:", error);
  }
};
onMounted(() => {
  renderChart(); // 초기 차트 렌더링
  loadChartData(); // 과거 데이터 로딩
});
</script>

<style scoped>
#main {
  width: 100%;
  height: 600px;
}
</style>
