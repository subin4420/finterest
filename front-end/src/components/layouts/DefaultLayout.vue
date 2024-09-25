<script setup>
import { defineProps } from 'vue'; // props 정의 추가
import Header from './Header.vue';
import Footer from './Footer.vue';

const props = defineProps({
  backgroundColor: {
    type: String,
    default: '#ffffff' // 기본 색상 설정
  }
});
</script>

<template>
  <div class="layout-container">
    <Header :backgroundColor="backgroundColor" /> <!-- backgroundColor 속성 추가 -->
    <div class="content-container">
      <slot></slot>
    </div>
    <Footer />
  </div>
</template>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding-top: 60px; /* 헤더 높이에 맞춰 패딩 추가 */
}

.content-container {
  flex: 1;
  width: 100%; /* 전체 너비를 사용하도록 설정 */
  padding: 0; /* 패딩 제거 */
  margin: 0; /* 마진 제거 */
}

.navbar {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1030;
  background-color: backgroundColor; /* props로 색상 설정 */
  margin-bottom: 20px; /* 헤더 아래에 마진 추가 */
}
</style>