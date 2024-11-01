import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import '@/assets/css/global.css'
import '@/assets/css/iconfont/iconfont.css'
import '@/assets/css/theme/index.css'
import 'highlight.js/styles/monokai-sublime.css'
import router from './router';
import request from './utils/request';
import store from './store';

Vue.config.productionTip = false
Vue.use(ElementUI, { size: 'small' });

Vue.config.productionTip = false

Vue.prototype.$request=request
Vue.prototype.$baseUrl=process.env.VUE_APP_BASEURL

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
