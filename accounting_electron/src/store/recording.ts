import { defineStore } from "pinia"
import { ref } from "vue"

//记录支出收入弹窗状态
export const useRecordingStore = defineStore("recording", () => {
    const dialogName = ref<string>("")
    const dialogType = ref<string>("")
    const show = ref<boolean>(false)
    return { dialogName, dialogType, show }
})
