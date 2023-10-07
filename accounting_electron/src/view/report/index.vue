<template>
  <div style="padding: 20px">
    <div v-for="item in information">
      <div style="color: rgb(235, 89, 44); font-size: 20px">
        {{ item.number }}
      </div>
      <div v-for="itm in item.month" style="margin-top: 10px">
        <span style="color: rgb(235, 89, 44); font-size: 18px"
          >{{ itm.number }}月</span
        >
        <el-collapse
          @change="handleChange"
          v-for="it in itm.day"
          style="margin-top: 10px"
        >
          <el-collapse-item
            :title="itm.number + '月' + it.number + '日'"
            name="1"
          >
            <div v-for="i in it.pay" class="pay_item">
              <el-divider />
              <div style="display: flex; justify-content: space-between">
                <span style="font-size: 18px; color: rgb(225, 161, 138)">{{
                  i.projectName
                }}</span>
                <span
                  :style="{
                    color: i.dialogType === 'spend' ? '#06cdba' : '#ff5f2f',
                    fontSize: '24px',
                  }"
                >
                  {{ i.dialogType == "spend" ? "-" : "+" }}{{ i.number }}
                </span>
              </div>
              <div>
                <span>{{ i.remark }}</span>
              </div>
              <div
                style="
                  display: flex;
                  justify-content: end;
                  font-size: 14px;
                  color: #afafaf;
                "
              >
                <span>{{ i.regionDesc }}</span>
                <span style="margin-left: 12px">{{ i.time }}</span>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getInfoRecording } from "@/api/record";
import { onMounted, ref } from "vue";

let information = ref<any[]>([]);
const handleChange = (val: string[]) => {
  console.log(val);
};

const getInfo = async (): Promise<void> => {
  const data = {
    // dialogType: "revenue",
    // region: 1,
    // year: "2023",
    // month: "9",
  };
  const res = await getInfoRecording(data);
  information.value = res.data.year;
  console.log(information);
  console.log(res);
};

// 在组件挂载之前调用getInfo()函数
onMounted(() => {
  getInfo();
});
</script>

<style lang="scss" scoped>
.pay_item {
  margin-top: 4px;
}
</style>
