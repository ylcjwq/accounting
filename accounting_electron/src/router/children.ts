const children = [
    {
        path: "",
        name: "main",
        title: "主页",
        component: () => import("@/view/main/index.vue")
    },
    {
        path: '/accounting',
        name: 'accounting',
        title: '记账',
        component: () => import('@/view/accounting/index.vue')
    }
]

export default children