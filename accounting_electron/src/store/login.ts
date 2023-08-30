import { defineStore } from "pinia";
// import { useRouter } from "vue-router";
// const router = useRouter();       //拿不到router

export const useLoginStore = defineStore("login", {
  actions: {
    async loginOut() {
      // 处理登出逻辑，例如清除token等
      // router.replace("/login");
      window.localStorage.removeItem("token");
      location.href = "/login";
    },
  },
});
