<template>
  <div class="reset-body">
    <div class="password-reset-container">
      <div class="title">비밀번호 찾기</div>
      <input type="text" placeholder="아이디" required />
      <input type="text" placeholder="이름" required />
      <input type="email" placeholder="이메일" required />
      <button class="btn" @click="handlePasswordReset">비밀번호 찾기</button>
      <div class="links">
        <router-link to="/login">로그인 </router-link> |
        <router-link to="/signup"> 회원가입</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    handlePasswordReset() {
      // ChangePassword.vue로 이동
      this.$router.push("/change-password");
    },
  },
};
</script>

<style scoped>
.reset-body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.password-reset-container {
  padding: 60px 70px;
  background: #ffffff;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 350px;
}

.title {
  color: #00c4d1;
  margin-bottom: 40px;
  /* 제목과 입력 필드 사이의 간격 늘림 */
  font-size: 28px;
  /* 글자 크기를 더 크게 설정 */
  font-weight: bold;
  /* 글자 굵기 추가 */
}

input[type="text"],
input[type="email"] {
  width: 100%;
  padding: 15px;
  /* 입력 필드의 패딩 늘림 */
  margin-bottom: 12px;
  /* 입력 필드 사이의 간격 늘림 */
  border: 1px solid #ccc;
  box-sizing: border-box;
  border-radius: 4px;
  font-size: 14px;
}

.btn {
  background-color: #00c4d1;
  color: white;
  padding: 14px 20px;
  margin-top: 20px;
  /* 버튼과 입력 필드 사이의 간격 늘림 */
  border: none;
  cursor: pointer;
  width: 100%;
  border-radius: 4px;
  font-size: 16px;
}

.btn:hover {
  opacity: 0.9;
}

.links {
  text-align: center;
  font-size: 14px;
  color: #00c4d1;
  margin-top: 12px;
  /* 링크와 버튼 사이의 간격 늘림 */
}

.links a {
  color: #00c4d1;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}
</style>
