<template>
    <div>
      <h1>Welcome to the Home Page</h1>
      <Button size="medium" @click="goToProfile">Go to Profile</Button>
    </div>
  </template>
  
  <script>
  import Button from '@/components/common/Button.vue';
  
  export default {
    components: { Button },
    methods: {
      goToProfile() {
        this.$router.push('/profile');
      },
    },
  };
  </script >
  