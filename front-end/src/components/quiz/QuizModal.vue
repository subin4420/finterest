<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <button class="close-button" @click="closeModal">&times;</button>
      <div class="modal-header">
        <button class="back-button" @click="closeModal">뒤로가기</button>
        <span class="learning-status">학습중</span>
        <hr />
        <div class="title-section">
          <img
            src="https://cdn.pixabay.com/photo/2024/01/02/20/13/dry-8483913_1280.jpg"
            alt="자료 이미지"
            class="modal-image"
          />
          <div class="title-info">
            <h3>제목 : {{ cardData.title }}</h3>
            <p>카테고리 : {{ cardData.categoryName }}</p>
          </div>
        </div>
      </div>
      <div class="content-section">
        <p>{{ cardData.content }}</p>
      </div>
      <button class="complete-button" @click="markComplete">학습완료</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: Boolean,
    cardData: {
      type: Object,
      required: true,
    },
  },
  methods: {
    closeModal() {
      this.$emit('update:isVisible', false);
    },
    markComplete() {
      // 학습완료 로직 처리
      console.log('학습완료 처리');
      this.closeModal();
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  width: 80%;
  max-width: 800px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  /* Makes modal content flow vertically */
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
  background: none;
  font-size: 24px;
  cursor: pointer;
}

.back-button {
  color: #007bff;
  background: none;
  border: none;
  cursor: pointer;
}

.learning-status {
  display: block;
  text-align: right;
  margin-top: 10px;
}

.title-section {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  /* Adds space below the title section */
}

.title-info {
  margin-left: 20px;
  /* Adds space between image and text */
}

img {
  width: 35%;
  height: 35%;
  object-fit: cover;
  /* 이미지가 박스에 맞게 조절되도록 설정 */
}

.content-section {
  flex-grow: 1;
  /* Allows this section to take up available space */
  margin-bottom: 20px;
  /* Adds space above the complete button */
}

.complete-button {
  background-color: #00c4d1;
  color: white;
  padding: 16px 20px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
  align-self: flex-end;
  /* Aligns the button to the right */
}

.complete-button:hover {
  opacity: 0.8;
}
</style>
