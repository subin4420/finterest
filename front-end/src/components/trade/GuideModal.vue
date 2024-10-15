<!-- front-end/src/components/trade/GuideModal.vue -->
<template>
  <div class="modal" v-if="isVisible">
    <div class="card">
      <div class="iconContainer">
        <span class="investmentIcon">💰</span>
        <!-- 모의투자와 관련된 기호 추가 -->
      </div>

      <h2 class="cookieHeading">모의투자 가이드</h2>
      <p class="cookieDescription" v-html="steps[currentStep].text"></p>
      <img :src="steps[currentStep].image" alt="Step Image" class="stepImage" />
      <div class="buttonContainer">
        <button class="acceptButton" @click="nextStep">다음</button>
        <button class="declineButton" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
import News from '@/assets/images/trade/news.png';
import Comment from '@/assets/images/trade/comment.png';
import Board from '@/assets/images/trade/board.png';
import Turn from '@/assets/images/trade/turn.png';
import Conversion from '@/assets/images/trade/conversion.png';
import Invest from '@/assets/images/trade/invest.png';
import Main from '@/assets/images/trade/main.png';
import Portfolio from '@/assets/images/trade/portfolio.png';

export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      currentStep: 0, // 현재 단계 초기화
      steps: [
        {
          text: '여기는 실전 모의투자를 경험할 수 있는 페이지입니다.<br>출석과 퀴즈를 통해 얻은 포인트를 모의투자금으로 전환하여 투자해보세요!',
          image: Main, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '실시간 주식 데이터를 바탕으로 한 모의 투자를 할 수 있는 모의투자 페이지입니다.',
          image: Invest, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '투자 현황과 수익률을 한 눈에 볼 수 있는 포트폴리오 페이지입니다.',
          image: Portfolio, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '관심 종목과 뉴스를 검색할 수 있는 뉴스 페이지입니다.',
          image: News, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '다양한 투자 정보를 공유할 수 있는 커뮤니티 페이지입니다.',
          image: Board, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '게시글에 대해서 자유롭게 댓글을 남기고 의견을 나눠보세요!',
          image: Comment, // 수정된 부분: 이미지 경로를 올바르게 설정
        },
        {
          text: '출석과 퀴즈를 통해 얻은 포인트를 모의투자금으로 전환해보세요!<br>투자를 통해 얻은 모의투자금을 포인트로 변경해 랭킹을 올려보세요!.',
          image: Conversion,
        },

        {
          text: '투자 시장 마감 시,<br>투자 경험을 쌓을 수 있는 턴제 모의투자 페이지입니다.',
          image: Turn,
        },
      ],
    };
  },
  methods: {
    closeModal() {
      this.currentStep = 0; // 모달 닫을 때 단계 초기화
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
  height: 500px;
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
  cursor: pointer;
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
  height: 200px; /* 추가된 부분: 이미지 높이를 고정 */
  object-fit: cover; /* 추가된 부분: 이미지 비율 유지 */
  margin-top: 10px;
}
</style>
