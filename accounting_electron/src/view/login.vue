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
        <span class="register" @click="Register">
          没有账号？去注册
        </span>
      </el-form>

    </div>
  </div>

  <!-- 注册对话框 -->
<el-dialog style="width: 33%;" title="注册用户" v-model="dialogFormVisible">
  <el-form
    ref="ruleFormRefr"
    :model="ruleFormr"
    status-icon
    :rules="rulesr"
    label-width="120px"
    class="demo-ruleForm"
  >
  <el-form-item label="请输入账号" prop="username">
      <el-input v-model="ruleFormr.username" type="text" autocomplete="off"/>
    </el-form-item>
    <el-form-item label="请输入密码" prop="password">
      <el-input v-model="ruleFormr.password" type="password" autocomplete="off" />
    </el-form-item>
    <el-form-item label="请确认密码" prop="checkPass">
      <el-input
        v-model="ruleFormr.checkPass"
        type="password"
        autocomplete="off"
      />
    </el-form-item>
    
    <el-form-item>
      <el-button type="primary" @click="submitFormr(ruleFormRefr)"
        >注 册</el-button
      >
      <el-button @click="resetForm(ruleFormRefr)">重 置</el-button>
    </el-form-item>
  </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
      </span>
    </template>
  </el-dialog>

</template>
  
<script lang="ts" setup>

import { reactive, ref } from 'vue'
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus"
import { login } from "@/api/login"
import { enroll } from "@/api/login"

const ruleFormRef = ref<FormInstance>()
const ruleFormRefr = ref<FormInstance>()
const router = useRouter()
// 引入对话框
const dialogFormVisible = ref(false)
// 点击登录事件
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

// 登录参数声明
const ruleForm = reactive({
  username: '',
  password: ''
})
// 注册参数声明
const ruleFormr = reactive({
  password: '',
  checkPass: '',
  username: '',
})



const rules = reactive<FormRules>({
  username: [{ validator: validatePass, trigger: 'blur' }],
  password: [{ validator: validatePass2, trigger: 'blur' }],
  // age: [{ validator: checkAge, trigger: 'blur' }],
})

// 点击登录
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
        router.push("/home")
      }
    } else {
      console.log('error submit!')
      return false
    }
  })
}
// 点击注册

const Register = () => {
  dialogFormVisible.value = true
}

// 注册表单验证及相关点击事件
const validatePass3 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleFormr.checkPass !== '') {
      if (!ruleFormRefr.value) return
      ruleFormRefr.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass4 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== ruleFormr.password) {
    callback(new Error("两次密码输入不一致"))
  } else {
    callback()
  }
}
const validatePass5 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入账号！'))
  } else {
    if (ruleFormr.username !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('password', () => null)
    }
    callback()
  }
}

const rulesr = reactive<FormRules<typeof ruleForm>>({
  password: [{ validator: validatePass3, trigger: 'blur' }],
  checkPass: [{ validator: validatePass4, trigger: 'blur' }],
  username: [{ validator: validatePass5, trigger: 'blur' }],
})

const submitFormr = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      
      console.log('submit!')
      const data = await enroll(ruleFormr)
      if(data.code == 200){
        console.log("注册成功!");
        dialogFormVisible.value = false
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
  .register{
  margin-left: 296px;
  line-height: 186px;
  font-size: 17px;
  color: red;
}
}
}
}


</style>
  
  