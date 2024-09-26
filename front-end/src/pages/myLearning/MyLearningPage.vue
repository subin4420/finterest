<template>
  <div id="container">
    <div class="content-area">
      <!-- ... 기존 content-area 내용 ... -->
    </div>
  </div>
</template>

<script>

export default {
  name: 'MyLearningPage',
  components: {
    Sidebar
  }
}
</script>

<style scoped>
#container {
  display: flex;
  margin: 0;
  width: 100%;
  position: absolute;
  min-height: 100vh;
  color: #333;
  background-color: #f5f5f5; /* 밝은 회색 배경으로 변경 */
}
/* ... 나머지 스타일 유지 ... */

@media (min-width: 768px) {
  .container {
    flex-direction: row;
  }

  .sidebar {
    margin-top: 60px; /* 데스크톱 버전에서도 동일한 여백 유지 */
  }

  /* ... 나머지 미디어 쿼리 스타일 유지 ... */
}
</style>
