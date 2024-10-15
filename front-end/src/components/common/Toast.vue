<!-- src/components/common/Toast.vue -->
<template>
  <transition name="toast">
    <div v-if="show" class="toast">
      {{ message }}
    </div>
  </transition>
</template>

<script>
export default {
  props: ['show', 'message']
}
</script>

<style scoped>
.toast {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #00c4d1;
  color: white;
  padding: 16px;
  border-radius: 4px;
  z-index: 1002;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.toast-enter-active, .toast-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}
.toast-enter, .toast-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(20px);
}
</style>
