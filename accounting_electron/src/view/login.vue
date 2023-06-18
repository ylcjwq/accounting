<template>
  <div class="loginBox">
    <div class="login_item">
      <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="70px" class="demo-ruleForm">
        <el-form-item label="账 号" prop="username">
          <el-input v-model="ruleForm.username" type="text" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密 码" prop="password">
          <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <!-- <el-form-item label="验证码" prop="age">
          <el-input v-model.number="ruleForm.age" />
        </el-form-item> -->
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormRef)">登 录</el-button>
          <el-button @click="resetForm(ruleFormRef)">重 置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>
  
<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { login } from "@/api/login"

const ruleFormRef = ref<FormInstance>()

// const checkAge = (rule: any, value: any, callback: any) => {
//   if (!value) {
//     return callback(new Error('请输入验证码！'))
//   }
//   setTimeout(() => {
//     if (!Number.isInteger(value)) {
//       callback(new Error('Please input digits'))
//     } else {
//       if (value < 18) {
//         callback(new Error('Age must be greater than 18'))
//       } else {
//         callback()
//       }
//     }
//   }, 1000)
// }

const validatePass = (rule: any, value: any, callback: any) => {
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
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码！'))
  } else {
    callback()
  }
}

const ruleForm = reactive({
  username: '',
  password: '',
  // age: '',
})

const rules = reactive<FormRules>({
  username: [{ validator: validatePass, trigger: 'blur' }],
  password: [{ validator: validatePass2, trigger: 'blur' }],
  // age: [{ validator: checkAge, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      console.log(ruleForm);
      console.log('submit!')
      const data = await login(ruleForm)
      console.log(data);
      if (data.code == 200) {
        localStorage.setItem('token', data.token);
      }
    } else {
      console.log('error submit!')
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>
  
<style scoped>
.loginBox {
  height: 100vh;
  background: url(../assets/login.jpg) no-repeat;
  /* 背景图片路径 */
  background-size: 100% 100%;
  ;
  /* 图片充满元素 */
}

.loginBox .login_item {
  width: 450px;
  height: 350px;
  background-color: rgb(255, 255, 255);
  border-radius: 8px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.loginBox .login_item .demo-ruleForm {
  /* margin-left: 0; */
  /* padding-left: 0; */
  margin-top: 80px;
  margin-right: 18px;
}
</style>
  
  