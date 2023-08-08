<template>
  <div class="enrollBox">
    <div class="login_item">
      <span class="log_item">注 册</span>
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
          />
        </el-form-item>
        <el-form-item label="密 码" prop="password">
          <el-input
            v-model="ruleForm.password"
            type="password"
            autocomplete="off"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            v-model="ruleForm.checkPass"
            type="password"
            autocomplete="off"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item style="margin-left: 60px">
          <el-button type="primary" @click="submitForm(ruleFormRef)"
            >注 册</el-button
          >
          <el-button @click="resetForm(ruleFormRef)" style="margin-left: 40px"
            >重 置</el-button
          >
        </el-form-item>
        <div class="register" @click="router.replace('/login')">
          <span style="color: rgb(141, 139, 139)">已有账号?</span>
          <span class="goToLogin">去登录</span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";
import { User, Lock } from "@element-plus/icons-vue";
import { enroll } from "@/api/login";

interface RuleForm {
  //定义输入框内容接口
  username: string;
  password: string;
  checkPass: string;
}

// 定义字体样式初始化
const isBoolean = reactive({
  isBoolean: false,
});
const isColor = reactive({
  color: false,
});

const router = useRouter();
const ruleFormRef = ref<FormInstance>();
const ruleForm = reactive<RuleForm>({
  username: "",
  password: "",
  checkPass: "",
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

  checkPass: [
    { required: true, message: "请再次输入密码", trigger: "blur" },
    { min: 6, max: 12, message: "密码必须为6~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
    {
      validator: (
        _rule: Record<string, any>,
        value: string,
        callback: (error?: Error) => void
      ) => {
        if (value !== ruleForm.password) {
          callback(new Error("两次输入的新密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
});

const submitForm = (formEl: FormInstance | undefined) => {
  //确认注册
  if (!formEl) return;
  formEl.validate(async (valid: any) => {
    if (valid) {
      const data: any = await enroll(ruleForm);
      if (data.code == 200) {
        console.log("注册成功!");
        router.replace("/login");
        open2();
      } else {
        console.log("失败");
        isBoolean.isBoolean = true;
        isColor.color = true;
        resetForm(ruleFormRef.value);
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
// 注册成功，消息提示
const open2 = () => {
  ElMessage({
    message: "注册成功",
    type: "success",
  });
};
</script>

<style scoped lang="scss">
.enrollBox {
  height: 100vh;
  background: url(../assets/login.jpg) no-repeat;
  /* 背景图片路径 */
  background-size: 100% 100%;
  /* 图片充满元素 */
  .login_item {
    .log_item {
      color: rgb(141, 139, 139);
      float: left;
      margin-top: 10px;
      margin-left: 10px;
    }

    width: 450px;
    height: 350px;
    background-color: rgb(255, 255, 255);
    border-radius: 8px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    .demo-ruleForm {
      margin-top: 80px;
      margin-right: 18px;
    }
  }
}

.register {
  margin: 30px 0 0 160px;

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
</style>
