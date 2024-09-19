<template>
  <div id="app">
    <!-- 헤더와 푸터를 특정 경로에서 숨기기 -->
    <Header v-if="showHeaderFooter" />
    <router-view />
    <Footer v-if="showHeaderFooter" />
  </div>
</template>

<script>
import Header from "@/components/layout/Header.vue";
import Footer from "@/components/layout/Footer.vue";

export default {
  name: "App",
  components: {
    Header,
    Footer,
  },
  data() {
    return {
      // 헤더와 푸터를 숨길 경로
      hideOnRoutes: ["/login", "/change-password", "/find-password", "/signup"], // 로그인, 비밀번호 찾기 경로에서 숨김
    };
  },
  computed: {
    showHeaderFooter() {
      // hideOnRoutes에 현재 경로가 포함되지 않으면 헤더와 푸터를 보여줌
      return !this.hideOnRoutes.includes(this.$route.path);
    },
  },
  watch: {
    // 라우트 변경 시 헤더와 푸터 상태를 업데이트
    $route() {
      this.showHeaderFooter = !this.hideOnRoutes.includes(this.$route.path);
    },
  },
};
</script>

<style scoped>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

router-view {
  flex-grow: 1;
}
</style>
