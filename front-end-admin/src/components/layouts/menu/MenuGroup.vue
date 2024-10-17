<script setup>
import MenuItem from './MenuItem.vue';
const props = defineProps({
  menus: { Type: Array, required: true },
});
</script>

<template>
  <ul class="navbar-nav">
    <MenuItem v-for="menu in menus" :menu="menu" />
  </ul>
</template>
