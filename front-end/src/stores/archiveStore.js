import { reactive, toRefs } from 'vue';
import { getArchive, getArchiveProgress, addFavorite, removeFavorite, updateArchiveStatus } from '@/services/archiveService';

const state = reactive({
  archives: [], // 초기값 설정
  inProgressArchives: [], // 진행 중인 학습 자료
  completedArchives: [], // 완료된 학습 자료

  textArchives: [], // 텍스트 자료
  videoArchives: [], // 비디오 자료
});

// 학습 상태 업데이트
const changeArchiveStatus = async (materialId, status) => {
  try {
    await updateArchiveStatus(materialId, { status }); // 상태 업데이트 요청
    // 상태 업데이트 후, 로컬 상태를 업데이트
    const archive = state.archives.find(archive => archive.materialId === materialId);
    if (archive) {
      archive.status = status; // 로컬 상태 업데이트
    }
  } catch (error) {
    console.error('Error changing archive status:', error);
  }
};


// // 전체 학습 자료 조회
// const fetchAllArchives = async () => {
//   try {
//     const data = await getArchive(); // 전체 자료 조회
//     state.archives = data.archives;
//   } catch (error) {
//     console.error('Error fetching all Archives:', error);
//   }
// };

// // text, video 구분해서 자료 조회
// const fetchArchive = async (type) => { // type 매개변수 추가 
//   try {
//     const data = await getArchive({ type }); // type을 객체로 전달
//     state.archives = data.archives.map(archive => ({
//       ...archive,
//     }));
//   } catch (error) {
//     console.error('Error fetching Archive:', error);
//   }
// };

// // 특정 카테고리 조회
// const fetchArchivesByCategory = async (categoryId) => {
//   try {
//     const data = await getArchive({ categoryId }); // 카테고리 ID를 전달
//     state.archives = data.archives;
//   } catch (error) {
//     console.error('Error fetching Archives by category:', error);
//   }
// };

// // 즐겨찾기한 자료만 조회
// const fetchFavoriteArchives = async () => {
//   try {
//     const data = await getArchive({ favorites: true }); // 즐겨찾기 자료 조회
//     state.archives = data.archives;
//   } catch (error) {
//     console.error('Error fetching favorite Archives:', error);
//   }
// };

// // 전체 학습 진행 상태 조회
// const fetchProgressArchives = async () => {
//   try {
//     const data = await getArchiveProgress();
//     state.archives = data.progress; // data.archives 대신 data.progress로 수정
//   } catch (error) {
//     console.error('Error fetching progress Archives:', error);
//   }
// };

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
    filterByCategory(selectedCategory.value); // 필터링 다시 적용
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
    filterByCategory(selectedCategory.value); // 필터링 다시 적용
  } catch (error) {
    console.error('Error removing favorite:', error);
  }
};
export const useArchiveStore = () => {
  return {
    ...toRefs(state),
    //fetchArchive, // text, video 구분
    fetchAllArchives,
    fetchArchivesByCategory,
    //fetchProgressArchives,
    fetchCompletedArchives,
    fetchInProgressArchives,
    fetchFavoriteArchives,
    addToFavorites,
    removeFromFavorites,
    changeArchiveStatus, // 추가된 메서드

    fetchTextArchive,  // 텍스트 자료 조회 함수 내보내기
    fetchVideoArchive, // 비디오 자료 조회 함수 내보내기
  };
};
