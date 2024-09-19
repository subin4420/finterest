<template>
  <header class="header">
    <nav class="navbar">
      <div class="logo">
        <router-link to="/">MyApp</router-link>
      </div>
      <ul class="nav-links">
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/learning">학습</router-link></li>
        <li><router-link to="/quiz">퀴즈</router-link></li>
        <li><router-link to="/trading">거래</router-link></li>
        <li><router-link to="/login">로그인</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: "Header",
};
</script>

<style scoped>
.header {
  background-color: #00c4d1;
  padding: 10px 0;
  text-align: center;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.logo a {
  font-size: 24px;
  color: white;
  text-decoration: none;
}

.nav-links {
  list-style: none;
  display: flex;
}

.nav-links li {
  margin-left: 20px;
}

.nav-links a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

.nav-links a:hover {
  text-decoration: underline;
}
</style>
