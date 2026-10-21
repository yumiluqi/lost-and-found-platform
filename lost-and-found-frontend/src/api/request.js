import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 导出图片基础路径，以后换 IP 只改这里
export const IMG_BASE_URL = 'http://172.23.48.86:8080'

// 创建 axios 实例
const request = axios.create({
  baseURL: '/api', // 后端服务地址
  timeout: 10000
})

// 请求拦截器：每次发请求自动带上 token
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器：统一处理错误码
request.interceptors.response.use(
  response => {
    const res = response.data
    // 成功
    if (res.code === 200) {
      return res
    }
    // 未登录或 token 过期
    if (res.code === 401) {
      ElMessage.error('登录已过期，请重新登录')
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      router.push('/login')
      return Promise.reject(new Error(res.msg || '未登录'))
    }
    
    // 其他错误情况（400, 403, 404, 500等）
    ElMessage.error(res.msg || '请求失败')
    return Promise.reject(new Error(res.msg || 'Error'))
  },
  error => {
    ElMessage.error(error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default request