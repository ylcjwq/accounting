import request from "@/util/request";

//定义记录接口的data类型
interface Record {
  form: {
    region: number;
    number: string;
    remark: string;
  };
  dialogType: string;
  userId: number;
}
//记录接口
export const record = (data: Record) => {
  return request({
    url: "/record/record",
    method: "post",
    data,
  });
};
