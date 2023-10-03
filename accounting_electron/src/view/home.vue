<template>
  <el-container class="layout-container-demo" style="height: 100vh">
    <el-aside :width="200">
      <el-scrollbar>
        <el-menu
          :collapse="isCollapse"
          :unique-opened="true"
          :router="true"
          :default-active="$route.path"
          class="el-menu-vertical-demo"
        >
          <el-menu-item index="/home">
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
              <el-menu-item index="/spending">支出</el-menu-item>
              <el-menu-item index="/revenue">收入</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="/report">
            <el-icon><icon-menu /></el-icon>
            <span>流水</span>
          </el-menu-item>
          <el-sub-menu index="4">
            <template #title>
              <el-icon>
                <setting />
              </el-icon>
              <span>设置</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/user">个人信息</el-menu-item>
              <el-menu-item index="4-2">Option 2</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header
        style="display: flex; font-size: 16px; justify-content: space-between"
      >
        <div class="leftbar">
          <el-icon @click="isCollapse = false" v-if="isCollapse">
            <Expand />
          </el-icon>
          <el-icon @click="isCollapse = true" v-else>
            <Fold />
          </el-icon>
        </div>
        <div class="h_title">记账本软件</div>
        <div class="toolbar">
          <span class="h_username">{{ greet(time) }} {{ name }}！</span>
          <el-dropdown>
            <el-avatar
              :size="50"
              type="file"
              :src="
                userimg ??
                'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
              "
            />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="routerPush('/user')"
                  >个人设置</el-dropdown-item
                >
                <el-dropdown-item @click="OutLogin">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <!-- 显示10秒后消失 -->
        <div class="tipTop">
          <el-icon color="#ebb462" size="30"><WarningFilled /></el-icon>
          <div>
            <span class="tipContent">预算超支！</span>
            <br />
            <span class="tipContent" style="font-size: 14px">{{
              emailsMsg.content
            }}</span>
          </div>
          <div style="position: absolute; right: 10px">x</div>
        </div>
        <router-view></router-view>
      </el-main>
    </el-container>
    <canvas id="canvas"></canvas>
  </el-container>
</template>

<script lang="ts" setup>
import { Menu as IconMenu } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { getUserMessage, emails } from "@/api/user";
import { RotationBall } from "@/util/mouseCanvas";

const router = useRouter();
console.log(router);

const isCollapse = ref<boolean>(false);
const userStore = useUserStore();
const { id, name, userimg, sex } = storeToRefs(userStore); //从仓库中获取用户信息
const time: Date = new Date(); //获取当前时间对象
const emailsMsg = reactive({
  isExceedingThreshold: false,
  content: "",
});

const greet = (time: Date): string => {
  //判断当前时间，返回对应的问候语
  if (time.getHours() >= 5 && time.getHours() < 12) {
    return "早上好!";
  } else if (time.getHours() >= 12 && time.getHours() < 14) {
    return "中午好!";
  } else if (time.getHours() >= 14 && time.getHours() < 18) {
    return "下午好!";
  }
  return "晚上好!";
};

//请求最新的用户信息
const getUser = async (): Promise<void> => {
  const res = await getUserMessage(id.value!);
  const data = res.user;
  console.log(data);
  name.value = data.nickName;
  userimg.value = `http://43.138.195.96:9999${data.avatar}`;
  sex.value = data.sex;
};
getUser();

const routerPush = (path: string): void => {
  //路由跳转方法
  router.push(path);
};
// 点击退出按钮
const OutLogin = (): void => {
  router.replace("/login");
  window.localStorage.removeItem("token");
};

//查询预算是否超支
const getEmails = async () => {
  const res = await emails();
  console.log(res.data);
  emailsMsg.content = res.data.content;
  emailsMsg.isExceedingThreshold = res.data.isExceedingThreshold;
};

onMounted(() => {
  new RotationBall(); //执行画布方法
  getEmails();
});
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

.layout-container-demo .h_title {
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

.layout-container-demo .toolbar .h_username {
  margin-right: 20px;
}

.layout-container-demo .toolbar .h_headscul {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  box-shadow: 8px 8px 15px 3px white;
}

.layout-container-demo .toolbar .h_headscul img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
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

#canvas {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 9999;
  opacity: 0.2;
  pointer-events: none; //设置画布层不捕获鼠标事件
}
.tipTop {
  background-color: #fdf8f0;
  width: 100%;
  padding: 10px 20px;
  position: fixed;
  display: flex;
  z-index: 999;
}
.tipContent {
  color: #ebb462;
  line-height: 20px;
  margin-left: 6px;
}
.el-alert {
  margin: 20px 0 0;
  position: fixed;
  // top: 0;
  z-index: 999;
}
.el-alert:first-child {
  margin: 0;
}
</style>
