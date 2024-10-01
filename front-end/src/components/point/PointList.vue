<template>
  <div>
    <!-- 포인트 테이블 -->
    <table class="table table-hover">
      <thead style="background-color: #f8f9fa; color: black;">
        <tr>
          <th scope="col">#</th>
          <th scope="col">활동 이름</th>
          <th scope="col">포인트</th>
          <th scope="col">날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(point, index) in filteredPoints" :key="point?.pointId || index">
          <td scope="row">{{ index + 1 }}</td>
          <td>{{ point?.activityName || 'N/A' }}</td>
          <td :class="point?.pointsChange > 0 ? 'text-success' : 'text-danger'">
            {{ point?.pointsChange > 0 ? '+' : '' }}{{ point?.pointsChange || 0 }}
          </td>
          <td>{{ point?.createdAt || 'N/A' }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import { usePointStore } from '@/stores/pointStore';

export default {
  name: 'PointHistoryList',
  props: {
    pointStatus: {
      type: String,
      required: true
    }
  },
  setup(props) {
    const pointStore = usePointStore();
    const points = pointStore.points;

    onMounted(async () => {
      await pointStore.fetchAllPoints();
      console.log("Fetched points data:", points.value); // 데이터 출력 (points.value로 접근)
    });

    // 포인트 상태에 따라 필터링된 포인트 반환
    const filteredPoints = computed(() => {
      if (props.pointStatus === 'earned') {
        return points.value.filter(point => point.pointsChange > 0); // 적립된 포인트 필터
      } else if (props.pointStatus === 'deducted') {
        return points.value.filter(point => point.pointsChange < 0); // 차감된 포인트 필터
      }
      return points.value; // 전체 포인트 반환
    });

    return {
      points,
      filteredPoints
    };
  }
};
</script>

<style>
.table > thead > tr > th {
  background-color: #e8e7e7; /* 옅은 회색 배경 */
  color: black; /* 검정색 글자 */
}
</style>
