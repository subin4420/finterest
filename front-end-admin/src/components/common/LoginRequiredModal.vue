<template>
  <div class="modal-overlay" @click="$emit('close')">
    <div class="modal-content" @click.stop>
      <h2>로그인 필요</h2>
      <p>해당 컨텐츠는 로그인 후 이용할 수 있습니다.</p>
      <div class="button-group">
        <button @click="$emit('login')" class="login-button">로그인하기</button>
        <button @click="$emit('close')" class="back-button">뒤로가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginRequiredModal'
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.modal-content {
  background-color: white;
  padding: 30px;
  border-radius: 12px;
  text-align: center;
  max-width: 400px;
  width: 90%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 2001;
}

h2 {
  font-size: 24px;
  margin-bottom: 15px;
  color: #333;
}

p {
  font-size: 16px;
  margin-bottom: 20px;
  color: #666;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.login-button, .back-button {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s, transform 0.1s;
}

.login-button {
  background-color: #00C4D1;
  color: white;
}

.login-button:hover {
  background-color: #00a8b3;
}

.back-button {
  background-color: #f0f0f0;
  color: #333;
}

.back-button:hover {
  background-color: #e0e0e0;
}

.login-button:active, .back-button:active {
  transform: scale(0.98);
}

@media (max-width: 480px) {
  .modal-content {
    padding: 20px;
  }

  h2 {
    font-size: 20px;
  }

  p {
    font-size: 14px;
  }

  .login-button, .back-button {
    padding: 8px 16px;
    font-size: 14px;
  }
}
</style>
