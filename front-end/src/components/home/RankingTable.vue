<template>
  <div class="ranking-table">
    <h2>랭킹</h2>
    <table>
      <thead>
        <tr>
          <th>순위</th>
          <th>사용자</th>
          <th>총 포인트</th>
          <th>랭킹 타이틀</th>
        </tr>
      </thead>
      <tbody>
        <tr 
          v-for="(rank, index) in topRankings" 
          :key="rank.userId"
          :class="{ 'highlight': isUserInRankings(rank.userId) }"
        >
          <td>{{ rank.rankPosition }}</td> <!-- 올바른 필드명 사용 -->
          <td>{{ rank.username }}</td>
          <td>{{ rank.totalPoints }}</td>
          <td>{{ rank.rankTitle }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 로그인한 사용자가 rankings에 포함되지 않은 경우에만 사용자 랭킹 정보 표시 -->
    <div v-if="userRanking && !isUserInTopRankings">
      <h3>내 랭킹</h3>
      <table>
        <tbody>
          <tr>
            <td>{{ userRanking.rankPosition }}</td>
            <td>{{ userRanking.username }}</td>
            <td>{{ userRanking.totalPoints }}</td>
            <td>{{ userRanking.rankTitle }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore'; // userStore 사용
import { useAuthStore } from '@/stores/auth'; // 인증 스토어 사용

export default {
  setup() {
    const topRankings = ref([]); // 상위 5명의 랭킹 정보
    const userRanking = ref(null); // 로그인한 사용자의 랭킹 정보
    const authStore = useAuthStore(); // 인증 스토어 사용
    const { rankings, rank, fetchAllRankings, fetchUserRanking } = useUserStore(); // userStore에서 함수 가져오기

    // 로그인한 사용자가 rankings에 포함되었는지 확인하는 함수
    const isUserInRankings = (userId) => {
      return userRanking.value && userRanking.value.userId === userId;
    };

    // rankings에 로그인한 사용자가 포함되었는지 여부 확인
    const isUserInTopRankings = computed(() => {
      return topRankings.value.some(r => isUserInRankings(r.userId));
    });

    // 전체 랭킹 및 사용자 랭킹 가져오기
    const fetchAndSetRankings = async () => {
      try {
        // 전체 및 사용자 랭킹 가져오기
        await fetchAllRankings(); // rankings 상태를 갱신

        if (rankings.value) {
          topRankings.value = rankings.value; // 상태에 저장된 rankings 값 사용
        } else {
          console.error('랭킹 데이터를 가져올 수 없습니다.');
        }

        // 로그인한 사용자 정보가 있으면 사용자 랭킹 가져오기
        const token = authStore.getToken();
        if (token) {
          await fetchUserRanking(); // 토큰을 이용하여 사용자 랭킹 조회
          if (rank.value) {
            userRanking.value = rank.value; // 상태에 저장된 사용자 랭킹 값 사용
          }
        }
      } catch (error) {
        console.error('랭킹 데이터를 가져오는 중 오류 발생:', error);
      }
    };

    // 컴포넌트 마운트 시 랭킹 데이터를 가져옴
    onMounted(() => {
      fetchAndSetRankings();
    });

    return {
      topRankings,
      userRanking,
      isUserInRankings,
      isUserInTopRankings,
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

h2, h3 {
  margin-bottom: 15px;
}

.highlight {
  background-color: #e0f7fa; /* 로그인한 사용자 행의 배경색 변경 */
}
</style>
