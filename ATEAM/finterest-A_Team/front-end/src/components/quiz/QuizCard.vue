<template>
  <div class="content-card" @click="$emit('click', cardData)">
    <div class="image-wrapper">
      <img :src="cardData.setImg || defaultImage" alt="썸네일 이미지" />
    </div>
    <div class="card-info">
      <div class="category-title">[{{ cardData.categoryName }}]</div>
      <div class="title">{{ cardData.setName }}</div>
      <p class="summary">{{ cardData.description }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuizCard',
  props: {
    cardData: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      defaultImage:
        'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg', // 대체 이미지 URL
    };
  },
};
</script>

<style scoped>
.content-card {
  width: 300px; /* 기본 너비 */
  height: auto; /* 높이를 자동으로 조정 */
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  background-color: #ffffff; /* 배경색을 흰색으로 변경 */
  padding: 15px; /* 패딩을 약간 늘림 */
  border-radius: 8px; /* 모서리를 둥글게 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  transition: transform 0.2s; /* 호버 시 부드러운 효과 */
}

.content-card:hover {
  transform: scale(1.05); /* 호버 시 카드 확대 효과 */
}

.image-wrapper {
  width: 100%;
  height: 120px; /* 고정된 높이 */
  background-color: #f0f0f0; /* 배경색을 연한 회색으로 변경 */
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px; /* 모서리를 둥글게 */
  overflow: hidden; /* 이미지가 영역을 넘지 않도록 설정 */
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s; /* 이미지에 부드러운 효과 추가 */
}

.image-wrapper img:hover {
  transform: scale(1.1); /* 이미지 호버 시 확대 효과 */
}

.card-info {
  padding-top: 10px;
  text-align: left;
  width: 100%;
  flex-grow: 1; /* 공간을 차지하도록 설정 */
}

.category-title {
  font-size: 14px;
  color: #888;
}

.title {
  font-weight: bold;
  font-size: 16px; /* 기본 글자 크기 */
  margin-top: 5px;
  white-space: nowrap; /* 한 줄로 표시 */
  overflow: hidden; /* 넘치는 내용 숨기기 */
  text-overflow: ellipsis; /* 넘치는 내용에 ... 표시 */
  width: 100%; /* 너비를 100%로 설정 */
}

.summary {
  margin-top: 5px;
  font-size: 12px; /* 기본 글자 크기 */
  color: #666;
  white-space: nowrap; /* 한 줄로 표시 */
  overflow: hidden; /* 넘치는 내용 숨기기 */
  text-overflow: ellipsis; /* 넘치는 내용에 ... 표시 */
  width: 100%; /* 너비를 100%로 설정 */
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
  .content-card {
    width: 90%; /* 작은 화면에서 카드 너비를 90%로 설정 */
    margin: 0 auto; /* 중앙 정렬 */
  }

  .image-wrapper {
    height: 100px; /* 작은 화면에서 이미지 높이 조정 */
  }

  .title {
    font-size: 14px; /* 작은 화면에서 글자 크기 줄임 */
  }

  .summary {
    font-size: 10px; /* 작은 화면에서 글자 크기 줄임 */
  }
}
</style>
