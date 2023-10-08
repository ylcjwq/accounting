import { storeToRefs } from "pinia";
import { useRecordingStore } from "@/store/recording";

const recordingStore = useRecordingStore();
let { dialogType, dialogName, show } = storeToRefs(recordingStore);
//支出收入图标的点击事件
export const recording = (name: string, type: string): void => {
  console.log(name, type);
  show.value = true;
  dialogType.value = type;
  dialogName.value = name;
};

interface Payment {
  //定义支付/收入方式的接口
  label: string;
  value: number;
}
export const account: Payment[] = [
  { label: "微信钱包", value: 1 },
  { label: "微信零钱通", value: 2 },
  { label: "支付宝余额", value: 3 },
  { label: "支付宝余额宝", value: 4 },
  { label: "银行卡", value: 5 },
  { label: "基金", value: 6 },
  { label: "其他", value: 7 },
  { label: "其他", value: 8 },
  { label: "其他", value: 9 },
];
