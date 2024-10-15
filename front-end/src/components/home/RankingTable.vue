<script setup>
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useAuthStore } from '@/stores/auth';

const topRankings = ref([]);
const userRanking = ref(null);
const authStore = useAuthStore();
const { rankings, rank, fetchAllRankings, fetchUserRanking } = useUserStore();

const isUserInRankings = (userId) => {
  return userRanking.value && userRanking.value.userId === userId;
};

const isUserInTopRankings = computed(() => {
  return topRankings.value.some(r => isUserInRankings(r.userId));
});

const fetchAndSetRankings = async () => {
  try {
    await fetchAllRankings();
    if (rankings.value) {
      topRankings.value = rankings.value;
    } else {
      console.error('랭킹 데이터를 가져올 수 없습니다.');
    }

    const token = authStore.getToken();
    if (token) {
      await fetchUserRanking();
      if (rank.value) {
        userRanking.value = rank.value;
      }
    }
  } catch (error) {
    console.error('랭킹 데이터를 가져오는 중 오류 발생:', error);
  }
};

onMounted(() => {
  fetchAndSetRankings();
});

const getRankIconClass = (rankTitle) => {
  switch (rankTitle) {
    case 'Bronze': return 'icon-bronze';
    case 'Silver': return 'icon-silver';
    case 'Gold': return 'icon-gold';
    case 'Platinum': return 'icon-platinum';
    case 'Diamond': return 'icon-diamond';
    case 'Challenger': return 'icon-challenger';
    default: return '';
  }
};
</script>

<template>
  <div class="ranking-container">
    <div class="point-policy-card">
      <h2 class="point-policy-card__title">포인트 지급 정책</h2>
      <table class="point-policy-table">
        <thead>
          <tr>
            <th>활동</th>
            <th>포인트</th>
            <th>설명</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>회원가입</td>
            <td>1000 포인트</td>
            <td>신규 회원 유치를 위한 보상 (1포인트당 1000원 가정)</td>
          </tr>
          <tr>
            <td>출석체크</td>
            <td>100 포인트</td>
            <td>지속적인 참여를 유도하기 위한 인센티브 (매일)</td>
          </tr>
          <tr>
            <td>학습자료 1회 학습</td>
            <td>100 포인트</td>
            <td>학습을 장려하기 위한 보상</td>
          </tr>
          <tr>
            <td>퀴즈 정답 1문제</td>
            <td>30 포인트</td>
            <td>퀴즈 참여 독려</td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <!-- 새로운 트레이드 기능 카드 -->
    <div class="trade-features-card">
      <h2 class="trade-features-card__title">트레이드 기능</h2>
      <div class="trade-features-grid">
        <div class="feature-item">
          <i class="fas fa-chart-line"></i>
          <h3>모의투자</h3>
          <p>실제 주식 시장 데이터를 기반으로 가상 투자를 체험해보세요.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-briefcase"></i>
          <h3>포트폴리오</h3>
          <p>자신의 투자 현황을 한눈에 파악하고 관리할 수 있습니다.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-newspaper"></i>
          <h3>뉴스</h3>
          <p>최신 금융 뉴스를 확인하여 시장 동향을 파악하세요.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-calculator"></i>
          <h3>결산</h3>
          <p>투자 성과를 정기적으로 확인하고 분석할 수 있습니다.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-exchange-alt"></i>
          <h3>포인트 전환</h3>
          <p>획득한 포인트를 가상 화폐로 전환하여 투자에 활용하세요.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-users"></i>
          <h3>커뮤니티</h3>
          <p>다른 투자자들과 정보를 공유하고 소통할 수 있는 공간입니다.</p>
        </div>
        <div class="feature-item">
          <i class="fas fa-chess"></i>
          <h3>턴제 모의투자</h3>
          <p>단계별로 진행되는 투자 시뮬레이션을 체험해보세요.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ranking-container {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
}

.ranking-card, .point-policy-card, .trade-features-card {
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  margin-bottom: 2rem;
  flex: 1;
  min-width: 300px;
}

.ranking-card__title, .point-policy-card__title, .trade-features-card__title {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
  padding-bottom: 0.75rem;
}

.trade-features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
}

.feature-item {
  text-align: center;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.feature-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.feature-item i {
  font-size: 2rem;
  color: #337ce5;
  margin-bottom: 0.5rem;
}

.feature-item h3 {
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.feature-item p {
  font-size: 0.9rem;
  color: #666;
  line-height: 1.4;
}

@media (max-width: 768px) {
  .trade-features-grid {
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  }
}

.ranking-card__content {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
}

th, td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

th {
  font-weight: bold;
  background-color: #f8f9fa;
  color: #495057;
  text-transform: uppercase;
  font-size: 0.85rem;
  letter-spacing: 0.5px;
}

tr:last-child td {
  border-bottom: none;
}

.highlight {
  background-color: #e8f5e9;
}

.user-ranking {
  margin-top: 1.5rem;
  padding-top: 1rem;
  border-top: 2px solid #e0e0e0;
}

.user-ranking h3 {
  font-size: 1.2rem;
  margin-bottom: 1rem;
  color: #333;
}

.point-policy-table th, .point-policy-table td {
  padding: 0.75rem;
  font-size: 0.9rem;
}

.point-policy-table th {
  background-color: #f8f9fa;
}

.rank-icon {
  display: inline-block;
  width: 24px;
  height: 24px;
  margin-right: 8px;
  background-size: contain;
  background-repeat: no-repeat;
  vertical-align: middle;
}

/* .icon-bronze { background-image: url('@/assets/images/icons/브론즈.jpeg'); }
.icon-silver { background-image: url('@/assets/images/icons/실버.jpeg'); }
.icon-gold { background-image: url('@/assets/images/icons/골드.jpeg'); }
.icon-platinum { background-image: url('@/assets/images/icons/플레티넘.jpeg'); }
.icon-diamond { background-image: url('@/assets/images/icons/다이아몬드.jpeg'); }
.icon-challenger { background-image: url('@/assets/images/icons/챌린저.jpeg'); } */

td:last-child {
  display: flex;
  align-items: center;
}

@media (max-width: 768px) {
  .ranking-container {
    flex-direction: column;
  }

  .ranking-card, .point-policy-card, .trade-features-card {
    padding: 1rem;
  }

  th, td, .point-policy-table th, .point-policy-table td {
    padding: 0.75rem 0.5rem;
  }

  .rank-icon {
    width: 20px;
    height: 20px;
    margin-right: 4px;
  }
}
</style>
