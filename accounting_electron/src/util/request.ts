import axios from "axios";
import type {
  InternalAxiosRequestConfig,
  AxiosResponse,
  AxiosError,
} from "axios";
const service = axios.create({
  //配置的跨域标识
  baseURL: "/api",
  //请求头
  headers: {},
  //超时
  timeout: 10000,
});
//请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem("token"); //携带token
    config.headers["Authorization"] = `Bearer ${token}`;
    return config;
  },
  (error: AxiosError) => {
    console.log(error, "e");
  }
);
//响应拦截器
service.interceptors.response.use(
  (config: AxiosResponse) => {
    const code = config.data["code"] || 200;
    const msg = config.data["msg"] || "未知错误";
    if (code == 200) {
      return Promise.resolve(config.data);
    } else {
      ElMessage.error(msg);
      return Promise.resolve(config.data);
    }
  },
  (error: AxiosError) => {
    console.log(error);
    return Promise.reject(error);
  }
);
export default service;
