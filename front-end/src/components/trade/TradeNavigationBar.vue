<template>
  <nav class="navbar">
    <ul class="nav-links">
      <li><router-link to="/trade">홈</router-link></li>
      <!-- /trade로 수정 -->
      <li><router-link to="/archive/ArchivePage">경제상식</router-link></li>
      <li><router-link to="/trade">투자</router-link></li>
      <!-- /trade로 수정 -->
      <li><router-link to="/trade/pointconversion">돈의 흐름</router-link></li>
      <li><router-link to="/trade/news">세계경제</router-link></li>
      <li><router-link to="/trade/simulatorstock">경제구조</router-link></li>
      <li><router-link to="/trade/community">기타</router-link></li>
      <li>
        <router-link to="/myLearning/MyLearningPage">즐겨찾기</router-link>
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
