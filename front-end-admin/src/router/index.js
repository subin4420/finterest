import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';
import ArchivePage from '../pages/archive/ArchivePage.vue';
import QuizPage from '../pages/quiz/QuizPage.vue';
import NoticePage from '../pages/notice/NoticePage.vue';
import authRoutes from './auth';
import UserPage from '@/pages/user/UserPage.vue'
import PointPage from '@/pages/point/PointPage.vue';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path: '/user/UserPage',
      name: 'user',
      component: UserPage,
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
      path: '/notice/NoticePage',
      name: 'notice',
      component: NoticePage,
    },
    {
      path: '/point/PointPage',
      name: 'point',
      component: PointPage,
    },

    ...authRoutes,

  ],

});

export default router;
