import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Profile from '@/views/Profile.vue';
import Quiz from '@/views/Quiz.vue';
import Archive from '@/views/Archive.vue';
import Trading from '@/views/Trading.vue';
import Login from '@/views/Login.vue';
import FindPassword from '@/views/FindPassword.vue';
import ChangePassword from '@/views/ChangePassword.vue';
import SignUp from '@/views/SignUp.vue';
import ProfileSettings from '@/views/ProfileSettings.vue';
import MyLearning from '@/views/MyLearning.vue';
import NoticeBoard from '@/views/NoticeBoard.vue';


const routes = [
    { path: '/', component: Home },
    { path: '/profile', component: Profile },
    { path: '/archive', component: Archive },
    { path: '/quiz', component: Quiz },
    { path: '/trading', component: Trading },
    { path: '/login', component: Login },
    { path: '/find-password', name: 'FindPassword', component: FindPassword, },
    { path: '/change-password', name: 'ChangePassword', component: ChangePassword, },
    { path: '/signup', name: 'SignUp', component: SignUp, },
    { path: '/profile-settings', name: 'ProfileSettings', component: ProfileSettings, },
    { path: '/my-learning', name: 'MyLearning', component: MyLearning, },
    { path: '/notice', name: 'NoticeBoard', component: NoticeBoard, },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
