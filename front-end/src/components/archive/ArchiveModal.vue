<template>
    <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close-button" @click="closeModal">
                    <i class="fas fa-times"></i>
                </button>
                <span class="learning-status" v-if="cardData.status" :class="['status', statusClass]">
                    <i :class="statusIcon"></i> {{ statusText }}
                </span>
            </div>
            <div class="content-section">
                <div class="title-section">
                    <img :src="IMAGE_PATHS.ARCHIVE_IMG + cardData.materialImg || defaultImage" alt="썸네일 이미지" />
                    <div class="title-info">
                        <h2>{{ cardData.title }}</h2>
                        <p><i class="fas fa-folder"></i> {{ cardData.categoryName }}</p>
                    </div>
                </div>
                <div class="archive-content" v-html="cardData.content"></div>
            </div>
            <button 
                class="complete-button" 
                @click="markComplete" 
                :disabled="cardData.status === 'completed'"
                :class="{ 'completed': cardData.status === 'completed' }"
            >
                <i class="fas fa-check"></i> <span style="margin-left: 5px;">학습완료</span>
            </button>
        </div>
    </div>
</template>

<script>
import { IMAGE_PATHS } from '@/constants/imagePaths';
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
            IMAGE_PATHS,
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
    mounted() {
        if (!this.cardData || !this.cardData.materialId) {
            console.error('Material ID is undefined or cardData is missing.');
            return;
        }

        console.log('Modal mounted with cardData:', this.cardData);
        console.log('Material ID:', this.cardData.materialId);  // materialId가 있는지 확인

        // cardData.status가 존재하지 않으면 null로 초기화
        if (this.cardData.status == 'N/A' || !this.cardData.status) {
            this.cardData.status = null;
            console.log('Setting status to null.');
        }

        this.checkAndAddStatus(); // 모달이 열릴 때 학습 상태가 null이면 추가
    },
    methods: {
        async checkAndAddStatus() {
            console.log('Checking status in checkAndAddStatus function:', this.cardData.status);
            if (!this.cardData.status || this.cardData.status === null) {  // status가 없거나 null로 강제 설정
                try {
                    console.log('Status is null, adding archive status.');
                    const archiveStore = useArchiveStore();
                    await archiveStore.addArchiveStatus(this.cardData.materialId, 'incomplete'); // 학습 상태 추가
                    console.log('Successfully added archive status.');
                    this.cardData.status = 'incomplete'; // 로컬 상태 업데이트
                } catch (error) {
                    console.error('Error adding archive status:', error);
                }
            } else {
                console.log('Status is not null, skipping status addition.');
            }
        },

        closeModal() {
            this.$emit('update:isVisible', false);
        },
        async markComplete() {
            if (this.cardData.status !== 'completed') {
                try {
                    const archiveStore = useArchiveStore();
                    await archiveStore.changeArchiveStatus(this.cardData.materialId, 'completed'); // 학습 완료 상태로 변경
                    this.cardData.status = 'completed'; // 로컬 상태 업데이트
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
.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.close-button {
    color: #333;
    background: none;
    border: none;
    cursor: pointer;
    font-size: 24px; /* 크기를 크게 설정 */
    display: flex;
    align-items: center;
    transition: color 0.3s ease;
}

.close-button:hover {
    color: #00c4d1; /* 마우스를 올렸을 때 색상 변경 */
}

.learning-status {
    font-size: 16px;
    font-weight: bold;
    padding: 5px 10px;
    border-radius: 15px;
}

.status-completed {
    background-color: #00c4d1;
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
    overflow-wrap: break-word;
    word-wrap: break-word;
    word-break: break-word;
}

/* HTML 태그 스타일링 */
.archive-content :deep(h1),
.archive-content :deep(h2),
.archive-content :deep(h3) {
    margin-top: 20px;
    margin-bottom: 10px;
    color: #2c3e50;
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
.archive-content :deep(h1) {
    font-size: 24px;
}

.archive-content :deep(h2) {
    font-size: 20px;
}

.archive-content :deep(h3) {
    font-size: 18px;
}

.archive-content :deep(p) {
    margin-bottom: 15px;
    line-height: 1.6;
}

.archive-content :deep(ul),
.archive-content :deep(ol) {
    margin-bottom: 15px;
    padding-left: 20px;
}

.archive-content :deep(li) {
    margin-bottom: 5px;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
    .archive-content :deep(h1) {
        font-size: 22px;
    }

    .archive-content :deep(h2) {
        font-size: 18px;
    }

    .archive-content :deep(h3) {
        font-size: 16px;
    }
}
</style>