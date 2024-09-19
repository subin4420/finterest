<template>
  <div class="changePW-body">
    <div class="change-password-container">
      <div class="title">비밀번호 변경</div>
      <div class="input-group">
        <input
          type="text"
          placeholder="인증번호"
          v-model="verificationCode"
          required
        />
        <button class="btn-small" @click="verifyCode">인증</button>
      </div>
      <div class="input-group">
        <input
          type="password"
          placeholder="새로운 비밀번호"
          v-model="newPassword"
          required
        />
      </div>
      <div class="input-group">
        <input
          type="password"
          placeholder="비밀번호 확인"
          v-model="confirmPassword"
          required
        />
      </div>
      <button class="btn" @click="changePassword">비밀번호 변경</button>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      verificationCode: "",
      newPassword: "",
      confirmPassword: "",
      errorMessage: "",
    };
  },
  methods: {
    verifyCode() {
      if (this.verificationCode !== "123456") {
        this.errorMessage = "인증번호가 올바르지 않습니다.";
      } else {
        this.errorMessage = "";
      }
    },
    changePassword() {
      if (this.newPassword !== this.confirmPassword) {
        this.errorMessage = "비밀번호가 일치하지 않습니다.";
      } else {
        this.errorMessage = "";
        console.log("비밀번호 변경 완료");
      }
    },
  },
};
</script>

<style>
.changePW-body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.change-password-container {
  padding: 60px 70px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 350px;
}

.title {
  color: #00c4d1;
  margin-bottom: 40px;
  font-size: 28px;
  font-weight: bold;
}

.input-group {
  width: 100%;
  display: flex;
  align-items: center;
  margin: 8px 0;
}

input[type="text"],
input[type="password"] {
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  width: calc(100% - 80px); /* 버튼 크기와 여백을 고려하여 input 너비 설정 */
}

.btn-small {
  background-color: #00c4d1;
  color: white;
  padding: 12px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  border-radius: 4px;
  margin-left: 10px;
  width: 70px; /* 버튼의 고정된 너비 */
}

.btn {
  width: 100%;
  margin-left: 0;
  padding: 12px 0;
  background-color: #00c4d1;
  color: white;
  border: none;
  cursor: pointer;
  font-size: 16px;
  border-radius: 4px;
}

.btn:hover,
.btn-small:hover {
  opacity: 0.9;
}

.error-message {
  color: red;
  margin-top: 20px;
  font-size: 14px;
}
</style>
