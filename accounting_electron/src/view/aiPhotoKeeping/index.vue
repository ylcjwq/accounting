<template>
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
    <el-icon>
      <Plus />
    </el-icon>
  </el-upload>

  <!-- 预览头像 -->
  <el-dialog v-model="dialogVisiblef">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>

  <el-button type="primary" @click="uploadImg">确认上传</el-button>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import { ElLoading, type UploadProps, type UploadUserFile } from "element-plus";
import { aiPhotoKeeping } from "@/api/record";

const fileList = ref<UploadUserFile[]>([]); //上传的图片
const dialogImageUrl = ref<string>(""); //图片预览路径
const dialogVisiblef = ref<boolean>(false); //图片预览弹框
const isShow = ref<boolean>(false); //是否隐藏上传框
const imageFile = ref<File | undefined>(); //文件信息

const request = (): void => {}; //让上传走自定义方法

//上传文件之前
const beforeUpload = (file: File) => {
  console.log(file);
  imageFile.value = file;
  isShow.value = true; //上传图片后隐藏上传框
};

//上传图片
const uploadImg = async () => {
  let formData = new FormData(); //new一个FormData对象用来传输文件
  formData.append("file", imageFile.value!); //将图片添加到FormData对象中，对应后端解析的字段avatar
  const loadingInstance = ElLoading.service({ fullscreen: true }); //开启loading动画
  const res = await aiPhotoKeeping(formData);
  // console.log(res);
  fileList.value = [];
  isShow.value = false;
  loadingInstance.close(); //关闭loading动画
};

//预览图片
const handlePictureCardPreview: UploadProps["onPreview"] = (
  uploadFile
): void => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisiblef.value = true;
};

const handleRemove: UploadProps["onRemove"] = (): void => {
  isShow.value = false; //移除图片后重新显示上传框
};
</script>
