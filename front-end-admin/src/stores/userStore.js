import { reactive, toRefs } from 'vue';
import { getDashboard } from '@/services/userService';

const state = reactive({
  todaySignupCount: 0, // 오늘의 가입자 수를 기본값 0으로 설정
  activeUserCount:0,
  totalQuizCount: 0, // 누적 포인트
  totalArchiveCount: 0, // 누적 가상자금
});

// 1. 대시보드 정보 조회
const fetchDashboard = async () => {
  try {
    const data = await getDashboard(); 
    state.todaySignupCount = data.todaySignupCount; // 데이터를 state에 할당
    state.activeUserCount = data.activeUserCount;
    state.totalQuizCount = data.totalQuizCount; // 데이터를 state에 할당
    state.totalArchiveCount = data.totalArchiveCount; // 데이터를 state에 할당
  } catch (error) {
    console.error('Error fetching today sign-ups:', error);
  }
};

export const useUserStore = () => {
  return {
    ...toRefs(state),
    fetchDashboard,
  };
};
