import { defineStore } from "pinia";

interface UserInfo {  //定义userInfo的接口
    id: number
    name: string | number
    img: string
}

export default defineStore("user", {
    state: () => {
        return {
            userInfo: {} as UserInfo,
        }
    }
})