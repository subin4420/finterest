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
    /* 중앙 정렬을 위한 flex 사용 */
    justify-content: center;
    /* 가로 방향으로 중앙 정렬 */
}

.nav-links {
    list-style: none;
    display: flex;
    justify-content: space-between;
    padding: 0;
    width: 80%;
    /* 적당한 너비로 설정 */
    max-width: 1200px;
    /* 최대 너비 설정 */
}

.nav-links a {
    color: white;
    text-decoration: none;
}
</style>
