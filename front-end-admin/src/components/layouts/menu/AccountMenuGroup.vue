<script setup>
import MenuItem from './MenuItem.vue';
import AccountMenuItem from './AccountMenuItem.vue';
import config from '@/config';
import { useAuthStore } from '@/stores/auth';
import LogoutMenuItem from './LogoutMenuItem.vue';
import { computed } from 'vue';

const { login, myLearning, news } = config.accoutMenus;
const auth = useAuthStore();

const islogin = computed(() => auth.isLogin);
const username = computed(() => auth.username);
</script>

<template>
  <ul class="navbar-nav ms-auto">
    
    <template v-if="islogin">
      <AccountMenuItem :username="username" />
      <LogoutMenuItem />
    </template>
    <template v-else>
      <MenuItem :menu="login" />
    </template>
  </ul>
</template>
