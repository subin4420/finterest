<script setup>
import { ref, computed, onMounted } from 'vue';
import { useQuizStore } from '@/stores/quizStore';
import { useUserStore } from '@/stores/userStore';
import { useAuthStore } from '@/stores/auth';
import QuizCard from '@/components/quiz/QuizCard.vue';

const { fetchCompletedQuizSets, completedQuizSets } = useQuizStore();
const { rankings, rank, fetchAllRankings, fetchUserRanking } = useUserStore();
const authStore = useAuthStore();

const topRankings = ref([]);
const userRanking = ref(null);

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

const getAvatarColor = (username) => {
  const colors = ['#FF6B6B', '#4ECDC4', '#45B7D1', '#FFA07A', '#98D8C8', '#F06292', '#AED581', '#FFD54F'];
  const index = username.charCodeAt(0) % colors.length;
  return colors[index];
};

const getRankMedalClass = (position) => {
  if (position === 1) return 'gold';
  if (position === 2) return 'silver';
  if (position === 3) return 'bronze';
  return '';
};

onMounted(() => {
  fetchCompletedQuizSets();
  fetchAndSetRankings();
});

const handleQuizCardClick = (cardData) => {
  console.log('Quiz card clicked:', cardData);
};

// completedQuizSets를 6개로 제한하는 computed 속성 추가
const topSixCompletedQuizSets = computed(() => {
  return completedQuizSets.value.slice(0, 6);
});
</script>

<template>
  <div class="lower-content">
    <div class="ranking-section">
      <h2 class="section-title">랭킹</h2>
      <div class="ranking-card__content">
        <table class="ranking-table">
          <thead>
            <tr>
              <th>순위</th>
              <th>사용자</th>
              <th>포인트</th>
              <th>등급</th>
            </tr>
          </thead>
          <tbody>
            <tr 
              v-for="(rank, index) in topRankings" 
              :key="rank.userId"
              :class="{ 'highlight': isUserInRankings(rank.userId) }"
            >
              <td class="rank-position">
                <span :class="['rank-medal', getRankMedalClass(index + 1)]">{{ index + 1 }}</span>
              </td>
              <td>
                <div class="user-info">
                  <span class="user-avatar" :style="{ backgroundColor: getAvatarColor(rank.username) }">
                    {{ rank.username.charAt(0).toUpperCase() }}
                  </span>
                  {{ rank.username }}
                </div>
              </td>
              <td class="points">{{ rank.totalPoints.toLocaleString() }}</td>
              <td>
                <span class="rank-icon" :class="getRankIconClass(rank.rankTitle)"></span>
                <span class="rank-title">{{ rank.rankTitle }}</span>
              </td>
            </tr>
          </tbody>
        </table>

        <div v-if="userRanking && !isUserInTopRankings" class="user-ranking">
          <div class="ranking-ellipsis">...</div>
          <table class="ranking-table">
            <tbody>
              <tr>
                <td class="rank-position">
                  <span class="rank-number">{{ userRanking.rankPosition }}</span>
                </td>
                <td>
                  <div class="user-info">
                    <span class="user-avatar" :style="{ backgroundColor: getAvatarColor(userRanking.username) }">
                      {{ userRanking.username.charAt(0).toUpperCase() }}
                    </span>
                    {{ userRanking.username }}
                  </div>
                </td>
                <td class="points">{{ userRanking.totalPoints.toLocaleString() }}</td>
                <td>
                  <span class="rank-icon" :class="getRankIconClass(userRanking.rankTitle)"></span>
                  <span class="rank-title">{{ userRanking.rankTitle }}</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="quiz-section">
      <h2 class="section-title">완료된 퀴즈 세트 TOP 6</h2>
      <div class="quiz-set-list">
        <QuizCard
          v-for="quizSet in topSixCompletedQuizSets"
          :key="quizSet.setId"
          :cardData="quizSet"
          @click="handleQuizCardClick"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.lower-content {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
}

.ranking-section, .quiz-section {
  flex: 1;
  min-width: 300px;
  background-color: #ffffff;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
}

h2 {
  font-size: 1.25rem;
  margin-bottom: 1rem;
  font-weight: bold;
  color: #333;
}

.ranking-card__content {
  overflow-x: auto;
}

.ranking-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 0.5rem;
}

.ranking-table th {
  background-color: #f8f9fa;
  color: #495057;
  font-weight: bold;
  text-transform: uppercase;
  font-size: 0.85rem;
  letter-spacing: 0.5px;
  padding: 1rem 0.75rem;
}

.ranking-table td {
  background-color: #ffffff;
  padding: 1rem 0.75rem;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}

.ranking-table tr td:first-child {
  border-left: 1px solid #e0e0e0;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
}

.ranking-table tr td:last-child {
  border-right: 1px solid #e0e0e0;
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}

.rank-position {
  text-align: center;
  font-weight: bold;
}

.rank-medal {
  display: inline-block;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  border-radius: 50%;
  color: white;
  font-weight: bold;
}

.rank-medal.gold { background-color: #FFD700; }
.rank-medal.silver { background-color: #C0C0C0; }
.rank-medal.bronze { background-color: #CD7F32; }

.rank-number {
  display: inline-block;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  border-radius: 50%;
  background-color: #f0f0f0;
  color: #333;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
  color: white;
  font-weight: bold;
}

.points {
  font-weight: bold;
  color: #28a745;
}

.rank-icon {
  width: 20px;
  height: 20px;
  margin-right: 5px;
}

.rank-title {
  font-weight: bold;
  color: #495057;
}

.highlight {
  background-color: #e8f5e9 !important;
}

.quiz-set-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
}

.quiz-set-list > * {
  flex: 1 1 calc(33.333% - 1rem);
  min-width: 200px;
}

@media (max-width: 768px) {
  .lower-content {
    flex-direction: column;
  }

  .quiz-set-list {
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  }

  .quiz-set-list > * {
    flex: 1 1 calc(50% - 1rem);
  }
}

@media (max-width: 480px) {
  .quiz-set-list {
    grid-template-columns: 1fr;
  }

  .quiz-set-list > * {
    flex: 1 1 100%;
  }
}

.section-title {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
  padding-bottom: 0.5rem;
}

.icon-bronze { background-image: url('@/assets/images/icons/브론즈.jpeg'); }
.icon-silver { background-image: url('@/assets/images/icons/실버.jpeg'); }
.icon-gold { background-image: url('@/assets/images/icons/골드.jpeg'); }
.icon-platinum { background-image: url('@/assets/images/icons/플레티넘.jpeg'); }
.icon-diamond { background-image: url('@/assets/images/icons/다이아.jpeg'); }
.icon-challenger { background-image: url('@/assets/images/icons/챌린저.jpeg'); }

.ranking-ellipsis {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  color: #999;
  margin: 10px 0;
  position: relative;
}

.ranking-ellipsis::before,
.ranking-ellipsis::after {
  content: '';
  position: absolute;
  top: 50%;
  width: 40%;
  height: 1px;
  background-color: #e0e0e0;
}

.ranking-ellipsis::before {
  left: 0;
}

.ranking-ellipsis::after {
  right: 0;
}

.user-ranking {
  margin-top: 1rem;
  border-top: 1px dashed #e0e0e0;
  padding-top: 1rem;
}
</style>
