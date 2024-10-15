<template>
  <div class="modal" v-if="isVisible">
    <div class="modal-content">
      <div class="description">
        <p>{{ data.description }}</p>
      </div>
      <div class="button-container">
        <button class="next-button" @click="handleNext">확인</button>
      </div>
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
  z-index: 1000; /* 다른 원소 위에 표시 */
}

.modal-content {
  padding: 20px;
  border-radius: 10px;
  width: 400px; /* 모달 폭 조정 */
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 내용과 버튼 간 공간 포함 */
  background-color: transparent;
}

.description {
  margin-bottom: 20px;
  font-size: 1.1rem;
  line-height: 1.5; /* 줄 간격 조정 */
  text-align: left; /* 텍스트 정렬 */
}

.button-container {
  display: flex;
  justify-content: center; /* 버튼 중심 정렬 */
  margin-top: 20px; /* 버튼과 내용간 공간 포함 */
}

.next-button {
  padding: 10px 15px;
  border: none;
  border-radius: 15px;
  background-color: black; /* 버튼 배경색 */
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.next-button:hover {
  background-color: #444; /* 버튼 호버 시 색상 변경 */
}

/* 반응형 디자인 */
@media (max-width: 480px) {
  .modal-content {
    width: 90%; /* 작은 화면에서 폭 조정 */
  }
}
</style>
