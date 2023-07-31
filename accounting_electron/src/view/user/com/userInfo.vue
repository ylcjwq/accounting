<template>
  <el-card class="box-card" shadow="always">
    <template #header>
      <div class="card-header">
        <span>个人信息</span>
      </div>
    </template>
    <div style="display: flex; justify-content: center">
      <el-avatar @click="replaceHscul"
        :size="100" type="file"
        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
      />
    </div>
    <el-divider />
    <div class="card-footer">
      <span>账号：</span>
      <span>{{ username }}</span>
    </div>
    <el-divider />
    <div class="card-footer">
      <span>用户名称：</span>
      <span>{{ name }}</span>
    </div>
    <el-divider />
    <div class="card-footer">
      <span>用户名称：</span>
      <span>{{ gender }}</span>
    </div>
    <el-divider />
    <div class="card-footer">
      <span>创建日期：</span>
      <span>{{ time }}</span>
    </div>
  </el-card>

  <!-- 上传头像对话框 -->
  <el-dialog
    v-model="dialogVisible"
    title="Tips"
    width="30%"
  >
  <!-- 上传本地头像文件 -->
  <el-upload
    class="avatar-uploader"
    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload"
  >
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { ref } from 'vue'
//引入
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

import type { UploadProps } from 'element-plus'
const userStore = useUserStore();
const { username, name, gender, time } = storeToRefs(userStore);
//控制对话框的显示和隐藏
const dialogVisible = ref(false)
// 点击退出按钮
const replaceHscul = () => {
  console.log('用户头像上传')
  dialogVisible.value = true
};
// 上传头像逻辑
const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}
</script>

<style lang="scss" scoped>
.box-card {
  width: 34%;
  height: 100%;
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
// 对话框样式
.dialog-footer button:first-child {
  margin-right: 10px;
}
// 上传本地头像样式
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
