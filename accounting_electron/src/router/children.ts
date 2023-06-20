const children = [
    {
        path: "",
        name: "main",
        title: "主页",
        component: () => import("@/view/main/index.vue")
    },
    {
        path: '/spending',
        name: 'spending',
        title: '支出',
        component: () => import('@/view/spending/index.vue')
    },
    {
        path: '/revenue',
        name: 'revenue',
        title: '收入',
        component: () => import('@/view/revenue/index.vue')
    },
    {
        path: '/accounting',
        name: 'accounting',
        title: '记账',
        component: () => import('@/view/accounting/index.vue')
    }
]

export default children