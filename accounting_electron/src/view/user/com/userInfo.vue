<template>
  <el-card class="box-card" shadow="always">
    <template #header>
      <div class="card-header">
        <span>个人信息</span>
      </div>
    </template>
    <div style="display: flex; justify-content: center">
      <el-avatar
        @click="dialogVisible = true"
        :size="100"
        type="file"
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
  <el-dialog v-model="dialogVisible" title="头像上传" width="30%">
    <!-- 上传本地头像文件 -->

    <!-- 对话框的取消与确定 -->
    <el-upload
      v-model:file-list="fileList"
      action=""
      list-type="picture-card"
      :limit="1"
      :before-upload="beforeUpload"
      :http-request="request"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :class="{ disabled: isShow }"
    >
      <el-icon><Plus /></el-icon>
    </el-upload>

    <el-dialog v-model="dialogVisiblef">
      <img w-full :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="uploadImg"> 确定上传 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/store/user";
import { Plus } from "@element-plus/icons-vue";
import type { UploadProps, UploadUserFile } from "element-plus";
import { changeUserImg } from "@/api/user";

const userStore = useUserStore();
const { username, name, gender, time, id } = storeToRefs(userStore);

//控制对话框的显示和隐藏
const dialogVisible = ref(false);
// 上传头像逻辑
const fileList = ref<UploadUserFile[]>([]);
const dialogImageUrl = ref<string>(""); //图片预览路径
const dialogVisiblef = ref<boolean>(false); //图片预览弹框
const isShow = ref<boolean>(false); //是否隐藏上传框
const imageFile = ref<File | undefined>();

//上传文件之前
const beforeUpload = (file: File) => {
  console.log(file);
  imageFile.value = file;
  isShow.value = true;
};

const request = (): void => {}; //让上传走自定义方法

const handleRemove: UploadProps["onRemove"] = (): void => {
  isShow.value = false;
};

const handlePictureCardPreview: UploadProps["onPreview"] = (
  uploadFile
): void => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisiblef.value = true;
};

//上传图片
const uploadImg = async () => {
  let formData = new FormData();
  formData.append("file", imageFile.value);
  await changeUserImg(id.value!, formData);
};
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

//在上传一张图片后，隐藏上传框
.disabled {
  ::v-deep .el-upload--picture-card {
    display: none !important;
  }
}
</style>
