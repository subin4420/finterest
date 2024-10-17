<template>
  <div class="board-list">
    <h2>게시판 목록</h2>
    <button @click="goToCreateBoard">작성목록</button>
    <!-- 작성목록 버튼 추가 -->
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
          <td>{{ getUserName(board.userId) }}</td>
          <td>{{ formatDate(board.createdAt) }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import boardApi from '@/api/boardApi';

export default {
  name: 'BoardList',
  data() {
    return {
      boardList: [],
      users: [],
    };
  },
  created() {
    this.loadBoards();
    this.loadUsers();
  },
  methods: {
    async loadBoards() {
      this.boardList = await boardApi.getList();
    },
    async loadUsers() {
      this.users = await boardApi.getUsers();
    },
    getUserName(userId) {
      const user = this.users.find((user) => user.id === userId);
      return user ? user.name : '익명';
    },
    formatDate(date) {
      const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        hour12: false,
      };
      return new Date(date).toLocaleString('ko-KR', options);
    },
    viewBoard(boardId) {
      this.$router.push(`/board/${boardId}`);
    },
    goToCreateBoard() {
      // 작성목록 버튼 클릭 시 호출될 메소드
      this.$router.push('/board/create'); // BoardCreate 페이지로 이동
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
button {
  margin-bottom: 10px; /* 버튼과 테이블 사이의 여백 */
}
</style>
