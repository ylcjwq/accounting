import request from "@/util/request";

interface UserResponse {
  user: {
    nickName: string;
    avatar: string;
    sex: string;
  };
}
//查询用户基本信息接口
export const getUserMessage = (userId: number): Promise<UserResponse> => {
  return request({
    url: `/getInfo`,
    method: "get",
  });
};

//修改用户基本信息接口
export const userMessage = (data: {
  id: number;
  name: string;
  sex: string;
}) => {
  return request({
    url: "/user/userMessage",
    method: "post",
    data,
  });
};

//修改用户密码接口
export const changePassword = (data: {
  id: number;
  oldPassword: string;
  newPassword: string;
}) => {
  return request({
    url: "/user/changePassword",
    method: "post",
    data,
  });
};

//上传头像接口
export const changeUserImg = (
  id: number,
  userimg: string | null,
  formData: FormData
) => {
  return request({
    url: `/user/avatar/${id}?userimg=${userimg}`,
    method: "post",
    data: formData,
  });
};
