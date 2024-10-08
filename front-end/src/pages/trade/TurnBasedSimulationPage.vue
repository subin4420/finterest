<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />
    <ScenarioList :initialConversions="scenarioConversions" />
    <ScenarioOrder />
    <!-- ScenarioOrder 컴포넌트 추가 -->
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import ScenarioList from '@/components/trade/ScenarioList.vue';
import ScenarioOrder from '@/components/trade/scenarioOrder.vue'; // 추가
import { ref, onMounted } from 'vue';
import { getAllScenarioConversions } from '@/api/scenarioApi.js';

export default {
  name: 'TurnBasedSimulationPage',
  components: {
    TradeImage,
    TradeNavigationBar,
    ScenarioList,
    ScenarioOrder, // 추가
  },

  setup() {
    const scenarioConversions = ref([]);

    onMounted(async () => {
      try {
        scenarioConversions.value = await getAllScenarioConversions();
      } catch (error) {
        console.error('Failed to fetch scenario conversions:', error);
      }
    });

    return {
      scenarioConversions,
    };
  },
};
</script>

<style>
.content-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 1rem;
}

.conversion-container {
  margin-top: 20px;
}

h2 {
  margin-bottom: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 5px;
}
</style>
