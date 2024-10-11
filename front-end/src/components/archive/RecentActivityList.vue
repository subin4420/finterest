<template>
  <div class="recent-activity-list">
    <div v-for="activity in activities" :key="activity.id" class="activity-item">
      <span class="activity-icon">{{ activityIcon(activity.type) }}</span>
      <span class="activity-text">{{ activity.text }}</span>
      <span class="activity-date">{{ formatDate(activity.date) }}</span>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'RecentActivityList',
  props: {
    activities: {
      type: Array,
      required: true
    }
  },
  methods: {
    activityIcon(type) {
      switch(type) {
        case 'study': return '📚';
        case 'complete': return '✅';
        case 'start': return '🚀';
        default: return '📌';
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString();
    }
  }
});
</script>

<style scoped>
.recent-activity-list {
  max-height: 300px;
  overflow-y: auto;
}
.activity-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
}
.activity-icon {
  margin-right: 10px;
  font-size: 1.2em;
}
.activity-text {
  flex-grow: 1;
}
.activity-date {
  font-size: 0.8em;
  color: #666;
}
</style>
