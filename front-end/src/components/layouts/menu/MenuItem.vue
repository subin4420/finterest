<script setup>
const props = defineProps({
  menu: { Type: Object, required: true },
});
</script>
<template>
  <li class="nav-item">
    <router-link class="nav-link" :to="menu.url">
      <i :class="menu.icon"></i>
      {{ menu.title }}
    </router-link>
  </li>
</template>
<style>
</style>
