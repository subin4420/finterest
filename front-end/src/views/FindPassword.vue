<template>
  <div class="changePW-body">
    <div class="change-password-container">
      <div class="title">비밀번호 변경</div>
      <div class="input-group">
        <input type="text" placeholder="인증번호" required />
        <button class="btn-small">인증</button>
      </div>
      <div class="input-group">
        <input type="password" placeholder="새로운 비밀번호" required />
      </div>
      <div class="input-group">
        <input type="password" placeholder="비밀번호 확인" required />
      </div>
      <button class="btn">비밀번호 변경</button>
    </div>
  </div>
</template>

<script>
export default {};
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
  /* 제목과 입력 필드 사이의 간격 늘림 */
  font-size: 28px;
  /* 글자 크기를 더 크게 설정 */
  font-weight: bold;
  /* 글자 굵기 추가 */
}

.input-group {
  width: 100%;
  display: flex;
  margin: 8px 0;
}

input[type="text"],
input[type="password"] {
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  flex-grow: 1;
  /* Allows input to fill space */
}

.btn,
.btn-small {
  background-color: #00c4d1;
  color: white;
  padding: 12px 15px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  border-radius: 4px;
  margin-left: 10px;
  /* Adds space between inputs and button */
}

.btn {
  width: 100%;
  /* Matches the full width of input groups */
  margin-left: 0;
  /* No margin for the main button */
  padding: 12px 0;
  /* Larger padding for better touch area */
}

.btn:hover,
.btn-small:hover {
  opacity: 0.9;
}

.link {
  text-align: center;
  font-size: 14px;
  color: #00c4d1;
}

.link a {
  color: #00c4d1;
  text-decoration: none;
}

.link a:hover {
  text-decoration: underline;
}
</style>
