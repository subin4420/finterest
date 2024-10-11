<template>
  <div>
    <!-- 차트가 렌더링될 div에 ref 속성 추가 -->
    <div ref="chartDom" id="main" style="width: 800px; height: 600px"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps, watch } from 'vue';
import * as echarts from 'echarts';
import axios from 'axios';

const chartDom = ref(null);
let myChart = null; // 차트 인스턴스 저장
let data0 = { categoryData: [], values: [] };
const emit = defineEmits(['loaded']);

const props = defineProps({
  stockData: {
    type: Array,
    required: true,
  },
});

const fetchStockData = async () => {
  try {
    const response = await axios.get('/api/scenario/select'); // 하나의 API에서 두 데이터를 받아옴
    const [stockName, srtnCd] = response.data; // 두 데이터를 응답에서 추출

    return { selectStockcode: srtnCd, stockName: stockName };
  } catch (error) {
    console.error('Error fetching stock data:', error);
    return {
      selectStockcode: 'Unknown Code',
      stockName: 'Unknown Stock',
    };
  }
};

const renderChart = (stockName) => {
  if (chartDom.value) {
    // 기존에 차트 인스턴스가 있으면 삭제
    if (myChart) {
      myChart.dispose();
    }

    myChart = echarts.init(chartDom.value);

    const upColor = '#ed2926'; // 상승 시 색상
    const downColor = '#2679ed'; // 하락 시 색상
    const upBorderColor = '#d01411'; // 상승 시 테두리 색상
    const downBorderColor = '#1160d0'; // 하락 시 테두리 색상

    // 차트 초기 옵션 설정
    const option = {
      title: {
        text: stockName,
        left: 30,
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
        },
      },
      grid: [
        {
          left: '10%',
          right: '8%',
          height: '60%', // 캔들스틱 차트 높이
          top: '10%',
        },
      ],
      xAxis: [
        {
          type: 'category',
          data: data0.categoryData,
          boundaryGap: true, // 동일하게 설정
          axisLine: { onZero: false },
          splitLine: { show: false },
          min: 'dataMin',
          max: 'dataMax',
          gridIndex: 0, // 캔들 차트 x축
          axisLabel: {
            formatter: (value) => {
              // 'yyyymmdd' 형식의 데이터를 'mm-dd' 형식으로 변환
              return value.slice(4, 6) + '-' + value.slice(6, 8);
            },
          },
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
      ],
      dataZoom: [
        {
          type: 'inside',
          xAxisIndex: [0, 1], // 두 개의 xAxis에 동일하게 적용
          start: 50,
          end: 100,
        },
        {
          show: true,
          type: 'slider',
          xAxisIndex: [0, 1], // 두 개의 xAxis에 동일하게 적용
          top: '90%',
          start: 50,
          end: 100,
        },
      ],
      series: [
        {
          name: 'Candlestick',
          type: 'candlestick',
          data: data0.values,
          barWidth: '30%', // 캔들스틱 차트의 너비 설정
          itemStyle: {
            color: upColor,
            color0: downColor,
            borderColor: upBorderColor,
            borderColor0: downBorderColor,
          },
        },
      ],
    };

    myChart.setOption(option);
  }
};

const updateChartData = (newData) => {
  const time = newData[0]; // 시간
  const open = newData[1]; // 시가
  const close = newData[2]; // 현재가
  const low = newData[3]; // 저가
  const high = newData[4]; // 고가

  // 데이터 카테고리에 시간 추가
  data0.categoryData.push(time);

  // 시가와 현재가를 비교하여 캔들의 색상을 결정
  const color =
    open <= close
      ? {
          color: '#ed2926', // 상승 시 빨간색
          borderColor: '#d01411',
        }
      : {
          color: '#2679ed', // 하락 시 파란색
          borderColor: '#1160d0',
        };

  // 차트에 추가할 데이터 구성
  data0.values.push([open, close, low, high]);

  if (myChart) {
    myChart.setOption({
      xAxis: [{ data: data0.categoryData }],
      series: [
        {
          name: 'Candlestick',
          data: data0.values,
        },
      ],
    });
  }
};

const loadChartData = async (selectStockcode) => {
  console.log(selectStockcode);
  try {
    const pastData = await axios.get(`/api/scenario/chart/${selectStockcode}`); // 비동기 데이터 수신을 기다림
    console.log(pastData);
    const formattedData = pastData.data.map((data) => [
      data[0], // 시간
      data[1], // 시가
      data[2], // 현재가
      data[3], // 저가
      data[4], // 고가
    ]);

    // 가공된 데이터를 updateChartData에 전달하여 차트를 업데이트
    formattedData.forEach((newData) => {
      updateChartData(newData);
    });
  } catch (error) {
    console.error('Error loading chart data:', error);
  }
};

onMounted(async () => {
  const { selectStockcode, stockName } = await fetchStockData();
  renderChart(stockName);
  loadChartData(selectStockcode);
  setTimeout(() => {
    // 일정 시간이 지나 차트가 로드되었다고 가정
    console.log('ScenarioChart 로딩 완료');
    emit('loaded'); // 로딩이 완료되면 부모 컴포넌트에 알림
  }, 1000); // 1초 후 로딩 완료
});

watch(
  () => props.stockData,
  (newData) => {
    if (newData && newData.length > 0) {
      updateChartData(newData);
    }
  }
);
</script>
