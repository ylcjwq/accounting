import { defineStore } from "pinia";


export default defineStore("userInfo", {
    state: () => {
        return {
            id: null as number | null,
            name: null as string | number | null,
            img: null as string | null,
        }
    }
})