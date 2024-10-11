<template>
  <div class="modal" v-if="isVisible">
    <div class="modal-content">
      <h2>턴 정보</h2>
      <div class="description">
        <p>{{ data.description }}</p>
      </div>
      <button class="next-button" @click="handleNext">다음</button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

// props 정의
const props = defineProps({
  isVisible: {
    type: Boolean,
    required: true,
  },
  data: {
    type: Object,
    required: true,
  },
});

// emit 정의
const emit = defineEmits(['next', 'close']); // 'close' 이벤트 추가

const handleNext = () => {
  emit('next'); // 'next' 이벤트를 emit
  emit('close'); // 'close' 이벤트를 emit하여 모달 닫기
};
</script>

<style scoped>
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7); /* 다크 배경 */
  z-index: 1000; /* 다른 요소 위에 표시 */
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 400px; /* 모달 폭 조정 */
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 내용과 버튼 간 공간 확보 */
}

.description {
  margin-top: 20px;
  font-size: 1.1rem;
  line-height: 1.5; /* 줄 간격 조정 */
  color: #333; /* 텍스트 색상 */
  text-align: left; /* 텍스트 정렬 */
}

.next-button {
  margin-top: auto; /* 버튼을 아래로 밀어내어 위치 조정 */
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: #2e78e0; /* 버튼 배경색 */
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s; /* 호버 효과 애니메이션 */
}

.next-button:hover {
  background-color: #1d5bbf; /* 버튼 호버 시 색상 변경 */
}

/* 반응형 디자인 */
@media (max-width: 480px) {
  .modal-content {
    width: 90%; /* 작은 화면에서 폭 조정 */
  }
}
</style>
