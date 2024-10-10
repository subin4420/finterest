<script setup>
import api from '@/api/boardApi';
import { ref, reactive, computed, watch } from 'vue';
import moment from 'moment';
import { useRoute, useRouter } from 'vue-router';

const cr = useRoute();
const router = useRouter();

const page = ref({
  totalCount: 0,
});
const articles = computed(() => page.value.list);

const pageRequest = reactive({
  page: parseInt(cr.query.page) || 1,
  amount: parseInt(cr.query.amount) || 10,
});

console.log('QUERY', cr.query);
console.log('PAGE REQUEST', pageRequest);

// 페이지네이션 페이지 변경
const handlePageChange = async (pageNum) => {
  console.log('CLICK,,,,');
  // url 변경 --> query 파트만 변경되므로 컴포넌트가 다시 마운트되지 않음
  // watch를 통해 cr이 변경됨을 감지하여 페이지 로드해야 함
  router.push({
    query: { page: pageNum, amount: pageRequest.amount },
  });
};

// pageRequest의 값 변경된 경우 호출
watch(cr, async (newValue) => {
  console.log('WATCH', cr.query.page);
  pageRequest.page = parseInt(cr.query.page);
  pageRequest.amount = parseInt(cr.query.amount);
  await load(pageRequest);
});

const load = async (query) => {
  try {
    page.value = await api.getList(query);
    console.log(page.value);
  } catch {}
};

load(pageRequest);
</script>

<template>
  <h1 class="mb-3"><i class="fa-solid fa-paste"></i> 게시글 목록</h1>

  <div class="mt-5 text-end">(총 {{ page.totalCount }}건)</div>
  <table class="table table-striped">
    <thead>
      <tr>
        <th style="width: 60px">No</th>
        <th>제목</th>
        <th style="width: 100px">작성자</th>
        <th style="width: 120px">작성일</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="article in articles" :key="article.no">
        <td>{{ article.no }}</td>
        <td>
          <router-link :to="{ name: 'board/detail', params: { no: article.no }, query: cr.query }"> {{ article.title }} </router-link>
        </td>
        <td>{{ article.writer }}</td>
        <td>{{ moment(article.regDate).format('YYYY-MM-DD') }}</td>
      </tr>
    </tbody>
  </table>

  <div class="my-5 d-flex">
    <div class="flex-grow-1 text-center">
      <vue-awesome-paginate
        :total-items="page.totalCount"
        :items-per-page="pageRequest.amount"
        :max-pages-shown="5"
        :show-ending-buttons="true"
        v-model="pageRequest.page"
        @click="handlePageChange"
      >
        <template #first-page-button><i class="fa-solid fa-backward-fast"></i></template>
        <template #prev-button><i class="fa-solid fa-caret-left"></i></template>
        <template #next-button><i class="fa-solid fa-caret-right"></i></template>
        <template #last-page-button><i class="fa-solid fa-forward-fast"></i></template>
      </vue-awesome-paginate>
    </div>
    <div>
      <router-link :to="{ name: 'board/create', query: cr.query }" class="btn btn-primary"><i class="fa-solid fa-pen-to-square"></i> 글 작성</router-link>
    </div>
  </div>
</template>
