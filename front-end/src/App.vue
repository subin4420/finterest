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
      //해터 푸터 숨길때 여기다 추가
      hideOnRoutes: ['/login'], // 로그인 페이지에서 헤더와 푸터 숨기기
    };
  },
  computed: {
    showHeaderFooter() {
      // 현재 경로가 hideOnRoutes에 포함되지 않는 경우에만 헤더와 푸터를 보여줌
      return !this.hideOnRoutes.includes(this.$route.path);
    },
  },
};
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

router-view {
  flex-grow: 1;
}
</style>
