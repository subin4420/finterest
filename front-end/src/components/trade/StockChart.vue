<template>
  <div>
    <!-- 차트가 렌더링될 div에 ref 속성 추가 -->
    <div ref="chartDom" id="main" style="width: 800px; height: 600px"></div>
  </div>
</template>

<script setup>
import {
  ref,
  onMounted,
  watch,
  defineProps,
  toRef,
  onBeforeUnmount,
} from "vue";
import * as echarts from "echarts";
import { useTradeStore } from "@/stores/tradeStore";
import axios from "axios";

const chartDom = ref(null);
let myChart = null; // 차트 인스턴스 저장
let data0 = { categoryData: [], values: [], volumes: [] }; // 초기 차트 데이터
const tradeStore = useTradeStore();
var w = null;

const props = defineProps({
  selectStockCode: String,
});

const stockCode = toRef(props, "selectStockCode");

// 차트 데이터를 가공하는 함수
const splitData = (rawData) => {
  const categoryData = [];
  const values = [];
  const volumes = [];

  for (let i = 0; i < rawData.length; i++) {
    categoryData.push(rawData[i][0]);
    values.push([rawData[i][1], rawData[i][2], rawData[i][3], rawData[i][4]]);
    volumes.push(rawData[i][5]); // 거래량 데이터도 추가
  }
  return {
    categoryData: categoryData,
    values: values,
    volumes: volumes, // 거래량 데이터 포함
  };
};

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
              // 'HHMMSS' 형식의 데이터를 'HH:MM' 형식으로 변환
              return value.slice(0, 2) + ":" + value.slice(2, 4);
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
  // if (!newData) {return}
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
const loadChartData = async (selectStockcode) => {
  console.log(selectStockcode);
  try {
    const pastData = await axios.get(`/api/chart/data/${selectStockcode}`); // 비동기 데이터 수신을 기다림
    const formattedData = pastData.data.map((data) => [
      data[0], // 시간
      data[1], // 시가
      data[2], // 현재가
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

// WebSocket 연결 및 실시간 데이터 수신
const connectWebSocketForBid = (stockcode) => {
  if (!stockcode) {
    console.log(
      "Stock code is null or undefined, skipping WebSocket connection."
    );
    return;
  }
  const g_app_key = "PSRXymebdmx9Kvgesb6qEHaj3zo5j6FHIftE";
  const g_appsecret =
    "1JhEewe7fshUrv42mE0enQSzTRIj/awR2RImFyplwmUiu3mDYrh5quUSna1Stdkw4JFOqJMT/gkwj05e8grWAjHUM+t8EOsp1Lx48L4uVA1t/bY5oUQuGd5h4D5Dg8A7zHQxFWkNfiewHEVJXguLWrcHxRC2j0zKdTcZgg1p3wyqBqJ1vG0=";
  const g_personalseckey =
    "1JhEewe7fshUrv42mE0enQSzTRIj/awR2RImFyplwmUiu3mDYrh5quUSna1Stdkw4JFOqJMT/gkwj05e8grWAjHUM+t8EOsp1Lx48L4uVA1t/bY5oUQuGd5h4D5Dg8A7zHQxFWkNfiewHEVJXguLWrcHxRC2j0zKdTcZgg1p3wyqBqJ1vG0=";
  // var w;

  try {
    const url = "ws://ops.koreainvestment.com:31000"; // WebSocket 서버 주소
    w = new WebSocket(url);

    w.onopen = function () {
      console.log("[Connection OK]");
      const result = `{"header":{"authoriztion":"","appkey":"${g_app_key}","appsecret":"${g_appsecret}","personalseckey":"${g_personalseckey}","custtype":"P","tr_type":"1","content-type":"utf-8"},"body": {"input": {"tr_id":"H0STCNT0","tr_key":"${stockcode}"}}}`;
      w.send(result);
    };

    w.onmessage = function (e) {
      handleBidData(e); // 데이터 수신 및 처리
    };

    w.onerror = function (e) {
      console.log("WebSocket Error: ", e);
    };

    w.onclose = function () {
      console.log("[Connection Closed]");
      w = null;
    };
  } catch (e) {
    console.log(e);
  }
};

// WebSocket에서 받은 데이터를 처리하는 함수
let highPrice = -Infinity; // 매우 작은 값으로 초기화하여 첫 가격이 무조건 최고가로 설정되게끔
let lowPrice = Infinity; // 매우 큰 값으로 초기화하여 첫 가격이 무조건 최저가로 설정되게끔
let deleyTime = false; // 타이머 제어용 플래그
let savedStartPrice = null; // 딜레이가 시작될 때의 시가 (처음의 curPrice 값 저장)
let endVolume = 0; // 딜레이 주기 동안 누적 거래량
let endCurPrice = null; // 딜레이가 끝날 때의 종가 (최종 curPrice 값 저장)

const handleBidData = (e) => {
  var recvdata = e.data;
  var parts = recvdata.split("|");
  var bodydata = parts[3];
  var bodyParts = bodydata.split("^");
  const time = bodyParts[1]; // 체결 시간
  const curPrice = parseFloat(bodyParts[2]); // 현재가 (실시간 데이터)
  const curVolume = parseFloat(bodyParts[12]); // 거래량을 숫자로 변환

  console.log("현재가:", curPrice, "StockCode: ", stockCode.value);

  if (!deleyTime) {
    deleyTime = true;
    savedStartPrice = curPrice; // 딜레이 시작 시의 현재가를 시가로 저장
    console.log("시가: ", savedStartPrice);
    tradeStore.setStockPrice(savedStartPrice);
    endVolume = 0; // 누적 거래량 초기화
    highPrice = curPrice; // 시작 시 고가 초기화
    lowPrice = curPrice; // 시작 시 저가 초기화

    setTimeout(() => {
      deleyTime = false;

      const chartData = [
        time,
        savedStartPrice, // 시가 (딜레이 시작 시의 가격)
        endCurPrice, // 종가 (딜레이 끝난 시점의 가격)
        lowPrice, // 저가
        highPrice, // 고가
        endVolume, // 누적 거래량
      ];

      console.log("누적 거래량: ", endVolume);
      console.log(
        "차트 업데이트 - 시가:",
        savedStartPrice,
        "종가:",
        endCurPrice,
        "최고가: ",
        highPrice,
        "최저가: ",
        lowPrice
      );

      updateChartData(chartData);

      // 초기화
      savedStartPrice = null;
      highPrice = curPrice;
      lowPrice = curPrice;
    }, 60000); // 1분 딜레이
  } else {
    endCurPrice = curPrice;
    // 딜레이 중에도 curPrice가 계속 갱신됨
    endVolume += curVolume; // 누적 거래량 계산
    // 딜레이 중에도 최고가와 최저가를 갱신
    if (curPrice > highPrice) {
      highPrice = curPrice; // 최고가 갱신
    }
    if (curPrice < lowPrice) {
      lowPrice = curPrice; // 최저가 갱신
    }
  }
};

onMounted(() => {
  console.log("onMounted에서 받은 주식 코드:", stockCode.value);
  renderChart(); // 초기 차트 렌더링
  loadChartData(stockCode.value); // 과거 데이터 로딩
  connectWebSocketForBid(stockCode.value); // 실시간 데이터 WebSocket 연결
});
onBeforeUnmount(() => {
  if (w) {
    console.log("컴포넌트 언마운트 시 WebSocket 닫기");
    w.close();
    w = null;
    deleyTime = true;
  }
});
watch(stockCode, (newStockCode, oldStockCode) => {
  // WebSocket이 이미 열려 있으면 먼저 닫기
  if (w) {
    console.log("기존 WebSocket 연결 닫기");
    w.close();
    w = null;
  }

  if (newStockCode) {
    console.log("새로운 주식 코드로 WebSocket 연결:", newStockCode);
    connectWebSocketForBid(newStockCode);
  }
});
</script>

<style scoped>
#main {
  width: 100%;
  height: 600px;
}
</style>
