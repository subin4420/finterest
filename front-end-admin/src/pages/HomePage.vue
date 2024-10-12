<script setup>
import UpperContentRow from '@/components/home/UpperContentRow.vue';
import MiddleContentRow from '@/components/home/MiddleContentRow.vue';
import LowerContentRow from '@/components/home/LowerContentRow.vue';

</script>


<template>
  <div class="home-page">
    <UpperContentRow />
    <MiddleContentRow/>
    <LowerContentRow />
    <!-- 다른 섹션들을 여기에 추가할 수 있습니다 -->
  </div>
</template>

<style scoped>
.home-page {
  .home-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f5f5f5;
}
}
</style>