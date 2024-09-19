<template>
  <div class="profile-body">
    <div class="profile-container">
      <div class="profile-pic-container">
        <img src="../assets/img/profile.png" alt="Profile Picture" />
      </div>
      <button class="btn-photo">사진선택</button>
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
      <div class="buttons-container">
        <button class="btn btn-save">저장</button>
        <button class="btn btn-cancel">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.profile-body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.profile-container {
  padding: 60px 70px;
  width: 350px;
  background: #ffffff;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.profile-pic-container {
  width: 100px;
  height: 100px;
  margin: 20px auto;
  background-color: #00c4d1;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 48px;
  color: white;
  position: relative;
}

.profile-pic-container img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.btn-photo {
  width: 100px;
  /* Matching profile pic size for alignment */
  background-color: #00c4d1;
  margin-bottom: 20px;
  color: white;
  border-radius: 4px;
  font-size: 16px;
  /* Aligning center and providing space */
  border: none;
  cursor: pointer;
}

.input-group {
  margin-bottom: 15px;
}

label {
  display: block;
  text-align: left;
  margin-bottom: 5px;
  color: #666;
  font-size: 14px;
}

input[type="text"],
input[type="password"],
input[type="email"] {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}

.buttons-container {
  display: flex;
  justify-content: space-around;
  margin-top: 30px;
}

.btn {
  padding: 12px;
  background-color: #00c4d1;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  flex: 1;
  margin: 20px;
}

.btn:hover {
  opacity: 0.8;
}

.btn-cancel {
  background-color: #ccc;
  color: #666;
}
</style>
