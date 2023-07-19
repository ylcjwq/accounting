<template>
  <el-card class="box-card" shadow="always">
    <template #header>
      <div class="card-header">
        <span>个人信息</span>
      </div>
    </template>
    <div style="display: flex; justify-content: center">
      <el-avatar
        :size="100"
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
    </div>
    <el-divider />
    <div class="card-footer">
      <span>用户名称：</span>
      <span>admin</span>
    </div>
    <el-divider />
    <div class="card-footer">
      <span>账号：</span>
      <span>admin</span>
    </div>
    <el-divider />
    <div class="card-footer">
      <span>创建日期：</span>
      <span>admin</span>
    </div>
  </el-card>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
.box-card {
  width: 34%;
  margin: 20px 0 0 20px;
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
