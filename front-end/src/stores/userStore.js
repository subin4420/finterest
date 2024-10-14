import { reactive, toRefs } from 'vue';
import { getAllRankings, getUserRanking } from '@/services/userService';

const state = reactive({
  rankings: [], // 전체 사용자 랭킹 리스트
  rank: null, // 특정 사용자 랭킹 정보
});

// 전체 랭킹 조회 함수
const fetchAllRankings = async () => {
  try {
    const data = await getAllRankings();
    state.rankings = data.rankings; // API에서 가져온 데이터를 직접 저장
    console.log('전체 사용자 랭킹:', state.rankings); // 콘솔로 데이터 확인
  } catch (error) {
    console.error('전체 사용자 랭킹 조회 실패:', error);
  }
};

// 특정 사용자 랭킹 조회 함수
const fetchUserRanking = async () => {
  try {
    const data = await getUserRanking();  // JWT 기반으로 랭킹 조회
    state.rank = data; // API에서 가져온 사용자 랭킹 데이터를 상태에 저장
    console.log('로그인된 사용자의 랭킹 정보:', state.rank);
  } catch (error) {
    console.error('사용자 랭킹 조회 실패:', error);
  }
};

// 상태와 함수를 반환하는 함수
export const useUserStore = () => {
  return {
    ...toRefs(state), // 상태를 toRefs로 변환해 반환
    fetchAllRankings, // 전체 랭킹 조회 함수 반환
    fetchUserRanking, // 특정 사용자 랭킹 조회 함수 반환
  };
};
