<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';

const route = useRoute();

// 헤더를 숨길 라우트 이름
const hideHeaderRoutes = ['login', 'findpassword', 'join'];

// 헤더 표시 여부를 결정하는 computed 속성
const showHeader = computed(() => !hideHeaderRoutes.includes(route.name));
</script>

<template>
  <DefaultLayout v-if="showHeader">
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
