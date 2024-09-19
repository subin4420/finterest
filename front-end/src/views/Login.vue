<template>
  <div class="login-container">
    <div class="title">finterest</div>
    <input type="text" placeholder="아이디" required />
    <input type="password" placeholder="비밀번호" required />
    <button class="btn">로그인</button>
    <div class="links">
      <a href="#">비밀번호 찾기</a> | <a href="#">회원가입</a>
    </div>
  </div>
</template>

<script setup></script>

<style>
.login-container {
  padding: 60px 70px;
  /* 컨테이너의 패딩을 조정하여 크기를 늘림 */
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 400px;
  /* 컨테이너의 너비를 늘림 */
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
input[type="password"] {
  width: 100%;
  padding: 15px;
  /* 입력 필드의 패딩을 조정하여 더 크게 만듦 */
  margin: 10px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
  border-radius: 4px;
}

.btn {
  background-color: #00c4d1;
  color: white;
  padding: 16px 20px;
  /* 버튼의 패딩을 늘림 */
  margin: 20px 0;
  /* 버튼과 링크 사이의 간격 조정 */
  border: none;
  cursor: pointer;
  width: 100%;
  border-radius: 4px;
}

.btn:hover {
  opacity: 0.8;
}

.links {
  text-align: center;
  /* 링크를 중앙 정렬 */
  color: #00c4d1;
  font-size: 14px;
}

.links a {
  color: #00c4d1;
  text-decoration: none;
}

.links a:hover {
  text-decoration: underline;
}
</style>
