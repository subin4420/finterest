<template>
    <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <button class="close-button" @click="closeModal">&times;</button>
            <div class="modal-header">
                <button class="back-button" @click="closeModal">< 뒤로가기</button>
                <!-- ArchiveCard에서 전달받은 status 출력 -->
                <span class="learning-status" v-if="cardData.status" :class="['status', statusClass]">{{ statusText }}</span>
            </div>
            <div class="content-section">
                <div class="title-section">
                    <!-- 이미지 섹션 -->
                    <img :src="cardData.materialImg || defaultImage" alt="썸네일 이미지" />
                    <div class="title-info">
                        <h3>제목 : {{ cardData.title }}</h3>
                        <p>카테고리 : {{ cardData.categoryName }}</p>
                    </div>
                </div>
                <p>{{ cardData.content }}</p>
            </div>
            <button 
                class="complete-button" 
                @click="markComplete" 
                :disabled="cardData.status === 'completed'"
                :class="{ 'completed': cardData.status === 'completed' }"
            >
                학습완료
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
            defaultImage: 'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg' // 대체 이미지 URL
        };
    },
    computed: {
        statusText() {
            // status 값이 제대로 있는지 확인하기 위해 콘솔 출력 추가
            console.log('Status in Modal:', this.cardData.status);
            return this.cardData.status === 'completed' ? '완료된 학습' : 
                   this.cardData.status === 'incomplete' ? '진행중인 학습' : 
                   this.cardData.status || 'N/A';  // status 값이 없을 경우 'N/A' 표시
        },
        statusClass() {
            return {
                'status-completed': this.cardData.status === 'completed',
                'status-incomplete': this.cardData.status === 'incomplete'
            };
        }
    },
    methods: {
        closeModal() {
            this.$emit('update:isVisible', false);
        },
        markComplete() {
            if (this.cardData.status !== 'completed') {
                // 학습완료 로직 처리
                console.log('학습완료 처리');
                this.closeModal();
            }
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
    background-color: rgba(0, 0, 0, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-content {
    background-color: white;
    padding: 20px;
    width: 60%;
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

.modal-header {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
}

.back-button {
  color: #007BFF;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 12px;
  margin-bottom: 20px; /* 뒤로가기 버튼 아래에 마진 추가 */
}

.learning-status {
  display: block;
  align-self: flex-start; /* 오른쪽 정렬 */
  font-size: 15px;
  font-weight: bold;
  margin-left: 5px;
  margin-top: 5px;
}

.title-section {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
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
    margin: 30px;
    /* Adds space above the complete button */
}

.complete-button {
    background-color: #00c4d1;
    color: white;
    padding: 8px 12px;
    border: none;
    cursor: pointer;
    border-radius: 10px;
    align-self: flex-end;
    font-size: 14px;
    margin-bottom: 30px;
    margin-right: 30px;
}

.complete-button:hover:not(:disabled) {
    opacity: 0.8;
}

.complete-button.completed {
    background-color: #cccccc;
    cursor: default;  /* 'not-allowed' 대신 'default' 사용 */
}

.complete-button:disabled {
    opacity: 0.6;
    pointer-events: none;  /* 비활성화된 버튼에 대한 모든 마우스 이벤트 무시 */
}
</style>
