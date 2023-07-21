import request from "@/util/request"

//登录接口
export const login = (data: {
    username:string,
    password:string
}) => {
    return request({
        url: '/login/login',
        method: 'post',
        data
    })
};
//注册接口
export const enroll = (data: {
    username:string,
    password:string
}) => {
    return request({
        url: '/login/enroll',
        method: 'post',
        data
    })
}