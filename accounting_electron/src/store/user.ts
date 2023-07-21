import { defineStore } from "pinia";

//存储用户信息
export const useUserStore = defineStore("userInfo", {
    state: () => {
        return {
            id: null as number | null,
            username:null as string | null,
            name: null as string | null,
            gender:null as string|null,
            img: null as string | null,
            time: null as string | null
        }
    },
    persist: true,
})