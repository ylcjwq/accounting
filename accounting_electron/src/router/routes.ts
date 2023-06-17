import children from "./children"
const routes = [
    {
        path: '/', redirect: '/home'
    },
    {
        path: "/home",
        name: "home",
        title: "首页",
        component: () => import("@/view/home.vue"),
        children: children
    },
    {
        path: "/login",
        name: "login",
        title: "登录",
        component: () => import("@/view/login.vue")
    }
]

export default routes