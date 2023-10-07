import request from "@/util/request";

//定义记录接口的data类型
interface Record {
  region: number;
  number: string;
  remark: string;
  dialogType: string;
  userId: number;
  projectName: string;
}
//记录接口
export const record = (data: Record) => {
  return request({
    url: "/record/add",
    method: "post",
    data,
  });
};

//查询是否设置预算接口
export const inquiryBudget = (userId: number) => {
  return request({
    url: `/record/getBudget/${userId}`,
    method: "get",
  });
};

//设置预算接口
export const setBudget = (data: { userId: number; budget: string }) => {
  return request({
    url: "/record/setBudget",
    method: "put",
    data,
  });
};

//开启关闭预算接口
export const openCloseBudget = (data: { status: boolean }, userId: number) => {
  return request({
    url: `/record/setBudgetStatus/${userId}`,
    method: "put",
    params: data,
  });
};

//查询收入支出详情
export const getInfoRecording = (data: {
  dialogType?: string;
  region?: number;
  year?: string;
  month?: string;
  day?: string;
}) => {
  return request({
    url: `/record/queryInfoByDate`,
    method: "get",
    params: data,
  });
};

//图片记账接口
export const aiPhotoKeeping = (formData: FormData) => {
  return request({
    url: `/baiduAIParse/upload`,
    method: "post",
    data: formData,
  });
};
