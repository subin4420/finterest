import { reactive, toRefs } from 'vue';
import { getArchive, getArchiveProgress, getArchiveChart } from '@/services/archiveService';

const state = reactive({
  archives: [], // 초기값 설정
  inProgressArchives: [], // 진행 중인 학습 자료
  completedArchives: [], // 완료된 학습 자료

  textArchives: [], // 텍스트 자료
  videoArchives: [], // 비디오 자료

  weeklyLearningCounts: [], // 주별 학습 자료 조회수
  monthlyLearningCounts: [], // 월별 학습 자료 조회수
});


// 주별 학습 자료 조회수 가져오기
const fetchWeeklyLearningCounts = async (year, month) => {
  try {
    const data = await getArchiveChart({ period: 'weekly', year, month });
    state.weeklyLearningCounts = data.learningCounts || []; // 주별 학습 자료 조회수 할당
  } catch (error) {
    console.error('Error fetching weekly learning counts:', error);
  }
};

// 월별 학습 자료 조회수 가져오기
const fetchMonthlyLearningCounts = async (year) => {
  try {
    const data = await getArchiveChart({ period: 'monthly', year });
    state.monthlyLearningCounts = data.learningCounts || []; // 월별 학습 자료 조회수 할당
  } catch (error) {
    console.error('Error fetching monthly learning counts:', error);
  }
};




export const useArchiveStore = () => {
  return {
    ...toRefs(state),
  

    fetchWeeklyLearningCounts, // 주별 학습 자료 조회수 함수 내보내기
    fetchMonthlyLearningCounts, // 월별 학습 자료 조회수 함수 내보내기
  };
};
