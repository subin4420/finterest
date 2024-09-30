<template>
  <div class="community-page">
    <TradeImage />
    <TradeNavigationBar />

    <h2>게시판 목록</h2>
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(board, index) in boardList" :key="board.boardId">
          <td>{{ index + 1 }}</td>
          <td @click="viewBoard(board.boardId)" style="cursor: pointer">
            {{ board.title }}
          </td>
          <td>{{ getUserName(board.userId) }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 작성 버튼 추가 (오른쪽 정렬 및 여백 추가) -->
    <div class="create-button">
      <button @click="goToCreateBoard">게시글 작성</button>
    </div>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue';
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue';
import boardApi from '@/api/boardApi';

export default {
  name: 'CommunityPage',
  components: {
    TradeImage,
    TradeNavigationBar,
  },
  data() {
    return {
      boardList: [], // 게시글 목록
      users: [], // 사용자 목록
    };
  },
  created() {
    this.loadBoards(); // 게시글 목록 로드
    this.loadUsers(); // 사용자 목록 로드
  },
  methods: {
    async loadBoards() {
      this.boardList = await boardApi.getList(); // API 호출하여 게시글 목록 가져오기
    },
    async loadUsers() {
      this.users = await boardApi.getUsers(); // API 호출하여 사용자 목록 가져오기
    },
    getUserName(userId) {
      const user = this.users.find((user) => user.id === userId); // 사용자 ID로 사용자 이름 찾기
      return user ? user.name : '익명'; // 이름이 없으면 '익명' 반환
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
      return new Date(date).toLocaleString('ko-KR', options); // 날짜 형식 변환
    },
    viewBoard(boardId) {
      this.$router.push(`/board/${boardId}`); // 특정 게시글 보기로 이동
    },
    goToCreateBoard() {
      this.$router.push('/trade/community/create'); // 게시글 작성 페이지로 이동
    },
  },
};
</script>

<style>
.community-page {
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
.create-button {
  margin-top: 20px; /* 리스트와 버튼 간격 조정 */
  text-align: right; /* 버튼을 오른쪽 정렬 */
}
button {
  margin-bottom: 10px; /* 버튼과 테이블 사이의 여백 */
}
</style>
