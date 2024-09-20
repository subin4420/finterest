<template>
  <div id="app">
    <NavigationBar />
    <div class="content-grid">
      <AchieveCard v-for="achieve in achieves" :key="achieve.materialId" :cardData="achieve"
        @click.native="openModal(achieve)" />
    </div>
    <AchieveModal :isVisible="isModalVisible" :cardData="selectedCard" @update:isVisible="isModalVisible = $event" />
  </div>
</template>

<script>
import NavigationBar from '@/components/common/AchieveNavigationBar.vue';
import AchieveCard from '@/components/common/AchieveCard.vue';
import AchieveModal from '@/components/common/AchieveModal.vue';
import { onMounted, ref } from "vue";
import { useAchieveStore } from "@/store/achieveStore";

export default {
  name: 'App',
  components: {
    NavigationBar,
    AchieveCard,
    AchieveModal
  },
  setup() {
    const achieveStore = useAchieveStore();
    const isModalVisible = ref(false);
    const selectedCard = ref({});

    onMounted(() => {
      achieveStore.fetchAchieve();
    });

    const achieves = achieveStore.achieves;

    function openModal(achieve) {
      selectedCard.value = achieve;
      isModalVisible.value = true;
    }

    return { achieves, isModalVisible, selectedCard, openModal };
  }
}
</script>

<style>
.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}
</style>
