<template>
  <el-card class="budget">
    <template #header>
      <div class="card-header">
        <span>设置预算</span>
        <el-switch
          v-model="enabled"
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
          :before-change="beforeChange"
          @change="changeEnabled"
        />
      </div>
    </template>
    <div class="text">
      <span>当月预算：</span>
      <span>￥{{ budget || "未设置" }}</span>
    </div>
    <el-divider />
    <div class="text">
      <span>当月消费：</span>
      <span>￥1000</span>
    </div>
    <el-divider />
    <div class="text">
      <span>剩余额度：</span>
      <span>￥500</span>
    </div>
    <el-divider />
    <div style="display: flex; justify-content: center">
      <el-button type="primary" round @click="editBudget">编辑预算</el-button>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { inquiryBudget, setBudget, openCloseBudget } from "@/api/record";

const enabled = ref<boolean>(false); //是否开启预算
const enabledShow = ref<boolean>(false); //是否设置过预算
const budget = ref<string>(""); //预算值

const userStore = useUserStore();
const { id } = storeToRefs(userStore);

//查询是否设置过预算
//INFO 如果要全局显示超出预算提示的话，该逻辑应该提升到home.vue组件中
const quiryBudget = async (): Promise<void> => {
  const loadingInstance = ElLoading.service({ fullscreen: true }); //开启loading动画
  const res = await inquiryBudget({ userId: id.value! });
  const data = res.data;
  if (data.setBudget === true) {
    enabledShow.value = true; //设置过预算
    budget.value = data.budget;
    enabled.value = data.enabled;
  }
  loadingInstance.close(); //关闭loading动画
};
quiryBudget();

//如果未设置过预算，则不允许开启预算功能
const beforeChange = (): boolean => {
  if (enabledShow.value === false) {
    ElMessage.warning("请先设置预算，再开启预算功能！");
    return false;
  }
  return true;
};

//改变预算开关状态
const changeEnabled = async (): Promise<void> => {
  const loadingInstance = ElLoading.service({ fullscreen: true }); //开启loading动画
  await openCloseBudget({ userId: id.value!, enabled: enabled.value });
  loadingInstance.close(); //关闭loading动画
};

//编辑预算
const editBudget = async (): Promise<void> => {
  try {
    const { value } = await ElMessageBox.prompt(
      "请输入您想要设置的预算",
      "编辑预算",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[1-9]\d*$/,
        inputErrorMessage: "预算只能输入正整数",
      }
    );
    const loadingInstance = ElLoading.service({ fullscreen: true }); //开启loading动画
    await setBudget({ userId: id.value!, budget: value });
    await quiryBudget(); //编辑预算后再调用一次查询方法
    loadingInstance.close(); //关闭loading动画
    ElMessage.success("编辑预算成功！");
  } catch (error) {
    console.log(error);
  }
};
</script>

<style lang="scss" scoped>
.budget {
  width: 480px;
  margin: 40px 0 0 20px;
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .text {
    display: flex;
    justify-content: space-between;
  }
}
</style>
