import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  // 从本地存储初始化状态
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo')) || null)

  // 登录动作
  const setLoginInfo = (newToken, newUserInfo) => {
    token.value = newToken
    userInfo.value = newUserInfo
    localStorage.setItem('token', newToken)
    localStorage.setItem('userInfo', JSON.stringify(newUserInfo))
  }

  // 退出登录动作
  const logout = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return { token, userInfo, setLoginInfo, logout }
})