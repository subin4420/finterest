<template>
  <TradeImage />
  <TradeNavigationBar />

  <div class="board-detail">
    <h2 class="board-title">{{ isEditing ? '게시글 수정' : board.title }}</h2>
    <p class="board-author">작성자: {{ getUserName(board.writer) }}</p>

    <div v-if="isEditing">
      <textarea
          v-model="editedContent"
          class="edit-input"
          placeholder="수정할 내용을 입력하세요..."
      ></textarea>
      <button @click="updateBoard" class="submit-button">수정 완료</button>
      <button @click="isEditing = false" class="cancel-button">취소</button>
    </div>

    <div v-else>
      <div class="board-content" v-html="board.content"></div>
      <button @click="startEditing" class="edit-button">수정하기</button>
    </div>

    <!-- 댓글 목록 -->
    <div class="comments">
      <h3>댓글</h3>
      <div v-for="comment in comments" :key="comment.commentId" class="comment">
        <div class="comment-header">
          <span class="comment-author">{{ getUserName(comment.userId) }}</span>
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <button class="delete-button" @click="deleteComment(comment.commentId)">
          삭제
        </button>
      </div>
    </div>

    <!-- 댓글 작성 폼 -->
    <div class="comment-form">
      <textarea
          v-model="newComment"
          placeholder="댓글을 작성하세요..."
          class="comment-input"
      ></textarea>
      <button @click="submitComment" class="submit-button">댓글 작성</button>
    </div>

    <button @click="goBack" class="back-button">목록으로 돌아가기</button>
  </div>
</template>

<script>
import TradeImage from '@/components/trade/TradeImage.vue'; // 경로 확인
import TradeNavigationBar from '@/components/trade/TradeNavigationBar.vue'; // 경로 확인
import boardApi from '@/api/boardApi'; // 경로 확인

export default {
  name: 'BoardDetailPage',
  components: {
    TradeImage,
    TradeNavigationBar,
  },
  data() {
    return {
      board: {}, // 게시글 정보
      users: [], // 사용자 목록
      comments: [], // 댓글 목록
      newComment: '', // 새 댓글 내용
      isEditing: false, // 수정 모드 상태
      editedContent: '', // 수정할 내용
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
        this.editedContent = this.board.content; // 수정 시 내용 초기화
      } catch (error) {
        console.error('Failed to load board:', error);
        alert('게시글을 불러오는 중 오류가 발생했습니다.');
      }
    },
    async loadUsers() {
      try {
        this.users = await boardApi.getUsers(); // 사용자 목록 로드
      } catch (error) {
        console.error('Failed to load users:', error);
        alert('사용자 목록을 불러오는 중 오류가 발생했습니다.');
      }
    },
    async loadComments() {
      const boardId = this.$route.params.id; // URL에서 ID 가져오기
      try {
        this.comments = await boardApi.getComments(boardId); // 댓글 데이터 로드
      } catch (error) {
        console.error('Failed to load comments:', error);
        alert('댓글을 불러오는 중 오류가 발생했습니다.');
      }
    },
    getUserName(userId) {
      const user = this.users.find((user) => user.id === userId);
      return user ? user.name : '익명'; // 이름이 없으면 '익명' 반환
    },
    async startEditing() {
      this.isEditing = true; // 수정 모드로 전환
    },
    async updateBoard() {
      const boardId = this.$route.params.id; // URL에서 ID 가져오기
      try {
        await boardApi.updateBoard(boardId, { content: this.editedContent }); // 게시글 수정 API 호출
        this.isEditing = false; // 수정 모드 종료
        this.loadBoard(); // 게시글 새로 고침
      } catch (error) {
        console.error('Failed to update board:', error);
        alert('게시글 수정 중 오류가 발생했습니다.');
      }
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
        this.newComment = ''; // 댓글 입력란 초기화
        this.loadComments(); // 댓글 목록 새로고침
      } catch (error) {
        console.error('Failed to submit comment:', error);
        alert('댓글 작성 중 오류가 발생했습니다.');
      }
    },
    async deleteComment(commentId) {
      try {
        await boardApi.deleteComment(commentId); // 댓글 삭제 API 호출
        this.loadComments(); // 댓글 목록 새로고침
      } catch (error) {
        console.error('Failed to delete comment:', error);
        alert('댓글 삭제 중 오류가 발생했습니다.');
      }
    },
    goBack() {
      this.$router.push('/trade/community'); // 목록 페이지로 돌아가기
    },
  },
};
</script>

<style>
.board-detail {
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.board-title {
  font-size: 24px;
  margin-bottom: 10px;
  color: #333;
}

.board-author {
  margin-bottom: 15px;
  color: #777;
}

.board-content {
  margin-bottom: 20px;
  line-height: 1.6;
}

.edit-input {
  width: 100%;
  height: 60px;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.comments {
  margin-top: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.comment {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
  font-weight: bold;
}

.comment-author {
  color: #4caf50;
}

.comment-content {
  margin-bottom: 5px;
}

.delete-button {
  background-color: #e57373;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #d32f2f;
}

.comment-form {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}

.comment-input {
  height: 60px;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #45a049;
}

.back-button {
  margin-top: 15px;
  background-color: #2196f3;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
}

.back-button:hover {
  background-color: #1976d2;
}

.edit-button {
  background-color: #ff9800;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: #fb8c00;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button:hover {
  background-color: #e53935;
}
</style>
