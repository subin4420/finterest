<template>
  <nav class="navbar">
    <ul class="nav-links">
      <li><router-link to="/trade" exact>홈</router-link></li>
      <li><router-link to="/trade/stocksimulator">모의투자</router-link></li>
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
export default {
  name: 'NavigationBar',
};
</script>

<style scoped>
.navbar {
  background-color: black;
  color: white;
  display: flex;
  justify-content: center; /* 가로 방향으로 중앙 정렬 */
  align-items: center; /* 수직 중앙 정렬 */
  max-width: 800px; /* 최대 너비 설정 */
  margin: -20px auto 10px auto; /* 상단 마진 -20px, 좌우 중앙 정렬 */
  border-radius: 5px; /* 둥근 모서리 추가 */
  padding: 10px; /* 패딩 추가하여 내용과 모서리 간격 조정 */
}

.nav-links {
  list-style: none;
  display: flex;
  justify-content: space-between;
  padding: 0;
  width: 90%; /* 적당한 너비로 설정 */
  max-width: 1200px; /* 최대 너비 설정 */
}

.nav-links li {
  position: relative; /* 자식 요소의 위치를 기준으로 설정 */
}

.nav-links a {
  color: white;
  text-decoration: none;
  font-size: 16px; /* 기본 글자 크기 설정 */
  padding: 5px 10px; /* 패딩 추가 */
}

.nav-links a.router-link-exact-active {
  color: #337ce5; /* 선택된 항목의 글씨 색상 변경 */
  border-bottom: 2px solid #337ce5; /* 밑줄 추가 */
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
