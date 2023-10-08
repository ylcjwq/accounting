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
    path: "/photoKeeping",
    name: "photoKeeping",
    title: "识图记账",
    component: () => import("@/view/aiPhotoKeeping/index.vue"),
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
  {
    path: "/setting",
    name: "setting",
    title: "其他设置",
    component: () => import("@/view/user/setting.vue"),
  },
];

export default children;
