import request from "@/util/request"

//记录接口
export const record = (data: any) => {
    return request({
        url: '/record/record',
        method: 'post',
        data
    })
};