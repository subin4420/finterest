<script setup>
import { ref, onMounted } from 'vue';
import { getArchive } from '@/services/archiveService'; // getArchive 함수 가져오기

const topLearning_Materials = ref([]); // 초기값 설정

// 학습 자료를 가져오는 함수
const fetchTopLearningMaterials = async () => {
  try {
    const data = await getArchive(); // API 호출하여 데이터 가져오기
    console.log('Fetched data:', data); // 가져온 데이터 출력
    topLearning_Materials.value = data.archives; // 가져온 데이터를 topLearning_Materials에 저장
  } catch (error) {
    console.error('Error fetching top learning materials:', error); // 에러 처리
  }
};

// 컴포넌트가 마운트될 때 fetchTopLearningMaterials 호출
onMounted(() => {
  fetchTopLearningMaterials();
});
</script>

<template>
  <section class="lower-content-row">
    <h2>finterest 최고 인기 TOP 3</h2>
    <div class="material-list">
      <div v-for="material in topLearning_Materials.slice(0, 3)" :key="material.id" class="material-card">
        <img :src="material.materialImg || defaultImage" alt="썸네일 이미지" class="material-image"/>

        <h3>{{ material.title }}</h3>
        <p class="material-category"> [{{ material.categoryName }}]</p>
        <div class="material-stats">
          <!-- 추가적인 통계 정보가 필요하다면 여기에 추가 -->
        </div>
        <p class="material-tags">
          <span v-for="tag in material.tags" :key="tag">{{ tag }}</span>
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.lower-content-row {
  padding: 2rem;
  background-color: #fff;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.material-list {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.material-card {
  flex: 1;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.material-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

h3 {
  font-size: 1.2rem;
  margin: 1rem;
}

.material-category {
  font-size: 0.9rem;
  color: #666;
  margin: 0 1rem;
}

.material-stats {
  display: flex;
  justify-content: space-between;
  margin: 1rem;
  font-size: 0.9rem;
}

.material-stats span {
  display: flex;
  align-items: center;
}

.material-stats img {
  width: 16px;
  height: 16px;
  margin-right: 0.5rem;
}

.material-tags {
  margin: 1rem;
}

.material-tags span {
  display: inline-block;
  background-color: #f0f0f0;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.8rem;
  margin-right: 0.5rem;
  margin-bottom: 0.5rem;
}
</style>
