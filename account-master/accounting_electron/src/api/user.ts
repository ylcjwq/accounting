import request from "@/util/request";

//查询用户基本信息接口
export const getUserMessage = (userId: number) => {
  return request({
    url: `/getInfo`,
    method: "get",
  });
};

//修改用户基本信息接口
export const userMessage = (data: {
  userId: number;
  nickName: string;
  sex: string;
}) => {
  return request({
    url: "/system/user/profile",
    method: "put",
    data,
  });
};

//修改用户密码接口
export const changePassword = (data: {
  oldPassword: string;
  newPassword: string;
}) => {
  return request({
    url: "/system/user/profile/updatePwd",
    method: "put",
    params: data
  });
};

//上传头像接口
export const changeUserImg = (
  formData: FormData
) => {
  return request({
    url: `/system/user/profile/avatar`,
    method: "post",
    data: formData,
  });
};
