<template>
  <!-- 登录模块 -->
  <div class="loginBox">
    <div class="login_item">
      <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="70px" class="demo-ruleForm">
        <el-form-item label="账 号" prop="username">
          <el-input v-model="ruleForm.username" type="text" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密 码" prop="password">
          <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)">登 录</el-button>
          <el-button @click="resetForm(ruleFormRef)">重 置</el-button>
        </el-form-item>
        <span class="register" @click="router.push('/enroll')">
          没有账号？去注册
        </span>
      </el-form>
    </div>
  </div>
</template>
  
<script lang="ts" setup>

import { reactive, ref } from 'vue'
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus"
import { login } from "@/api/login"

const ruleFormRef = ref<FormInstance>()

const router = useRouter()

// 登录参数声明
const ruleForm = reactive({
  username: '',
  password: ''
})

// 点击登录事件
const validatePass = (_rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入账号！'))
  } else {
    if (ruleForm.password !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('password', () => null)
    }
    callback()
  }
}
const validatePass2 = (_rule: any, value: any, callback: any) => {   //未使用的参数前面加_解决ts报错的问题
  if (value === '') {
    callback(new Error('请输入密码！'))
  } else {
    callback()
  }
}

const rules = reactive<FormRules>({
  username: [{ validator: validatePass, trigger: 'blur' }],
  password: [{ validator: validatePass2, trigger: 'blur' }],
})

// 点击登录
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      const data: any = await login(ruleForm)
      if (data.code == 200) {
        localStorage.setItem('token', data.token);
        router.replace("/home")
      }
    } else {
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
  
<style scoped lang="scss">
.loginBox {
  height: 100vh;
  background: url(../assets/login.jpg) no-repeat;
  /* 背景图片路径 */
  background-size: 100% 100%;
  ;

  /* 图片充满元素 */
  .login_item {
    width: 450px;
    height: 350px;
    background-color: rgb(255, 255, 255);
    border-radius: 8px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);

    .demo-ruleForm {
      /* margin-left: 0; */
      /* padding-left: 0; */
      margin-top: 80px;
      margin-right: 18px;

      .register {
        margin-left: 296px;
        line-height: 186px;
        font-size: 17px;
        color: red;
      }
    }
  }
}
</style>
  
  