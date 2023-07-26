<template>
  <el-card class="budget">
    <template #header>
      <div class="card-header">
        <span>设置预算</span>
        <el-switch
          v-model="enabled"
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
        />
      </div>
    </template>
    <div class="text">
      <span>当月预算：</span>
      <span>￥{{ budget }}</span>
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
      <el-button type="primary" round>编辑预算</el-button>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { inquiryBudget, setBudget } from "@/api/record";

const enabled = ref<boolean>(false); //是否开启预算
const enabledShow = ref<boolean>(false); //是否设置预算
const budget = ref<string>(""); //预算值

const userStore = useUserStore();
const { id } = storeToRefs(userStore);

//查询是否设置过预算
const quiryBudget = async () => {
  const res = await inquiryBudget({ userId: id.value! });
  console.log(res.data);
  const data = res.data;
  if (data.setBudget === true) {
    enabledShow.value = true; //设置过预算
    budget.value = data.budget;
    if (data.enabled === 1) {
      enabled.value = true;
    } else {
      enabled.value = false;
    }
  }
};
quiryBudget();
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
