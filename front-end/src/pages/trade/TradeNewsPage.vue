<template>
  <div class="page-container">
    <div class="trade-header"></div>
    <div class="content-wrapper">
      <SideTradeNavigationBar />
      <div class="content">
        <h1 class="page-title">뉴스 페이지</h1>

        <div class="search-container">
          <input
            type="text"
            v-model="searchQuery"
            placeholder="검색어를 입력하세요"
            @keyup.enter="searchNews"
          />
          <button @click="searchNews">검색</button>
        </div>

        <div class="news-container">
          <h2>최신 경제 뉴스</h2>
          <div v-if="loading" class="loading">
            <div class="spinner"></div>
            뉴스를 불러오는 중...
          </div>
          <div v-else-if="articles.length === 0" class="no-results">
            검색 결과가 없습니다.
          </div>
          <div v-else class="articles-container">
            <div v-for="article in articles" :key="article.url" class="article">
              <div
                class="article-image"
                :style="{
                  backgroundImage: `url(${
                    article.urlToImage || 'default-image-url.jpg'
                  })`,
                }"
              ></div>
              <div class="article-content">
                <h3>{{ article.title }}</h3>
                <p class="article-description">{{ article.description }}</p>
                <div class="article-footer">
                  <span class="author">{{
                    article.author || '작성자 없음'
                  }}</span>
                  <a :href="article.url" target="_blank" class="read-more"
                    >자세히 보기</a
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';
import axios from 'axios';

export default {
  name: 'TradeNewsPage',
  components: {
    SideTradeNavigationBar,
  },
  data() {
    return {
      articles: [],
      loading: true,
      searchQuery: '경제', // 기본 검색어 설정
    };
  },
  async mounted() {
    await this.fetchNews(); // 초기 뉴스 가져오기
  },
  methods: {
    async fetchNews() {
      const yesterday = this.getYesterdayDate(); // 어제 날짜 구하기
      try {
        const response = await axios.get(
          `https://newsapi.org/v2/everything?q=${this.searchQuery}&from=${yesterday}&sortBy=popularity&apiKey=ab5fa71416bc4687a2c535d3606c3f91`
        );
        this.articles = response.data.articles;
      } catch (error) {
        console.error('Error fetching news:', error);
      } finally {
        this.loading = false; // 로딩 완료
      }
    },
    searchNews() {
      this.loading = true; // 로딩 시작
      this.fetchNews(); // 검색 결과 가져오기
    },
    getYesterdayDate() {
      const today = new Date();
      today.setDate(today.getDate() - 1); // 어제 날짜로 설정
      return today.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
    },
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

.search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}

input {
  width: 300px;
  padding: 12px 20px;
  border: none;
  border-radius: 25px 0 0 25px;
  font-size: 16px;
  outline: none;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

button {
  padding: 12px 25px;
  border: none;
  background-color: #4caf50;
  color: white;
  border-radius: 0 25px 25px 0;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #45a049;
}

.news-container {
  background-color: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.loading {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  font-size: 18px;
  color: #666;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-right: 15px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.no-results {
  text-align: center;
  font-size: 18px;
  color: #666;
  margin-top: 30px;
}

.articles-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
}

.article {
  background-color: white;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.article:hover {
  transform: translateY(-5px);
}

.article-image {
  height: 200px;
  background-size: cover;
  background-position: center;
}

.article-content {
  padding: 20px;
}

.article h3 {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}

.article-description {
  font-size: 14px;
  color: #666;
  margin-bottom: 15px;
  line-height: 1.5;
}

.article-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
}

.author {
  font-size: 12px;
  color: #999;
}

.read-more {
  color: #4caf50;
  text-decoration: none;
  font-size: 14px;
  font-weight: bold;
}

.read-more:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  .articles-container {
    grid-template-columns: 1fr;
  }

  .search-container {
    flex-direction: column;
    align-items: center;
  }

  input {
    width: 100%;
    border-radius: 25px;
    margin-bottom: 10px;
  }

  button {
    width: 100%;
    border-radius: 25px;
  }
}
</style>
