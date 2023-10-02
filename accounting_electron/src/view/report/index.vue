<template>
  <div style="padding: 20px">
    <div v-for="item in information">
      <div>{{ item }}</div>
      <div>9月</div>
      <el-collapse @change="handleChange">
        <el-collapse-item title="Consistency" name="1">
          <div>
            Consistent with real life: in line with the process and logic of
            real life, and comply with languages and habits that the users are
            used to;
          </div>
          <div>
            Consistent within interface: all elements should be consistent, such
            as: design style, icons and texts, position of elements, etc.
          </div>
        </el-collapse-item>
      </el-collapse>
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
    year: "2023",
    // month: "9",
  };
  const res = await getInfoRecording(data);
  information.value = res.data;
  console.log(information);
  console.log(res);
};

// 在组件挂载之前调用getInfo()函数
onMounted(() => {
  getInfo();
});
</script>
