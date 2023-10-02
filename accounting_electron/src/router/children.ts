const children = [
  {
    path: "",
    name: "main",
    title: "主页",
    component: () => import("@/view/main/index.vue"),
  },
  {
    path: "/spending",
    name: "spending",
    title: "支出",
    component: () => import("@/view/spending/index.vue"),
  },
  {
    path: "/revenue",
    name: "revenue",
    title: "收入",
    component: () => import("@/view/revenue/index.vue"),
  },
  {
    path: "/report",
    name: "report",
    title: "报表",
    component: () => import("@/view/report/index.vue"),
  },
  {
    path: "/user",
    name: "user",
    title: "个人信息",
    component: () => import("@/view/user/index.vue"),
  },
];

export default children;
