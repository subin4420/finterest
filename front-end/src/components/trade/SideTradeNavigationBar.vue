
<template>
  <div class="sidebar-container">
    <ul class="list-group">
      <li
        class="list-group-item"
        v-for="(item, index) in menuItems"
        :key="index"
      >
        <router-link
          :to="item.path"
          class="sidebar-link"
          exact-active-class="active"
        >
          <i :class="item.icon"></i>
          <span>{{ item.title }}</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  name: 'SideTradeNavigationBar',
  setup() {
    const menuItems = ref([
      { title: '홈', path: '/trade', icon: 'fas fa-home' },
      {
        title: '모의투자',
        path: '/trade/stocksimulator',
        icon: 'fas fa-chart-line',
      },
      {
        title: '포트폴리오',
        path: '/trade/portfolio',
        icon: 'fas fa-briefcase',
      },
      { title: '뉴스', path: '/trade/news', icon: 'fas fa-newspaper' },
      { title: '결산', path: '/trade/settlement', icon: 'fas fa-calculator' },
      {
        title: '포인트 전환',
        path: '/trade/pointconversion',
        icon: 'fas fa-exchange-alt',
      },
      { title: '커뮤니티', path: '/trade/community', icon: 'fas fa-users' },
      {
        title: '턴제 모의투자',
        path: '/trade/turnbasedsimulation',
        icon: 'fas fa-dice',
      },
    ]);

    return {
      menuItems,
    };
  },
};
</script>

<style scoped>
.sidebar-container {
  width: 250px;
  height: calc(100vh - 60px); /* 헤더 높이를 뺀 전체 높이 */
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: absolute; /* 절대 위치에서 고정 위치로 변경 */
  top: 60px; /* 헤더 바로 아래에 위치 */
  left: 0; /* 왼쪽에 붙임 */
  z-index: 1000; /* 다른 요소들 위에 표시 */
  overflow-y: auto; /* 내용이 많을 경우 스크롤 가능하게 */
}

.list-group {
  padding: 0;
  margin: 0;
  list-style-type: none;
}

.list-group-item {
  border: none;
}

.sidebar-link {
  display: flex;
  align-items: center;
  padding: 12px 15px;
  text-decoration: none;
  color: #333;
  border-radius: 8px;
  margin: 5px 0;
  transition: background-color 0.3s ease;
}

.sidebar-link.active,
.sidebar-link:hover {
  background-color: #f5f5f5;
  color: #00c4d1;
}

.sidebar-link i {
  margin-right: 10px;
  width: 20px;
  text-align: center;
}

.sidebar-link span {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sidebar-link::after {
  content: '';
  display: none;
  position: absolute;
  right: 30px;
  top: 50%;
  width: 6px;
  height: 6px;
  background-color: #00c4d1;
  border-radius: 50%;
  transform: translateY(-50%);
}

.sidebar-link:hover::after,
.sidebar-link.active::after {
  display: block;
}

@media (max-width: 768px) {
  .sidebar-container {
    width: 60px;
  }

  .sidebar-link span {
    display: none;
  }

  .sidebar-link i {
    margin-right: 0;
  }
}
</style>
