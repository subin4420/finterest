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
      </div>

      <!-- 콘텐츠 -->
      <div class="content">
        <h1>{{ pageTitle }}</h1>
        <hr />

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
import { ref, computed } from 'vue';
import ArchiveList from '@/components/archive/ArchiveList.vue';
import QuizSetList from '@/components/quiz/QuizSetList.vue';
import InvestmentList from '@/components/invest/InvestmentList.vue';
import PointHistoryList from '@/components/point/PointList.vue';

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

    const learningStatuses = ref([
      { value: 'incomplete', label: '진행중인 학습' },
      { value: 'completed', label: '종료된 학습' },
    ]);

    const pointStatuses = ref([
      { value: 'all', label: '전체' },
      { value: 'earned', label: '적립' },
      { value: 'deducted', label: '차감' },
    ]);

    // 현재 탭에 맞는 상태 목록 반환
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
  padding: 20px;
}

.content {
  width: 75%;
  margin-top: 20px;
  padding: 20px;
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

.sidebar-link::after {
  content: '';
  display: none;
  position: absolute;
  right: 40px;
  top: 50%;
  width: 8px;
  height: 8px;
  background-color: #00C4D1;
  border-radius: 50%;
  transform: translateY(-50%);
}

.sidebar-link:hover::after,
.sidebar-link.active::after {
  display: block;
}


.tabs {
  margin-bottom: 20px;
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

.cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}
</style>
