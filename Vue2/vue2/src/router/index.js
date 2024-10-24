import Vue from 'vue';
import Router from 'vue-router';
import HomeView from '../views/HomeView.vue'; // 确保路径正确
import TestView from '../views/TestView.vue'; // 确保路径正确
import LoginView from '@/views/LoginView.vue';
import RegisterView from '@/views/RegisterView.vue';
Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HomeView,
    },
    {
      path: '/test',
      name: 'Test',
      component: TestView,
    },
    {
      path: '/login',
      name: 'LoginView',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'RegisterView',
      component: RegisterView,
    },
  ],
});
