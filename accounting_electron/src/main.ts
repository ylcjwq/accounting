import { createApp } from 'vue'
import App from './App.vue'
import router from '@/router'

import '@/style/reset.css'
import "@/style/iconfont/iconfont.css";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App).use(router)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')