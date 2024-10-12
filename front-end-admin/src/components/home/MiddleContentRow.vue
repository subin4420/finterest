<template>
  <section class="middle-content-row">
    <!-- 가운데 차트 3개 -->
    <div class="row mb-4">
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Point Percent</h5>
            <p class="card-text">포인트 획득 비율에 따른 차트</p>
            <!-- 차트 영역 -->
            <canvas id="dailySalesChart"></canvas>
            <p class="text-muted mt-2">updated just now</p>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Archive</h5>
            <p class="card-text">주별/월별 학습 자료 조회 수</p>
            <!-- 주별/월별 버튼 및 연도/월 선택 -->
            <div class="mb-3 d-flex justify-content-center">
              <select v-model="selectedYear" class="form-select me-2" @change="updateChartData('archive')">
                <option v-for="year in availableYears" :key="year" :value="year">{{ year }}</option>
              </select>
              <select v-model="selectedMonth" class="form-select" @change="updateChartData('archive')">
                <option v-for="month in availableMonths" :key="month.value" :value="month.value">{{ month.text }}</option>
              </select>
            </div>
            <div class="button-group text-center mb-3">
              <button
                class="btn me-2"
                :class="{'btn-primary': chartPeriod === 'weekly', 'btn-default': chartPeriod !== 'weekly'}"
                :style="chartPeriod === 'weekly' ? activeButtonStyle : {}"
                @click="setChartPeriod('weekly', 'archive')"
              >
                주별
              </button>
              <button
                class="btn"
                :class="{'btn-primary': chartPeriod === 'monthly', 'btn-default': chartPeriod !== 'monthly'}"
                :style="chartPeriod === 'monthly' ? activeButtonStyle : {}"
                @click="setChartPeriod('monthly', 'archive')"
              >
                월별
              </button>
            </div>
            <!-- 차트 영역 -->
            <canvas id="emailSubscriptionChart"></canvas>
            <p class="text-muted mt-2">updated just now</p>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Quiz Sets</h5>
            <p class="card-text">주별/월별 퀴즈 세트 조회 수</p>
            <!-- 주별/월별 버튼 및 연도/월 선택 -->
            <div class="mb-3 d-flex justify-content-center">
              <select v-model="selectedYearQuiz" class="form-select me-2" @change="updateChartData('quiz')">
                <option v-for="year in availableYears" :key="year" :value="year">{{ year }}</option>
              </select>
              <select v-model="selectedMonthQuiz" class="form-select" @change="updateChartData('quiz')">
                <option v-for="month in availableMonths" :key="month.value" :value="month.value">{{ month.text }}</option>
              </select>
            </div>
            <div class="button-group text-center mb-3">
              <button
                class="btn me-2"
                :class="{'btn-primary': chartPeriodQuiz === 'weekly', 'btn-default': chartPeriodQuiz !== 'weekly'}"
                :style="chartPeriodQuiz === 'weekly' ? activeButtonStyle : {}"
                @click="setChartPeriod('weekly', 'quiz')"
              >
                주별
              </button>
              <button
                class="btn"
                :class="{'btn-primary': chartPeriodQuiz === 'monthly', 'btn-default': chartPeriodQuiz !== 'monthly'}"
                :style="chartPeriodQuiz === 'monthly' ? activeButtonStyle : {}"
                @click="setChartPeriod('monthly', 'quiz')"
              >
                월별
              </button>
            </div>
            <!-- 차트 영역 -->
            <canvas id="completedTasksChart"></canvas>
            <p class="text-muted mt-2">updated just now</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Chart, registerables } from 'chart.js';
import { usePointStore } from '@/stores/pointStore';
import { useArchiveStore } from '@/stores/archiveStore';
import { useQuizStore } from '@/stores/quizStore';

// Chart.js에서 모든 필요한 요소를 등록
Chart.register(...registerables);

const { pointChart, fetchPointChart } = usePointStore();
const { weeklyLearningCounts, monthlyLearningCounts, fetchWeeklyLearningCounts, fetchMonthlyLearningCounts } = useArchiveStore();
const { weeklyQuizCounts, monthlyQuizCounts, fetchWeeklyQuizCounts, fetchMonthlyQuizCounts } = useQuizStore();

const dailySalesChart = ref(null);
const emailSubscriptionChart = ref(null);
const completedTasksChart = ref(null);

const chartPeriod = ref('weekly');
const chartPeriodQuiz = ref('weekly');

const selectedYear = ref(new Date().getFullYear());
const selectedMonth = ref(new Date().getMonth() + 1);

const selectedYearQuiz = ref(new Date().getFullYear());
const selectedMonthQuiz = ref(new Date().getMonth() + 1);

const availableYears = [2023, 2024, 2025];
const availableMonths = [
  { value: 1, text: '1월' }, { value: 2, text: '2월' }, { value: 3, text: '3월' },
  { value: 4, text: '4월' }, { value: 5, text: '5월' }, { value: 6, text: '6월' },
  { value: 7, text: '7월' }, { value: 8, text: '8월' }, { value: 9, text: '9월' },
  { value: 10, text: '10월' }, { value: 11, text: '11월' }, { value: 12, text: '12월' }
];

// 활성화된 버튼의 스타일
const activeButtonStyle = {
  backgroundColor: '#00C4D1',
  color: '#fff',
  borderColor: '#00C4D1',
};

// 차트 업데이트 함수
const updateEmailSubscriptionChart = () => {
  if (emailSubscriptionChart.value) {
    emailSubscriptionChart.value.destroy();
  }

  const chartData = chartPeriod.value === 'weekly' ? weeklyLearningCounts.value : monthlyLearningCounts.value;
  const labels = chartData.map((item) => item.week || item.month);
  const data = chartData.map((item) => item.count);

  emailSubscriptionChart.value = new Chart(document.getElementById('emailSubscriptionChart'), {
    type: 'line',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Learning Counts',
          data: data,
          borderColor: '#36A2EB',
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
        },
      ],
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 0.5, // Y축 간격을 1로 설정
          },
        },
      },
      plugins: {
        legend: {
          position: 'top',
        },
      },
    },
  });
};

const updateCompletedTasksChart = () => {
  if (completedTasksChart.value) {
    completedTasksChart.value.destroy();
  }

  const chartData = chartPeriodQuiz.value === 'weekly' ? weeklyQuizCounts.value : monthlyQuizCounts.value;
  const labels = chartData.map((item) => item.week || item.month);
  const data = chartData.map((item) => item.count);

  completedTasksChart.value = new Chart(document.getElementById('completedTasksChart'), {
    type: 'line',
    data: {
      labels: labels,
      datasets: [
        {
          label: 'Quiz Set Counts',
          data: data,
          borderColor: '#FF6384',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
        },
      ],
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            stepSize: 0.5, // Y축 간격을 1로 설정
          },
        },
      },
      plugins: {
        legend: {
          position: 'top',
        },
      },
    },
  });
};

// 차트 데이터 업데이트 함수
const updateChartData = async (type) => {
  if (type === 'archive') {
    if (chartPeriod.value === 'weekly') {
      await fetchWeeklyLearningCounts(selectedYear.value, selectedMonth.value);
    } else {
      await fetchMonthlyLearningCounts(selectedYear.value);
    }
    updateEmailSubscriptionChart();
  } else if (type === 'quiz') {
    if (chartPeriodQuiz.value === 'weekly') {
      await fetchWeeklyQuizCounts(selectedYearQuiz.value, selectedMonthQuiz.value);
    } else {
      await fetchMonthlyQuizCounts(selectedYearQuiz.value);
    }
    updateCompletedTasksChart();
  }
};

// 차트 기간 설정 함수
const setChartPeriod = async (period, type) => {
  if (type === 'archive') {
    chartPeriod.value = period;
    await updateChartData('archive');
  } else if (type === 'quiz') {
    chartPeriodQuiz.value = period;
    await updateChartData('quiz');
  }
};

onMounted(async () => {
  await fetchPointChart();

  dailySalesChart.value = new Chart(document.getElementById('dailySalesChart'), {
    type: 'pie',
    data: {
      labels: pointChart.value.map((item) => item.activityName),
      datasets: [
        {
          label: 'Activity Points Distribution',
          data: pointChart.value.map((item) => item.points),
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
        },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'top',
        },
      },
    },
  });

  chartPeriod.value = 'weekly';
  await fetchWeeklyLearningCounts(selectedYear.value, selectedMonth.value);
  updateEmailSubscriptionChart();

  chartPeriodQuiz.value = 'weekly';
  await fetchWeeklyQuizCounts(selectedYearQuiz.value, selectedMonthQuiz.value);
  updateCompletedTasksChart();
});
</script>

<style scoped>
.button-group {
  margin-bottom: 10px;
}

.form-select {
  width: auto;
  display: inline-block;
}
</style>

<style lang="scss" scoped>
.middle-content-row {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
  background-color: #fff;
  min-height: 300px;
}

.middle-content-row .row {
  display: flex;
  justify-content: space-around;
  width: 100%;
  max-width: 1200px;
}

.middle-content-row .col-md-4 {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card {
  width: 100%;
  max-width: 350px;
  margin: 0 10px;
}
</style>
