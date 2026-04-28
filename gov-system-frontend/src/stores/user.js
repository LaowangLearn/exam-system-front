import { defineStore } from 'pinia'
import axios from '@/utils/axios'

export const useUserStore = defineStore('user', {
  state: () => ({
    userId: null,
    username: '',
    realName: '',
    token: localStorage.getItem('gov_token') || '',
    orgId: null,
    orgName: '',
    roles: []
  }),

  actions: {
    async login(credentials) {
      const res = await axios.post('/auth/login', credentials)
      const data = res.data?.data || {}
      this.token = data.token || 'mock_token_' + Date.now()
      this.userId = data.userId || 1
      this.username = credentials.username
      this.realName = data.realName || '系统管理员'
      this.orgId = data.orgId || 1
      this.orgName = data.orgName || '测试党政机关'
      localStorage.setItem('gov_token', this.token)
      return data
    },

    async logout() {
      this.token = ''
      this.userId = null
      this.username = ''
      this.realName = ''
      localStorage.removeItem('gov_token')
    },

    async fetchUserInfo() {
      if (!this.token) return
      try {
        const res = await axios.get('/auth/userinfo')
        const data = res.data?.data || {}
        this.userId = data.userId || this.userId
        this.username = data.username || this.username
        this.realName = data.realName || this.realName
        this.roles = data.roles || []
      } catch {}
    }
  }
})
