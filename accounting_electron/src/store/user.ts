import { defineStore } from "pinia";
import { queryType } from "@/api/user";

//存储用户信息
export const useUserStore = defineStore("userInfo", {
  state: () => {
    return {
      id: null as number | null,
      username: null as string | null,
      name: null as string | null,
      sex: null as string | null,
      userimg: null as string | null,
      time: null as string | null,
    };
  },
  persist: true,
});

export const useQueryTypeStore = defineStore("queryType", {
  state: () => {
    return {
      type: [] as { label: string; value: string }[],
    };
  },
  actions: {
    async getQueryType() {
      const res = await queryType();
      console.log(res);
      res.data.forEach((item: any) => {
        this.type.push({ label: item.name, value: item.value });
      });
    },
  },
});
