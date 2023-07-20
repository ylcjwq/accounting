import request from "@/util/request"

//修改用户基本信息接口
export const userMessage = (data: any) => {
    return request({
        url: '/user/userMessage',
        method: 'post',
        data
    })
};