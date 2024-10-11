<script setup>
import { ref, watch, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';

// 배경색 관리
const backgroundColor = ref('transparent'); // 기본 배경색을 투명으로 설정
const route = useRoute();

// trade 경로를 체크하는 함수
const isTradePath = (path) => /^\/trade($|\/)/.test(path);

// 라우터의 경로에 따라 배경색을 변경하는 로직
watch(
  () => route.path,
  (newPath) => {
    if (
      newPath === '/' ||
      newPath === '/archive/ArchivePage' ||
      newPath === '/quiz/QuizPage' ||
      isTradePath(newPath)
    ) {
      backgroundColor.value = 'transparent';
    } else {
      backgroundColor.value = '#00C4D1';
    }
  },
  { immediate: true }
);

// 헤더를 숨길 라우트 이름
const hideHeaderRoutes = ['login', 'findpassword', 'join'];

// 헤더 표시 여부를 결정하는 computed 속성
const showHeader = computed(() => !hideHeaderRoutes.includes(route.name));

// 챗봇 상태 관리
const isChatBotOpen = ref(false);

// 라우트 변경 시 챗봇 닫기
watch(route, () => {
  isChatBotOpen.value = false;
});

// 챗봇 열기 메서드
const openChatBot = () => {
  const chatbotBubble = document.getElementById('dify-chatbot-bubble-button');
  if (chatbotBubble) {
    chatbotBubble.click();
  } else {
    // 챗봇 스크립트가 로드되지 않았을 경우 스크립트를 추가하고 클릭
    loadDifyChatbotScript(() => {
      const loadedBubble = document.getElementById(
        'dify-chatbot-bubble-button'
      );
      if (loadedBubble) {
        loadedBubble.click();
      }
    });
  }
};

// Dify 챗봇 스크립트 설정 및 로드
const loadDifyChatbotScript = (callback) => {
  if (!document.getElementById('PkEAZiqlpOOppUXB')) {
    window.difyChatbotConfig = {
      token: 'PkEAZiqlpOOppUXB',
    };
    const script = document.createElement('script');
    script.src = 'https://udify.app/embed.min.js';
    script.id = 'PkEAZiqlpOOppUXB';
    script.defer = true;
    script.onload = callback;
    document.body.appendChild(script);
  } else if (callback) {
    callback();
  }
};

onMounted(() => {
  loadDifyChatbotScript();
});
</script>

<template>
  <DefaultLayout v-if="showHeader" :backgroundColor="backgroundColor">
    <RouterView />
    <!-- Dify 챗봇 설정 및 스크립트 로드 -->

    <!-- 챗봇 버튼 -->
    <div id="custom-chatbot-button" @click="openChatBot"></div>
  </DefaultLayout>
  <RouterView v-else />
</template>

<style scoped>
/* 글로벌 스타일 */
body {
  font-family: 'Noto Sans KR', sans-serif;
}

/* 챗봇 버튼 스타일 */
#custom-chatbot-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #1c64f2;
  color: white;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  z-index: 1000;
}

/* Dify 챗봇 버튼 스타일 */
#dify-chatbot-bubble-button {
  background-color: #1c64f2 !important;
}
</style>
