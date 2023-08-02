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
    title="头像上传"
    width="30%"
  >
  <!-- 上传本地头像文件 -->

  
<!-- 对话框的取消与确定 -->
  <!-- <span>This is a message</span> -->
  <el-upload
    v-model:file-list="fileList"
    action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
    list-type="picture-card"
    :on-preview="handlePictureCardPreview"
    :on-remove="handleRemove"
  >
    <el-icon><Plus /></el-icon>
  </el-upload>

  <el-dialog v-model="dialogVisiblef">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>


    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          确定上传
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
// import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

import type { UploadProps,UploadUserFile } from 'element-plus'
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
const fileList = ref<UploadUserFile[]>([
  { name: 'food.jpeg',
  url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
}
])

const dialogImageUrl = ref('')
const dialogVisiblef = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!
  dialogVisiblef.value = true
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
