<script setup>
import { ref, watch, computed } from 'vue';
import { useRoute } from 'vue-router';
import { RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';

const backgroundColor = ref('transparent'); // 기본 배경색을 투명으로 설정
const route = useRoute();

// 라우터의 경로에 따라 배경색을 변경하는 로직
watch(() => route.path, (newPath) => {
  if (newPath === '/'
  || newPath === '/archive/ArchivePage'  
  || newPath === '/quiz/QuizPage' 
  || newPath === '/trade/TradePage'
  ) {
    backgroundColor.value = 'transparent';
  } else {
    backgroundColor.value = '#00C4D1'; 
  }
}, { immediate: true });

// 헤더를 숨길 라우트 이름 배열
const hideHeaderRoutes = ['login', 'findpassword', 'join'];

// 헤더 표시 여부를 결정하는 computed 속성
const showHeader = computed(() => !hideHeaderRoutes.includes(route.name));
</script>

<template>
  <DefaultLayout v-if="showHeader" :backgroundColor="backgroundColor">
    <RouterView />
  </DefaultLayout>
  <RouterView v-else />
</template>

<style>
/* global.css 또는 App.vue의 style 섹션 */
body {
    font-family: 'Noto Sans KR', sans-serif;
}
</style>
