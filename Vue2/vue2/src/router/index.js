import Vue from 'vue';
import Router from 'vue-router';
import HomeView from '../components/HomeView.vue'; // 确保路径正确
import TestView from '../components/TestView.vue'; // 确保路径正确

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
  ],
});
