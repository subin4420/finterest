import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import ArchivePage from '../pages/archive/ArchivePage.vue';
import QuizPage from '../pages/quiz/QuizPage.vue';
import MyLearningPage from '../pages/myLearning/MyLearningPage.vue';
import NoticePage from '../pages/notice/NoticePage.vue';
import authRoutes from './auth';
import tradeRoutes from './trade';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path: '/archive/ArchivePage',
      name: 'archive',
      component: ArchivePage,
    },
    {
      path: '/quiz/QuizPage',
      name: 'quiz',
      component: QuizPage,
    },
    {
      path: '/myLearning/MyLearningPage',
      name: 'myLearning',
      component: MyLearningPage,
    },
    {
      path: '/notice/NoticePage',
      name: 'notice',
      component: NoticePage,
    },
    ...authRoutes,
    ...tradeRoutes,

  ],
});

export default router;
