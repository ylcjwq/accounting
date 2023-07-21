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
      <span>￥1500</span>
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

const enabled = ref<boolean>(false);
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
