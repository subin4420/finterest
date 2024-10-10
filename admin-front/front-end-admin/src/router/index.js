import Vue from 'vue'
import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/pages/HomePage.vue'
import Users from '@/pages/Users.vue'
import Archives from '@/pages/Archives.vue'
import Quizzes from '@/pages/Quizzes.vue'
import UserForm from '@/components/User/UserForm.vue'
import ArchiveForm from '@/components/Archive/ArchiveForm.vue'
import QuizForm from '@/components/Quiz/QuizForm.vue'
import authRoutes from './auth';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    { path: '/users', component: Users },
    { path: '/users/create', component: UserForm },
    { path: '/users/edit/:id', component: UserForm, props: true },
    { path: '/archives', component: Archives },
    { path: '/archives/create', component: ArchiveForm },
    { path: '/archives/edit/:id', component: ArchiveForm, props: true },
    { path: '/quizzes', component: Quizzes },
    { path: '/quizzes/create', component: QuizForm },
    { path: '/quizzes/edit/:id', component: QuizForm, props: true },
    ...authRoutes,

  ],

});

export default router;
