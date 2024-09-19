import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Profile from '@/views/Profile.vue';
import Quiz from '@/views/Quiz.vue';
import Learning from '@/views/Learning.vue';
import Trading from '@/views/Trading.vue';
import Login from '@/views/Login.vue';
import FindPassword from '@/views/FindPassword.vue';
import SignUp from '@/views/SignUp.vue';


const routes = [
    { path: '/', component: Home },
    { path: '/profile', component: Profile },
    { path: '/learning', component: Learning },
    { path: '/quiz', component: Quiz },
    { path: '/trading', component: Trading },
    { path: '/login', component: Login },
    { path: '/find-password', name: 'FindPassword', component: FindPassword, },
    { path: '/signup', name: 'SignUp', component: SignUp, },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
