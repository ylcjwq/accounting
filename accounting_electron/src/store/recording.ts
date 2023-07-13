import { defineStore } from "pinia"
import { ref } from "vue"

//记录支出收入弹窗状态
export const useRecordingStore = defineStore("recording", () => {
    const show = ref<boolean>(false)
    return { show }
})
