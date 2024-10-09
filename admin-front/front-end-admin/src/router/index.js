import Vue from 'vue'
import Router from 'vue-router'
import Users from '@/pages/Users.vue'
import Archives from '@/pages/Archives.vue'
import Quizzes from '@/pages/Quizzes.vue'
import UserForm from '@/components/User/UserForm.vue'
import ArchiveForm from '@/components/Archive/ArchiveForm.vue'
import QuizForm from '@/components/Quiz/QuizForm.vue'

Vue.use(Router)

export default new Router({
  routes: [
    { path: '/users', component: Users },
    { path: '/users/create', component: UserForm },
    { path: '/users/edit/:id', component: UserForm, props: true },
    { path: '/archives', component: Archives },
    { path: '/archives/create', component: ArchiveForm },
    { path: '/archives/edit/:id', component: ArchiveForm, props: true },
    { path: '/quizzes', component: Quizzes },
    { path: '/quizzes/create', component: QuizForm },
    { path: '/quizzes/edit/:id', component: QuizForm, props: true },
  ]
})
