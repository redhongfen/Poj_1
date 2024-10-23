import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import '@/assets/css/global.css'
import '@/assets/css/iconfont/iconfont.css'
import '@/assets/css/theme/index.css'
import 'highlight.js/styles/monokai-sublime.css'
import router from './router';

Vue.config.productionTip = false
Vue.use(ElementUI, { size: 'small' });

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
