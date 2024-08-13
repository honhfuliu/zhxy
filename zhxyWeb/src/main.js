// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI, {Container} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//导入icon样式
import '../src/assets/font_icon/iconfont.css'
import axios from "axios";


// 配置URL地址
axios.defaults.baseURL = "http://192.168.129.140:8989"
// 将axios挂载到vue对象中
Vue.prototype.$axios = axios

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Container)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
