<template>
    <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <div class="modal-header">
                <button class="back-button" @click="closeModal">
                    <i class="fas fa-arrow-left"></i> 뒤로가기
                </button>
                <span class="learning-status" v-if="cardData.status" :class="['status', statusClass]">
                    <i :class="statusIcon"></i> {{ statusText }}
                </span>
            </div>
            <div class="content-section">
                <div class="title-section">
                    <img :src="cardData.materialImg || defaultImage" alt="썸네일 이미지" />
                    <div class="title-info">
                        <h2>{{ cardData.title }}</h2>
                        <p><i class="fas fa-folder"></i> {{ cardData.categoryName }}</p>
                    </div>
                </div>
                <div class="archive-content">
                    <p>{{ cardData.content }}</p>
                </div>
            </div>
            <button 
                class="complete-button" 
                @click="markComplete" 
                :disabled="cardData.status === 'completed'"
                :class="{ 'completed': cardData.status === 'completed' }"
            >
                <i class="fas fa-check"></i> 학습완료
            </button>
        </div>
    </div>
</template>

<script>
import { useArchiveStore } from '@/stores/archiveStore';

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
            defaultImage: 'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg'
        };
    },
    computed: {
        statusText() {
            console.log('Status in Modal:', this.cardData.status);
            return this.cardData.status === 'completed' ? '완료된 학습' : 
                   this.cardData.status === 'incomplete' ? '진행중인 학습' : 
                   this.cardData.status || 'N/A';
        },
        statusClass() {
            return {
                'status-completed': this.cardData.status === 'completed',
                'status-incomplete': this.cardData.status === 'incomplete'
            };
        },
        statusIcon() {
            return this.cardData.status === 'completed' ? 'fas fa-check-circle' : 'fas fa-clock';
        }
    },
    methods: {
        closeModal() {
            this.$emit('update:isVisible', false);
        },
        async markComplete() {
            if (this.cardData.status !== 'completed') {
                try {
                    const archiveStore = useArchiveStore();
                    await archiveStore.changeArchiveStatus(this.cardData.materialId, 'completed');
                    this.closeModal(); // 모달 닫기
                } catch (error) {
                    console.error('Error updating archive status:', error);
                }
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

.learning-status {
    font-size: 16px;
    font-weight: bold;
    padding: 5px 10px;
    border-radius: 15px;
}

.status-completed {
    background-color: #2ecc71;
    color: white;
}

.status-incomplete {
    background-color: #f39c12;
    color: white;
}

.title-section {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.title-info {
    margin-left: 20px;
}

.title-info h2 {
    margin: 0 0 10px 0;
    color: #2c3e50;
}

.title-info p {
    margin: 0;
    color: #7f8c8d;
}

img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-radius: 10px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}

.archive-content {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 10px;
    margin-bottom: 20px;
}

.complete-button {
    background-color: #00c4d1;
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 20px;
    font-size: 16px;
    transition: background-color 0.3s ease;
    display: flex;
    align-items: center;
    justify-content: center;
}

.complete-button:hover:not(:disabled) {
    background-color: #00a8b3;
}

.complete-button.completed {
    background-color: #95a5a6;
}

.complete-button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.complete-button i {
    margin-right: 5px;
}

@media (max-width: 768px) {
    .modal-content {
        width: 95%;
        padding: 20px;
    }

    .title-section {
        flex-direction: column;
        align-items: flex-start;
    }

    .title-info {
        margin-left: 0;
        margin-top: 15px;
    }

    img {
        width: 100%;
        height: auto;
    }
}
</style>