<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute, RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';
import ChatBot from '@/components/ChatBot.vue';

// 현재 경로를 가져오는 Vue Router 기능
const route = useRoute();

// 상태 관리
const isChatBotOpen = ref(true);
// 라우트 변경 시 챗봇 닫기
watch(route, () => {
  isChatBotOpen.value = true;
});

// 챗봇 열기 메서드
const openChatBot = () => {
  isChatBotOpen.value = true;
};
</script>

<template>
  <DefaultLayout>
    <RouterView />
    <!-- 챗봇 버튼 (예: 이미지 버튼) -->
    <img
      v-if="!isChatBotOpen"
      src="@/assets/chatbot-icon.png"
      alt="Chatbot"
      class="chatbot-button"
      @click="openChatBot"
    />

    <!-- 챗봇 컴포넌트 -->
    <ChatBot v-if="isChatBotOpen" @close="isChatBotOpen = true" />
  </DefaultLayout>
</template>

<style scoped>
/* 챗봇 버튼 스타일 */
.chatbot-button {
  position: fixed;
  bottom: 10%; /* 화면 중간에 위치 */
  right: 20px; /* 오른쪽 여백 */
  transform: translateY(50%); /* 버튼을 수직 중앙으로 조정 */
  width: 60px; /* 이미지 크기 조정 */
  height: 80px; /* 이미지 크기 조정 */
  cursor: pointer;
}

.chatbot-button:hover {
  opacity: 0.8; /* 호버 시 투명도 조정 */
}
</style>
