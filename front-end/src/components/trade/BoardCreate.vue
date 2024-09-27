<template>
  <div class="board-create">
    <h2>게시글 작성</h2>
    <form @submit.prevent="createBoard">
      <div>
        <label for="title">제목</label>
        <input type="text" v-model="article.title" required />
      </div>
      <div>
        <label for="writer">작성자</label>
        <input type="text" v-model="article.writer" required />
      </div>
      <div>
        <label for="content">내용</label>
        <textarea v-model="article.content" required></textarea>
      </div>
      <button type="submit">작성</button>
    </form>
  </div>
</template>

<script>
import boardApi from '@/api/boardApi';

export default {
  name: 'BoardCreate',
  data() {
    return {
      article: {
        title: '',
        writer: '',
        content: '',
      },
    };
  },
  methods: {
    async createBoard() {
      await boardApi.create(this.article);
      this.$router.push('/board/list'); // 게시글 작성 후 목록 페이지로 이동
    },
  },
};
</script>

<style>
.board-create {
  width: 100%;
}
form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
label {
  font-weight: bold;
}
button {
  margin-top: 10px;
}
</style>
