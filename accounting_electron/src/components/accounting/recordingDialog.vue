<template>
  <el-dialog v-model="dialogFormVisible" :title="dialogName + title">
    <el-form :model="form">
      <el-form-item :label="title + '方式'" :label-width="formLabelWidth">
        <el-select
          v-model="form.region"
          :placeholder="'请选择' + title + '方式'"
        >
          <el-option
            v-for="item in account"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="title + '金额'" :label-width="formLabelWidth">
        <el-input v-model.number="form.number" autocomplete="off" />
      </el-form-item>
      <el-form-item label="备注" :label-width="formLabelWidth">
        <el-input v-model="form.remark" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { computed, reactive, ref } from "vue";
import { storeToRefs } from "pinia";
import { useRecordingStore } from "@/store/recording";
import { useUserStore } from "@/store/user";
import { account } from "@/util/accounting/recording";
import { record } from "@/api/record";

interface Form {
  region: number | null;
  number: string;
  remark: string;
}

let dialogFormVisible = ref<boolean>(false); //弹窗的状态
const recordingStore = useRecordingStore();
const userStore = useUserStore();
const { dialogType, dialogName, show } = storeToRefs(recordingStore);
const { id } = storeToRefs(userStore);

const form = reactive<Form>({
  region: null,
  number: "",
  remark: "",
});

dialogFormVisible = show; //从仓库中同步弹窗状态

//计算当前是支付还是支出
const title = computed(() => {
  if (dialogType.value === "spend") {
    return "支出";
  }
  return "收入";
});

const formLabelWidth = "140px";

//点击确认时将支出/收入保存
const save = async (): Promise<void> => {
  console.log(dialogType);
  console.log(form);
  if (form.region == null) {
    ElMessage.info(`请选择${title.value}方式！`);
    return;
  }
  if (form.number == "") {
    ElMessage.info("请填写金额！");
    return;
  }
  let data = {
    form: { region: form.region, number: form.number, remark: form.remark },
    dialogType: dialogType.value,
    userId: id.value!,
  };
  console.log(data);

  await record(data);
  console.log(111);
  dialogFormVisible.value = false;
};
</script>

<style scoped lang="scss">
.el-button--text {
  margin-right: 15px;
}

.el-select {
  width: 300px;
}

.el-input {
  width: 300px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>
