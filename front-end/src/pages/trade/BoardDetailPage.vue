<script setup>
import { useRoute, useRouter } from 'vue-router';
import api from '@/api/boardApi';
import { ref, onMounted } from 'vue';
import moment from 'moment';
import { useAuthStore } from '@/stores/auth';
import { downloadFile } from '@/util/download';
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import SideTradeNavigationBar from '@/components/trade/SideTradeNavigationBar.vue';

const auth = useAuthStore();
const cr = useRoute();
const router = useRouter();

const no = cr.params.no;
const article = ref({});
const comments = ref([]);
const newCommentContent = ref('');
const newCommentWriter = ref('');

const back = () => {
  router.push({ name: 'board/list', query: cr.query });
};

const update = () => {
  router.push({ name: 'board/update', params: { no: no }, query: cr.query });
};

const remove = async () => {
  if (!confirm('정말로 삭제하시겠습니까?')) return;
  try {
    await api.delete(no);
    alert('게시글이 성공적으로 삭제되었습니다.');
    router.push({ name: 'board/list', query: cr.query });
  } catch (error) {
    console.error('게시글 삭제 중 오류 발생:', error);
    alert('게시글 삭제에 실패했습니다. 다시 시도해 주세요.');
  }
};

const download = async (no) => {
  try {
    const URL = '/api/board/download/' + no;
    await downloadFile(URL);
    alert('파일 다운로드가 시작되었습니다.');
  } catch (error) {
    console.error('파일 다운로드 중 오류 발생:', error);
    alert('파일 다운로드에 실패했습니다. 다시 시도해 주세요.');
  }
};

const load = async () => {
  try {
    article.value = await api.get(no);
  } catch (error) {
    console.error('게시글 로딩 중 오류 발생:', error);
    alert('게시글을 불러오는데 실패했습니다. 다시 시도해 주세요.');
  }
};

const loadComments = async () => {
  try {
    comments.value = await api.getComments(no);
    console.log(`게시글 ${no}의 댓글을 성공적으로 불러왔습니다.`);
  } catch (error) {
    console.error(`게시글 ${no}의 댓글 로딩 중 오류 발생:`, error);
    alert('댓글을 불러오는데 실패했습니다. 페이지를 새로고침 해주세요.');
  }
};

const addComment = async () => {
  if (!newCommentContent.value.trim()) {
    alert('댓글 내용을 입력해주세요.');
    return;
  }

  const commentData = {
    content: newCommentContent.value,
    writer: newCommentWriter.value.trim() || '익명',
    bno: no,
  };

  try {
    await api.addComment(no, commentData);
    alert('댓글이 성공적으로 추가되었습니다.');
    await loadComments();
    newCommentContent.value = '';
    newCommentWriter.value = '';
  } catch (error) {
    console.error('댓글 추가 중 오류 발생:', error);
    alert('댓글 추가에 실패했습니다. 다시 시도해 주세요.');
  }
};

onMounted(() => {
  load();
  loadComments();
});
</script>

<template>
  <div class="페이지-컨테이너">
    <div class="거래-헤더"></div>
    <div class="콘텐츠-래퍼">
      <SideTradeNavigationBar />
      <div class="콘텐츠">
        <h1 class="페이지-제목">게시글 상세</h1>
        <div class="게시판-상세-컨테이너">
          <h2 class="게시판-제목">{{ article.title }}</h2>
          <div class="게시판-메타">
            <div class="게시판-작성자">
              <i class="fa-solid fa-user"></i>
              {{ article.writer }}
            </div>
            <div class="게시판-날짜">
              <i class="fa-regular fa-clock"></i>
              {{ moment(article.updateDate).format('YYYY-MM-DD HH:mm') }}
            </div>
          </div>
          <hr class="게시판-구분선" />
          <div class="게시판-첨부파일">
            <div
              v-for="file in article.attaches"
              :key="file.no"
              class="첨부파일"
            >
              <span @click="download(file.no)">
                <i class="fa-solid fa-paperclip"></i>
                {{ file.filename }}
              </span>
            </div>
          </div>
          <div class="게시판-내용">{{ article.content }}</div>
          <div class="게시판-액션">
            <button class="버튼" @click="back">
              <i class="fa-solid fa-list"></i> 목록
            </button>
            <template v-if="auth.username == article.writer">
              <button class="버튼" @click="update">
                <i class="fa-regular fa-pen-to-square"></i> 수정
              </button>
              <button class="버튼" @click="remove">
                <i class="fa-solid fa-trash-can"></i> 삭제
              </button>
            </template>
          </div>

          <div class="댓글-섹션">
            <h2 class="댓글-제목">댓글</h2>
            <div v-if="comments && comments.length > 0" class="댓글-목록">
              <div v-for="comment in comments" :key="comment.no" class="댓글">
                <p class="댓글-내용">{{ comment.content }}</p>
                <small class="댓글-메타"
                  >작성자: {{ comment.writer }} |
                  {{
                    moment(comment.regDate).format('YYYY-MM-DD HH:mm')
                  }}</small
                >
              </div>
            </div>
            <div v-else class="댓글-없음">
              <p>아직 댓글이 없습니다. 첫 댓글을 작성해보세요!</p>
            </div>

            <div class="댓글-폼">
              <input
                v-model="newCommentWriter"
                placeholder="이름 (선택사항)"
                class="댓글-입력 댓글-작성자"
              />
              <textarea
                v-model="newCommentContent"
                placeholder="댓글을 입력하세요"
                class="댓글-입력 댓글-내용-입력"
              ></textarea>
              <button @click="addComment" class="버튼 버튼-댓글">
                댓글 작성
              </button>
            </div>
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
  min-height: 100vh;
  background-color: #ffffff;
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
  flex: 1;
}

.콘텐츠 {
  flex: 1;
  margin-left: 250px;
  padding: 20px;
}

.페이지-제목 {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #2e78e0;
}

.게시판-상세-컨테이너 {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #ffffff;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.게시판-제목 {
  font-size: 2.2rem;
  color: #2e78e0;
  margin-bottom: 1rem;
}

.게시판-메타 {
  display: flex;
  justify-content: space-between;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.게시판-구분선 {
  border: none;
  border-top: 1px solid #eee;
  margin: 1rem 0;
}

.게시판-첨부파일 {
  text-align: right;
  margin-bottom: 1rem;
}

.첨부파일 {
  font-size: 0.9rem;
  color: #2e78e0;
  cursor: pointer;
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}

.첨부파일:hover {
  color: #1c4f94;
  text-decoration: underline;
}

.게시판-내용 {
  white-space: pre-line;
  color: #333;
  line-height: 1.8;
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.게시판-액션 {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}

.버튼 {
  padding: 0.7rem 1.5rem;
  font-size: 1rem;
  border-radius: 6px;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
  font-weight: bold;
  background-color: #f0f0f0;
  color: #333;
}

.버튼:hover {
  background-color: #e0e0e0;
}

.댓글-섹션 {
  margin-top: 3rem;
  background-color: #f9f9f9;
  padding: 2rem;
  border-radius: 8px;
}

.댓글-제목 {
  font-size: 1.5rem;
  color: #2e78e0;
  margin-bottom: 1rem;
}

.댓글-목록 {
  margin-bottom: 2rem;
}

.댓글 {
  border-bottom: 1px solid #eee;
  padding: 1rem 0;
}

.댓글-내용 {
  font-size: 1rem;
  color: #333;
  margin-bottom: 0.5rem;
}

.댓글-메타 {
  font-size: 0.8rem;
  color: #666;
}

.댓글-없음 {
  color: #666;
  font-style: italic;
}

.댓글-폼 {
  margin-top: 2rem;
}

.댓글-입력 {
  width: 100%;
  margin-bottom: 1rem;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.댓글-작성자 {
  width: 50%;
}

.댓글-내용-입력 {
  height: 100px;
  resize: vertical;
}

.버튼-댓글 {
  background-color: #2e78e0;
  color: white;
}

.버튼-댓글:hover {
  background-color: #1c4f94;
}
</style>
