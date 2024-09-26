import { reactive, toRefs } from 'vue';
import { getArchive } from '@/services/archiveService';

const state = reactive({
  archives: [] // 초기값 설정
});

const fetchArchive = async (type) => { // type 매개변수 추가
  try {
    const data = await getArchive({ type }); // type을 객체로 전달
    state.archives = data.archives; // data.archives로 수정
  } catch (error) {
    console.error('Error fetching Archive:', error);
  }
};

// 전체 학습 자료 조회
const fetchAllArchives = async () => {
  try {
    const data = await getArchive(); // 전체 자료 조회
    state.archives = data.archives;
  } catch (error) {
    console.error('Error fetching all Archives:', error);
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

export const useArchiveStore = () => {
  return {
    ...toRefs(state),
    fetchArchive,
    fetchAllArchives,
    fetchArchivesByCategory,
    fetchFavoriteArchives
  };
};
