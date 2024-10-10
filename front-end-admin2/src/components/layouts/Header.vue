<script setup>
import { reactive, computed, defineProps } from 'vue';
import config from '@/config';
import MenuGroup from './menu/MenuGroup.vue';
import AccountMenuGroup from './menu/AccountMenuGroup.vue';
import logoTransparentImage from '../../assets/images/logo/logo_transparent1.png'; // 상대 경로 사용
import logoImage from '../../assets/images/logo/logo.png'; // 상대 경로 사용

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
  <nav class="navbar navbar-expand-sm navbar-dark" :style="{ backgroundColor: props.backgroundColor }">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">
        <img :src="logoTransparentImage" alt="Logo" class="logo-image">
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
  position: fixed; /* 절대 위치에서 고정 위치로 변경 */
  top: 0;
  left: 0;
  right: 0;
  z-index: 1030;
  background-color: backgroundColor; /* props로 색상 설정 */
  padding: 10px 15px; /* 패딩 조정 */
  border-bottom: 1px solid rgba(240, 240, 240, 0.4); /* 연한 회색 */  
  height: 60px; /* 고정 높이 설정 */
}

.container-fluid {
  margin: 0 20px; /* 좌우 20px의 마진 추가 */
}

.logo-image {
  height: 25px; /* 40px에서 30px로 변경 */
  width: auto;
  margin-right: 5px; /* 오른쪽 마진 추가 */
  margin-left: 20px; /* 왼쪽 마진 추가 */
}

.header-title {
  font-size: 20px; /* 20px에서 18px로 변경 */
  font-weight: bold;
  color: #fff; /* 원하는 색상으로 변경 */
  letter-spacing: 1px; /* 자간 조정 (선택사항) */
}

.navbar-brand {
  display: flex;
  align-items: center;
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
  .navbar {
    padding: 10px 15px; /* 작은 화면에서 패딩 조정 */
  }

  .container-fluid {
    margin: 0 10px; /* 작은 화면에서 마진 조정 */
  }

  .logo-image {
    height: 25px; /* 작은 화면에서 더 작게 조정 */
    margin-right: 5px; /* 작은 화면에서 마진 조정 */
  }

  .header-title {
    font-size: 16px; /* 작은 화면에서 폰트 크기 조정 */
  }
}

</style>
