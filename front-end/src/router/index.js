import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import ArchivePage from '../pages/archive/ArchivePage.vue';
import QuizPage from '../pages/quiz/QuizPage.vue';
import TradePage from '../pages/trade/TradePage.vue';
import MyLearningPage from '../pages/myLearning/MyLearningPage.vue';
import NoticePage from '../pages/notice/NoticePage.vue';
import authRoutes from './auth';
import NoticeDetailPage from '@/pages/notice/NoticeDetailPage.vue';
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
      path: '/trade',
      name: 'trade',
      component: TradePage,
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
    {
      path: '/notices/:id',
      name: 'NoticeDetail',
      component: NoticeDetailPage,
    },

    ...authRoutes,
    ...tradeRoutes,
  ],
});

export default router;
