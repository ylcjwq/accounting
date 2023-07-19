<template>
  <el-card class="box-card" shadow="always">
    <template #header>
      <div class="card-header">
        <span>基本资料</span>
      </div>
    </template>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="基本资料" name="first">
        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
          class="demo-ruleForm"
          :size="formSize"
          status-icon
        >
          <el-form-item label="用户名称" prop="name">
            <el-input v-model="ruleForm.name" />
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="修改密码" name="second">
        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
          class="demo-ruleForm"
          :size="formSize"
          status-icon
        >
          <el-form-item label="旧密码" prop="name">
            <el-input v-model="ruleForm.name" />
          </el-form-item>
          <el-form-item label="新密码" prop="name">
            <el-input v-model="ruleForm.name" />
          </el-form-item>
          <el-form-item label="确认密码" prop="name">
            <el-input v-model="ruleForm.name" />
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    <div style="display: flex; justify-content: end">
      <el-button type="primary">保存</el-button>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormRules, TabsPaneContext } from "element-plus";

interface RuleForm {
  name: string;
}

const formSize = ref("default");
const ruleFormRef = ref<FormInstance>();
const ruleForm = reactive<RuleForm>({
  name: "",
});

const activeName = ref("first");

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event);
};

const rules = reactive<FormRules<RuleForm>>({
  name: [
    { required: true, message: "Please input Activity name", trigger: "blur" },
    { min: 3, max: 5, message: "Length should be 3 to 5", trigger: "blur" },
  ],
});
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
  .card-footer {
    display: flex;
    justify-content: space-between;
  }
}
</style>
