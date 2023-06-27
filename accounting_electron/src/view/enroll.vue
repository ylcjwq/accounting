<template>
    <div class="enrollBox">
        <div class="login_item">
            <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="70px"
                class="demo-ruleForm">
                <el-form-item label="账 号" prop="username">
                    <el-input v-model="ruleForm.username" type="text" autocomplete="off" />
                </el-form-item>
                <el-form-item label="密 码" prop="password">
                    <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input v-model="ruleForm.checkPass" type="password" autocomplete="off" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm(ruleFormRef)">登 录</el-button>
                    <!-- <el-button @click="resetForm(ruleFormRef)">重 置</el-button> -->
                </el-form-item>
                <span class="register" @click="router.push('/login')">
                    已有账号？去注册
                </span>
            </el-form>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { reactive, ref } from 'vue'
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus"
import { enroll } from "@/api/login"

interface RuleForm {            //定义输入框内容接口
    username: string | number
    password: string | number
    checkPass: string | number
}

const router = useRouter()
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
    username: '',
    password: '',
    checkPass: '',
})

const validatePass1 = (rule: any, value: any, callback: any) => {  //账号校验规则
    if (value === '') {
        callback(new Error('请输入账号！'))
    } else {
        if (ruleForm.username !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('password', () => null)
        }
        callback()
    }
}
const validatePass2 = (rule: any, value: any, callback: any) => {  //密码校验规则
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        if (ruleForm.checkPass !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}
const validatePass3 = (rule: any, value: any, callback: any) => {  //确认密码校验规则
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== ruleForm.password) {
        console.log(value);
        console.log(ruleForm.password);
        callback(new Error("两次密码输入不一致"))
    } else {
        callback()
    }
}

const rules = reactive<FormRules>({
    username: [{ validator: validatePass1, trigger: 'blur' }],
    password: [{ validator: validatePass2, trigger: 'blur' }],
    checkPass: [{ validator: validatePass3, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {   //确认注册
    if (!formEl) return
    formEl.validate(async (valid: any) => {
        if (valid) {

            console.log('submit!')
            const data = await enroll(ruleForm)
            if (data.code == 200) {
                console.log("注册成功!");
            }
        } else {
            console.log('error submit!')
            return false
        }
    })
}
</script>

<style scoped lang="scss">
.enrollBox {
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
