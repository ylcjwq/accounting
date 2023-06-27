import request from "@/util/request"
export const login = (data: any) => {
    return request({
        url: '/login/login',
        method: 'post',
        data
    })
};
export const enroll = (data: any) => {
    return request({
        url: '/login/enroll',
        method: 'post',
        data
    })
}