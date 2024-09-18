<template>
    <button :class="['btn', size]" @click="onClick">
      <slot></slot>
    </button>
  </template>
  
  <script>
  export default {
    props: {
      size: {
        type: String,
        default: 'medium',
      },
    },
    methods: {
      onClick() {
        this.$emit('click');
      },
    },
  };
  </script>
  
  <style>
  .btn {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  .medium {
    font-size: 16px;
  }
  </style>
  