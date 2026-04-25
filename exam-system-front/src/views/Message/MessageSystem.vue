<template>
  <div class="message-system-container">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="system-header">
      <h1>企业沟通系统</h1>
      <p>内部协作与信息沟通平台</p>
    </div>

    <div class="module-grid">
      <div class="module-card" @click="activeModule = 'message'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>内部消息</h3>
        <p>即时通讯、文件传输</p>
      </div>

      <div class="module-card" @click="activeModule = 'announcement'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 17H2a3 3 0 0 0 3-3V9a7 7 0 0 1 14 0v5a3 3 0 0 0 3 3zm-8.27 4a2 2 0 0 1-3.46 0"></path>
          </svg>
        </div>
        <h3>公告通知</h3>
        <p>公司公告、新闻推送</p>
      </div>

      <div class="module-card" @click="activeModule = 'group'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>讨论组</h3>
        <p>团队协作、专题讨论</p>
      </div>

      <div class="module-card" @click="activeModule = 'chat'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"></path>
          </svg>
        </div>
        <h3>群聊</h3>
        <p>实时聊天、历史记录</p>
      </div>
    </div>

    <div class="module-content" v-if="activeModule">
      <h2>{{ getModuleTitle() }}</h2>
      <p>API接口: /{{ activeModule === 'chat' ? 'group-message' : activeModule }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const activeModule = ref('')

const goBack = () => {
  router.push('/enterprise')
}

const getModuleTitle = () => {
  const titles: Record<string, string> = {
    message: '内部消息管理',
    announcement: '公告通知管理',
    group: '讨论组管理',
    chat: '群聊消息管理'
  }
  return titles[activeModule.value] || ''
}
</script>

<style scoped>
.message-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #38bdf8 0%, #0ea5e9 100%);
  padding: 40px 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 2px solid white;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
  z-index: 10;
}

.back-btn:hover {
  background: white;
  color: #0ea5e9;
}

.system-header {
  text-align: center;
  margin-bottom: 40px;
}

.system-header h1 {
  font-size: 2.5rem;
  color: #fff;
  margin-bottom: 10px;
}

.system-header p {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 20px;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 25px;
  max-width: 1200px;
  margin: 0 auto;
}

.module-card {
  background: white;
  border-radius: 15px;
  padding: 30px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.module-icon {
  color: #0ea5e9;
  margin-bottom: 20px;
}

.module-card h3 {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 10px;
}

.module-card p {
  color: #666;
  font-size: 0.95rem;
}

.module-content {
  max-width: 1200px;
  margin: 40px auto 0;
  background: white;
  border-radius: 15px;
  padding: 30px;
  text-align: center;
}

.module-content h2 {
  color: #333;
  margin-bottom: 15px;
}

.module-content p {
  color: #666;
}
</style>
