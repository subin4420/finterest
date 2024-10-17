<template>
    <div class="progress-container">
      <div class="progress-bar" :style="{ width: `${percentage}%` }">
        <span class="progress-text">{{ percentage }}%</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ProgressBar',
    props: {
      value: {
        type: Number,
        required: true
      },
      max: {
        type: Number,
        default: 100
      }
    },
    computed: {
      percentage() {
        return Math.round((this.value / this.max) * 100);
      }
    }
  }
  </script>
  
  <style scoped>
  .progress-container {
    width: 100%;
    background-color: #e0e0e0;
    border-radius: 10px;
    overflow: hidden;
  }
  
  .progress-bar {
    height: 20px;
    background-color: #00c4d1;
    transition: width 0.5s ease-in-out;
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }
  
  .progress-text {
    color: white;
    padding-right: 5px;
    font-size: 12px;
    font-weight: bold;
  }
  </style>