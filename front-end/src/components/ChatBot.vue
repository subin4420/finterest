<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import DefaultLayout from '@/components/layouts/DefaultLayout.vue';

// 현재 경로를 가져오는 Vue Router 기능
const route = useRoute();

// 상태 관리
const isChatBotOpen = ref(false);
// 라우트 변경 시 챗봇 닫기
watch(route, () => {
  isChatBotOpen.value = false;
});

// 챗봇 열기 메서드
const openChatBot = () => {
  isChatBotOpen.value = true;
  const chatbotBubble = document.getElementById('dify-chatbot-bubble-button');
  if (chatbotBubble) {
    chatbotBubble.click();
  }
};

// Dify 챗봇 스크립트 설정 및 로드
onMounted(() => {
  if (!document.getElementById('PkEAZiqlpOOppUXB')) {
    window.difyChatbotConfig = {
      token: 'PkEAZiqlpOOppUXB'
    };
    const script = document.createElement('script');
    script.src = 'https://udify.app/embed.min.js';
    script.id = 'PkEAZiqlpOOppUXB';
    script.defer = true;
    document.body.appendChild(script);
  }
});
</script>



<template>
  <DefaultLayout>
    <RouterView />
    <!-- Dify 챗봇 설정 및 스크립트 로드 -->

    <!-- 챗봇 버튼 -->
    <div id="custom-chatbot-button" @click="openChatBot">
      Chat with us
    </div>

    <!-- Dify 챗봇 스크립트 설정 및 로드 -->

  </DefaultLayout>
</template>

<style scoped>
/* 챗봇 버튼 스타일 */
#custom-chatbot-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #1C64F2;
  color: white;
  padding: 10px 15px;
  border-radius: 50px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-size: 16px;
}

/* Dify 챗봇 버튼 스타일 */
#dify-chatbot-bubble-button {
  background-color: #1C64F2 !important;
}


</style>