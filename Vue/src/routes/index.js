import { createRouter, createMemoryHistory } from 'vue-router';
import HelloWorld from '../components/HelloWorld.vue';
import TestComponent from '../components/TestComponent.vue';

const routes = [
  {
    path: '/',         // 根路径
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
