<template>
  <div class="container">
    <div class="sidebar">
      <div class="option" data-category="archive">아카이브 이력</div>
      <div class="option" data-category="quizzes">퀴즈 이력</div>
      <div class="option" data-category="inquiries">문의사항 이력</div>
      <div class="option" data-category="points">포인트 이력</div>
    </div>
    <div class="content-area">
      <div class="header">
        <div class="title">아카이브 이력</div>
        <div class="filters">
          <button class="filter-btn active">진행중인 학습</button>
          <button class="filter-btn">종료된 학습</button>
        </div>
      </div>
      <hr />
      <!-- 헤더 내부의 수평선 -->
      <div class="grid">
        <!-- 각 카드는 학습 아카이브의 내용을 보여줌 -->
        <div class="card">
          <div class="content">카드1</div>
        </div>
        <div class="card">
          <div class="content">카드2</div>
        </div>
        <div class="card">
          <div class="content">카드3</div>
        </div>
        <!-- 추가 카드 -->
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 기본 스타일 */
.container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  /* 뷰포트의 전체 높이 */
  color: #333;
  background: #f9f9f9;
}

.sidebar {
  width: 100%;
  height: auto;
  /* 자동 높이 설정 */
  background: #fff;
  display: flex;
  flex-direction: row;
  /* 모바일에서는 수평 스크롤 */
  overflow-x: auto;
  /* 필요한 경우 수평 스크롤 표시 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.option {
  flex: 1;
  padding: 15px;
  cursor: pointer;
  text-align: center;
  border-right: 1px solid #e9ecef;
}

.option:last-child {
  border-right: none;
}

.option.active {
  background: #e9ecef;
}

.content-area {
  flex-grow: 1;
  padding: 20px;
}

.header {
  display: flex;
  flex-direction: column;
  /* 요소들을 세로로 나열 */
  align-items: flex-start;
  /* 왼쪽 정렬 */
  width: 100%;
  /* header의 너비를 부모 요소에 꽉 차게 설정 */
}

.title {
  color: #000;
  font-weight: bold;
  margin-bottom: 10px;
  /* 제목과 구분선 사이의 간격 */
}

hr {
  width: 100%;
  /* 구분선 너비를 header 너비에 맞춤 */
  border: 0;
  height: 1px;
  background-color: #ccc;
  /* 구분선 색상 */
  margin-bottom: 10px;
  /* 구분선과 필터 사이의 간격 */
}

.filters {
  display: flex;
  justify-content: flex-start;
  /* 필터 요소들을 왼쪽으로 정렬 */
  width: 100%;
  /* 필터의 너비를 부모 요소에 꽉 차게 설정 */
}

.filter-btn {
  padding: 10px;
  margin-left: 10px;
  cursor: pointer;
  background-color: #e9ecef;
  border: none;
}

.filter-btn.active {
  background-color: #00c4d1;
  color: white;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100%, 1fr));
  /* 모바일 뷰에서는 1열로 표시 */
  gap: 15px;
}

.card {
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* 미디어 쿼리를 사용한 반응형 디자인 */
@media (min-width: 768px) {
  .container {
    flex-direction: row;
  }

  .sidebar {
    width: 200px;
    /* 데스크탑에서는 고정 폭 */
    height: auto;
    /* 자동 높이 */
    flex-direction: column;
    /* 세로 배열 */
    overflow-y: auto;
    /* 필요한 경우 수직 스크롤 */
    overflow-x: hidden;
    /* 수평 스크롤 제거 */
  }

  .option {
    text-align: left;
    padding: 20px;
    border-right: none;
    border-bottom: 1px solid #e9ecef;
  }

  .option:last-child {
    border-bottom: none;
  }

  .grid {
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    /* 데스크탑과 태블릿에서는 여러 열로 표시 */
  }

  .header .title {
    font-size: 30px;
    /* 데스크탑에서의 폰트 크기 */
  }
}
</style>
