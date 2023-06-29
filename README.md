# Accounting

#### 介绍
记账本桌面版

#### 软件介绍
本软件是记账本软件，主要功能是记录支出和收入明细，并将数据通过可视化图标展示
#### 开发规范
  # import引入规范
  1.最开始引入vue相关内容
  2.其次引入ui组件库相关内容
  3.引入其他插件相关内容
  4.引入组件
  5.引入封装的功能函数或方法
  6.引入接口api

  eg：import { reactive, ref } from 'vue'
      import { useRouter } from "vue-router";
      import type { FormInstance, FormRules } from "element-plus"
      import { User, Lock } from '@element-plus/icons-vue'
      import { enroll } from "@/api/login"

  # 编码规范
  1.尽量避免ts语法爆红
  2.尽量减少any类型的使用
  3.每个方法，以及逻辑区域都要写明注释这一块是在干什么
  4.较大代码块要抽离成组件，复用度高的组件或方法尽量封装

#### 安装教程

1.  npm i
2.  npm run dev
3.  npm run start

#### 使用说明

1.  electron文件夹是前端内容
2.  node文件夹是后端内容

