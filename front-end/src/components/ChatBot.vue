<template>
  <div class="chatbot-container" v-if="isOpen">
    <div class="chatbot-content">
      <div class="header">
        <h5>Finterest 챗봇</h5>
        <br />
        <h6>💬챗봇을 통해 문의를 해결해보세요!</h6>
        <button class="close" @click="closeChatBot">&times;</button>
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
            placeholder="챗봇에게 물어보세요"
            @keyup.enter="sendMessage"
            @input="resizeTextarea"
          ></textarea>
          <button @click="sendMessage" class="send-button">
            <span class="material-symbols-outlined"><b>⇧</b></span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <img
    class="chatbot-icon"
    v-if="!isOpen"
    @click="openChatBot"
    src="@/assets/chatbot-icon.png"
    alt="챗봇 열기"
  />
</template>

<script>
import axios from 'axios';

export default {
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
      if (!this.userInput) return;

      const message = {
        sender: 'user',
        text: this.userInput,
        timestamp: this.getCurrentTime(),
      };

      this.messages.push(message);
      this.saveMessages();

      try {
        const response = await axios.post(
          'https://api.openai.com/v1/chat/completions',
          {
            model: 'gpt-3.5-turbo',
            messages: [{ role: 'user', content: this.userInput }],
          },
          {
            headers: {
              Authorization: `Bearer ${import.meta.env.VITE_OPENAI_API_KEY}`, // 환경 변수 사용
              'Content-Type': 'application/json',
            },
          }
        );

        const botMessage = {
          sender: 'bot',
          text: response.data.choices[0].message.content,
          timestamp: this.getCurrentTime(),
        };

        this.messages.push(botMessage);
        this.saveMessages();
      } catch (error) {
        console.error('챗봇 오류: ', error); // 수정된 부분
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
        text: '안녕하세요! Finterst 챗봇 입니다. 무엇을 도와드릴까요?',
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
  width: 350px;
  height: 620px;
  background-color: white;
  border-radius: 10px;
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
  background: transparent;
  border: none;
  font-size: 20px;
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
  width: 250px;
  background: #00c4d1;
  color: white;
  padding: 5px;
  border-radius: 10px;
  margin-bottom: 10px;
  align-self: flex-end;
}

.bot-message {
  width: 250px;
  background: darkgray;
  padding: 5px;
  border-radius: 5px 5px 0 0;
  margin-bottom: 5px;
}

.timestamp {
  font-size: 0.8em;
  color: white;
  margin-top: 5px;
  text-align: right;
  margin-left: auto;
}

.input-container {
  display: flex;
}

textarea {
  width: 100%;
  height: auto;
  min-height: 40px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 10px;
  resize: none;
}

.send-button {
  width: 85px;

  background-color: #00c4d1;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  margin-left: 10px;
}

.send-button:hover {
  background-color: #3498db;
}
</style>
