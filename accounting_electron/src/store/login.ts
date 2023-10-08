import { defineStore } from "pinia";

export const useLoginStore = defineStore("login", {
  actions: {
    async loginOut() {
      // 处理登出逻辑，例如清除token等
      window.localStorage.removeItem("token");
      location.href = "/login";
    },
  },
});
