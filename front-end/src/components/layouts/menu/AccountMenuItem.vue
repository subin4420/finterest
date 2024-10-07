<script setup>
import { computed, ref, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';

const props = defineProps({ username: String });
const auth = useAuthStore();

const avatarTimestamp = ref(Date.now());

const avatar = computed(() => `/api/users/${props.username}/avatar?t=${avatarTimestamp.value}`);

// auth 스토어의 avatarUpdated 값을 감시
watch(() => auth.avatarUpdated, () => {
  avatarTimestamp.value = Date.now();
});
</script>

<template>
  <li class="nav-item">
    <router-link class="nav-link" to="/auth/profile">
      <img :src="avatar" class="avatar avatar-sm" />
      {{ username }}
    </router-link>
  </li>
</template>
<style></style>
