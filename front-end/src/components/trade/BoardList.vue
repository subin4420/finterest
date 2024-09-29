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
          <td>{{ getUserName(board.userId) }}</td>
          <!-- 작성자 이름 가져오기 -->
          <td>{{ formatDate(board.createdAt) }}</td>
          <!-- 작성일 포맷팅 -->
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
      users: [], // 사용자 정보를 저장할 배열
    };
  },
  created() {
    this.loadBoards();
    this.loadUsers(); // 사용자 정보 로드
  },
  methods: {
    async loadBoards() {
      this.boardList = await boardApi.getList();
    },
    async loadUsers() {
      this.users = await boardApi.getUsers(); // 사용자 정보를 가져오는 API 호출
    },
    getUserName(userId) {
      const user = this.users.find((user) => user.id === userId);
      return user ? user.name : '익명'; // 사용자가 없을 경우 '익명' 표시
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
      return new Date(date).toLocaleString('ko-KR', options); // 날짜 포맷팅
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
