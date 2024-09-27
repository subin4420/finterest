<script setup>
import { reactive, computed, defineProps } from 'vue';
import config from '@/config';
import MenuGroup from './menu/MenuGroup.vue';
import AccountMenuGroup from './menu/AccountMenuGroup.vue';
import logoImage from '../../assets/images/logo.png'; // 상대 경로 사용

const props = defineProps({
  backgroundColor: {
    type: String,
    default: 'transparent' // 기본값은 투명
  }
});

let state = reactive({ isNavShow: false });

let navClass = computed(() => (state.isNavShow ? 'collapse navbar-collapse show' : 'collapse navbar-collapse'));

const toggleNavShow = () => (state.isNavShow = !state.isNavShow);
</script>

<template>
  <nav class="navbar navbar-expand-sm navbar-dark">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">
        <img :src="logoImage" alt="Logo" class="logo-image">
        <span class="header-title">{{ config.title }}</span>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar" @click="toggleNavShow">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div :class="navClass" id="collapsibleNavbar">
        <MenuGroup :menus="config.menus" />
        <AccountMenuGroup />
      </div>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1030;
  background-color: backgroundColor; /* props로 색상 설정 */
}

.logo-image {
  height: 30px;
  width: auto;
  margin-right: 10px;
}

.header-title {
  font-size: 24px; /* 원하는 크기로 조정 */
  font-weight: bold;
  color: #fff; /* 원하는 색상으로 변경 */
  letter-spacing: 1px; /* 자간 조정 (선택사항) */
}

</style>
