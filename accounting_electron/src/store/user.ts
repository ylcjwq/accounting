import { defineStore } from "pinia";

//存储用户信息
export default defineStore("userInfo", {
    state: () => {
        return {
            id: null as number | null,
            name: null as string | number | null,
            img: null as string | null,
        }
    }
})