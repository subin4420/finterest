<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">턴제 시뮬레이션 페이지</h1>
        <ScenarioList :initialConversions="scenarioConversions" />
        <ScenarioOrder />
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import ScenarioList from '@/components/trade/ScenarioList.vue';
import ScenarioOrder from '@/components/trade/ScenarioOrder.vue';
import { ref, onMounted } from 'vue';
// import { getAllScenarioConversions } from '@/api/scenarioApi.js';

export default {
  name: 'TurnBasedSimulationPage',
  components: {
    SideTradeNavigationBar,
    ScenarioList,
    ScenarioOrder,
  },

  setup() {
    const scenarioConversions = ref([]);

    onMounted(async () => {
      try {
        scenarioConversions.value = await getAllScenarioConversions();
      } catch (error) {
        console.error('시나리오 변환 정보를 가져오는데 실패했습니다:', error);
      }
    });

    return {
      scenarioConversions,
    };
  },
};
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
}

.trade-header {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.content-wrapper {
  display: flex;
  margin-top: 20px;
}

.content {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
}

.page-title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
}
</style>
