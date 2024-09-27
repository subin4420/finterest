<template>
  <div class="board-list">
    <h2>게시판 목록</h2>
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(board, index) in boardList" :key="board.boardId">
          <td>{{ index + 1 }}</td>
          <td @click="viewBoard(board.boardId)" style="cursor: pointer">
            {{ board.title }}
          </td>
          <td>{{ board.writer }}</td>
          <td>{{ board.createdDate }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import boardApi from '@/api/boardApi'; // 위에서 구현한 board API 사용

export default {
  name: 'BoardList',
  data() {
    return {
      boardList: [],
    };
  },
  created() {
    this.loadBoards();
  },
  methods: {
    async loadBoards() {
      this.boardList = await boardApi.getList();
    },
    viewBoard(boardId) {
      this.$router.push(`/board/${boardId}`);
    },
  },
};
</script>

<style>
.board-list {
  width: 100%;
}
table {
  width: 100%;
  border-collapse: collapse;
}
th,
td {
  border: 1px solid #ddd;
  padding: 8px;
}
th {
  background-color: #f2f2f2;
}
td:hover {
  background-color: #f5f5f5;
}
</style>
