<template>
  <div class="user">
    <userInfo />
    <basicInfo />
  </div>
</template>

<script setup lang="ts">
import userInfo from "./com/userInfo.vue";
import basicInfo from "./com/basicInfo.vue";
</script>

<style lang="scss" scoped>
.user {
  display: flex;
}
</style>
