<template>
  <div class="search-list-container">
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색어를 입력하세요"
        @input="filterData"
      />
    </div>

    <div class="stock-list">
      <ul>
        <li
          v-for="stock in displayedStockList"
          :key="stock.srtnCd"
          class="stock-item"
          :class="{ selected: stock.srtnCd === stockStore.selectedStockCode }"
          @click="selectStock(stock)"
        >
          <img :src="stock.thumbnail" class="thumbnail" />
          <div class="stock-info">
            <span class="stock-name">{{ stock.itmsNm }}</span>
            <span class="stock-code">({{ stock.srtnCd }})</span>
            <span class="stock-market-amount"
              >{{ formatCurrency(stock.mrktTotAmt) }} 원</span
            >
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { useStockStore } from '@/stores/tradestock'; // Pinia 스토어 가져오기

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
    stockStore() {
      return useStockStore(); // Pinia 스토어 인스턴스 가져오기
    },
    displayedStockList() {
      // 검색어가 없으면 기본 주식 리스트 반환, 검색 결과가 있으면 필터된 리스트 반환
      return this.searchQuery
        ? this.filteredStockList.length > 0
          ? this.filteredStockList
          : this.uniqueKospiStockList
        : this.uniqueKospiStockList.sort((a, b) => b.mrktTotAmt - a.mrktTotAmt);
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
      const stockStore = useStockStore(); // Pinia 스토어 인스턴스 가져오기
      stockStore.setSelectedStockCode(stock.srtnCd); // 주식 코드 저장
      console.log('선택된 주식 코드:', stockStore.selectedStockCode); // 선택된 주식 코드 콘솔 출력
    },
    formatCurrency(amount) {
      return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
  },
  created() {
    // 컴포넌트 로드 시 기본 주식 리스트로 초기화
    this.filteredStockList = this.uniqueKospiStockList;
  },
};
</script>

<style scoped>
.search-container {
  position: relative;
  width: 300px;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
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
  width: 300px;
}

.stock-item.selected {
  background-color: #d0e7ff; /* 선택된 항목의 배경색 */
}

.thumbnail {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.stock-info {
  display: flex;
  flex-direction: column;
}

.stock-name {
  font-weight: bold;
}

.stock-market-amount {
  margin-top: 5px;
}
</style>
