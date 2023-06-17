import axios from "axios"
import type { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios'
import { Names } from './requestCom/envName'//枚举
import { errorCodeType } from './requestCom/errorCode'//请求错误代码
// console.log(import.meta.env.VITE_HTTP)
const service = axios.create({
    //配置的跨域标识
    baseURL: '/api',
    //请求头
    headers: {},
    //超时
    timeout: 1000 * Names.TIME,
})
//请求拦截器
service.interceptors.request.use((config: AxiosRequestConfig) => {
    return config
}, (error: AxiosError) => {
    console.log(error, 'e')
})
//响应拦截器
service.interceptors.response.use((config: AxiosResponse) => {
    const code = config.data['code'] || 200;
    if (code == 200) {
        return Promise.resolve(config.data)
    } else {
        const msg = errorCodeType(code)
        ElMessage.error(msg)
        return Promise.reject(config.data)
    }
    // return config;
}, (error: AxiosError) => {
    console.log(error)
})
export default service
