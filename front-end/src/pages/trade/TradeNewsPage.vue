<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />

    <!-- 검색 기능 추가 -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색어를 입력하세요"
        @keyup.enter="searchNews"
      />

      <button @click="searchNews">검색</button>
    </div>

    <!-- 최근 뉴스 섹션 -->
    <div class="news-container">
      <h2>검색 결과</h2>
      <div v-if="loading" class="loading">Loading...</div>
      <div v-else>
        <div v-if="articles.length === 0" class="no-results">
          검색 결과가 없습니다.
        </div>
        <!-- 결과가 없을 때 메시지 -->
        <div class="articles-container">
          <div v-for="article in articles" :key="article.url" class="article">
            <div class="article-header">
              <img
                v-if="article.urlToImage"
                :src="article.urlToImage"
                alt="Article Image"
                class="article-image"
              />
              <!-- 썸네일 이미지 추가 -->
              <div class="article-info">
                <p class="author">{{ article.author || '작성자 없음' }}</p>
                <!-- 작가 정보 추가 -->
                <h3>{{ article.title }}</h3>
                <p>{{ article.description }}</p>
                <a :href="article.url" target="_blank" class="read-more"
                  >Read more</a
                >
                <!-- Read more로 텍스트 변경 -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import axios from 'axios';

export default {
  name: 'CommunityPage',
  components: {
    TradeImage,
    TradeNavigationBar,
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
.search-container {
  margin: 20px 0; /* 위아래 여백 추가 */
  display: flex;
  justify-content: center; /* 가운데 정렬 */
}

input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 10px; /* 버튼과 간격 */
}

button {
  padding: 10px 15px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.news-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

.loading {
  text-align: center;
  font-size: 1.5em;
}

.no-results {
  text-align: center;
  font-size: 1.2em;
  color: red; /* 경고 색상 */
}

.articles-container {
  display: flex; /* Flexbox로 변경 */
  flex-direction: column; /* 세로 방향으로 정렬 */
  gap: 20px; /* 기사 간 간격 추가 */
}

.article {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.article-header {
  display: flex; /* Flexbox로 변경 */
  gap: 15px; /* 이미지와 텍스트 간격 */
}

.article-image {
  width: 100px; /* 이미지 너비 조정 */
  height: 80px; /* 이미지 높이 조정 */
  border-radius: 4px; /* 이미지의 모서리 둥글게 */
}

.article-info {
  flex-grow: 1; /* 남은 공간을 모두 차지 */
}

.article h3 {
  margin: 10px 0 5px 0;
  font-size: 1.1em;
}

.article p {
  margin: 0 0 10px 0;
  font-size: 0.9em;
}

.article .author {
  font-size: 0.8em; /* 작가 글씨 크기 */
  color: #555; /* 작가 색상 */
}

.read-more {
  color: blue;
  text-decoration: underline;
  font-size: 0.9em;
  margin-top: 10px; /* 위쪽 여백 추가 */
}
</style>
