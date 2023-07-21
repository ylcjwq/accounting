<template>
  <el-card class="box-card" shadow="always">
    <template #header>
      <div class="card-header">
        <span>基本资料</span>
      </div>
    </template>
    <el-tabs v-model="activeName" class="demo-tabs">
      <el-tab-pane label="基本资料" name="first">
        <el-form
          :model="ruleFormBasic"
          :rules="rulesBasic"
          label-width="80px"
          class="demo-ruleForm"
          status-icon
        >
          <el-form-item label="用户名称" prop="name">
            <el-input v-model="ruleFormBasic.name" />
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio-group v-model="ruleFormBasic.gender">
              <el-radio label="男" />
              <el-radio label="女" />
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div style="display: flex; justify-content: end">
          <el-button type="primary" @click="saveUserMessage">保存</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="修改密码" name="second">
        <el-form
          :model="ruleFormPassword"
          :rules="rulesPassword"
          label-width="80px"
          class="demo-ruleForm"
          status-icon
        >
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input
              v-model="ruleFormPassword.oldPassword"
              placeholder="请输入旧密码"
              type="password"
              show-password
            />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input
              v-model="ruleFormPassword.newPassword"
              placeholder="请输入新密码"
              type="password"
              show-password
            />
          </el-form-item>
          <el-form-item label="确认密码" prop="surePassword">
            <el-input
              v-model="ruleFormPassword.surePassword"
              placeholder="请再次输入新密码"
              type="password"
              show-password
            />
          </el-form-item>
        </el-form>
        <div style="display: flex; justify-content: end">
          <el-button type="primary" @click="saveChangePassword">保存</el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormRules } from "element-plus";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { userMessage, changePassword } from "@/api/user";

//基本信息的接口
interface RuleFormBasic {
  name: string;
  gender: string;
}
//修改密码的接口
interface RuleFormPassword {
  oldPassword: string;
  newPassword: string;
  surePassword: string;
}

const activeName = ref<string>("first");
const userStore = useUserStore();
const { id, name, gender } = storeToRefs(userStore);

//基本信息
const ruleFormBasic = reactive<RuleFormBasic>({
  name: name.value!,
  gender: gender.value!,
});
//修改密码
const ruleFormPassword = reactive<RuleFormPassword>({
  oldPassword: "",
  newPassword: "",
  surePassword: "",
});

//基本信息校验规则
const rulesBasic = reactive<FormRules<RuleFormBasic>>({
  name: [
    { required: true, message: "请输入用户名称", trigger: "blur" },
    { min: 2, max: 8, message: "用户名称必须为2~8位字符", trigger: "blur" },
  ],
  gender: [
    {
      required: true,
      message: "请选择性别",
      trigger: "change",
    },
  ],
});

//修改密码校验规则
const rulesPassword = reactive<FormRules<RuleFormPassword>>({
  oldPassword: [
    { required: true, message: "请输入旧密码", trigger: "blur" },
    { min: 6, max: 12, message: "密码必须为6~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
  ],
  newPassword: [
    { required: true, message: "请输入新密码", trigger: "blur" },
    { min: 6, max: 12, message: "密码必须为6~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
    {
      validator: (_rule, value, callback) => {
        if (value === ruleFormPassword.oldPassword) {
          callback(new Error("新密码不能与旧密码一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
  surePassword: [
    { required: true, message: "请再次输入新密码", trigger: "blur" },
    { min: 6, max: 12, message: "密码必须为6~12位字符", trigger: "blur" },
    {
      pattern: /^[a-zA-Z0-9@.~!#$%^]*$/,
      message: "密码只能包含字母、数字和特殊字符",
      trigger: "blur",
    },
    {
      validator: (_rule, value, callback) => {
        if (value === ruleFormPassword.oldPassword) {
          callback(new Error("新密码不能与旧密码一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
    {
      validator: (_rule, value, callback) => {
        if (value !== ruleFormPassword.newPassword) {
          callback(new Error("两次输入的新密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
});

//修改基本信息保存按钮
const saveUserMessage = async (): Promise<void> => {
  const data = {
    id: id.value!,
    name: ruleFormBasic.name,
    gender: ruleFormBasic.gender,
  };
  await userMessage(data);
  ElMessage.success("修改成功！");
  name.value = ruleFormBasic.name;
  gender.value = ruleFormBasic.gender;
};

const saveChangePassword = async (): Promise<void> => {
  const data = {
    id: id.value!,
    oldPassword: ruleFormPassword.oldPassword,
    newPassword: ruleFormPassword.newPassword,
  };
  await changePassword(data);
  ElMessage.success("修改成功！");
  ruleFormPassword.oldPassword = "";
  ruleFormPassword.newPassword = "";
  ruleFormPassword.surePassword = "";
};
</script>

<style lang="scss" scoped>
.box-card {
  flex: 1;
  margin: 20px 20px 0 20px;
  height: 100%;
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
