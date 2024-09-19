<template>
  <div class="login-body">
    <div class="login-container">
      <div class="title">finterest</div>
      <input type="text" placeholder="아이디" required />
      <input type="password" placeholder="비밀번호" required />
      <button class="btn">로그인</button>
      <div class="links">
        <router-link to="/find-password">비밀번호 찾기 </router-link> |
        <router-link to="/signup"> 회원가입</router-link>
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style>
.login-body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-container {
  padding: 60px 70px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 400px;
}

.title {
  color: #00c4d1;
  margin-bottom: 40px;
  font-size: 28px;
  font-weight: bold;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 15px;
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
  margin: 20px 0;
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
