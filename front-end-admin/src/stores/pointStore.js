import { reactive, toRefs } from 'vue';
import { getAllPoints, getTotalPoints, getTotalMoney, getPointChart } from '@/services/pointService';

const state = reactive({
  points: [], // 포인트 내역
  totalPoints: 0, // 누적 포인트
  totalMoney: 0, // 누적 가상자금
  pointChart: [],
});

// 1. 전체 포인트 내역 조회
const fetchAllPoints = async (params = {}) => {
  try {
    const data = await getAllPoints(params); // 전체 포인트 내역 조회
    state.points = data.points;
  } catch (error) {
    console.error('Error fetching all points:', error);
  }
};

// 2. 포인트 적립 내역만 조회
const fetchEarnedPoints = async () => {
  try {
    const data = await getAllPoints({ filter: 'earned' }); // 적립된 포인트만 조회
    state.points = data.points;
  } catch (error) {
    console.error('Error fetching earned points:', error);
  }
};

// 3. 포인트 차감 내역만 조회
const fetchDeductedPoints = async () => {
  try {
    const data = await getAllPoints({ filter: 'deducted' }); // 차감된 포인트만 조회
    state.points = data.points;
  } catch (error) {
    console.error('Error fetching deducted points:', error);
  }
};

// 4. 특정 기간 동안의 포인트 내역 조회
const fetchPointsByDateRange = async (startDate, endDate) => {
  try {
    const data = await getAllPoints({ startDate, endDate }); // 특정 기간 동안의 포인트 내역 조회
    state.points = data.points;
  } catch (error) {
    console.error('Error fetching points by date range:', error);
  }
};

// 5. 누적 포인트 조회
const fetchTotalPoints = async () => {
  try {
    const data = await getTotalPoints(); // 누적 포인트 조회
    state.totalPoints = data.totalPoints;
  } catch (error) {
    console.error('Error fetching total points:', error);
  }
};

// 6. 누적 가상자금 조회
const fetchTotalMoney = async () => {
  try {
    const data = await getTotalMoney(); // 누적 포인트 조회
    state.totalMoney = data.totalMoney;
  } catch (error) {
    console.error('Error fetching total points:', error);
  }
};

// 활동별 포인트 획득 비율 조회
const fetchPointChart = async () => {
  try {
    const data = await getPointChart(); // 활동별 포인트 획득 비율 조회
    state.pointChart = data.activityBreakdown; // 데이터를 state에 할당
    state.totalPoints = data.totalPoints; // 전체 포인트 값도 업데이트
  } catch (error) {
    console.error('Error fetching point chart:', error);
  }
};

export const usePointStore = () => {
  return {
    ...toRefs(state),
    fetchAllPoints,
    fetchEarnedPoints,
    fetchDeductedPoints,
    fetchPointsByDateRange,
    fetchTotalPoints,
    fetchTotalMoney,
    fetchPointChart
  };
};
