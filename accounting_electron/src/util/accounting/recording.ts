import { storeToRefs } from 'pinia'
import { useRecordingStore } from "@/store/recording";

const recordingStore = useRecordingStore()
let { show } = storeToRefs(recordingStore)
//支出收入图标的点击事件
export const recording = (name: string, type: string): void => {
    console.log(name, type);
    show.value = true
}