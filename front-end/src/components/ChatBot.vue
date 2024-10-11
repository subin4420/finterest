<template>
  <div class="chatbot-container" v-if="isOpen">
    <div class="chatbot-content">
      <div class="header">
        <h5>Finterest AI 챗봇</h5>
        <br />
        <h6>💬AI챗봇을 통해 문의를 해결해보세요!</h6>
        <button class="close" @click="closeChatBot">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            class="bi bi-chevron-down"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708"
            />
          </svg>
        </button>
      </div>
      <div class="responses" ref="responsesContainer">
        <div
          v-for="(msg, index) in messages"
          :key="index"
          :class="{
            'user-message': msg.sender === 'user',
            'bot-message': msg.sender === 'bot',
          }"
        >
          <div class="profile" v-if="index === messages.length - 1">
            <img
              src="@/assets/images/chatbot-image.png"
              alt="@/assets/chatbot-image.png"
            />
          </div>
          <div>
            <span class="message-text">{{ msg.text }}</span>
          </div>
          <div class="timeStampDiv">
            <span class="timestamp">{{ msg.timestamp }}</span>
          </div>
        </div>
      </div>
      <div class="footer">
        <div class="input-container">
          <textarea
            ref="userInputArea"
            v-model="userInput"
            placeholder="AI 챗봇에게 물어보세요"
            @input="resizeTextarea"
            @keydown.enter.prevent="sendMessage"
          ></textarea>
          <button @click="sendMessage" class="send-button">
            <span class="material-symbols-outlined"
              ><b>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  class="bi bi-send-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471z"
                  />
                </svg> </b
            ></span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <img
    class="chatbot-icon"
    v-if="!isOpen"
    @click="openChatBot"
    src="../assets/chatbot-icon.png"
    alt="챗봇 열기"
  />
</template>

<script>
import axios from 'axios';

export default {
  emits: ['close'],
  data() {
    return {
      userInput: '',
      messages: JSON.parse(localStorage.getItem('chatMessages')) || [],
      isOpen: false,
      isGreetingSent: false,
    };
  },
  methods: {
    closeChatBot() {
      this.isOpen = false;
      this.$emit('close');
    },
    openChatBot() {
      this.isOpen = true;
      if (!this.isGreetingSent) {
        this.sendGreeting();
        this.isGreetingSent = true;
      }
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },
    async sendMessage() {
      if (!this.userInput.trim()) {
        console.error('사용자 입력이 없습니다.');
        return;
      }

      const message = {
        sender: 'user',
        text: this.userInput,
        timestamp: this.getCurrentTime(),
      };

      this.messages.push(message);
      this.saveMessages();
      console.log('사용자 입력:', this.userInput);

      try {
        const response = await axios.post(
          `/api/chatbot/ask/${this.userInput}`, //수정
          {
            model: 'gpt-4o',
            messages: [
              {
                role: 'user',
                query: this.userInput, // 사용자가 입력한 메시지
              },
            ],
          },
          // {
          //   query: this.userInput,
          //   response_mode: 'streaming',
          //   user: 'unique_user_id', // 유저 식별자 (유니크하게 설정 필요)
          //   conversation_id:
          //     this.messages.length > 1
          //       ? this.messages[this.messages.length - 2].conversation_id
          //       : undefined,
          // },
          {
            headers: {
              'Content-Type': 'application/json',
            },
          }
        );

        const botMessage = {
          sender: 'bot',
          text: response.data || '답변을 가져오지 못했습니다.', //수정
          timestamp: this.getCurrentTime(),
        };

        this.messages.push(botMessage);
        this.saveMessages();
      } catch (error) {
        console.error('에러 발생:', error);
        this.messages.push({
          sender: 'bot',
          text: '챗봇 응답 오류가 발생했습니다.',
          timestamp: this.getCurrentTime(),
        });
      } finally {
        this.userInput = '';
        this.resizeTextarea();
        this.scrollToBottom();
      }
    },
    sendGreeting() {
      const greetingMessage = {
        sender: 'bot',
        text: '안녕하세요! Finterst 챗봇 입니다. 무엇을 도움드릴까요?',
        timestamp: this.getCurrentTime(),
      };
      this.messages.push(greetingMessage);
      this.saveMessages();
    },
    getCurrentTime() {
      const now = new Date();
      return `${now.getHours()}:${
        now.getMinutes() < 10 ? '0' : ''
      }${now.getMinutes()}`;
    },
    resizeTextarea() {
      this.$nextTick(() => {
        const textarea = this.$refs.userInputArea; // ref로 textarea 접근
        if (textarea) {
          textarea.style.height = 'auto';
          textarea.style.height = `${textarea.scrollHeight}px`;
        }
      });
    },
    saveMessages() {
      localStorage.setItem('chatMessages', JSON.stringify(this.messages));
    },
    scrollToBottom() {
      const container = this.$refs.responsesContainer;
      container.scrollTop = container.scrollHeight;
    },
  },
  mounted() {
    this.resizeTextarea();
  },
};
</script>

<style>
.timeStampDiv {
  width: 40px;
  border-radius: 5px;
  text-align: center;
}

.chatbot-icon {
  position: fixed;
  bottom: 20px;
  right: 10px;
  cursor: pointer;
  width: 70px;
  z-index: 1000;
}

.chatbot-icon:hover {
  opacity: 0.8;
}

.chatbot-container {
  position: fixed;
  bottom: 30px;
  right: 15px;
  width: 400px;
  height: 650px;
  background-color: white;
  border-radius: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  z-index: 1000;
}

.chatbot-content {
  display: flex;
  flex-direction: column;
  height: 100%;
  font-size: 15px;
}

.header {
  background: #00c4d1;
  color: white;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 10px 10px 0 0;
}

.header h5,
.header h6 {
  margin: 0;
  color: white;
}

.close {
  position: absolute;
  left: 90%;
  top: 1%;
  background: transparent;
  border: none;
  font-size: 30px;
  color: white;
  cursor: pointer;
}

.responses {
  flex: 1;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  margin: 0;
}

.user-message {
  background: rgb(182, 218, 255);
  color: rgb(36, 51, 66);
  padding: 10px;
  border-radius: 1.25rem 1.25rem 0;
  margin-bottom: 10px;
  margin-top: 5px;
  align-self: end;
  position: relative;
  max-width: 70%;
  margin-right: 10px;
}

.bot-message {
  width: 250px;
  background: rgb(233, 235, 237);
  padding: 5px;
  margin: 20px 20px 5px 40px;
  border-radius: 0px 1.25rem 1.25rem;
  position: relative;
}

.bot-message .profile {
  position: absolute;
  top: -23px;
  left: -35px;
}

.timestamp {
  font-size: 0.8em;
  color: white;
  margin-top: 5px;
  text-align: right;
  margin-left: auto;
}

.input-container {
  position: relative;
  display: flex;
  align-items: flex-end;
}

textarea {
  width: 100%;
  height: auto;
  min-height: 40px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px;
  resize: none;
  margin-right: -40px;
  z-index: 1;
}

.send-button {
  position: absolute;
  right: 15px;
  bottom: 18px;
  background-color: #00c4d1;
  color: white;
  border: none;
  border-radius: 7px;
  cursor: pointer;
  z-index: 2;
  width: 30px;
  height: 30px;
}

.send-button:hover {
  opacity: 0.8;
}

.profile img {
  top: 30px;
  left: 50px;
  width: 40px;
  height: 40px;
}
</style>
