<template>
  <div class="search-list-container">
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색어를 입력하세요"
        @input="filterData"
        class="search-input"
      />
    </div>

    <div class="stock-list">
      <ul>
        <li
          v-for="stock in displayedStockList"
          :key="stock.srtnCd"
          class="stock-item"
          :class="{ selected: stock.srtnCd === tradeStore.selectedStockCode }"
          @click="selectStock(stock)"
        >
          <img :src="getLogoPath(stock.srtnCd)" class="thumbnail" />
          <div class="stock-info">
            <div class="stock-name">{{ stock.itmsNm }}</div>
            <div class="stock-end-price">
              {{ formatCurrency(stock.clpr) }} 원
            </div>
            <span class="stock-code">({{ stock.srtnCd }})</span>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { useTradeStore } from '@/stores/tradeStore'; // Pinia 스토어 가져오기

export default {
  props: {
    kospiStockList: Array,
  },
  data() {
    return {
      searchQuery: '',
      filteredStockList: [],
    };
  },
  computed: {
    tradeStore() {
      return useTradeStore(); // Pinia 스토어 인스턴스 가져오기
    },
    displayedStockList() {
      return this.searchQuery
        ? this.filteredStockList.length > 0
          ? this.filteredStockList
          : this.uniqueKospiStockList
        : this.uniqueKospiStockList
            .sort((a, b) => b.mrktTotAmt - a.mrktTotAmt)
            .slice(0, 10);
    },
    uniqueKospiStockList() {
      const seen = new Set();
      return this.kospiStockList.filter((stock) => {
        if (!seen.has(stock.srtnCd)) {
          seen.add(stock.srtnCd);
          return true;
        }
        return false;
      });
    },
  },
  methods: {
    filterData() {
      const lowerCaseQuery = this.searchQuery.toLowerCase();
      this.filteredStockList = this.uniqueKospiStockList.filter((stock) => {
        return stock.itmsNm.toLowerCase().includes(lowerCaseQuery);
      });
    },
    selectStock(stock) {
      const tradeStore = useTradeStore();
      // 주식 코드와 이름을 함께 설정
      tradeStore.setSelectedStockCode(stock.srtnCd);
      tradeStore.setStockName(stock.itmsNm);
      console.log('선택된 주식 코드:', tradeStore.selectedStockCode);
      console.log('선택된 주식 이름:', tradeStore.stockName);
    },
    getLogoPath(stockCode) {
      return `/logos/${stockCode}.png`; // stockCode에 따라 경로를 설정
    },
    formatCurrency(amount) {
      return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
  },
  created() {
    this.filteredStockList = this.uniqueKospiStockList;
  },
};
</script>

<style scoped>
.search-list-container {
  border-radius: 10px; /* 전체 모서리 둥글게 */
  overflow: hidden; /* 자식 요소가 컨테이너를 벗어나는 것을 방지 */
  border: 1px solid #eaeaea; /* 테두리 추가 */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  width: 300px;
}

.search-container {
  position: relative;
  width: 100%;
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 20px; /* padding-top 추가 */
}

input[type='text'] {
  width: 100%; /* 입력창이 컨테이너에 맞게 확장 */
  padding: 10px; /* padding 추가 */
  border: 1px solid #eaeaea; /* 테두리 추가 */
  border-radius: 10px; /* 모서리 둥글게 */
}

ul {
  list-style-type: none;
  margin: 0;
  padding-left: 0; /* padding-left 제거 */
}

li {
  padding: 10px;
  cursor: pointer;
}

li:hover {
  background-color: #f0f0f0;
}

.stock-code {
  color: gray;
  font-size: 0.8em;
  margin-left: 5px;
}

.stock-list {
  margin-top: 20px;
}

.stock-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eaeaea;
  width: 100%; /* 너비를 100%로 설정 */
}

.stock-item.selected {
  background-color: #d0e7ff; /* 선택된 항목의 배경색 */
}

.thumbnail {
  width: 40px;
  height: 40px;
  margin-right: 10px;
  margin-left: 10px;
  border-radius: 70%;
  overflow: hidden;
}

.stock-info {
  display: flex;
  flex-direction: column;
}
.stock-info-header {
  display: flex; /* 요소들을 가로로 나열 */
  justify-content: space-between; /* 양쪽 끝으로 배치 */
  align-items: center; /* 수직 정렬 */
  width: 100%; /* 전체 너비 사용 */
}

.stock-name {
  font-weight: bold;
}

.stock-end-price {
  font-size: 14px;
  color: #333;
}
</style>
