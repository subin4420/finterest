<template>
  <div v-if="isVisible" class="explain-modal">
    <div class="modal-content">
      <h2>{{ currentContent.title }}</h2>
      <p>{{ currentContent.content }}</p>
      <button @click="handleNext" class="next-button">다음</button>
      <p v-if="showChartMessage" class="chart-message">{{ chartMessage }}</p>
      <!-- 차트 메시지 추가 -->
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';

const props = defineProps({
  isVisible: Boolean,
  contents: Array, // 여러 내용 배열을 받음
  onClose: Function,
});

const currentIndex = ref(0); // 현재 인덱스
const showChartMessage = ref(false); // 차트 메시지 표시 상태
const chartMessage = '이곳은 주식의 가격 변동을 확인 할 수 있는 차트입니다. '; // 차트 메시지 내용

const currentContent = ref(props.contents[currentIndex.value]); // 현재 내용

const handleNext = () => {
  if (currentIndex.value < props.contents.length - 1) {
    currentIndex.value++;
    currentContent.value = props.contents[currentIndex.value]; // 다음 내용으로 업데이트
  } else {
    showChartMessage.value = true; // 마지막 내용에서 차트 메시지 표시
  }
};

const closeModal = () => {
  props.onClose(); // 부모 컴포넌트의 닫기 핸들러 호출
};
</script>

<style>
.explain-modal {
  background-color: rgba(0, 0, 0, 0.7); /* 배경을 더 어둡게 */
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 30px; /* 패딩을 늘려서 여유 공간 추가 */
  border-radius: 12px; /* 모서리를 더 둥글게 */
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2); /* 그림자 추가 */
  width: 80%; /* 가로 크기 조정 */
  max-width: 500px; /* 최대 가로 크기 설정 */
}

.next-button {
  background-color: #007bff; /* 버튼 색상 변경 */
  color: white; /* 버튼 텍스트 색상 */
  border: none; /* 테두리 제거 */
  padding: 10px 20px; /* 버튼 패딩 */
  border-radius: 5px; /* 버튼 모서리 둥글게 */
  cursor: pointer; /* 커서 변경 */
  transition: background-color 0.3s; /* 배경색 전환 효과 */
}

.next-button:hover {
  background-color: #0056b3; /* 호버 시 색상 변경 */
}

.chart-message {
  margin-top: 20px; /* 메시지와 버튼 간격 */
  font-size: 1rem; /* 메시지 폰트 크기 */
  color: #333; /* 메시지 텍스트 색상 */
  font-weight: bold; /* 메시지 텍스트 두껍게 */
}
</style>
