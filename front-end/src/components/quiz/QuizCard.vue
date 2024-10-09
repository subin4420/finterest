<template>
    <div class="content-card" @click="$emit('click', cardData)">
        <div class="image-wrapper">
            <img :src="cardData.setImg || defaultImage" alt="썸네일 이미지" />
        </div>
        <div class="card-info">
            <div class="category-score-wrapper">
                <div class="category-title">[{{ cardData.categoryName }}]</div>
                <div class="score" v-if="cardData.userScore !== null && cardData.userScore !== undefined">
                    {{ cardData.userScore }}점
                </div>
            </div>
            <div class="title-wrapper">
                <div class="title">{{ cardData.setName }}</div>
                <div :class="['completion-status', { 'completed': cardData.completedAt, 'incomplete': !cardData.completedAt }]">
                    {{ cardData.completedAt ? `완료: ${formatDate(cardData.completedAt)}` : '미완료' }}
                </div>
            </div>
            <p class="summary">{{ cardData.description }}</p>
        </div>
    </div>
</template>

<script>
import { useQuizStore } from '@/stores/quizStore';
export default {
    name: 'QuizCard',
    props: {
        cardData: {
            type: Object,
            required: true
        }
    },
    created() {
        console.log('Card Data:', this.cardData);
    },
    data() {
        return {
            defaultImage: 'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg'
        };
    },
    methods: {
        formatDate(dateString) {
            const date = new Date(dateString);
            return `${date.getFullYear()}.${String(date.getMonth() + 1).padStart(2, '0')}.${String(date.getDate()).padStart(2, '0')}`;
        }
    }
}
</script>

<style scoped>
.content-card {
    width: 100%;
    height: 200px;
    display: flex;
    flex-direction: column;
    background-color: #ffffff;
    padding: 12px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s;
    box-sizing: border-box;
}

.content-card:hover {
    transform: scale(1.05);
}

.image-wrapper {
    width: 100%;
    height: 110px;
    background-color: #f0f0f0;
    border-radius: 6px;
    overflow: hidden;
    margin-bottom: 8px;
}

.image-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s;
}

.image-wrapper img:hover {
    transform: scale(1.1);
}

.card-info {
    display: flex;
    flex-direction: column;
    flex-grow: 1;
}

.category-score-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 4px;
}

.category-title {
    font-size: 12px;
    color: #888;
}

.score {
    color: #00C4D1;
    font-size: 12px;
    padding: 2px 6px;
    border-radius: 4px;
    background-color: rgba(0, 196, 209, 0.1);
}

.title-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 4px;
}

.title {
    font-weight: bold;
    font-size: 16px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    flex-grow: 1;
    margin-right: 8px;
}

.completed-at {
    font-size: 11px;
    color: #888;
    white-space: nowrap;
}

.summary {
    font-size: 12px;
    color: #666;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    margin: 0;
}

.completion-status {
    font-size: 11px;
    white-space: nowrap;
}

.completed {
    color: #888; /* 완료 상태일 때 초록색 */
}

.incomplete {
    color: #888; /* 미완료 상태일 때 주황색 */
}

@media (max-width: 768px) {
    .content-card {
        height: 180px;
    }

    .image-wrapper {
        height: 90px;
    }

    .title {
        font-size: 14px;
    }

    .summary {
        font-size: 11px;
    }
}
</style>

