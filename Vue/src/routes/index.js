import { createRouter, createMemoryHistory } from 'vue-router';
import HelloWorld from '../components/HelloWorld.vue';
import TestComponent from '../components/TestComponent.vue';
import HomePage from '@/components/HomePage.vue';
const routes = [
  {
    path: '/',     // 测试页面路径
    component: HomePage,
  },
  {
    path: '/helloworld',         // 根路径
    component: HelloWorld,
  },
  {
    path: '/test',     // 测试页面路径
    component: TestComponent,
  },
  
];

const router = createRouter({
  history: createMemoryHistory(),
  routes,
});

export default router;
