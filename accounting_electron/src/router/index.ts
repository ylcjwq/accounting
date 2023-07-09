import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
})

NProgress.configure({
    easing: 'ease',   // 动画方式
    speed: 1000,   // 递增进度条的速度
    showSpinner: false,   // 是否显示加载ico
    trickleSpeed: 200,   // 自动递增间隔
    minimum: 0.3,   // 更改启动时使用的最小百分比
    parent: 'body',   //指定进度条的父容器
})

router.beforeEach((to: any, _from: any, next: any) => {
    NProgress.start()
    if (to.path == "/login") {
        next()
    } else if (to.path == "/enroll") {
        next()
    } else {
        const token = localStorage.getItem("token")
        if (token) {
            next()
        } else {
            next({ path: "/login" })
        }
    }
})

router.afterEach((to: any, from: any) => {
    NProgress.done(true)
})

export default router
