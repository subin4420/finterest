<script setup>
import { ref, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import boardApi from '@/api/boardApi';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';

const cr = useRoute();
const router = useRouter();
const no = cr.params.no;
const article = reactive({});
const attachments = ref([]);
const orgArticle = ref({});
const files = ref(null);

const back = () => {
  router.push({ name: 'board/detail', params: { no }, query: cr.query });
};

const removeFile = async (no, filename) => {
  if (!confirm(filename + '을 삭제할까요?')) return;
  await boardApi.deleteAttachment(no);
  const ix = attachments.value.findIndex((f) => f.no === no);
  attachments.value.splice(ix, 1);
};

const submit = async () => {
  if (!confirm('수정할까요?')) return;
  if (files.value.files.length > 0) {
    article.files = files.value.files;
  }
  await boardApi.update(article);
  router.push({ name: 'board/detail', params: { no }, query: cr.query });
};

const reset = () => {
  article.no = orgArticle.value.no;
  article.title = orgArticle.value.title;
  article.writer = orgArticle.value.writer;
  article.content = orgArticle.value.content;
  console.log(article);
};

const load = async () => {
  const data = await boardApi.get(no);
  orgArticle.value = { ...data };
  attachments.value = data.attaches;
  reset();
};

load();
</script>

<template>
  <div class="페이지-컨테이너">
    <div class="거래-헤더"></div>
    <div class="콘텐츠-래퍼">
      <SideTradeNavigationBar />
      <div class="콘텐츠">
        <h1 class="페이지-제목">글 수정</h1>
        <form @submit.prevent="submit" class="게시판-수정-폼">
          <div class="폼-그룹">
            <label for="title" class="폼-라벨">제목</label>
            <input
              type="text"
              class="폼-컨트롤"
              placeholder="제목"
              id="title"
              v-model="article.title"
            />
          </div>
          <div class="폼-그룹">
            <label class="폼-라벨">기존 첨부파일</label>
            <div v-for="file in attachments" :key="file.no" class="첨부">
              <i class="fa-solid fa-paperclip"></i> {{ file.filename }}
              <i
                class="fa-solid fa-trash-can text-danger ms-2"
                @click="removeFile(file.no, file.filename)"
              ></i>
            </div>
          </div>
          <div class="폼-그룹">
            <label for="files" class="폼-라벨">첨부파일</label>
            <input
              type="file"
              class="폼-컨트롤 파일-입력"
              placeholder="첨부파일"
              id="files"
              ref="files"
              multiple
            />
          </div>
          <div class="폼-그룹">
            <label for="content" class="폼-라벨">내용</label>
            <textarea
              class="폼-컨트롤"
              placeholder="내용"
              id="content"
              v-model="article.content"
              rows="10"
            ></textarea>
          </div>
          <div class="버튼-그룹">
            <button type="submit" class="버튼 버튼-주요">
              <i class="fa-solid fa-check"></i> 확인
            </button>
            <button type="button" class="버튼 버튼-보조" @click="reset">
              <i class="fa-solid fa-undo"></i> 취소
            </button>
            <button class="버튼 버튼-보조" @click="back">
              <i class="fa-solid fa-arrow-left"></i> 돌아가기
            </button>
          </div>
        </form>
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
  margin-top: 60px;
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
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #b3b3b3;
}

.게시판-수정-폼 {
  max-width: 800px;
  margin: 0 auto;
}

.폼-그룹 {
  margin-bottom: 1.5rem;
}

.폼-라벨 {
  font-weight: bold;
  margin-bottom: 0.5rem;
  display: block;
}

.폼-컨트롤 {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.파일-입력 {
  padding: 0.375rem 0.75rem;
}

.첨부 {
  margin-bottom: 0.5rem;
}

.fa-trash-can {
  cursor: pointer;
}

.버튼-그룹 {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 2rem;
}

.버튼 {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.버튼-주요 {
  background-color: #007bff;
  color: white;
}

.버튼-주요:hover {
  background-color: #0056b3;
}

.버튼-보조 {
  background-color: #6c757d;
  color: white;
}

.버튼-보조:hover {
  background-color: #545b62;
}
</style>
