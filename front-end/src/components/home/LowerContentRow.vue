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
  return topRankings.value.some((r) => isUserInRankings(r.userId));
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
    case 'Bronze':
      return 'icon-bronze';
    case 'Silver':
      return 'icon-silver';
    case 'Gold':
      return 'icon-gold';
    case 'Platinum':
      return 'icon-platinum';
    case 'Diamond':
      return 'icon-diamond';
    case 'Challenger':
      return 'icon-challenger';
    default:
      return '';
  }
};

onMounted(() => {
  fetchCompletedQuizSets();
  fetchAndSetRankings();
});

const handleQuizCardClick = (cardData) => {
  console.log('Quiz card clicked:', cardData);
};
</script>

<template>
  <div class="lower-content">
    <div class="ranking-section">
      <h2>랭킹</h2>
      <div class="ranking-card__content">
        <table>
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
              v-for="rank in topRankings"
              :key="rank.userId"
              :class="{ highlight: isUserInRankings(rank.userId) }"
            >
              <td>{{ rank.rankPosition }}</td>
              <td>{{ rank.username }}</td>
              <td>{{ rank.totalPoints }}</td>
              <td>
                <span
                  class="rank-icon"
                  :class="getRankIconClass(rank.rankTitle)"
                ></span>
                {{ rank.rankTitle }}
              </td>
            </tr>
          </tbody>
        </table>

        <div v-if="userRanking && !isUserInTopRankings" class="user-ranking">
          <h3>내 랭킹</h3>
          <table>
            <tbody>
              <tr>
                <td>{{ userRanking.rankPosition }}</td>
                <td>{{ userRanking.username }}</td>
                <td>{{ userRanking.totalPoints }}</td>
                <td>
                  <span
                    class="rank-icon"
                    :class="getRankIconClass(userRanking.rankTitle)"
                  ></span>
                  {{ userRanking.rankTitle }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="quiz-section">
      <h2>완료된 퀴즈 세트 TOP 3</h2>
      <div class="quiz-set-list">
        <QuizCard
          v-for="quizSet in completedQuizSets.slice(0, 3)"
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

.ranking-section,
.quiz-section {
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

table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
}

th,
td {
  padding: 0.75rem;
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

.rank-icon {
  display: inline-block;
  width: 24px;
  height: 24px;
  margin-right: 8px;
  background-size: contain;
  background-repeat: no-repeat;
  vertical-align: middle;
}

.icon-bronze {
  background-image: url('@/assets/images/icons/브론즈.jpeg');
}
.icon-silver {
  background-image: url('@/assets/images/icons/실버.jpeg');
}
.icon-gold {
  background-image: url('@/assets/images/icons/골드.jpeg');
}
.icon-platinum {
  background-image: url('@/assets/images/icons/플레티넘.jpeg');
}
.icon-diamond {
  background-image: url('@/assets/images/icons/다이아.jpeg');
}
.icon-challenger {
  background-image: url('@/assets/images/icons/첼린저.jpeg');
}

.quiz-set-list {
  display: flex;
  flex-wrap: wrap;
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

  .quiz-set-list > * {
    flex: 1 1 calc(50% - 1rem);
  }
}

@media (max-width: 480px) {
  .quiz-set-list > * {
    flex: 1 1 100%;
  }
}
</style>
