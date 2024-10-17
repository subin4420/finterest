<script setup>
import api from '@/api/boardApi';
import { ref, reactive, computed, watch } from 'vue';
import moment from 'moment';
import { useRoute, useRouter } from 'vue-router';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';

const cr = useRoute();
const router = useRouter();
const page = ref({});

const articles = computed(() => page.value.list);

const pageRequest22 = reactive({
  page: parseInt(cr.query.page) || 1,
  amount: parseInt(cr.query.amount) || 10,
});

const handlePageChange = async (pageNum) => {
  router.push({
    query: { page: pageNum, amount: pageRequest22.amount },
  });
};

watch(cr, async (newValue) => {
  pageRequest22.page = parseInt(cr.query.page);
  pageRequest22.amount = parseInt(cr.query.amount);
  await load(pageRequest22);
});

const load = async (query) => {
  try {
    page.value = await api.getList(query);
  } catch (error) {
    console.error('게시글 목록을 불러오는 중 오류 발생:', error);
  }
};

load(pageRequest22);
</script>

<template>
  <div class="페이지-컨테이너">
    <div class="거래-헤더"></div>
    <div class="콘텐츠-래퍼">
      <SideTradeNavigationBar />
      <div class="콘텐츠">
        <h1 class="페이지-제목">모의투자 커뮤니티</h1>
        <div class="게시판-목록-컨테이너">
          <div class="총-개수">(총 {{ page.totalCount }}건)</div>

          <ul class="게시판-리스트">
            <li
              v-for="(article, index) in articles"
              :key="article.no"
              class="게시판-항목"
              :class="{ 짝수: index % 2 === 0 }"
            >
              <div class="게시글-번호">{{ article.no }}</div>
              <div class="게시글-내용">
                <router-link
                  :to="{
                    name: 'board/detail',
                    params: { no: article.no },
                    query: cr.query,
                  }"
                  class="게시글-제목"
                >
                  {{ article.title }}
                </router-link>
                <div class="게시글-정보">
                  <span class="작성자-정보">
                    <img
                      :src="`/api/users/${article.writer}/avatar`"
                      class="아바타 아바타-sm"
                    />
                    <span>{{ article.writer }}</span>
                  </span>
                  <span class="게시글-날짜">{{
                    moment(article.regDate).format('YYYY-MM-DD')
                  }}</span>
                </div>
              </div>
            </li>
          </ul>
          <div class="게시판-푸터">
            <vue-awesome-paginate
              :total-items="page.totalCount"
              :items-per-page="pageRequest22.amount"
              :max-pages-shown="5"
              :show-ending-buttons="true"
              v-model="pageRequest22.page"
              @click="handlePageChange"
              class="페이지네이션"
            >
              <template #first-page-button
                ><i class="fa-solid fa-backward-fast"></i
              ></template>
              <template #prev-button
                ><i class="fa-solid fa-caret-left"></i
              ></template>
              <template #next-button
                ><i class="fa-solid fa-caret-right"></i
              ></template>
              <template #last-page-button
                ><i class="fa-solid fa-forward-fast"></i
              ></template>
            </vue-awesome-paginate>

            <router-link
              :to="{ name: 'board/create', query: cr.query }"
              class="생성-버튼"
            >
              <i class="fa-solid fa-pen-to-square"></i> 글 작성
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.페이지-컨테이너 {
  display: flex;
  flex-direction: column;
}

.거래-헤더 {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
  background-color: #2e78e0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.콘텐츠-래퍼 {
  display: flex;
  margin-top: 20px;
}

.콘텐츠 {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
}

.페이지-제목 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-top: 0;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
}

.게시판-목록-컨테이너 {
  max-width: 1500px;
  margin: 0 auto;
  padding: 0rem;
}

.총-개수 {
  text-align: right;
  color: #666;
  margin-bottom: 1rem;
}

.게시판-리스트 {
  list-style-type: none;
  padding: 0;
}

.게시판-항목 {
  display: flex;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.3s ease;
}

.게시판-항목:hover {
  background-color: #f0f8ff;
}

.게시판-항목.짝수 {
  background-color: #f9f9f9;
}

.게시판-항목.짝수:hover {
  background-color: #e6f2ff;
}

.게시글-번호 {
  flex: 0 0 60px;
  font-weight: bold;
  color: #007bff;
}

.게시글-내용 {
  flex: 1;
}

.게시글-제목 {
  display: block;
  font-size: 1.1rem;
  color: #333;
  text-decoration: none;
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}

.게시글-제목:hover {
  color: #007bff;
}

.게시글-정보 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.9rem;
  color: #666;
}

.작성자-정보 {
  display: flex;
  align-items: center;
}

.아바타 {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  margin-right: 0.5rem;
}

.게시판-푸터 {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 2rem;
}

.페이지네이션 {
  /* 기존 vue-awesome-paginate 스타일을 오버라이드할 수 있습니다 */
}

.생성-버튼 {
  background-color: #007bff;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.생성-버튼:hover {
  background-color: #0056b3;
}
</style>
