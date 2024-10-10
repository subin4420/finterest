<script setup>
import { reactive, computed } from 'vue';
import config from '@/config';
import MenuGroup from './menu/MenuGroup.vue';
import AccountMenuGroup from './menu/AccountMenuGroup.vue';
import logoTransparentImage from '../../assets/images/logo/logo_transparent1.png'; // 상대 경로 사용

let state = reactive({ isNavShow: false });

let navClass = computed(() => (state.isNavShow ? 'collapse navbar-collapse show' : 'collapse navbar-collapse'));

const toggleNavShow = () => (state.isNavShow = !state.isNavShow);
</script>

<template>
  <nav class="navbar navbar-expand-sm navbar-dark">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">
        <img :src="logoTransparentImage" alt="Logo" class="logo-image">
        <span class="header-title">{{ config.title }}</span>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar"
        @click="toggleNavShow">
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
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1030;
  background-color: #00C4D1;
  /* 고정된 배경색 설정 */
  padding: 10px 15px;
  border-bottom: 1px solid rgba(240, 240, 240, 0.4);
  height: 60px;
}

.container-fluid {
  margin: 0 20px;
}

.logo-image {
  height: 25px;
  width: auto;
  margin-right: 5px;
  margin-left: 20px;
}

.header-title {
  font-size: 20px;
  font-weight: bold;
  color: #fff;
  letter-spacing: 1px;
}

.navbar-brand {
  display: flex;
  align-items: center;
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
  .navbar {
    padding: 10px 15px;
  }

  .container-fluid {
    margin: 0 10px;
  }

  .logo-image {
    height: 25px;
    margin-right: 5px;
  }

  .header-title {
    font-size: 16px;
  }
}
</style>
