<template>
  <div class="center-container new" :class="{ 'day-mode': isDayMode }">
    <!-- 页面头部 -->
    <div class="center-header">
      <div class="header-content">
        <div class="logo">
          <div class="logo-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
              <polyline points="2 17 12 22 22 17"></polyline>
              <polyline points="2 12 12 17 22 12"></polyline>
            </svg>
          </div>
          <div class="logo-text">
            <h2>系统管理平台</h2>
            <p>高效管理，智能办公</p>
          </div>
        </div>
        <div class="header-actions">
          <button class="mode-toggle" @click="toggleMode">
            <span v-if="isDayMode" class="mode-icon">🌙</span>
            <span v-else class="mode-icon">☀️</span>
            <span class="mode-text">{{ isDayMode ? '夜间模式' : '白天模式' }}</span>
          </button>
          <button class="glass-button" @click="switchToOld">切换到旧版本</button>
        </div>
      </div>
      <div class="header-title">
        <h1>综合系统中心</h1>
        <p>欢迎使用新一代系统管理平台</p>
      </div>
      <button class="danger-button font-orbitron" @click="clearStorage">清除登录状态</button>
    </div>
    
    <!-- 主内容区 -->
    <div class="center-content">
      <div class="system-category">
        <h2>通用办公类</h2>
        <p>企业日常运营管理系统</p>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToEnterpriseSystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="3" y1="9" x2="21" y2="9"></line>
            <line x1="9" y1="21" x2="9" y2="9"></line>
          </svg>
        </div>
        <h3>企业管理系统</h3>
        <p>六大办公子系统集成平台（包含OA功能）</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToExamSystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <h3>考试系统</h3>
        <p>在线考试管理平台</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToPersonalCenter">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <h3>个人中心</h3>
        <p>个人信息管理中心</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToHISSystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 8h-14"></path>
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          </svg>
        </div>
        <h3>HIS系统</h3>
        <p>医院信息管理系统</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToSystemSettings">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="3"></circle>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
          </svg>
        </div>
        <h3>系统设置</h3>
        <p>用户、权限与系统配置</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card glass glass-hover" @click="goToGameCenter">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polygon points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon>
          </svg>
        </div>
        <h3>游戏中心</h3>
        <p>休闲娱乐游戏集合</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
    </div>
    
    <!-- 页面底部 -->
    <div class="center-footer">
      <div class="footer-content">
        <div class="footer-info">
          <h4>系统管理平台</h4>
          <p>提供全方位的系统管理解决方案</p>
        </div>
        <div class="footer-links">
          <a href="#">关于我们</a>
          <a href="#">帮助中心</a>
          <a href="#">联系我们</a>
        </div>
      </div>
      <p class="copyright">© 2026 系统管理平台</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ref, onMounted, onUnmounted } from 'vue'

const router = useRouter()
const isDayMode = ref(false)
let modeCheckInterval: number | null = null

// 检查当前时间并设置模式
const checkModeByTime = () => {
  const now = new Date()
  const hour = now.getHours()
  // 7:00-19:00 为白天模式，其他时间为夜间模式
  isDayMode.value = hour >= 7 && hour < 19
  // 保存模式到本地存储
  localStorage.setItem('systemMode', isDayMode.value ? 'day' : 'night')
}

// 切换模式
const toggleMode = () => {
  isDayMode.value = !isDayMode.value
  // 保存模式到本地存储
  localStorage.setItem('systemMode', isDayMode.value ? 'day' : 'night')
}

// 页面加载时检查本地存储的模式设置
onMounted(() => {
  // 从本地存储读取模式设置
  const savedMode = localStorage.getItem('systemMode')
  if (savedMode) {
    isDayMode.value = savedMode === 'day'
  } else {
    // 首次加载时根据时间设置模式
    checkModeByTime()
  }
  
  // 设置定时检查，每小时检查一次
  modeCheckInterval = window.setInterval(checkModeByTime, 60 * 60 * 1000)
})

// 组件卸载时清除定时器
onUnmounted(() => {
  if (modeCheckInterval) {
    clearInterval(modeCheckInterval)
  }
})

const goToExamSystem = () => {
  router.push('/modules')
}

const goToOASystem = () => {
  router.push('/oa')
}

const goToPersonalCenter = () => {
  router.push('/personal')
}

const goToHISSystem = () => {
  router.push('/his')
}

const goToSystemSettings = () => {
  router.push('/system-settings')
}

const goToGameCenter = () => {
  router.push('/game-center')
}

const goToEnterpriseSystem = () => {
  router.push('/enterprise')
}

const switchToOld = () => {
  router.push('/old-center')
}

const clearStorage = () => {
  localStorage.removeItem('username')
  ElMessage.success('登录状态已清除')
}
</script>

<style scoped>
/* 全局样式 */
@import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;600;700&display=swap');
@import url('https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css');

/* 模式切换动画 */
.center-container.new {
  transition: all 0.5s ease-in-out;
}

/* 玻璃效果 */
.glass {
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 16px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  transition: all 0.5s ease-in-out;
}

.glass-hover {
  transition: all 0.3s ease;
}

.glass-hover:hover {
  border: 1px solid rgba(0, 247, 255, 0.3);
  box-shadow: 0 0 20px rgba(0, 247, 255, 0.2);
  transform: translateY(-4px);
}

/* 模式切换按钮 */
.mode-toggle {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  color: #00f7ff;
  padding: 8px 16px;
  border-radius: 20px;
  font-family: 'Orbitron', sans-serif;
  font-size: 0.85rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  margin-right: 16px;
  cursor: pointer;
}

.mode-toggle:hover {
  background: rgba(0, 247, 255, 0.15);
  border-color: rgba(0, 247, 255, 0.4);
  box-shadow: 0 0 15px rgba(0, 247, 255, 0.3);
  transform: translateY(-2px);
}

.mode-icon {
  font-size: 1rem;
}

/* 玻璃按钮 */
.glass-button {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  color: #00f7ff;
  padding: 10px 24px;
  border-radius: 24px;
  font-family: 'Orbitron', sans-serif;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  z-index: 10;
}

.glass-button:hover {
  background: rgba(0, 247, 255, 0.15);
  border-color: rgba(0, 247, 255, 0.4);
  box-shadow: 0 0 20px rgba(0, 247, 255, 0.3);
  transform: translateY(-2px);
}

.glass-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(0, 247, 255, 0.2), transparent);
  transition: left 0.5s ease;
  z-index: -1;
}

.glass-button:hover::before {
  left: 100%;
}

/* 危险按钮 */
.danger-button {
  background: rgba(255, 69, 69, 0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 69, 69, 0.3);
  color: #ff4545;
  padding: 10px 24px;
  border-radius: 24px;
  transition: all 0.3s ease;
  position: relative;
  z-index: 10;
}

.danger-button:hover {
  background: rgba(255, 69, 69, 0.2);
  box-shadow: 0 0 15px rgba(255, 69, 69, 0.4);
  transform: translateY(-2px);
}

/* 页面容器 - 夜景模式 */
.center-container.new {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 0;
  font-family: 'Orbitron', sans-serif;
  color: white;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

/* 页面容器 - 白天模式 */
.center-container.new.day-mode {
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f2 50%, #d1d9e6 100%);
  color: #333;
}

/* 页面头部 - 夜景模式 */
.center-header {
  background: rgba(255, 255, 255, 0.06);
  backdrop-filter: blur(16px);
  border-bottom: 1px solid rgba(0, 247, 255, 0.15);
  padding: 24px 48px;
  position: relative;
  z-index: 10;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  transition: all 0.5s ease-in-out;
}

/* 页面头部 - 白天模式 */
.center-container.new.day-mode .center-header {
  background: rgba(255, 255, 255, 0.9);
  border-bottom: 1px solid rgba(0, 188, 212, 0.2);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto 32px;
}

.header-actions {
  display: flex;
  align-items: center;
}

.logo {
  display: flex;
  align-items: center;
  gap: 20px;
}

.logo-icon {
  color: #00f7ff;
  font-size: 48px;
  text-shadow: 0 0 12px rgba(0, 247, 255, 0.6);
  transition: all 0.3s ease;
}

/* 白天模式下的图标颜色 */
.center-container.new.day-mode .logo-icon {
  color: #00bcd4;
  text-shadow: 0 0 12px rgba(0, 188, 212, 0.4);
}

.logo-icon:hover {
  transform: scale(1.05);
  text-shadow: 0 0 16px rgba(0, 247, 255, 0.8);
}

/* 白天模式下的图标悬停效果 */
.center-container.new.day-mode .logo-icon:hover {
  text-shadow: 0 0 16px rgba(0, 188, 212, 0.6);
}

.logo-text h2 {
  margin: 0;
  font-size: 1.6rem;
  font-weight: 600;
  color: #00f7ff;
  text-shadow: 0 0 8px rgba(0, 247, 255, 0.6);
  letter-spacing: 1px;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的标题颜色 */
.center-container.new.day-mode .logo-text h2 {
  color: #00bcd4;
  text-shadow: 0 0 8px rgba(0, 188, 212, 0.4);
}

.logo-text p {
  margin: 6px 0 0;
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.6);
  letter-spacing: 0.5px;
  line-height: 1.4;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的副标题颜色 */
.center-container.new.day-mode .logo-text p {
  color: rgba(51, 51, 51, 0.6);
}

.header-title {
  text-align: center;
  margin-bottom: 24px;
  position: relative;
}

.header-title::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 120px;
  height: 120px;
  background: radial-gradient(circle, rgba(0, 247, 255, 0.1) 0%, transparent 70%);
  border-radius: 50%;
  z-index: -1;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的标题背景 */
.center-container.new.day-mode .header-title::before {
  background: radial-gradient(circle, rgba(0, 188, 212, 0.1) 0%, transparent 70%);
}

.header-title h1 {
  font-size: 2.8rem;
  margin-bottom: 12px;
  color: #ffffff;
  font-weight: 700;
  text-shadow: 0 0 12px rgba(0, 247, 255, 0.4);
  letter-spacing: 2px;
  line-height: 1.2;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的主标题颜色 */
.center-container.new.day-mode .header-title h1 {
  color: #333;
  text-shadow: 0 0 12px rgba(0, 188, 212, 0.3);
}

.header-title p {
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.5);
  margin: 0;
  letter-spacing: 1px;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的主副标题颜色 */
.center-container.new.day-mode .header-title p {
  color: rgba(51, 51, 51, 0.5);
}

/* 主内容区 */
.center-content {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 32px;
  max-width: 1200px;
  width: 100%;
  margin: 48px auto;
  padding: 0 48px;
  position: relative;
  z-index: 1;
}

/* 系统分类标题 */
.system-category {
  grid-column: 1 / -1;
  text-align: center;
  margin-bottom: 16px;
  padding: 24px;
  background: rgba(0, 247, 255, 0.1);
  border-radius: 16px;
  backdrop-filter: blur(12px);
  border: 1px solid rgba(0, 247, 255, 0.2);
}

.system-category h2 {
  font-size: 1.8rem;
  margin: 0 0 8px 0;
  color: #00f7ff;
  text-shadow: 0 0 12px rgba(0, 247, 255, 0.6);
  letter-spacing: 2px;
}

.system-category p {
  margin: 0;
  color: rgba(255, 255, 255, 0.7);
  font-size: 1rem;
  letter-spacing: 1px;
}

/* 白天模式下的系统分类标题 */
.center-container.new.day-mode .system-category {
  background: rgba(0, 188, 212, 0.1);
  border: 1px solid rgba(0, 188, 212, 0.2);
}

.center-container.new.day-mode .system-category h2 {
  color: #00bcd4;
  text-shadow: 0 0 12px rgba(0, 188, 212, 0.4);
}

.center-container.new.day-mode .system-category p {
  color: rgba(51, 51, 51, 0.7);
}

/* 白天模式下的卡片样式 */
.center-container.new.day-mode .system-card {
  background: rgba(255, 255, 255, 0.8);
  border: 1px solid rgba(0, 188, 212, 0.2);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.05);
}

.system-card {
  border-radius: 16px;
  padding: 40px 32px;
  text-align: center;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.5s ease;
}

.card-icon {
  margin-bottom: 28px;
  color: #00f7ff;
  transition: all 0.3s ease;
  text-shadow: 0 0 12px rgba(0, 247, 255, 0.6);
  position: relative;
}

/* 白天模式下的卡片图标颜色 */
.center-container.new.day-mode .card-icon {
  color: #00bcd4;
  text-shadow: 0 0 12px rgba(0, 188, 212, 0.4);
}

.card-icon::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80px;
  height: 80px;
  background: radial-gradient(circle, rgba(0, 247, 255, 0.1) 0%, transparent 70%);
  border-radius: 50%;
  z-index: -1;
  transition: all 0.3s ease;
}

/* 白天模式下的图标背景 */
.center-container.new.day-mode .card-icon::after {
  background: radial-gradient(circle, rgba(0, 188, 212, 0.1) 0%, transparent 70%);
}

.system-card:hover .card-icon {
  transform: scale(1.08);
  color: #00f7ff;
  text-shadow: 0 0 16px rgba(0, 247, 255, 0.8);
}

/* 白天模式下的卡片悬停图标颜色 */
.center-container.new.day-mode .system-card:hover .card-icon {
  color: #00bcd4;
  text-shadow: 0 0 16px rgba(0, 188, 212, 0.6);
}

.system-card:hover .card-icon::after {
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, rgba(0, 247, 255, 0.15) 0%, transparent 70%);
}

/* 白天模式下的卡片悬停图标背景 */
.center-container.new.day-mode .system-card:hover .card-icon::after {
  background: radial-gradient(circle, rgba(0, 188, 212, 0.15) 0%, transparent 70%);
}

.system-card h3 {
  font-size: 1.4rem;
  margin-bottom: 14px;
  color: #ffffff;
  font-weight: 600;
  transition: color 0.3s ease;
  letter-spacing: 1px;
}

/* 白天模式下的卡片标题颜色 */
.center-container.new.day-mode .system-card h3 {
  color: #333;
}

.system-card:hover h3 {
  color: #00f7ff;
  text-shadow: 0 0 8px rgba(0, 247, 255, 0.6);
}

/* 白天模式下的卡片悬停标题颜色 */
.center-container.new.day-mode .system-card:hover h3 {
  color: #00bcd4;
  text-shadow: 0 0 8px rgba(0, 188, 212, 0.4);
}

.system-card p {
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 32px;
  font-size: 0.9rem;
  line-height: 1.5;
  letter-spacing: 0.5px;
}

/* 白天模式下的卡片描述颜色 */
.center-container.new.day-mode .system-card p {
  color: rgba(51, 51, 51, 0.5);
}

.card-hover {
  position: absolute;
  bottom: -50px;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, rgba(0, 247, 255, 0.1) 0%, rgba(0, 247, 255, 0.05) 100%);
  color: white;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.4s ease;
  border-top: 1px solid rgba(0, 247, 255, 0.2);
}

/* 白天模式下的卡片悬停效果 */
.center-container.new.day-mode .card-hover {
  background: linear-gradient(135deg, rgba(0, 188, 212, 0.1) 0%, rgba(0, 188, 212, 0.05) 100%);
  color: #333;
  border-top: 1px solid rgba(0, 188, 212, 0.2);
}

.system-card:hover .card-hover {
  bottom: 0;
}

.hover-text {
  font-weight: 500;
  font-size: 0.9rem;
  letter-spacing: 0.5px;
}

.hover-arrow {
  font-size: 1.2rem;
  font-weight: bold;
  transition: transform 0.3s ease;
}

.system-card:hover .hover-arrow {
  transform: translateX(4px);
}

/* 页面底部 - 夜景模式 */
.center-footer {
  background: rgba(25, 25, 45, 0.9);
  backdrop-filter: blur(16px);
  border-top: 1px solid rgba(0, 247, 255, 0.15);
  padding: 48px;
  margin-top: 64px;
  position: relative;
  z-index: 1;
  box-shadow: 0 -8px 32px rgba(0, 0, 0, 0.2);
  transition: all 0.5s ease-in-out;
}

/* 页面底部 - 白天模式 */
.center-container.new.day-mode .center-footer {
  background: rgba(255, 255, 255, 0.9);
  border-top: 1px solid rgba(0, 188, 212, 0.15);
  box-shadow: 0 -8px 32px rgba(0, 0, 0, 0.05);
}

.center-footer::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(to right, transparent, #00f7ff, transparent);
  opacity: 0.3;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的底部分割线 */
.center-container.new.day-mode .center-footer::before {
  background: linear-gradient(to right, transparent, #00bcd4, transparent);
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 40px;
}

.footer-info h4 {
  font-size: 1.2rem;
  margin-bottom: 12px;
  font-weight: 600;
  color: #00f7ff;
  text-shadow: 0 0 8px rgba(0, 247, 255, 0.6);
  letter-spacing: 1px;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的底部标题颜色 */
.center-container.new.day-mode .footer-info h4 {
  color: #00bcd4;
  text-shadow: 0 0 8px rgba(0, 188, 212, 0.4);
}

.footer-info p {
  margin: 0;
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.85rem;
  letter-spacing: 0.5px;
  line-height: 1.4;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的底部描述颜色 */
.center-container.new.day-mode .footer-info p {
  color: rgba(51, 51, 51, 0.5);
}

.footer-links {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.5);
  text-decoration: none;
  font-size: 0.85rem;
  transition: all 0.3s ease;
  text-shadow: 0 0 5px rgba(0, 247, 255, 0.2);
  letter-spacing: 0.5px;
  position: relative;
}

/* 白天模式下的底部链接颜色 */
.center-container.new.day-mode .footer-links a {
  color: rgba(51, 51, 51, 0.5);
  text-shadow: 0 0 5px rgba(0, 188, 212, 0.2);
}

.footer-links a:hover {
  color: #00f7ff;
  text-shadow: 0 0 10px rgba(0, 247, 255, 0.6);
  transform: translateY(-2px);
}

/* 白天模式下的底部链接悬停颜色 */
.center-container.new.day-mode .footer-links a:hover {
  color: #00bcd4;
  text-shadow: 0 0 10px rgba(0, 188, 212, 0.4);
}

.footer-links a::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0;
  height: 1px;
  background: #00f7ff;
  transition: width 0.3s ease;
}

/* 白天模式下的底部链接下划线颜色 */
.center-container.new.day-mode .footer-links a::after {
  background: #00bcd4;
}

.footer-links a:hover::after {
  width: 100%;
}

.copyright {
  text-align: center;
  margin: 0;
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.8rem;
  padding-top: 24px;
  border-top: 1px solid rgba(0, 247, 255, 0.1);
  text-shadow: 0 0 5px rgba(0, 247, 255, 0.2);
  letter-spacing: 0.5px;
  transition: all 0.5s ease-in-out;
}

/* 白天模式下的版权信息颜色 */
.center-container.new.day-mode .copyright {
  color: rgba(51, 51, 51, 0.4);
  border-top: 1px solid rgba(0, 188, 212, 0.1);
  text-shadow: 0 0 5px rgba(0, 188, 212, 0.2);
}

/* 白天模式下的按钮样式 */
.center-container.new.day-mode .glass-button {
  background: rgba(0, 188, 212, 0.1);
  border: 1px solid rgba(0, 188, 212, 0.2);
  color: #00bcd4;
}

.center-container.new.day-mode .glass-button:hover {
  background: rgba(0, 188, 212, 0.2);
  border-color: rgba(0, 188, 212, 0.4);
  box-shadow: 0 0 20px rgba(0, 188, 212, 0.3);
}

.center-container.new.day-mode .glass-button::before {
  background: linear-gradient(90deg, transparent, rgba(0, 188, 212, 0.2), transparent);
}

/* 白天模式下的模式切换按钮 */
.center-container.new.day-mode .mode-toggle {
  background: rgba(0, 188, 212, 0.1);
  border: 1px solid rgba(0, 188, 212, 0.2);
  color: #00bcd4;
}

.center-container.new.day-mode .mode-toggle:hover {
  background: rgba(0, 188, 212, 0.2);
  border-color: rgba(0, 188, 212, 0.4);
  box-shadow: 0 0 15px rgba(0, 188, 212, 0.3);
}

/* 白天模式下的危险按钮 */
.center-container.new.day-mode .danger-button {
  background: rgba(255, 69, 69, 0.1);
  border: 1px solid rgba(255, 69, 69, 0.3);
  color: #ff4545;
}

.center-container.new.day-mode .danger-button:hover {
  background: rgba(255, 69, 69, 0.2);
  box-shadow: 0 0 15px rgba(255, 69, 69, 0.4);
}
</style>