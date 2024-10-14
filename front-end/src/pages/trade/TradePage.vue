<template>
  <div class="trade-page">
    <TradeImage />
    <TradeNavigationBar />
    <div class="content-wrapper">
      <div class="hold-total-assets-container">
        <div
          class="balance-container"
          style="display: flex; justify-content: space-between; width: 100%"
        >
          <div
            v-if="authStore.isLogin"
            class="user-balance"
            style="background-color: #ffffff"
          >
            <h2 class="balance-title">현재 보유 잔고</h2>
            <!-- 변경: 포인트와 모의투자금을 각각 따로 관리 -->
            <div class="balance-box" @click="navigateToPointConversion">
              <div class="balance-item">
                <i class="fas fa-coins"></i>
                <span class="balance-label">포인트</span>
                <span class="balance-value">{{
                  formatCurrency(conversionStore.userBalance.totalPoints)
                }}</span>
              </div>
            </div>
            <div class="balance-box" @click="navigateToPointConversion">
              <div class="balance-item">
                <i class="fas fa-dollar-sign"></i>
                <span class="balance-label"> 모의투자금</span>
                <span class="balance-value"
                  >{{
                    formatCurrency(conversionStore.userBalance.money)
                  }}
                  원</span
                >
              </div>
            </div>
          </div>
          <div class="spacer" style="flex-grow: 1">
            <div class="guide-info">
              <h2>모의투자 가이드</h2>
              <button @click="openModal">모의투자 사용설명서</button>
              <GuideModal
                :isVisible="showModal"
                @update:isVisible="showModal = $event"
              />
            </div>
          </div>
          <HoldTotalAssets />
        </div>
      </div>
      <div class="content-grid">
        <!-- 아카이브카드 제거 -->
      </div>
      <div v-if="loading">
        <!-- 로딩 상태 표시 -->
        <p>로딩 중...</p>
      </div>
    </div>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import DefaultLayout from '@/components/layouts/DefaultLayout.vue';
import { onMounted, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useConversionStore } from '@/stores/conversionStore';
import HoldTotalAssets from '@/components/trade/portfolioChart/HoldTotalAssets.vue';
import { useRouter } from 'vue-router'; // 추가: 라우터 사용
import GuideModal from '@/components/trade/GuideModal.vue';

export default {
  name: 'TradePage',
  components: {
    TradeImage,
    DefaultLayout,
    TradeNavigationBar,
    HoldTotalAssets,
    GuideModal,
  },
  setup() {
    const authStore = useAuthStore();
    const conversionStore = useConversionStore();
    const loading = ref(true); // 로딩 상태 변수 추가
    const router = useRouter(); // 추가: 라우터 인스턴스 생성

    onMounted(async () => {
      if (authStore.isLogin) {
        await conversionStore.fetchUserBalance();
      }
      loading.value = false; // 데이터 로드 완료 후 로딩 상태 변경
    });

    function formatCurrency(value) {
      return new Intl.NumberFormat('ko-KR').format(value);
    }

    // 추가: 포인트 변환 페이지로 이동하는 함수
    function navigateToPointConversion() {
      router.push({ name: 'pointconversion' }); // 라우터를 사용하여 페이지 이동
    }

    return {
      authStore,
      conversionStore,
      formatCurrency,
      loading, // 로딩 상태 반환
      navigateToPointConversion, // 추가: 함수 반환
    };
  },
  data() {
    return {
      showModal: false, // 모달 표시 여부를 위한 변수
    };
  },
  methods: {
    openModal() {
      this.showModal = true; // 모달 열기
    },
  },
};
</script>

<style scoped>
.trade-page {
  background-color: #f5f5f5;
}

.content-wrapper {
  min-height: calc(100vh - 60px);
  padding: 20px;
  margin-top: 30px;
  display: flex; /* 추가: flexbox 사용 */
  flex-direction: column; /* 추가: 세로 방향으로 정렬 */
  align-items: center; /* 추가: 중앙 정렬 */
}

.hold-total-assets-container {
  display: flex; /* 추가: 가로로 배치 */
  justify-content: space-between; /* 추가: 아이템 간격 조정 */
  width: 100%; /* 추가: 전체 너비 사용 */
}

.user-balance {
  background: #ffffff; /* 변경: 배경 색상을 흰색으로 설정 */
  border-radius: 12px;
  padding: 20px; /* 패딩 유지 */
  margin-bottom: 20px;
  text-align: center;
  color: #000000; /* 텍스트 색상 변경: 검정색 */
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2); /* 그림자 효과 유지 */
  max-width: 350px; /* 최대 너비 유지 */
}

.balance-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 15px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.balance-container {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 10px;
}

.balance-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
}

.balance-item i {
  font-size: 1.8em;
  margin-bottom: 10px;
  color: #3858d6;
}

.balance-label {
  font-size: 0.9em;
  margin-bottom: 5px;
  font-weight: 600;
}

.balance-value {
  font-size: 1.1em;
  font-weight: bold;
}

.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}

.spacer {
  background-color: #ffffff;
  height: 100%;
  margin-left: 10px;
  margin-right: 10px;
  border-radius: 10px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
  margin-top: 10px;
  margin-bottom: 10px;
}

.balance-box {
  border: 1px solid #ccc; /* 변경: 테두리 추가 */
  border-radius: 8px; /* 변경: 테두리 둥글게 */
  padding: 10px; /* 변경: 내부 여백 추가 */
  margin-bottom: 10px; /* 변경: 아래쪽 여백 추가 */
  cursor: pointer;
}

.balance-box:hover {
  background-color: #f0f0f0;
}
</style>
