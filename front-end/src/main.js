import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from 'pinia';
//import './styles/global.css';  // 전역 스타일 불러오기
import vuetify from './plugins/vuetify'; // Vuetify 플러그인

const app = createApp(App);

app.use(router);
app.use(createPinia());
app.use(vuetify);
app.mount('#app');
