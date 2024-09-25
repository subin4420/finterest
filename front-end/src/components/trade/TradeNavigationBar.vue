<template>
  <nav class="navbar">
    <ul class="nav-links">
      <li><router-link to="/trade">홈</router-link></li>
      <!-- /trade로 수정 -->
      <li><router-link to="/trade/stocksimulator">모의투자</router-link></li>
      <!-- /trade로 수정 -->
      <li><router-link to="/trade/portfolio">포트폴리오</router-link></li>
      <li><router-link to="/trade/news">뉴스</router-link></li>
      <li><router-link to="/trade/settlement">결산</router-link></li>
      <li>
        <router-link to="/trade/pointconversion">포인트 전환</router-link>
      </li>
      <li><router-link to="/trade/community">커뮤니티</router-link></li>
      <li>
        <router-link to="/trade/turnbasedsimulation">턴제 모의투자</router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import router from '@/router';

export default {
  name: 'NavigationBar',
};
</script>

<style scoped>
.navbar {
  background-color: black;
  color: white;
  display: flex;
  margin: 0px auto;
  justify-content: center; /* 가로 방향으로 중앙 정렬 */
}

.nav-links {
  list-style: none;
  display: flex;
  justify-content: space-between;
  padding: 0;
  width: 80%; /* 적당한 너비로 설정 */
  max-width: 1200px; /* 최대 너비 설정 */
}

.nav-links a {
  color: white;
  text-decoration: none;
  font-size: 16px; /* 기본 글자 크기 설정 */
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
  .nav-links a {
    font-size: 14px; /* 작은 화면에서 글자 크기 줄임 */
  }
}

@media (max-width: 480px) {
  .nav-links a {
    font-size: 12px; /* 더 작은 화면에서 글자 크기 줄임 */
  }
}
</style>
