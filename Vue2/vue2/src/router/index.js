import Vue from 'vue';
import VueRouter from 'vue-router';
import HomeView from '../views/manager/HomeView.vue'; // 确保路径正确
import LoginView from '@/views/LoginView.vue';
import RegisterView from '@/views/RegisterView.vue';
import ManagerView from '@/views/ManagerView.vue';
import UserView from '@/views/manager/UserView.vue';
import AuthView from '@/views/manager/AuthView.vue';
import NullView from '@/views/404View.vue'
import PresonView from '@/views/manager/PresonView.vue';
import PasswordView from '@/views/manager/PasswordView.vue';
import CoursesView from '@/views/manager/CoursesView.vue';
import TeachersView from '@/views/manager/TeachersView.vue';
import StudentsView from '@/views/manager/StudentsView.vue';
import EnrollmentsView from '@/views/manager/EnrollmentsView.vue';
import GradesView from '@/views/manager/GradesView.vue';
import StudentGradesView from '@/views/manager/StudentGradesView.vue';
import SummaryView from '@/views/manager/SummaryView.vue';
Vue.use(VueRouter);

const routes=[
  {
    path: '/',
    name: 'Manager',
    component: ManagerView,
    redirect:'home',
    children:[
      {path:'home',name:'HomeView',meta:{name:'首页系统'},component:HomeView},
      {path:'user',name:'UserView',meta:{name:'用户信息'},component:UserView},
      {path: '403',name: 'AuthView',meta:{name:'无权限'},component: AuthView,},
      {path: 'preson',name: 'PresonView',meta:{name:'个人信息'},component: PresonView,},
      {path: 'password',name: 'PasswordView',meta:{name:'修改密码'},component: PasswordView,},
      {path: 'courses',name: 'CoursesView',meta:{name:'课程信息'},component: CoursesView,},
      {path: 'students',name: 'StudentsView',meta:{name:' 学生信息'},component: StudentsView,},
      {path: 'teachers',name: 'TeachersView',meta:{name:'教师信息'},component: TeachersView,},
      {path: 'enrollments',name: 'EnrollmentsView',meta:{name:'选课信息'},component: EnrollmentsView,},
      {path: 'grades',name: 'GradesView',meta:{name:'成绩管理'},component: GradesView,},
      {path: 'gradesstudent',name: 'StudentGradesView',meta:{name:'成绩信息'},component: StudentGradesView,},
      {path: 'summary',name: 'SummaryView',meta:{name:'成绩信息'},component: SummaryView,},
    ]
  },
  {path: '/login',name: 'LoginView',meta:{name:'登录'},component: LoginView,},
  {path: '/register',name: 'RegisterView',meta:{name:'注册'},component: RegisterView,},
  {path: '*',name: '404',meta:{name:'无法访问'},component: NullView,},

]
const router = new VueRouter({
  mode:'history',
  base:process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('honey-user') || '{}')

  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，然后当前的到达的路径是管理员才有权限访问的路径，那这个时候我就让用户去到一个没有权限的页面，不让他访问实际的页面
    next('/403')
  } else {
    next()
  }
})
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router