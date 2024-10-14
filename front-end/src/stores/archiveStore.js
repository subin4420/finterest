import { reactive, toRefs } from 'vue';
import { getArchive, getArchiveProgress, addFavorite, removeFavorite, updateArchiveStatus, insertArchiveStatus, getRecentTextArchives, getPopularVideoArchives } from '@/services/archiveService';

const state = reactive({
  archives: [], // 초기값 설정
  inProgressArchives: [], // 진행 중인 학습 자료
  completedArchives: [], // 완료된 학습 자료

  textArchives: [], // 텍스트 자료
  videoArchives: [], // 비디오 자료
  recentActivities: [], // 빈 배열로 초기화

  recentText: [],
  popularVideo: [],
});

// text 자료 조회
const fetchTextArchive = async () => {
  try {
    const data = await getArchive({ type: 'text' }); // type을 'text'로 설정
    state.textArchives  = data.archives || [];
  } catch (error) {
    console.error('Error fetching text Archive:', error);
  }
};

// video 자료 조회
const fetchVideoArchive = async () => {
  try {
    const data = await getArchive({ type: 'video' }); // type을 'video'로 설정
    state.videoArchives = data.archives || []; // 배열 체크
  } catch (error) {
    console.error('Error fetching video Archive:', error);
  }
};

// 특정 카테고리 조회
const fetchArchivesByCategory = async (categoryId) => {
  try {
    const data = await getArchive({ categoryId }); // 카테고리 ID를 전달
    state.archives = data.archives;
  } catch (error) {
    console.error('Error fetching Archives by category:', error);
  }
};

// 즐겨찾기한 자료만 조회
const fetchFavoriteArchives = async () => {
  try {
    const data = await getArchive({ favorites: true }); // 즐겨찾기 자료 조회
    state.archives = data.archives;
  } catch (error) {
    console.error('Error fetching favorite Archives:', error);
  }
};

// 모든 자료 조회 (type, categoryId, favorites를 사용하여 필터링)
const fetchAllArchives = async (type, categoryId, favorites) => {
  try {
    const params = {}; // 파라미터 객체 생성

    if (type) params.type = type; // type이 있으면 추가
    if (categoryId) params.categoryId = categoryId; // categoryId가 있으면 추가
    if (favorites) params.favorites = favorites; // favorites가 있으면 추가

    const data = await getArchive(params); // getArchive 호출
    state.archives = data.archives.map(archive => ({
      ...archive,
    }));
  } catch (error) {
    console.error('Error fetching all Archives:', error);
  }
};

// 완료된 자료만 조회
const fetchCompletedArchives = async () => {
  try {
    const data = await getArchiveProgress({ status: 'completed' }); // 완료된 자료만 조회
    state.completedArchives   = data.progress; // data.archives 대신 data.progress로 수정
  } catch (error) {
    console.error('Error fetching completed Archives:', error);
  }
};

// 미완료된 자료만 조회
const fetchInProgressArchives = async () => {
  try {
    const data = await getArchiveProgress({ status: 'incomplete' }); // 미완료된 자료만 조회
    state.inProgressArchives  = data.progress; // data.archives 대신 data.progress로 수정
  } catch (error) {
    console.error('Error fetching in-progress Archives:', error);
  }
};

// 최근 활동 조회
const fetchRecentActivities = async () => {
  try {
    const data = await getArchiveProgress();
    console.log('Raw data from getArchiveProgress:', data);

    const processedActivities = data.progress
      .filter(item => item.status !== null)
      .map(item => {
        let type, date;
        if (item.status === 'completed') {
          type = 'complete';
          date = item.completedAt;
        } else if (item.status === 'incomplete') {
          type = 'study';
          date = item.startedAt;
        } else {
          type = 'start';
          date = item.startedAt;
        }
        return {
          id: item.materialId,
          type: type,
          text: item.title,
          date: date
        };
      })
      .sort((a, b) => new Date(b.date) - new Date(a.date)); // 날짜 기준 내림차순 정렬
    
    state.recentActivities = processedActivities;
    console.log('Processed recent activities:', state.recentActivities);
  } catch (error) {
    console.error('Error fetching recent activities:', error);
    state.recentActivities = [];
  }
};

// 전체 학습 진행률 계산
const calculateOverallProgress = async () => {
  try {
    const data = await getArchiveProgress();
    const totalArchives = data.progress.length;
    const completedArchives = data.progress.filter(item => item.status === 'completed').length;
    
    const progress = totalArchives > 0 ? (completedArchives / totalArchives) * 100 : 0;
    return Math.round(progress); // 소수점 반올림
  } catch (error) {
    console.error('Error calculating overall progress:', error);
    return 0;
  }
};

// 즐겨찾기 추가
const addToFavorites = async (materialId) => {
  try {
    const response = await addFavorite(materialId);
    console.log(response.message); // 성공 메시지
    // 로컬 상태 업데이트
    const archive = state.archives.find(a => a.materialId === materialId);
    if (archive) {
      archive.favorite = true; // 즐겨찾기 상태로 변경
    }
    // filterByCategory(selectedCategory.value); // 필터링 다시 적용
  } catch (error) {
    console.error('Error adding favorite:', error);
  }
};

// 즐겨찾기 삭제
const removeFromFavorites = async (materialId) => {
  try {
    const response = await removeFavorite(materialId);
    console.log(response.message); // 성공 메시지
    // 로컬 상태 업데이트
    const archive = state.archives.find(a => a.materialId === materialId);
    if (archive) {
      archive.favorite = false; // 즐겨찾기 상태 제거
    }
    // filterByCategory(selectedCategory.value); // 필터링 다시 적용
  } catch (error) {
    console.error('Error removing favorite:', error);
  }
};

// 학습 상태(incomplet) 추가
const addArchiveStatus = async(materialId, status) => {
  try {
        const response = await insertArchiveStatus(materialId, { status });
        console.log('Server response:', response.data); // 서버 응답 로그
        const archive = state.archives.find(archive => archive.materialId == materialId);
        if (archive) {
            archive.status = status;
        }
    } catch (error) {
        console.log('Error adding archive status:', error);
    }
};

// 학습 상태(completed) 업데이트
const changeArchiveStatus = async (materialId, status) => {
  try {
        const response = await updateArchiveStatus(materialId, { status });
        console.log('Server response:', response.data); // 서버 응답 로그
        const archive = state.archives.find(archive => archive.materialId === materialId);
        if (archive) {
            archive.status = status;
        }
    } catch (error) {
        console.error('Error changing archive status:', error);
    }
};

// 최근 업데이트된 텍스트 자료 8개 조회
const fetchRecentTextArchives = async () => {
  try {
    const data = await getRecentTextArchives(); // 최근 업데이트된 텍스트 자료 조회
    state.recentText = data.texts || [];  // 데이터가 있으면 설정, 없으면 빈 배열
    console.log('Fetched recent text archives:', state.textArchives);
  } catch (error) {
    console.error('Error fetching recent text archives:', error);
  }
};

// 완료 횟수가 많은 영상 자료 8개 조회
const fetchPopularVideoArchives = async () => {
  try {
    const data = await getPopularVideoArchives(); // 완료 횟수가 많은 영상 자료 조회
    state.popularVideo = data.videos || [];  // 데이터가 있으면 설정, 없으면 빈 배열
    console.log('Fetched popular video archives:', state.videoArchives);
  } catch (error) {
    console.error('Error fetching popular video archives:', error);
  }
};

export const useArchiveStore = () => {
  return {
    ...toRefs(state),
    fetchAllArchives,
    fetchArchivesByCategory,
    fetchCompletedArchives,
    fetchInProgressArchives,
    fetchFavoriteArchives,
    addToFavorites,
    removeFromFavorites,
    addArchiveStatus,
    changeArchiveStatus, // 추가된 메서드

    fetchTextArchive,  // 텍스트 자료 조회 함수 내보내기
    fetchVideoArchive, // 비디오 자료 조회 함수 내보내기
    fetchRecentActivities, // 새로운 함수 추가
    calculateOverallProgress, // 새로운 함수 추가

    fetchRecentTextArchives,
    fetchPopularVideoArchives,
  };
};
