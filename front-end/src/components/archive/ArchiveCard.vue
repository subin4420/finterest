<template>
    <div class="content-card">
        <!-- 이미지 섹션 -->
        <div class="image-wrapper">
            <img :src="cardData.materialImg || defaultImage" alt="썸네일 이미지" />
            <button class="favorite-button" @click.stop="toggleFavorite">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 50 50">
                    <path :d="favoriteIcon" />
                </svg>
            </button>
        </div>

        <!-- 카드 내용 섹션 -->
        <div class="card-info">
            <div class="category-status-wrapper">
                <div class="category-title">[{{ cardData.categoryName }}]</div>
                <div v-if="cardData.status" :class="['status', statusClass]">{{ statusText }}</div>
            </div>
            <div class="title">{{ cardData.title }}</div>
            <p class="summary">{{ cardData.content }}</p>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ArchiveCard',
    props: {
        cardData: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            defaultImage: 'https://cdn.pixabay.com/photo/2021/12/28/11/38/trees-6899050_1280.jpg', // 대체 이미지 URL
            isFavorite: false
        };
    },
    computed: {
        statusText() {
            switch(this.cardData.status) {
                case 'completed':
                    return '완료';
                case 'incomplete':
                    return '학습중';
                default:
                    return this.cardData.status;
            }
        },
        statusClass() {
            return {
                'status-completed': this.cardData.status === 'completed',
                'status-incomplete': this.cardData.status === 'incomplete'
            };
        },
         favoriteIcon() {
            return this.isFavorite
                ? "M 37 48 C 36.824219 48 36.652344 47.953125 36.496094 47.863281 L 25 41.15625 L 13.503906 47.863281 C 13.195313 48.042969 12.8125 48.046875 12.503906 47.867188 C 12.191406 47.6875 12 47.359375 12 47 L 12 3 C 12 2.449219 12.449219 2 13 2 L 37 2 C 37.554688 2 38 2.449219 38 3 L 38 47 C 38 47.359375 37.808594 47.6875 37.496094 47.867188 C 37.34375 47.957031 37.171875 48 37 48 Z"
                : "M 12.8125 2 C 12.335938 2.089844 11.992188 2.511719 12 3 L 12 47 C 11.996094 47.359375 12.1875 47.691406 12.496094 47.871094 C 12.804688 48.054688 13.1875 48.054688 13.5 47.875 L 25 41.15625 L 36.5 47.875 C 36.8125 48.054688 37.195313 48.054688 37.503906 47.871094 C 37.8125 47.691406 38.003906 47.359375 38 47 L 38 3 C 38 2.449219 37.550781 2 37 2 L 13 2 C 12.96875 2 12.9375 2 12.90625 2 C 12.875 2 12.84375 2 12.8125 2 Z M 14 4 L 36 4 L 36 45.25 L 25.5 39.125 C 25.191406 38.945313 24.808594 38.945313 24.5 39.125 L 14 45.25 Z";
        }
    },
    methods: {
        toggleFavorite() {
            this.isFavorite = !this.isFavorite;
            // 여기에 즐겨찾기 상태를 서버에 저장하는 로직을 추가할 수 있습니다.
        }
    }
}
</script>

<style scoped>
.content-card {
    width: 300px; /* 기본 너비 */
    height: auto; /* 높이를 자동으로 조정 */
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    background-color: #ffffff; /* 배경색을 흰색으로 변경 */
    padding: 15px; /* 패딩을 약간 늘림 */
    border-radius: 8px; /* 모서리를 둥글게 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
    transition: transform 0.2s; /* 호버 시 부드러운 효과 */
}

.content-card:hover {
    transform: scale(1.05); /* 호버 시 카드 확대 효과 */
}

.image-wrapper {
    width: 100%;
    height: 120px;
    background-color: #f0f0f0;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 8px;
    overflow: hidden;
    position: relative; /* 추가: 상대 위치 설정 */
}

.image-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s; /* 이미지에 부드러운 효과 추가 */
}

.favorite-button {
    position: absolute;
    top: 10px;
    right: 10px; /* left: 10px에서 right: 10px로 변경 */
    background: rgba(255, 255, 255, 0.7);
    border: none;
    cursor: pointer;
    padding: 5px;
    border-radius: 50%;
    z-index: 2;
    display: flex;
    justify-content: center;
    align-items: center;
}

.favorite-button svg {
    width: 24px;
    height: 24px;
    fill: none;
    stroke: #000;
    stroke-width: 2px;
    transition: fill 0.3s ease;
}

.favorite-button:hover svg {
    fill: #00C4D1;

}

.favorite-button svg path {
    fill: #00C4D1;

}

.image-wrapper img:hover {
    transform: scale(1.1); /* 이미지 호버 시 확대 효과 */
}

.status {
    font-size: 12px;
    padding: 2px 6px;
    border-radius: 4px;
}

.status-completed {
    color: #00C4D1; /* 완료 상태 색상 */
}

.status-incomplete {
    color: #888; /* 미완료 상태 색상 */
}

.card-info {
    padding-top: 10px;
    text-align: left;
    width: 100%;
    flex-grow: 1; /* 공간을 차지하도록 설정 */
}

.category-status-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 5px;
}

.category-title {
    font-size: 14px;
    color: #888;
}

.title {
    font-weight: bold;
    font-size: 16px; /* 기본 글자 크기 */
    margin-top: 5px;
    white-space: nowrap; /* 한 줄로 표시 */
    overflow: hidden; /* 넘치는 내용 숨기기 */
    text-overflow: ellipsis; /* 넘치는 내용에 ... 표시 */
    width: 100%; /* 너비를 100%로 설정 */
}

.summary {
    margin-top: 5px;
    font-size: 12px; /* 기본 글자 크기 */
    color: #666;
    white-space: nowrap; /* 한 줄로 표시 */
    overflow: hidden; /* 넘치는 내용 숨기기 */
    text-overflow: ellipsis; /* 넘치는 내용에 ... 표시 */
    width: 100%; /* 너비를 100%로 설정 */
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
    .content-card {
        width: 90%; /* 작은 화면에서 카드 너비를 90%로 설정 */
        margin: 0 auto; /* 중앙 정렬 */
    }

    .image-wrapper {
        height: 100px; /* 작은 화면에서 이미지 높이 조정 */
    }

    .title {
        font-size: 14px; /* 작은 화면에서 글자 크기 줄임 */
    }

    .summary {
        font-size: 10px; /* 작은 화면에서 글자 크기 줄임 */
    }
}
</style>
