<template>
  <div class="community-page">
    <h1>커뮤니티 페이지</h1>
    <p>여기는 커뮤니티 페이지입니다.</p>
  </div>
</template>

<script>
export default {
  name: 'CommunityPage',
};
</script>

<style scoped>
.community-page {
  /* 배경색과 패딩 추가 */
  background-color: #f0f0f0; /* 밝은 회색 배경 */
  padding: 20px; /* 내부 여백 */
  border-radius: 8px; /* 모서리 둥글게 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  text-align: center; /* 텍스트 중앙 정렬 */
}

h1 {
  color: #333; /* 글씨 색상 */
  font-size: 2.5em; /* 글씨 크기 */
  margin-bottom: 10px; /* 제목과 본문 간격 */
}

p {
  color: #555; /* 본문 글씨 색상 */
  font-size: 1.2em; /* 본문 글씨 크기 */
}
</style>
