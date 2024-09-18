<template>
    <div>
      <h1>Profile Page</h1>
      <div v-if="user">
        <p>Name: {{ user.name }}</p>
        <p>Email: {{ user.email }}</p>
      </div>
      <div v-else>
        <p>Loading...</p>
      </div>
    </div>
  </template>
  
  <script>
  import { useUserStore } from '@/store/userStore';
  
  export default {
    setup() {
      const userStore = useUserStore();
      return { user: userStore.userInfo };
    },
  };
  </script>
  