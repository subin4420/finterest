<template>
    <nav class="navbar">
        <ul class="nav-links">
            <li><a href="#!">전체</a></li>
            <li><a href="#!">경제상식</a></li>
            <li><a href="#!">투자</a></li>
            <li><a href="#!">돈의흐름</a></li>
            <li><a href="#!">세계경제</a></li>
            <li><a href="#!">경제구조</a></li>
            <li><a href="#!">기타</a></li>
            <li><a href="#!">즐겨찾기</a></li>
        </ul>
    </nav>
</template>

<script>
export default {
    name: 'NavigationBar'
}
</script>

<style scoped>
.navbar {
    background-color: black;
    color: white;
    display: flex;
    margin: 0px auto;
    justify-content: center; /* 가로 방향으로 중앙 정렬 */
}

.nav-links {
    list-style: none;
    display: flex;
    justify-content: space-between;
    padding: 0;
    width: 80%; /* 적당한 너비로 설정 */
    max-width: 1200px; /* 최대 너비 설정 */
}

.nav-links a {
    color: white;
    text-decoration: none;
    font-size: 16px; /* 기본 글자 크기 설정 */
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
