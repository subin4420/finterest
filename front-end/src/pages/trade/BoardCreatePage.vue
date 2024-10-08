<script setup>
import boardApi from '@/api/boardApi';
import { computed, reactive, ref } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRoute, useRouter } from 'vue-router';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';

const auth = useAuthStore();
const router = useRouter();
const cr = useRoute();
const files = ref(null);
const article = reactive({
  writer: auth.username,
  title: '',
  files: null,
});
const disableSubmit = computed(() => !article.title);

const submit = async () => {
  if (!confirm('게시글을 등록하시겠습니까?')) return;

  if (files.value.files.length > 0) {
    article.files = files.value.files;
  }

  try {
    await boardApi.create(article);
    router.push({ name: 'board/list', query: cr.query });
  } catch (error) {
    console.error('게시글 작성 중 오류 발생:', error);
    alert('게시글 작성에 실패했습니다. 다시 시도해 주세요.');
  }
};
</script>

<template>
  <div class="페이지-컨테이너">
    <div class="거래-헤더"></div>
    <div class="콘텐츠-래퍼">
      <SideTradeNavigationBar />
      <div class="콘텐츠">
        <h1 class="페이지-제목">새 게시글 작성</h1>
        <div class="게시판-생성-컨테이너">
          <form @submit.prevent="submit" class="게시판-생성-폼">
            <div class="폼-그룹">
              <label for="title" class="폼-라벨">제목</label>
              <input
                type="text"
                class="폼-컨트롤"
                placeholder="제목을 입력해주세요"
                id="title"
                v-model="article.title"
              />
            </div>
            <div class="폼-그룹">
              <label for="files" class="폼-라벨">첨부파일</label>
              <input
                type="file"
                class="폼-컨트롤 파일-입력"
                placeholder="첨부파일 선택"
                id="files"
                ref="files"
                multiple
              />
            </div>
            <div class="폼-그룹">
              <label for="content" class="폼-라벨">내용</label>
              <textarea
                class="폼-컨트롤"
                placeholder="내용을 입력해주세요"
                id="content"
                v-model="article.content"
                rows="10"
              ></textarea>
            </div>
            <div class="폼-액션">
              <button
                type="submit"
                class="버튼 버튼-주요 제출-버튼"
                :disabled="disableSubmit"
              >
                <i class="fa-solid fa-paper-plane"></i>
                게시하기
              </button>
              <router-link
                class="버튼 버튼-보조 목록-버튼"
                :to="{ name: 'board/list', query: cr.query }"
              >
                <i class="fa-solid fa-arrow-left"></i>
                목록으로
              </router-link>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.페이지-컨테이너 {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f8f9fa;
}

.거래-헤더 {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 60px;
  z-index: 1000;
  background-color: #3498db;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.콘텐츠-래퍼 {
  display: flex;
  margin-top: 60px;
}

.콘텐츠 {
  flex: 1;
  margin-left: 250px;
  padding: 40px;
}

.페이지-제목 {
  font-size: 2.2rem;
  font-weight: bold;
  color: #2c3e50;
  text-align: center;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 3px solid #3498db;
}

.게시판-생성-컨테이너 {
  max-width: 800px;
  margin: 0 auto;
  padding: 2.5rem;
  background-color: #ffffff;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.게시판-생성-폼 {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.폼-그룹 {
  display: flex;
  flex-direction: column;
}

.폼-라벨 {
  font-weight: bold;
  margin-bottom: 0.7rem;
  color: #34495e;
  font-size: 1.1rem;
}

.폼-컨트롤 {
  padding: 0.9rem;
  border: 2px solid #bdc3c7;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

.폼-컨트롤:focus {
  border-color: #3498db;
  outline: none;
}

.파일-입력 {
  padding: 0.7rem;
  background-color: #ecf0f1;
}

textarea.폼-컨트롤 {
  resize: vertical;
  min-height: 180px;
}

.폼-액션 {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  margin-top: 2.5rem;
}

.버튼 {
  padding: 0.9rem 1.8rem;
  font-size: 1.1rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.버튼-주요 {
  background-color: #3498db;
  color: white;
  border: none;
}

.버튼-주요:hover {
  background-color: #2980b9;
}

.버튼-보조 {
  background-color: #95a5a6;
  color: white;
  border: none;
}

.버튼-보조:hover {
  background-color: #7f8c8d;
}

.버튼:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
