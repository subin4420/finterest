<template>
    <div class="content-card">
        <!-- 이미지 섹션 -->
        <div class="image-wrapper">
            <!-- 이걸로 바꿔야 함
            <img :src="cardData.material_img" alt="자료 이미지" /> 
            -->
            <img src="https://cdn.pixabay.com/photo/2024/01/02/20/13/dry-8483913_1280.jpg" alt="자료 이미지" />
        </div>

        <!-- 카드 내용 섹션 -->
        <div class="card-info">
            <strong>[{{ cardData.categoryName }}]</strong>
            <div class="title">{{ cardData.title }}</div>
            <p>{{ cardData.content }}</p>
        </div>

        <!-- 더보기 링크 -->
        <a :href="`/api/achieve/${cardData.material_id}`" class="more-link">더보기</a>
    </div>
</template>

<script>
export default {
    name: 'AchieveCard',
    props: {
        cardData: {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped>
.content-card {
    width: 300px;
    height: 350px;
    margin: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: flex-start;
    overflow: hidden;
    transition: transform 0.3s ease-in-out;
    border: 1px solid #ccc;
}

.content-card:hover {
    transform: translateY(-10px);
}

.image-wrapper {
    width: 100%;
    height: 150px;
    overflow: hidden;
}

.image-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    /* 이미지가 박스에 맞게 조절되도록 설정 */
}

.card-info {
    padding: 0.5em 1em;
    background-color: #f0f0f0;
    overflow: hidden;
    flex-grow: 1;
}

.title {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 100%;
}

.more-link {
    color: #007BFF;
    text-decoration: none;
    font-size: 14px;
    margin-top: auto;
    padding: 0.5em 1em;
    background-color: #f0f0f0;
    width: 100%;
    text-align: right;
    box-sizing: border-box;
    /* padding이 포함된 너비를 계산 */
}

.more-link:hover {
    text-decoration: underline;
}
</style>
