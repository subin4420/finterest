<template>
  <div class="ranking-table">
    <h2>Top 5 Rankings</h2>
    <table>
      <thead>
        <tr>
          <th>Rank</th>
          <th>User</th>
          <th>Total Points</th>
          <th>Rank Title</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(rank, index) in topRankings" :key="rank.user_id">
          <td>{{ index + 1 }}</td>
          <td>{{ rank.username }}</td>
          <td>{{ rank.total_points }}</td>
          <td>{{ rank.rank_title }}</td>
        </tr>
        <!-- 상위 5명의 랭킹 이후 "..." 표시 -->
        <tr v-if="topRankings.length === 5">
          <td colspan="4" class="ellipsis">...</td>
        </tr>
      </tbody>
    </table>

    <!-- 로그인한 사용자 랭킹 정보 -->
    <div v-if="userRanking">
      <h3>My Ranking</h3>
      <table>
        <tbody>
          <tr>
            <td>{{ userRanking.rank_position }}</td>
            <td>{{ userRanking.username }}</td>
            <td>{{ userRanking.total_points }}</td>
            <td>{{ userRanking.rank_title }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { fetchAllRankings, fetchUserRanking } from '@/services/userService';
import { useAuthStore } from '@/stores/auth';

export default {
  setup() {
    const topRankings = ref([]); // 상위 5명의 랭킹 정보
    const userRanking = ref(null); // 로그인한 사용자의 랭킹 정보
    const authStore = useAuthStore(); // 인증 스토어 사용

    // 전체 랭킹 및 사용자 랭킹 가져오기
    const fetchRankings = async () => {
      try {
        // 전체 랭킹 데이터 가져오기
        const data = await fetchAllRankings();
        topRankings.value = data.slice(0, 5); // 상위 5명만 추출해서 topRankings에 저장

        // 로그인한 사용자 정보가 있으면 사용자 랭킹 가져오기
        const token = authStore.getToken();
        if (token) {
          const userId = authStore.getUserId();
          const userRankData = await fetchUserRanking(userId);
          userRanking.value = userRankData; // 사용자 랭킹 정보 설정
        }
      } catch (error) {
        console.error('랭킹 데이터를 가져오는 중 오류 발생:', error);
      }
    };

    // 컴포넌트 마운트 시 랭킹 데이터를 가져옴
    onMounted(() => {
      fetchRankings();
    });

    return {
      topRankings,
      userRanking,
    };
  },
};
</script>

<style scoped>
.ranking-table {
  width: 100%;
  margin: 20px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: center;
}

.ellipsis {
  text-align: center;
  font-weight: bold;
}

h2, h3 {
  margin-bottom: 15px;
}
</style>
