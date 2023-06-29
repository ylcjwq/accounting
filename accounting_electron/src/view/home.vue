<template>
  <el-container class="layout-container-demo" style="height: 100vh">
    <el-aside :width="200">
      <el-scrollbar>
        <el-menu default-active="1" :collapse="isCollapse" class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="routerPush('/')">
            <el-icon>
              <House />
            </el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <Notebook />
              </el-icon>
              <span>记账</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="routerPush('/spending')">支出</el-menu-item>
              <el-menu-item index="2-2" @click="routerPush('/revenue')">收入</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><icon-menu /></el-icon>
              <span>Navigator Two</span>
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="3-1">Option 1</el-menu-item>
              <el-menu-item index="3-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="3-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="3-4">
              <template #title>Option 4</template>
              <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <setting />
              </el-icon>
              <span>Navigator Three</span>
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="4-1">Option 1</el-menu-item>
              <el-menu-item index="4-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="4-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="4-4">
              <template #title>Option 4</template>
              <el-menu-item index="4-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="display: flex; font-size: 16px;justify-content: space-between;">
        <div class="leftbar">
          <el-icon @click="isCollapse = false" v-if="isCollapse">
            <Expand />
          </el-icon>
          <el-icon @click="isCollapse = true" v-else>
            <Fold />
          </el-icon>
        </div>
        <div class="h_title">
          记账本软件
        </div>
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>个人设置</el-dropdown-item>
                <el-dropdown-item @click="OutLogin">退出登录</el-dropdown-item>
                <!-- <el-dropdown-item>Delete</el-dropdown-item> -->
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Tom</span>
        </div>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
  
<script lang="ts" setup>
import { Menu as IconMenu } from '@element-plus/icons-vue'
import { useRouter } from "vue-router"
import { Ref, ref } from "vue";

const router = useRouter()
const isCollapse: Ref<boolean> = ref(false)

const routerPush = (path: string) => {    //路由跳转方法
  router.push(path)
}
// 点击退出按钮
const OutLogin = ()=>{
  router.replace('/login');
  window.localStorage.removeItem('token')
}
</script>
  
<style scoped lang="scss">
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .h_title{
  font-weight: 530;
  font-size: 20px;
  letter-spacing: 10px;
  margin-top: 20px;
}
.layout-container-demo .toolbar {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.layout-container-demo .leftbar {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 22px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 200px;
}
</style>
  
  