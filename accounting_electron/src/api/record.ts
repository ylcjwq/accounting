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

//查询是否设置预算接口
export const inquiryBudget = (data: { userId: number }) => {
  return request({
    url: "/record/inquiryBudget",
    method: "get",
    params: data,
  });
};

//设置预算接口
export const setBudget = (data: { userId: number; budget: string }) => {
  return request({
    url: "/record/setBudget",
    method: "post",
    data,
  });
};

//开启关闭预算接口
export const openCloseBudget = (data: { userId: number; enabled: boolean }) => {
  return request({
    url: "/record/openCloseBudget",
    method: "post",
    data,
  });
};
