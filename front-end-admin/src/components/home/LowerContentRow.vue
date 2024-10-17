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
    <!-- 아래 표 2개 -->
  <div class="row">
    <div class="col-md-6">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">사용자 정보</h5>
          <table class="table table-striped">
            <thead>
              <tr>
                <th>사용자 ID</th>
                <th>이메일</th>
                <th>실명</th>
                <th>역할</th>
                <th>계정 상태</th>
                <th>보유 금액</th>
                <th>누적 포인트</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>user01</td>
                <td>user01@example.com</td>
                <td>김유정</td>
                <td>admin</td>
                <td>active</td>
                <td>10,000,000</td>
                <td>100</td>
                <td><button class="btn btn-sm btn-primary">수정</button> <button class="btn btn-sm btn-danger">삭제</button></td>
              </tr>
              <!-- 추가 행 -->
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">포인트 정보</h5>
          <table class="table table-striped">
            <thead>
              <tr>
                <th>포인트 ID</th>
                <th>사용자 ID</th>
                <th>포인트 획득 활동</th>
                <th>획득 포인트</th>
                <th>날짜</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>123</td>
                <td>회원가입</td>
                <td>1000</td>
                <td>2023-09-12</td>
              </tr>
              <!-- 추가 행 -->
            </tbody>
          </table>
        </div>
      </div>
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
