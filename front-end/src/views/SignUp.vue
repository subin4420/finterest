<template>
  <div class="signup-body">
    <div class="signup-container">
      <div class="title">finterest</div>
      <div class="input-group">
        <label for="username">아이디</label>
        <input
          type="text"
          id="username"
          placeholder="아이디 입력하세요."
          required
        />
      </div>
      <div class="input-group">
        <label for="password">비밀번호</label>
        <input
          type="password"
          id="password"
          placeholder="비밀번호 입력하세요."
          required
        />
      </div>
      <div class="input-group">
        <label for="confirm_password">비밀번호 확인</label>
        <input
          type="password"
          id="confirm_password"
          placeholder="비밀번호 확인 입력하세요."
          required
        />
      </div>
      <div class="input-group">
        <label for="name">이름</label>
        <input type="text" id="name" placeholder="이름 입력하세요." required />
      </div>
      <div class="input-group">
        <label for="email">이메일</label>
        <input
          type="email"
          id="email"
          placeholder="이메일 입력하세요."
          required
        />
      </div>
      <button class="btn">회원가입</button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.signup-body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.signup-container {
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
  flex-direction: column;
  margin-bottom: 15px;
}

label {
  color: #666666;
  font-size: 14px;
  margin-bottom: 5px;
}

input[type="text"],
input[type="password"],
input[type="email"] {
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

.btn {
  background-color: #00c4d1;
  color: white;
  padding: 12px;
  margin-top: 30px;
  border: 1px solid #ccc;
  width: 100%;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.btn:hover {
  opacity: 0.9;
}
</style>
