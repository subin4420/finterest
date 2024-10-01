<template>
  <div class="board-detail">
    <h2>{{ board.title }}</h2>
    <p>작성자: {{ getUserName(board.writer) }}</p>
    <div v-html="board.content"></div>

    <!-- 댓글 목록 -->
    <div class="comments">
      <h3>댓글</h3>
      <div v-for="comment in comments" :key="comment.commentId" class="comment">
        <p>{{ getUserName(comment.userId) }}: {{ comment.content }}</p>
        <button @click="deleteComment(comment.commentId)">삭제</button>
        <!-- 삭제 버튼 추가 -->
      </div>
    </div>

    <!-- 댓글 작성 폼 -->
    <div>
      <textarea
        v-model="newComment"
        placeholder="댓글을 작성하세요..."
      ></textarea>
      <button @click="submitComment">댓글 작성</button>
    </div>

    <button @click="goBack">목록으로 돌아가기</button>
  </div>
</template>

<script>
import boardApi from "@/api/boardApi"; // 경로 확인

export default {
  name: "BoardDetailPage",
  data() {
    return {
      board: {}, // 게시글 정보
      users: [], // 사용자 목록
      comments: [], // 댓글 목록
      newComment: "", // 새 댓글 내용
    };
  },
  created() {
    this.loadBoard(); // 게시글 로드
    this.loadUsers(); // 사용자 목록 로드
    this.loadComments(); // 댓글 목록 로드
  },
  methods: {
    async loadBoard() {
      const boardId = this.$route.params.id; // URL에서 ID 가져오기
      try {
        this.board = await boardApi.getBoard(boardId); // 게시글 데이터 로드
      } catch (error) {
        console.error("Failed to load board:", error);
        alert("게시글을 불러오는 중 오류가 발생했습니다.");
      }
    },
    async loadUsers() {
      try {
        this.users = await boardApi.getUsers(); // 사용자 목록 로드
      } catch (error) {
        console.error("Failed to load users:", error);
        alert("사용자 목록을 불러오는 중 오류가 발생했습니다.");
      }
    },
    async loadComments() {
      const boardId = this.$route.params.id; // URL에서 ID 가져오기
      try {
        this.comments = await boardApi.getComments(boardId); // 댓글 데이터 로드
      } catch (error) {
        console.error("Failed to load comments:", error);
        alert("댓글을 불러오는 중 오류가 발생했습니다.");
      }
    },
    getUserName(userId) {
      const user = this.users.find((user) => user.id === userId);
      return user ? user.name : "익명"; // 이름이 없으면 '익명' 반환
    },
    async submitComment() {
      const boardId = this.$route.params.id; // URL에서 ID 가져오기
      try {
        const newComment = {
          content: this.newComment,
          userId: 1, // 현재 로그인된 사용자 ID로 변경해야 함
          boardId: boardId,
        };
        await boardApi.createComment(newComment); // 댓글 생성 API 호출
        this.newComment = ""; // 댓글 입력란 초기화
        this.loadComments(); // 댓글 목록 새로고침
      } catch (error) {
        console.error("Failed to submit comment:", error);
        alert("댓글 작성 중 오류가 발생했습니다.");
      }
    },
    async deleteComment(commentId) {
      // 댓글 삭제 메소드 추가
      try {
        await boardApi.deleteComment(commentId); // 댓글 삭제 API 호출
        this.loadComments(); // 댓글 목록 새로고침
      } catch (error) {
        console.error("Failed to delete comment:", error);
        alert("댓글 삭제 중 오류가 발생했습니다.");
      }
    },
    goBack() {
      this.$router.push("/trade/community"); // 목록 페이지로 돌아가기
    },
  },
};
</script>

<style>
.board-detail {
  padding: 20px;
}
.board-detail h2 {
  margin-bottom: 10px;
}
.comments {
  margin-top: 20px;
}
.comment {
  margin-bottom: 10px;
}
</style>
