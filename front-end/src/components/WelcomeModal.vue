<template>
  <div class="modal">
    <div class="modal-content">
      <h2>회원가입을 축하합니다!</h2>
      <p>회원가입을 해주셔서 감사합니다. 1,000 포인트가 지급되었습니다.</p>
      <button @click="$emit('close')" class="close-button">확인</button>
    </div>
  </div>
</template>

<script>
export default {
  emits: ['close']
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  max-width: 400px;
  width: 90%;
}

h2 {
  color: #00C4D1;
  margin-bottom: 15px;
}

p {
  margin-bottom: 20px;
}

.close-button {
  background-color: #00C4D1;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.close-button:hover {
  background-color: #0098a8;
}
</style>
