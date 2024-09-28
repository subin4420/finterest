<template>
    <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <button class="close-button" @click="closeModal">&times;</button>
            <div class="modal-header">
                <button class="back-button" @click="closeModal">
                    <i class="fas fa-arrow-left"></i> 뒤로가기
                </button>
                <h2>{{ cardData.title }}</h2>
            </div>
            <div class="quiz-navigation">
                <button 
                    v-for="(quiz, index) in cardData.quizzes" 
                    :key="index"
                    :class="['quiz-nav-button', { 'answered': answeredQuizzes[index], 'current': currentQuizIndex === index }]"
                    @click="setCurrentQuiz(index)"
                >
                    {{ index + 1 }}
                </button>
            </div>
            <div class="quiz-content" v-if="currentQuiz">
                <p>{{ currentQuiz.question }}</p>
                <div class="quiz-options">
                    <label v-for="(option, index) in currentQuiz.options" :key="index">
                        <input 
                            type="radio" 
                            :name="'quiz-option'" 
                            :value="index" 
                            v-model="currentQuiz.selectedAnswer"
                            @change="updateAnsweredQuizzes"
                        >
                        {{ option }}
                    </label>
                </div>
            </div>
            <button 
                class="submit-button" 
                @click="submitQuiz" 
                :disabled="!allQuizzesAnswered"
            >
                퀴즈 제출
            </button>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        isVisible: Boolean,
        cardData: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            currentQuizIndex: 0,
            answeredQuizzes: {},
        };
    },
    computed: {
    currentQuiz() {
        // cardData가 존재하고 quizzes 배열이 정의되어 있으며, currentQuizIndex가 범위 내에 있을 때만 반환
        return this.cardData && this.cardData.quizzes && this.cardData.quizzes.length > 0
            ? this.cardData.quizzes[this.currentQuizIndex]
            : null;
    },
    allQuizzesAnswered() {
        return this.cardData && this.cardData.quizzes && Object.keys(this.answeredQuizzes).length === this.cardData.quizzes.length;
    }
},
    methods: {
        closeModal() {
            this.$emit('update:isVisible', false);
        },
        setCurrentQuiz(index) {
            this.currentQuizIndex = index;
        },
        updateAnsweredQuizzes() {
            this.$set(this.answeredQuizzes, this.currentQuizIndex, true);
        },
        submitQuiz() {
            // 퀴즈 제출 로직 구현
            console.log('퀴즈 제출됨');
            this.closeModal();
        }
    }
}
</script>

<style scoped>
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background-color: white;
    padding: 30px;
    border-radius: 15px;
    max-width: 80%;
    max-height: 80%;
    overflow-y: auto;
    z-index: 1001;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

.close-button {
    position: absolute;
    top: 15px;
    right: 20px;
    border: none;
    background: none;
    font-size: 28px;
    cursor: pointer;
    color: #333;
    transition: color 0.3s ease;
}

.close-button:hover {
    color: #e74c3c;
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.back-button {
    color: #3498db;
    background: none;
    border: none;
    cursor: pointer;
    font-size: 14px;
    display: flex;
    align-items: center;
    transition: color 0.3s ease;
}

.back-button:hover {
    color: #2980b9;
}

.quiz-navigation {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.quiz-nav-button {
    width: 30px;
    height: 30px;
    margin: 0 5px;
    border: none;
    border-radius: 50%;
    background-color: #ddd;
    cursor: pointer;
}

.quiz-nav-button.answered {
    background-color: #2ecc71;
    color: white;
}

.quiz-nav-button.current {
    background-color: #3498db;
    color: white;
}

.quiz-content {
    margin-bottom: 20px;
}

.quiz-options {
    display: flex;
    flex-direction: column;
}

.quiz-options label {
    margin: 10px 0;
    cursor: pointer;
}

.submit-button {
    background-color: #00c4d1;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 20px;
    font-size: 16px;
    transition: background-color 0.3s ease;
}

.submit-button:hover:not(:disabled) {
    background-color: #00a8b3;
}

.submit-button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}
</style>