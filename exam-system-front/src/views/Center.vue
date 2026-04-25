<template>
  <div class="center-container">
    <div class="grid-bg"></div>
    <div class="floating-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
    </div>

    <div class="center-header">
      <div class="header-badge">SYS MANAGER</div>
      <h1 class="glow-text">综合系统中心</h1>
      <p class="header-subtitle">欢迎使用系统管理平台</p>
      <div class="header-buttons">
        <button class="neon-button" @click="switchToNew">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="1 4 1 10 7 10"></polyline>
            <path d="M3.51 15a9 9 0 1 0 2.13-9.36L1 10"></path>
          </svg>
          切换新版本
        </button>
        <button class="danger-button" @click="clearStorage">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
            <polyline points="16 17 21 12 16 7"></polyline>
            <line x1="21" y1="12" x2="9" y2="12"></line>
          </svg>
          清除登录
        </button>
      </div>
    </div>

    <div class="center-content">
      <div class="system-card glass card-shine" v-for="(card, index) in systemCards" :key="index" :style="{ animationDelay: `${index * 0.1}s` }" @click="navigateTo(card.path)">
        <div class="card-glow-effect"></div>
        <div class="card-icon-wrapper">
          <div class="card-icon" v-html="card.icon"></div>
        </div>
        <h3>{{ card.title }}</h3>
        <p>{{ card.description }}</p>
        <div class="card-footer">
          <span>立即访问</span>
          <span class="arrow">→</span>
        </div>
      </div>
    </div>

    <div class="center-footer">
      <div class="footer-line"></div>
      <p>© 2026 系统管理平台 · 高效管理，智能办公</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()

const systemCards = [
  {
    title: '考试系统',
    description: '在线考试管理平台',
    path: '/modules',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>'
  },
  {
    title: 'OA系统',
    description: '办公自动化管理平台',
    path: '/oa',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path></svg>'
  },
  {
    title: '个人中心',
    description: '个人信息管理中心',
    path: '/personal',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>'
  },
  {
    title: 'HIS系统',
    description: '医院信息管理系统',
    path: '/his',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 8h-14"></path><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path></svg>'
  },
  {
    title: '系统设置',
    description: '用户、权限与系统配置',
    path: '/system-settings',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>'
  },
  {
    title: '游戏中心',
    description: '休闲娱乐游戏集合',
    path: '/game-center',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon></svg>'
  },
  {
    title: '其他平台',
    description: '更多系统平台',
    path: '/other-platforms',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2L2 7l10 5 10-5-10-5z"></path><path d="M2 17l10 5 10-5"></path><path d="M2 12l10 5 10-5"></path></svg>'
  }
]

const navigateTo = (path: string) => {
  router.push(path)
}

const clearStorage = () => {
  localStorage.removeItem('username')
  ElMessage.success('登录状态已清除')
}

const switchToNew = () => {
  router.push('/')
}
</script>

<style scoped>
.center-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  position: relative;
  overflow: hidden;
}

.center-header {
  text-align: center;
  margin-bottom: 50px;
  position: relative;
  z-index: 10;
}

.header-badge {
  display: inline-block;
  padding: 6px 16px;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 2px;
  color: #00f0ff;
  margin-bottom: 20px;
}

.center-header h1 {
  font-size: 3rem;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 12px;
  letter-spacing: 4px;
}

.header-subtitle {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 30px;
}

.header-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

.header-buttons button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  border-radius: 12px;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.center-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  max-width: 1100px;
  width: 100%;
  position: relative;
  z-index: 10;
}

.system-card {
  position: relative;
  padding: 32px 28px;
  border-radius: 20px;
  text-align: center;
  cursor: pointer;
  overflow: hidden;
  animation: fadeInUp 0.6s ease forwards;
  opacity: 0;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.card-glow-effect {
  position: absolute;
  top: -80px;
  left: -80px;
  width: 160px;
  height: 160px;
  background: radial-gradient(circle, rgba(0, 240, 255, 0.12) 0%, transparent 70%);
  pointer-events: none;
  transition: all 0.5s ease;
}

.system-card:hover .card-glow-effect {
  transform: scale(1.5);
  opacity: 1.5;
}

.card-icon-wrapper {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 72px;
  height: 72px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.15) 0%, rgba(102, 126, 234, 0.15) 100%);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 18px;
  margin-bottom: 20px;
  transition: all 0.4s ease;
}

.system-card:hover .card-icon-wrapper {
  transform: scale(1.1);
  border-color: rgba(0, 240, 255, 0.4);
  box-shadow: 0 0 30px rgba(0, 240, 255, 0.2);
}

.card-icon {
  color: #00f0ff;
  transition: all 0.3s ease;
}

.system-card:hover .card-icon {
  transform: scale(1.1);
}

.system-card h3 {
  font-size: 1.3rem;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 8px;
  transition: all 0.3s ease;
}

.system-card:hover h3 {
  color: #00f0ff;
}

.system-card p {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 24px;
}

.card-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding-top: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
  font-size: 0.85rem;
  color: rgba(0, 240, 255, 0.7);
  opacity: 0;
  transform: translateY(10px);
  transition: all 0.4s ease;
}

.system-card:hover .card-footer {
  opacity: 1;
  transform: translateY(0);
}

.arrow {
  transition: transform 0.3s ease;
}

.system-card:hover .arrow {
  transform: translateX(6px);
}

.center-footer {
  margin-top: 60px;
  text-align: center;
  position: relative;
  z-index: 10;
}

.footer-line {
  width: 80px;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(0, 240, 255, 0.3), transparent);
  margin: 0 auto 20px;
}

.center-footer p {
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.3);
}

.floating-shapes {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  pointer-events: none;
}

.shape {
  position: absolute;
  border-radius: 50%;
  opacity: 0.06;
}

.shape-1 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #00f0ff, #667eea);
  top: -150px;
  right: -100px;
  animation: float 15s ease-in-out infinite;
}

.shape-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #764ba2, #667eea);
  bottom: -100px;
  left: -80px;
  animation: float 12s ease-in-out infinite reverse;
}

@media (max-width: 768px) {
  .center-header h1 {
    font-size: 2rem;
    letter-spacing: 2px;
  }

  .center-content {
    grid-template-columns: 1fr;
    max-width: 400px;
  }
}
</style>