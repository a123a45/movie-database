import { defineStore } from 'pinia'
import { ref } from 'vue'
import * as authApi from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  async function login(payload) {
    const data = await authApi.login(payload)
    token.value = data.token
    userInfo.value = data.userInfo
    localStorage.setItem('token', data.token)
    localStorage.setItem('userInfo', JSON.stringify(data.userInfo))
    return data
  }

  async function logout() {
    try {
      await authApi.logout()
    } finally {
      token.value = ''
      userInfo.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }

  async function fetchUserInfo() {
    if (!token.value) return
    try {
      const data = await authApi.getUserInfo()
      userInfo.value = data
      localStorage.setItem('userInfo', JSON.stringify(data))
    } catch {
      // token expired or invalid
      token.value = ''
      userInfo.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }

  const isAdmin = () => userInfo.value?.role === 'ADMIN'

  return { token, userInfo, login, logout, fetchUserInfo, isAdmin }
})
