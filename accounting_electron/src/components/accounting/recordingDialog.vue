<template>
  <el-dialog v-model="dialogFormVisible" :title="dialogName+title">
    <el-form :model="form">
      <el-form-item :label="title+'方式'" :label-width="formLabelWidth">
        <el-select v-model="form.region" :placeholder="'请选择'+title+'方式'">
          <el-option
            v-for="item in account"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="title+'金额'" :label-width="formLabelWidth">
        <el-input v-model="form.number" autocomplete="off" />
      </el-form-item>
      <el-form-item label="备注" :label-width="formLabelWidth">
        <el-input v-model="form.remark" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
  
<script lang="ts" setup>
import { computed, reactive, ref } from "vue";
import { storeToRefs } from "pinia";
import { useRecordingStore } from "@/store/recording";
import { account } from "@/util/accounting/recording";

let dialogFormVisible = ref<boolean>(false); //弹窗的状态
const recordingStore = useRecordingStore();
const { dialogType, dialogName, show } = storeToRefs(recordingStore);
console.log(dialogType);

dialogFormVisible = show;
//计算弹窗名称
const title = computed(() => {
  if (dialogType.value === "spend") {
    return "支出";
  }
  return "收入";
});

const formLabelWidth = "140px";

const form = reactive({
  number: "",
  remark:"",
  region: "",
  type: [],
});

const save = ():void=>{    //点击确认时将支出/收入保存
  console.log(dialogType);
  console.log(form);
  dialogFormVisible.value = false
}
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
