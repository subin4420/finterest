<script setup>
import { ref, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore'; // 스토어 이름 확인

const { todaySignupCount, activeUserCount, totalQuizCount, totalArchiveCount, fetchDashboard } = useUserStore(); // 스토어에서 todaySignUp 가져오기

// 컴포넌트가 마운트되면 오늘 가입자 수를 가져오는 함수 호출
onMounted(() => {
  fetchDashboard().then(() => {
    console.log('Fetched todaySignUp:', todaySignupCount.value);
    console.log('Fetched todaySignUp:', totalQuizCount.value);
    console.log('Fetched todaySignUp:', totalArchiveCount.value);
  });
});
</script>

<template>
  <section class="content-row">
    <!-- 위 카드 4개 -->
    <div class="row mb-4">
      <div class="col-md-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">오늘 가입자</h5>
            <p class="card-text">{{ todaySignupCount }}명</p> <!-- todaySignUp 수 표시 -->
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">활성 사용자 수</h5>
            <p class="card-text">{{ activeUserCount }}명</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">등록된 퀴즈</h5>
            <p class="card-text">{{ totalQuizCount }}개</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">등록된 용어사전</h5>
            <p class="card-text">{{ totalArchiveCount }}개</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>



<style scoped>
.content-row {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  padding: 2rem;
  background-color: #fff;
  min-height: 300px; /* 최소 높이 설정 */
}

.row {
  width: 100%;
  max-width: 1200px; /* 최대 너비 설정 */
  display: flex;
  justify-content: space-around; /* 카드 간의 간격을 균등하게 설정 */
}

.col-md-3 {
  flex: 1;
  margin: 0 10px; /* 카드 간의 간격을 조정 */
}

.card {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
