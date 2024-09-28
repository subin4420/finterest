<script setup>
import { RouterView } from 'vue-router';
import DefaultLayout from './components/layouts/DefaultLayout.vue';
</script>

<template>
  <DefaultLayout>
    <RouterView />
  </DefaultLayout>
</template>

<style >
/* global.css 또는 App.vue의 style 섹션 */
body {
    font-family: 'Noto Sans KR', sans-serif;
}
</style>
