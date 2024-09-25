import { reactive, toRefs } from 'vue';
import { getArchive } from '@/services/archiveService';

const state = reactive({
  archives: [] // 초기값 설정
});

const fetchArchive = async () => {
  try {
    const data = await getArchive(); // API 호출
    state.archives = data.archives; // data.archives로 수정
  } catch (error) {
    console.error('Error fetching Archive:', error);
  }
};

export const useArchiveStore = () => {
  return {
    ...toRefs(state),
    fetchArchive
  };
};
