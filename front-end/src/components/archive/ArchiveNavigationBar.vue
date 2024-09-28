<template>
    <nav class="navbar">
        <ul class="nav-links">
            <li @click="selectCategory(null)" :class="{ active: selectedCategory === null }"><a href="#!">전체</a></li>
            <li @click="selectCategory('경제 기본 상식')" :class="{ active: selectedCategory === '경제 기본 상식' }"><a href="#!">경제상식</a></li>
            <li @click="selectCategory('투자')" :class="{ active: selectedCategory === '투자' }"><a href="#!">투자</a></li>
            <li @click="selectCategory('세금')" :class="{ active: selectedCategory === '세금' }"><a href="#!">세금</a></li>
            <li @click="selectCategory('재무관리')" :class="{ active: selectedCategory === '재무관리' }"><a href="#!">재무관리</a></li>
            <li @click="selectCategory('기타')" :class="{ active: selectedCategory === '기타' }"><a href="#!">기타</a></li>
            <li @click="selectCategory('즐겨찾기')" :class="{ active: selectedCategory === '즐겨찾기' }"><a href="#!">즐겨찾기</a></li>
        </ul>
    </nav>
</template>

<script>
export default {
    name: 'NavigationBar',
    props: {
        selectedCategory: {
            type: String,
            default: null
        }
    },
    methods: {
        selectCategory(category) {
            this.$emit('category-selected', category); // 선택된 카테고리 이벤트 발생
        }
    }
}
</script>

<style scoped>

.navbar {
    background-color: black;
    color: white;
    display: flex;
    justify-content: center; /* 가로 방향으로 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */
    max-width: 1000px; /* 최대 너비 설정 */
    margin: -20px auto 40px auto; /* 상단 마진 -20px, 좌우 중앙 정렬 */
    border-radius: 5px; /* 둥근 모서리 추가 */
    padding: 10px; /* 패딩 추가하여 내용과 모서리 간격 조정 */
}

.nav-links {
    list-style: none;
    display: flex;
    justify-content: space-between;
    padding: 0;
    width: 90%; /* 적당한 너비로 설정 */
    max-width: 1200px; /* 최대 너비 설정 */
}

.nav-links li {
    position: relative; /* 자식 요소의 위치를 기준으로 설정 */
}

.nav-links a {
    color: white;
    text-decoration: none;
    font-size: 16px; /* 기본 글자 크기 설정 */
    padding: 5px 10px; /* 패딩 추가 */
}

.nav-links li.active a {
    color: #00D18B; /* 선택된 항목의 글씨 색상 변경 */
    border-bottom: 2px solid #00D18B; /* 밑줄 추가 */
}

/* 미디어 쿼리 추가 */
@media (max-width: 768px) {
    .nav-links a {
        font-size: 14px; /* 작은 화면에서 글자 크기 줄임 */
    }
}

@media (max-width: 480px) {
    .nav-links a {
        font-size: 12px; /* 더 작은 화면에서 글자 크기 줄임 */
    }
}
</style>
