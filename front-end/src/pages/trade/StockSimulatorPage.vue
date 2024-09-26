<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />

    <!-- 검색 입력창 추가 -->
    <div class="search-container">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색어를 입력하세요"
        @input="filterData"
        @focus="showDropdown = true"
        @blur="hideDropdown"
      />

      <!-- 검색어와 관련된 주식 리스트 자동완성 -->
      <div
        v-if="showDropdown && searchQuery && filteredStockList.length"
        class="autocomplete-dropdown"
      >
        <ul>
          <li
            v-for="stock in filteredStockList"
            :key="stock.id"
            @mousedown="selectStock(stock)"
          >
            {{ stock.itmsNm }}
            <span class="stock-code">({{ stock.srtncd }})</span>
            <!-- srtncd를 표시 -->
          </li>
        </ul>
      </div>
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
      kospiStockList: [], // KOSPI 주식 리스트
      searchQuery: '', // 검색어 저장
      filteredStockList: [], // 필터된 주식 리스트
      showDropdown: false, // 드롭다운을 보여줄지 여부
    };
  },
  methods: {
    async fetchKOSPIStockList() {
      try {
        const response = await axios.get('/api/stock/list/KOSPI');
        console.log('KOSPI Stock List Response:', response.data); // 데이터 구조 확인
        this.kospiStockList = Array.isArray(response.data) ? response.data : [];
      } catch (error) {
        console.error('Error fetching KOSPI Stock List:', error);
      }
    },
    filterData() {
      // 검색어를 기준으로 주식 리스트 필터링 및 중복 제거
      const lowerCaseQuery = this.searchQuery.toLowerCase();
      const seenStocks = new Set();

      this.filteredStockList = this.kospiStockList.filter((stock) => {
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
      this.searchQuery = stock.itmsNm; // 선택된 주식명으로 검색어 설정
      this.showDropdown = false; // 드롭다운 숨김
    },
    hideDropdown() {
      setTimeout(() => {
        this.showDropdown = false;
      }, 200); // 약간의 지연 후 드롭다운을 숨김
    },
  },
  mounted() {
    this.fetchKOSPIStockList(); // KOSPI 주식 리스트 데이터 가져오기
  },
};
</script>

<style scoped>
.search-container {
  position: relative;
  width: 300px; /* 검색창 크기 설정 */
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
  color: gray; /* 회색 글씨 */
  font-size: 0.8em; /* 글자 크기 조정 */
  margin-left: 5px; /* 주식 이름과의 간격 */
}
</style>
<template>
    <div>
      <div id="chart">
        <StockChart class="stock-chart" />
        <router-view></router-view>
      </div>
    </div>
  </template>
  
  <script>
  import StockChart from '@/components/trade/StockChart.vue';
  
  export default {
    name: 'StockSimulatorPage',
    components: {
      StockChart,
    },
  };
  </script>
  
  <style scoped>
  #chart {
    height: 400px;
    width: 600px;
  }
  
  .stock-chart {
    height: 400px;
    width: 600px;
  }
  </style>
  