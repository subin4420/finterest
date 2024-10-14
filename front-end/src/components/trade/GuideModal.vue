<!-- front-end/src/components/trade/GuideModal.vue -->
<template>
  <div class="modal" v-if="isVisible">
    <div class="card">
      <svg
        id="cookieSvg"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 50 50"
      >
        <g>
          <path
            d="M25 0C11.2 0 0 11.2 0 25s11.2 25 25 25 25-11.2 25-25S38.8 0 25 0zm0 46.5c-11.8 0-21.5-9.7-21.5-21.5S13.2 3.5 25 3.5 46.5 13.2 46.5 25 36.8 46.5 25 46.5z"
          />
        </g>
      </svg>
      <h2 class="cookieHeading">모의투자 가이드</h2>
      <p class="cookieDescription">{{ steps[currentStep].text }}</p>
      <img :src="steps[currentStep].image" alt="Step Image" class="stepImage" />
      <div class="buttonContainer">
        <button class="acceptButton" @click="nextStep">다음</button>
        <button class="declineButton" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      currentStep: 0,
      steps: [
        {
          text: '여기는 실전 모의투자를 경험할 수 있는 페이지입니다. 출석과 퀴즈를 통해 얻은 포인트를 모의투자금으로 전환하여 투자해보세요!',
        },
        {
          text: '모실제 주식 데이터를 바탕으로 한 차트를 확인할 수 있습니다.',
          image: 'url_to_image_2.jpg',
        },
        {
          text: '마지막 단계의 내용입니다.',
          image: 'url_to_image_3.jpg',
        },
      ],
    };
  },
  methods: {
    closeModal() {
      this.$emit('update:isVisible', false);
    },
    nextStep() {
      if (this.currentStep < this.steps.length - 1) {
        this.currentStep++;
      } else {
        this.closeModal();
      }
    },
  },
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
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.card {
  width: 600px;
  height: 220px;
  background-color: rgb(255, 255, 255);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 30px;
  gap: 13px;
  position: relative;
  overflow: hidden;
  box-shadow: 2px 2px 20px rgba(0, 0, 0, 0.062);
}

#cookieSvg {
  width: 50px;
}

#cookieSvg g path {
  fill: rgb(97, 81, 81);
}

.cookieHeading {
  font-size: 1.2em;
  font-weight: 800;
  color: rgb(26, 26, 26);
}

.cookieDescription {
  text-align: center;
  font-size: 0.7em;
  font-weight: 600;
  color: rgb(99, 99, 99);
}

.cookieDescription a {
  --tw-text-opacity: 1;
  color: rgb(59, 130, 246);
}

.cookieDescription a:hover {
  -webkit-text-decoration-line: underline;
  text-decoration-line: underline;
}

.buttonContainer {
  display: flex;
  gap: 20px;
  flex-direction: row;
}

.acceptButton {
  width: 80px;
  height: 30px;
  background-color: #7b57ff;
  transition-duration: 0.2s;
  border: none;
  color: rgb(241, 241, 241);
  cursor: pointer;
  font-weight: 600;
  border-radius: 20px;
  box-shadow: 0 4px 6px -1px #977ef3, 0 2px 4px -1px #977ef3;
  transition: all 0.6s ease;
}

.declineButton {
  width: 80px;
  height: 30px;
  background-color: #dadada;
  transition-duration: 0.2s;
  color: rgb(46, 46, 46);
  border: none;
  cursor: not-allowed;
  font-weight: 600;
  border-radius: 20px;
  box-shadow: 0 4px 6px -1px #bebdbd, 0 2px 4px -1px #bebdbd;
  transition: all 0.6s ease;
}

.declineButton:hover {
  background-color: #ebebeb;
  box-shadow: 0 10px 15px -3px #bebdbd, 0 4px 6px -2px #bebdbd;
  transition-duration: 0.2s;
}

.acceptButton:hover {
  background-color: #9173ff;
  box-shadow: 0 10px 15px -3px #977ef3, 0 4px 6px -2px #977ef3;
  transition-duration: 0.2s;
}

.stepImage {
  width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
