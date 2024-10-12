<template>
  <div class="page-container">
    <div class="content-wrapper">
      <!-- 사이드바 -->
      <div class="sidebar-container">
        <ul class="list-group">
          <li class="list-group-item" v-for="tab in tabs" :key="tab.value">
            <a
              href="#"
              class="sidebar-link"
              :class="{ active: activeTab === tab.value }"
              @click.prevent="setTab(tab.value)"
            >
              {{ tab.label }}
            </a>
          </li>
        </ul>

        <!-- 포인트 이력 탭일 때만 포인트 박스 표시 -->
        <div v-if="activeTab === 'points'" class="points-summary">
          <div class="points-box">
            <div class="points-title">포인트</div>
            <div class="points-value">
              <span class="icon">P</span>
              <!-- totalPoints가 undefined가 아니면 toLocaleString 호출 -->
              {{ totalPoints !== undefined ? totalPoints.toLocaleString() : '0' }}
            </div>
          </div>
        </div>

        <!-- 모의투자 이력 탭일 때만 가상자금 박스 표시 -->
        <div v-if="activeTab === 'investment'" class="money-summary">
          <div class="money-box">
            <div class="money-title">가상 자금</div>
            <div class="money-value">
              <span class="icon">₩</span>
              <!-- totalMoney가 undefined가 아니면 toLocaleString 호출 -->
              {{ totalMoney !== undefined ? totalMoney.toLocaleString() : '0' }}
            </div>
          </div>
        </div>
      </div>

      <!-- 콘텐츠 -->
      <div class="content">
        <h3>{{ pageTitle }}</h3>

        <!-- 학습 상태 필터 또는 포인트 상태 필터 -->
        <div class="tabs" v-if="activeTab === 'archive' || activeTab === 'points'">
          <button
            v-for="status in currentStatuses"
            :key="status.value"
            :class="{ active: (activeTab === 'archive' && learningStatus === status.value) || 
                       (activeTab === 'points' && pointStatus === status.value) }"
            @click="setLearningStatus(status.value)"
          >
            {{ status.label }}
          </button>
        </div>

        <!-- 선택된 탭에 따라 컴포넌트 렌더링 -->
        <component 
          :is="currentComponent" 
          :learningStatus="learningStatus" 
          :pointStatus="pointStatus"
          @openModal="openModal"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import ArchiveList from '@/components/archive/ArchiveList.vue';
import QuizSetList from '@/components/quiz/QuizSetList.vue';
import InvestmentList from '@/components/invest/InvestmentList.vue';
import PointHistoryList from '@/components/point/PointList.vue';
import { usePointStore } from '@/stores/pointStore'; // fetchTotalPoints 함수 import

export default {
  name: 'MyLearningPage',
  components: {
    ArchiveList,
    QuizSetList,
    InvestmentList,
    PointHistoryList
  },
  setup() {
    const activeTab = ref('archive');
    const learningStatus = ref('incomplete');
    const pointStatus = ref('all');
    const isModalVisible = ref(false); // 모달 표시 여부
    const selectedCard = ref(null); // 선택된 카드 데이터
    const pointStore = usePointStore();
    const totalPoints = ref(); // 총 포인트 값
    const totalMoney = ref(); // 총 가상 자금 값

    const learningStatuses = ref([
      { value: 'incomplete', label: '진행중인 학습' },
      { value: 'completed', label: '완료된 학습' },
    ]);

    const pointStatuses = ref([
      { value: 'all', label: '전체' },
      { value: 'earned', label: '적립' },
      { value: 'deducted', label: '차감' },
    ]);

    // 현재 탭에 맞는 상 목록 반환
    const currentStatuses = computed(() => {
      if (activeTab.value === 'archive') {
        return learningStatuses.value;
      } else if (activeTab.value === 'points') {
        return pointStatuses.value;
      }
      return [];
    });

    // 현재 탭에 따른 컴포넌트 렌더링
    const currentComponent = computed(() => {
      switch (activeTab.value) {
        case 'archive':
          return 'ArchiveList';
        case 'quiz':
          return 'QuizSetList';
        case 'investment':
          return 'InvestmentList';
        case 'points':
          return 'PointHistoryList';
        default:
          return null;
      }
    });

    const setLearningStatus = (status) => {
      if (activeTab.value === 'archive') {
        learningStatus.value = status;
      } else if (activeTab.value === 'points') {
        pointStatus.value = status;
      }
    };

    const setTab = (tab) => {
      activeTab.value = tab;
      if (tab === 'archive') {
        setLearningStatus('incomplete');
      } else if (tab === 'points') {
        setLearningStatus('all');
      }
    };

    // 총 포인트 가져오는 함수 실행
    onMounted(async () => {
      try {
        await pointStore.fetchTotalPoints(); // fetchTotalPoints 호출
        totalPoints.value = pointStore.totalPoints.value; // store에서 totalPoints 값 가져오기

        await pointStore.fetchTotalMoney(); // fetchTotalMoney 호출
        totalMoney.value = pointStore.totalMoney.value; // store에서 totalMoney 값 가져오기
      } catch (error) {
        console.error('Error fetching totals:', error);
      }
    });

    // 모달 열기 함수
    const openModal = (card) => {
      selectedCard.value = card;
      isModalVisible.value = true;
    };

    return {
      activeTab,
      currentComponent,
      currentStatuses,
      learningStatus,
      pointStatus,
      setLearningStatus,
      setTab,
      isModalVisible,
      selectedCard,
      openModal,
      totalPoints, // 총 포인트 상태 추가
      totalMoney, // 총 가상 자금 상태 추가
    };
  },
  data() {
    return {
      tabs: [
        { value: 'archive', label: '아카이브 이력' },
        { value: 'quiz', label: '퀴즈 이력' },
        { value: 'investment', label: '모의투자 이력' },
        { value: 'points', label: '포인트 이력' },
      ],
    };
  },
  computed: {
    pageTitle() {
      switch (this.activeTab) {
        case 'archive':
          return '아카이브 이력';
        case 'quiz':
          return '퀴즈 이력';
        case 'investment':
          return '모의투자 이력';
        case 'points':
          return '포인트 이력';
        default:
          return '이력';
      }
    },
  },
};
</script>




<style scoped>
h3{
  font-size: 1.8rem; /* 폰트 크기를 조금 더 키워서 눈에 띄게 */
  font-weight: bold; /* 텍스트를 굵게 */
  font-size: 1.8rem; /* 폰트 크기를 조금 더 키워서 눈에 띄게 */
  font-weight: bold; /* 텍스트를 굵게 */
  color: #333; /* 짙은 회색으로 시각적으로 부드럽게 */
  text-align: left; /* 텍스트를 중앙에 배치 */
  margin-bottom: 1.5rem; /* 제목 아래쪽에 간격 추가 */
  padding-bottom: 0.5rem; /* 제목과 카드 사이에 간격 */ /* 짙은 회색으로 시각적으로 부드럽게 */
  text-align: left; /* 텍스트를 중앙에 배치 */
  margin-bottom: 1.5rem; /* 제목 아래쪽에 간격 추가 */
  padding-bottom: 1rem; /* 제목과 카드 사이에 간격 */
  border-bottom: 2px solid #b3b3b3; /* 제목 아래에 구분선 추가 */
}
/* 사이드바 및 탭 스타일 */
.page-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.content-wrapper {
  display: flex;
  width: 100%;
  max-width: 1200px;
  border-radius: 10px;
  overflow: hidden;
}

.sidebar-container {
  width: 25%;
  padding: 30px;
}

.content {
  width: 75%;
  /* width: 75%; */
  margin-top: 30px;
  padding: 30px;
  border-radius: 10px;
  background-color: #FFFFFF;
}

.list-group {
  padding: 0;
  margin: 0;
  list-style-type: none;
}

.list-group-item {
  border: none;
}

.sidebar-link {
  display: block;
  padding: 15px 20px;
  text-decoration: none;
  color: #333;
  border-radius: 10px;
  margin: 10px;
  transition: background-color 0.3s ease;
}

.sidebar-link.active,
.sidebar-link:hover {
  background-color: #f5f5f5;
  color: #00C4D1;
}

.sidebar-link.active::after {
  content: '';
  display: block;
  position: absolute;
  right: 40px;
  top: 50%;
  width: 8px;
  height: 8px;
  background-color: #00C4D1;
  border-radius: 50%;
  transform: translateY(-50%);
}

.sidebar-link::after {
  content: '';
  display: none;
}

.sidebar-link.active::after {
  display: block;
}

/* 마우스를 올렸을 때 동그라미가 나오지 않도록 설정 */
.sidebar-link:hover::after {
  display: none;
}

.sidebar-link.active {
  background-color: #f5f5f5;
  color: #00C4D1;
}
.tabs button {
  background: none;
  border: none;
  font-size: 16px;
  margin-right: 20px;
  cursor: pointer;
  padding: 10px 0;
  color: #333;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
  margin-bottom: 0rem; /* 제목 아래쪽에 간격 추가 */
}

.tabs button.active {
  font-weight: bold;
  color: #00C4D1;
  border-bottom: 2px solid #00C4D1;
}


/* 스타일은 기존과 동일하게 유지 */
.points-summary, .money-summary {
  margin-top: 20px;
  padding: 10px;
}

.points-box, .money-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  background-color: #FFFFFF;
  border-radius: 5px;
}

.points-title, .money-title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
 
} 

.points-value, .money-value {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  display: flex;
  align-items: center;
  border-top: 2px solid #b3b3b3; /* 제목 아래에 구분선 추가 */
  margin-top: 1rem; /* 제목 아래쪽에 간격 추가 */
  padding-top: 1rem; /* 제목과 카드 사이에 간격 */
}

.icon {
  color: #ffc107;
  font-size: 20px;
  margin-right: 5px;
  background-color: #ffc107; /* 노란 배경 */
  border-radius: 50%;
  width: 24px;
  height: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white; /* 글자색을 흰색으로 */
}

/* 탭 버튼과 카드 사이의 간격 조정 */
.tabs {
  margin-bottom: 30px; /* 탭 버튼과 카드 사이의 간격 증가 */
}

.tabs button {
  background: none;
  border: none;
  font-size: 16px;
  margin-right: 20px;
  cursor: pointer;
  padding: 10px 0;
  color: #333;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
}

.tabs button.active {
  font-weight: bold;
  color: #00C4D1;
  border-bottom: 2px solid #00C4D1;
}

/* 컨텐츠 영역 패딩 조정 */
.content {
  width: 71%;
  margin-top: 30px;
  padding: 30px;
  border-radius: 10px;
  background-color: #FFFFFF;
}

/* 카드 리스트에 상단 여백 추가 */
.card-list {
  margin-top: 20px; /* 카드 리스트 상단 여백 추가 */
}

/* 개별 카드에 여백 추가 */
.card {
  margin-bottom: 20px; /* 카드 간 여백 추가 */
}

/* 기타 스타일은 그대로 유지 */

/* 반응형 스타일 수정 */
@media (max-width: 1200px) {
  .content-wrapper {
    flex-direction: column;
    align-items: center;
  }

  .sidebar-container {
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px;
    padding: 0;
  }

  .list-group {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    padding: 10px 0;
  }

  .list-group-item {
    flex: 0 0 auto;
    margin: 5px;
  }

  .sidebar-link {
    padding: 10px 15px;
    margin: 0;
    display: inline-block;
    white-space: nowrap;
  }

  .sidebar-link.active::after {
    display: none;
  }

  .content {
    width: 100%;
    max-width: 800px;
    margin-top: 20px;
  }

  .points-summary, .money-summary {
    display: inline-block;
    margin-top: 10px;
    margin-right: 10px;
  }

  .points-box, .money-box {
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 10px 15px;
  }

  .points-title, .money-title {
    margin-bottom: 0;
    margin-right: 10px;
  }
}

@media (max-width: 768px) {
  .page-container {
    padding: 10px;
  }

  .sidebar-container, .content {
    padding: 15px;
  }

  .sidebar-link {
    padding: 8px 12px;
    font-size: 14px;
  }

  .tabs button {
    font-size: 14px;
    margin-right: 10px;
  }

  h3 {
    font-size: 1.5rem;
  }

  .list-group {
    justify-content: flex-start;
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
  }
}

@media (max-width: 480px) {
  .sidebar-container, .content {
    padding: 10px;
  }

  .sidebar-link {
    padding: 6px 10px;
    font-size: 12px;
  }

  .points-box, .money-box {
    padding: 8px;
  }

  .points-title, .money-title {
    font-size: 16px;
  }

  .points-value, .money-value {
    font-size: 18px;
  }

  .icon {
    width: 18px;
    height: 18px;
    font-size: 14px;
  }

  .tabs button {
    font-size: 12px;
    margin-right: 5px;
  }
}
</style>
