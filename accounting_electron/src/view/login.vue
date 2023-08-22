<template>
  <!-- 登录模块 -->
  <div class="loginBox">
    <video
      src="@/assets/login_bg.mp4"
      class="beijin"
      autoplay
      loop
      muted
    ></video>
    <div class="login_item" v-if="logOrEnr">
      <span class="log_item">登 录</span>
      <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="90px"
        class="demo-ruleForm"
      >
        <el-form-item label="账 号" prop="username">
          <el-input
            v-model="ruleForm.username"
            type="text"
            autocomplete="off"
            :prefix-icon="User"
            :append="''"
            size="large"
          />
        </el-form-item>
        <el-form-item label="密 码" prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            autocomplete="off"
            :prefix-icon="Lock"
            show-password
            size="large"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm(ruleFormRef)"
            style="margin-left: 73px; margin-top: 30px"
            >登 录</el-button
          >
          <el-button
            @click="resetForm(ruleFormRef)"
            style="margin-left: 40px; margin-top: 30px"
            >重 置</el-button
          >
        </el-form-item>
        <span class="register" @click="logOrEnr = false">
          <span style="color: rgb(141, 139, 139)">没有账号?</span>
          <span class="goToLogin">去注册</span>
        </span>
      </el-form>
    </div>
    <enroll v-else @getToLogin="getToLogin" />
  </div>
</template>

<script lang="ts" setup>
import { User, Lock } from "@element-plus/icons-vue";
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";
import { useUserStore } from "@/store/user";
import { login } from "@/api/login";
import enroll from "@/view/enroll.vue";

const logOrEnr = ref<boolean>(true);
const ruleFormRef = ref<FormInstance>();
const router = useRouter();
const userStore = useUserStore();

// 登录参数声明
const ruleForm = reactive({
  username: "",
  password: "",
});

const rules = reactive<FormRules>({
  username: [
    { required: true, message: "请输入账号", trigger: "blur" },
    { min: 5, max: 12, message: "账号必须为5~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
  ],

  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 12, message: "密码必须为6~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
  ],
});

// 点击登录
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      const data: any = await login(ruleForm);
      console.log(data);

      if (data.code == 200) {
        userStore.$patch({
          //将用户信息存入仓库
          id: data.id,
          username: data.username,
          name: data.nickname,
          sex: data.sex,
          userimg: data.avatar,
          time: data.loginDate,
        });
        localStorage.setItem("token", data.token);
        router.replace("/home");
      }
    } else {
      return false;
    }
  });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

const getToLogin = (): void => {
  logOrEnr.value = true;
};
</script>

<style scoped lang="scss">
.loginBox {
  height: 100vh;
  width: 100vw;
  .beijin {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -10;
    object-fit: cover;
  }
  .login_item {
    .log_item {
      color: rgb(141, 139, 139);
      float: left;
      margin-top: 10px;
      margin-left: 10px;
    }

    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: rgba(255, 255, 255, 0.5);
    /* 半透明背景颜色 */
    width: 500px;
    height: 400px;
    padding: 20px;
    border-radius: 25px;
    align-items: center;
    backdrop-filter: blur(4px) saturate(150%);
    box-shadow: 0 0 10px rgba(255, 255, 255, 0.5);

    .demo-ruleForm {
      /* margin-left: 0; */
      /* padding-left: 0; */
      margin-top: 80px;
      margin-right: 18px;

      .register {
        margin: 30px 0 0 160px;
        cursor: pointer;
        line-height: 81px;
        font-size: 17px;
        color: red;

        .goToLogin {
          font-size: 16px;
          background: -webkit-gradient(
            linear,
            left top,
            right top,
            color-stop(0, #89e972),
            color-stop(0.4, rgb(159, 241, 173)),
            color-stop(0.5, rgb(188, 243, 227)),
            color-stop(0.6, #92e9f0),
            color-stop(1, rgb(96, 207, 235))
          );
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          cursor: pointer;
        }
      }
    }
  }
}
::v-deep .el-input__wrapper {
  background-color: rgba(255, 255, 255, 0.4);
}
</style>
