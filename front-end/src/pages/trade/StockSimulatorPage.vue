<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />

    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색어를 입력하세요"
        @input="filterData"
        @focus="showDropdown = true"
        @blur="hideDropdown"
      />

      <div
        v-if="showDropdown && searchQuery && filteredStockList.length"
        class="autocomplete-dropdown"
      >
        <ul>
          <li
            v-for="stock in filteredStockList"
            :key="stock.srtnCd"
            @click="selectStock(stock)"
          >
            {{ stock.itmsNm }}
            <span class="stock-code">({{ stock.srtnCd }})</span>
          </li>
        </ul>
      </div>
    </div>

    <div class="stock-list">
      <ul>
        <li
          v-for="stock in uniqueKospiStockList.slice(0, 10)"
          :key="stock.srtnCd"
          class="stock-item"
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
import axios from 'axios';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';

export default {
  components: {
    TradeImage,
    TradeNavigationBar,
  },
  data() {
    return {
      kospiStockList: [],
      searchQuery: '',
      filteredStockList: [],
      showDropdown: false,
    };
  },
  computed: {
    // 중복을 제거한 KOSPI 주식 리스트
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
    async fetchKOSPIStockList() {
      try {
        const response = await axios.get('/api/stock/list/KOSPI');
        this.kospiStockList = Array.isArray(response.data) ? response.data : [];

        // 주가 총액(mrktTotAmt) 기준으로 정렬
        this.kospiStockList.sort(
          (a, b) => Number(b.mrktTotAmt) - Number(a.mrktTotAmt)
        );
      } catch (error) {
        console.error('Error fetching KOSPI Stock List:', error);
      }
    },
    filterData() {
      const lowerCaseQuery = this.searchQuery.toLowerCase();
      const seenStocks = new Set();

      this.filteredStockList = this.uniqueKospiStockList.filter((stock) => {
        if (
          stock.itmsNm.toLowerCase().includes(lowerCaseQuery) &&
          !seenStocks.has(stock.itmsNm)
        ) {
          seenStocks.add(stock.itmsNm);
          return true;
        }
        return false;
      });
    },
    selectStock(stock) {
      this.searchQuery = stock.itmsNm;
      this.showDropdown = false;
    },
    hideDropdown() {
      this.showDropdown = false;
    },
    formatCurrency(amount) {
      // 금액을 숫자로 변환 후 쉼표와 '원' 추가
      return amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
  },
  mounted() {
    this.fetchKOSPIStockList();
  },
};
</script>

<style scoped>
/* 스타일 추가 */
.search-container {
  position: relative;
  width: 300px;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
}

.autocomplete-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: white;
  border: 1px solid #ccc;
  z-index: 10;
  max-height: 200px;
  overflow-y: auto;
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
}

.thumbnail {
  width: 40px; /* 썸네일 이미지 너비 */
  height: 40px; /* 썸네일 이미지 높이 */
  margin-right: 10px; /* 썸네일과 텍스트 사이의 간격 */
}

.stock-info {
  display: flex;
  flex-direction: column;
}

.stock-name {
  font-weight: bold;
}

.stock-market-amount {
  margin-top: 5px; /* 주가 총액과 주식 이름 사이의 간격 */
}
</style>
