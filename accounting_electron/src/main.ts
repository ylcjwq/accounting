import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'

import '@/style/reset.css'
import "@/style/iconfont/iconfont.css";

createApp(App).use(router).mount('#app')
