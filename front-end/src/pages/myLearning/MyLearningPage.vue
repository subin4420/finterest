<template>
  <div class="container">
    <div class="sidebar-container">
      <ul class="list-group">
        <li class="list-group-item">
          <a
            href="#"
            class="sidebar-link"
            :class="{ active: activeTab === 'archive' }"
            @click.prevent="setTab('archive')"
          >
            아카이브 이력
          </a>
        </li>
        <li class="list-group-item">
          <a
            href="#"
            class="sidebar-link"
            :class="{ active: activeTab === 'quiz' }"
            @click.prevent="setTab('quiz')"
          >
            퀴즈 이력
          </a>
        </li>
        <li class="list-group-item">
          <a
            href="#"
            class="sidebar-link"
            :class="{ active: activeTab === 'investment' }"
            @click.prevent="setTab('investment')"
          >
            모의투자 이력
          </a>
        </li>
        <li class="list-group-item">
          <a
            href="#"
            class="sidebar-link"
            :class="{ active: activeTab === 'points' }"
            @click.prevent="setTab('points')"
          >
            포인트 이력
          </a>
        </li>
      </ul>
    </div>

    <div class="content">
      <h1>{{ pageTitle }}</h1>

      <div class="tabs">
        <button
          :class="{ active: learningStatus === 'ongoing' }"
          @click="setLearningStatus('ongoing')"
        >
          진행중인 학습
        </button>
        <button
          :class="{ active: learningStatus === 'completed' }"
          @click="setLearningStatus('completed')"
        >
          종료된 학습
        </button>
      </div>

      <div class="cards">
        <ArchiveCard v-for="(card, index) in filteredCards" :key="index" :cardData="card" />
      </div>

      <div class="pagination">
        <a v-for="page in totalPages" :key="page" href="#" :class="{ active: currentPage === page }" @click.prevent="setPage(page)">
          {{ page }}
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import ArchiveCard from '../../components/archive/ArchiveCard.vue';

export default {
  components: {
    ArchiveCard,
  },
  data() {
    return {
      activeTab: 'archive',
      learningStatus: 'ongoing',
      currentPage: 1,
      cards: [
        {
          title: '제목 1',
          categoryName: '카테고리 1',
          content: '본문 약간 1',
          materialImg: null,
        },
        {
          title: '제목 2',
          categoryName: '카테고리 2',
          content: '본문 약간 2',
          materialImg: 'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg',
        },
      ],
      itemsPerPage: 6,
    };
  },
  computed: {
    filteredCards() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      return this.cards.slice(start, start + this.itemsPerPage);
    },
    totalPages() {
      return Math.ceil(this.cards.length / this.itemsPerPage);
    },
    pageTitle() {
      switch (this.activeTab) {
        case 'archive':
          return '아카이브 이력';
        case 'quiz':
          return '퀴즈 이력';
        case 'investment':
          return '모의투자 이력';
        case 'points':
          return '포인트 이력';
        default:
          return '이력';
      }
    },
  },
  methods: {
    setTab(tab) {
      this.activeTab = tab;
    },
    setLearningStatus(status) {
      this.learningStatus = status;
    },
    setPage(page) {
      this.currentPage = page;
    },
  },
};
</script>

<style scoped>
/* Sidebar styles integrated */
.sidebar-container {
  background-color: #FFFFFF;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  height: 100%;
  border-radius: 5px;
}

.list-group {
  padding: 0;
  margin: 0;
}

.list-group-item {
  padding: 0;
  border: none;
}

.sidebar-link {
  display: block;
  padding: 25px 40px;
  text-decoration: none;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sidebar-link.active {
  color: #00C2FF;
}

.sidebar-link:hover {
  background-color: #F8F9FA;
}

/* Content and other existing styles can go here */
.container {
  display: flex;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.content {
  width: 80%;
  padding-left: 30px;
}

.content h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.tabs {
  margin-bottom: 20px;
}

.tabs button {
  background: none;
  border: none;
  font-size: 18px;
  margin-right: 20px;
  cursor: pointer;
}

.tabs button.active {
  font-weight: bold;
  border-bottom: 2px solid #00C2FF;
}

.cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.pagination {
  text-align: center;
  margin-top: 20px;
}

.pagination a {
  text-decoration: none;
  color: #333;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin: 0 5px;
}

.pagination a.active {
  background-color: #00C2FF;
  color: white;
}
</style>
