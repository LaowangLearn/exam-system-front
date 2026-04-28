<template>
  <div class="center-container" :class="{ 'night-mode': !isDayMode }">
    <!-- 装饰性圆形 -->
    <div class="decoration-circle circle-1"></div>
    <div class="decoration-circle circle-2"></div>
    <div class="decoration-circle circle-3"></div>
    
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
            <span class="mode-icon">{{ isDayMode ? '🌙' : '☀️' }}</span>
            <span class="mode-text">{{ isDayMode ? '夜间模式' : '白天模式' }}</span>
          </button>
          <button class="secondary-button" @click="switchToOld">切换到旧版本</button>
        </div>
      </div>
      
      <!-- Hero区域 -->
      <div class="hero-section">
        <div class="badge">
          <span class="badge-icon">✨</span>
          <span>Welcome to 澄心若素</span>
        </div>
        <h1 class="hero-title">
          <span class="gradient-text main-title">澄心若素</span>
          <span class="subtitle">· 纯净心灵，简约生活</span>
        </h1>
        <p class="hero-description">
          澄心若素，希望每一个人都能保持内心的纯净与宁静，在快节奏的生活中找到属于自己的平衡与美好。
          我们致力于为您提供简单、高效、优雅的系统管理体验，让每一次使用都成为一种享受。
        </p>
        <div class="hero-buttons">
          <button class="primary-button" @click="goToEnterpriseSystem">
            <span class="btn-icon">✨</span>
            开始体验
          </button>
          <button class="outline-button" @click="goToExamSystem">
            <span class="btn-icon">🎯</span>
            了解更多
          </button>
        </div>
      </div>
      
      <!-- 统计数据区域 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-value gradient-text">22°C</div>
          <div class="stat-label">北京市天气</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">7</div>
          <div class="stat-label">系统数量</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">{{ currentDate }}</div>
          <div class="stat-label">今天日期时间</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">99.9%</div>
          <div class="stat-label">系统可用率</div>
        </div>
      </div>
    </div>
    
    <!-- 主内容区 -->
    <div class="center-content">
      <div class="system-category">
        <h2>通用办公类</h2>
        <p>企业日常运营管理系统</p>
      </div>
      
      <div class="system-card" @click="goToEnterpriseSystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="3" y1="9" x2="21" y2="9"></line>
            <line x1="9" y1="21" x2="9" y2="9"></line>
          </svg>
        </div>
        <h3>通用办公类</h3>
        <p>六大办公子系统集成平台（包含OA功能）</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card" @click="goToMarketingSystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>营销客户类</h3>
        <p>全渠道客户营销与管理平台</p>
        <div class="card-hover">
          <span class="hover-text">立即访问</span>
          <span class="hover-arrow">→</span>
        </div>
      </div>
      
      <div class="system-card" @click="goToExamSystem">
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
      
      <div class="system-card" @click="goToPersonalCenter">
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
      
      <div class="system-card" @click="goToHISSystem">
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
      
      <div class="system-card" @click="goToSystemSettings">
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
      
      <div class="system-card" @click="goToGameCenter">
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
      
      <div class="system-card" @click="goToAPISystem">
        <div class="card-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M18 20V10"></path>
            <path d="M12 20V4"></path>
            <path d="M6 20v-6"></path>
          </svg>
        </div>
        <h3>公共 API</h3>
        <p>系统对外接口服务</p>
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
const currentDate = ref('')
let modeCheckInterval: number | null = null
let dateUpdateInterval: number | null = null

// 检查当前时间并设置模式
const checkModeByTime = () => {
  const now = new Date()
  const hour = now.getHours()
  // 7:00-19:00 为白天模式，其他时间为夜间模式
  isDayMode.value = hour >= 7 && hour < 19
  // 保存模式到本地存储
  localStorage.setItem('systemMode', isDayMode.value ? 'day' : 'night')
}

// 更新当前日期时间
const updateCurrentDate = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  currentDate.value = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
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
  
  // 初始更新日期时间
  updateCurrentDate()
  
  // 设置定时检查，每小时检查一次
  modeCheckInterval = window.setInterval(checkModeByTime, 60 * 60 * 1000)
  
  // 设置定时更新日期时间，每秒更新一次
  dateUpdateInterval = window.setInterval(updateCurrentDate, 1000)
})

// 组件卸载时清除定时器
onUnmounted(() => {
  if (modeCheckInterval) {
    clearInterval(modeCheckInterval)
  }
  if (dateUpdateInterval) {
    clearInterval(dateUpdateInterval)
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

const goToAPISystem = () => {
  router.push('/api')
}

const goToMarketingSystem = () => {
  router.push('/marketing')
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
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

/* 页面容器 */
.center-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #f5f0fa 0%, #f8f5fc 50%, #faf7fe 100%);
  padding: 0;
  font-family: 'Inter', sans-serif;
  color: #2d3748;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

/* 装饰性圆形 */
.decoration-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.3;
  z-index: 0;
  pointer-events: none;
}

.circle-1 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  top: 10%;
  left: -100px;
  filter: blur(60px);
}

.circle-2 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #ed8936 0%, #ecc94b 100%);
  bottom: 10%;
  right: -150px;
  filter: blur(80px);
}

.circle-3 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #4299e1 0%, #667eea 100%);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  filter: blur(100px);
}

/* 页面头部 */
.center-header {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(128, 90, 213, 0.1);
  padding: 24px 48px;
  position: relative;
  z-index: 10;
  box-shadow: 0 4px 30px rgba(128, 90, 213, 0.05);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto 48px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo-icon {
  color: #805ad5;
  font-size: 48px;
  transition: all 0.3s ease;
}

.logo-text h2 {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 700;
  color: #805ad5;
  letter-spacing: -0.5px;
}

.logo-text p {
  margin: 4px 0 0;
  font-size: 0.875rem;
  color: #718096;
  letter-spacing: 0.3px;
}

/* Hero区域 */
.hero-section {
  text-align: center;
  max-width: 900px;
  margin: 0 auto 48px;
  position: relative;
  z-index: 10;
}

.badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(128, 90, 213, 0.1);
  border: 1px solid rgba(128, 90, 213, 0.2);
  padding: 8px 20px;
  border-radius: 50px;
  font-size: 0.875rem;
  color: #805ad5;
  margin-bottom: 24px;
}

.badge-icon {
  font-size: 1rem;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: 800;
  color: #2d3748;
  margin-bottom: 16px;
  letter-spacing: -1px;
  line-height: 1.2;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.main-title {
  font-size: 4.5rem;
  font-weight: 900;
  letter-spacing: 8px;
  text-shadow: 0 4px 20px rgba(128, 90, 213, 0.3);
  animation: glow 2s ease-in-out infinite alternate;
}

.subtitle {
  font-size: 1.5rem;
  font-weight: 500;
  color: #718096;
  letter-spacing: 2px;
}

@keyframes glow {
  from {
    text-shadow: 0 4px 20px rgba(128, 90, 213, 0.3);
  }
  to {
    text-shadow: 0 4px 30px rgba(128, 90, 213, 0.6), 0 0 40px rgba(128, 90, 213, 0.3);
  }
}

.gradient-text {
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 50%, #ed8936 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-description {
  font-size: 1.25rem;
  color: #718096;
  line-height: 1.6;
  margin-bottom: 32px;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}

.hero-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

/* 按钮样式 */
.primary-button {
  background: linear-gradient(135deg, #805ad5 0%, #ed8936 100%);
  color: white;
  border: none;
  padding: 14px 32px;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(128, 90, 213, 0.3);
}

.primary-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(128, 90, 213, 0.4);
}

.outline-button {
  background: transparent;
  color: #805ad5;
  border: 2px solid #805ad5;
  padding: 14px 32px;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
}

.outline-button:hover {
  background: rgba(128, 90, 213, 0.1);
  transform: translateY(-2px);
}

.secondary-button {
  background: transparent;
  color: #805ad5;
  border: 1px solid #805ad5;
  padding: 10px 24px;
  border-radius: 12px;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.secondary-button:hover {
  background: rgba(128, 90, 213, 0.1);
}

.mode-toggle {
  background: rgba(128, 90, 213, 0.1);
  border: 1px solid rgba(128, 90, 213, 0.2);
  color: #805ad5;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 0.875rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.mode-toggle:hover {
  background: rgba(128, 90, 213, 0.15);
  border-color: rgba(128, 90, 213, 0.3);
}

.btn-icon {
  font-size: 1.1rem;
}

/* 统计数据区域 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.stat-card {
  background: white;
  border: 1px solid rgba(128, 90, 213, 0.15);
  border-radius: 16px;
  padding: 28px 24px;
  text-align: center;
  transition: all 0.3s ease;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(128, 90, 213, 0.1);
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 800;
  margin-bottom: 8px;
  line-height: 1.2;
}

.stat-label {
  font-size: 0.75rem;
  color: #718096;
  font-weight: 600;
  letter-spacing: 1px;
}

/* 主内容区 */
.center-content {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
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
  margin-bottom: 8px;
  padding: 20px;
}

.system-category h2 {
  font-size: 1.75rem;
  margin: 0 0 6px 0;
  color: #805ad5;
  font-weight: 700;
  letter-spacing: -0.5px;
}

.system-category p {
  margin: 0;
  color: #718096;
  font-size: 1rem;
}

/* 系统卡片 */
.system-card {
  background: white;
  border: 1px solid rgba(128, 90, 213, 0.15);
  border-radius: 16px;
  padding: 40px 32px;
  text-align: center;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.4s ease;
  box-shadow: 0 4px 20px rgba(128, 90, 213, 0.05);
}

.system-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 40px rgba(128, 90, 213, 0.15);
  border-color: rgba(128, 90, 213, 0.3);
}

.card-icon {
  margin-bottom: 24px;
  color: #805ad5;
  transition: all 0.3s ease;
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  background: rgba(128, 90, 213, 0.1);
  border-radius: 20px;
}

.system-card:hover .card-icon {
  transform: scale(1.1);
  background: rgba(128, 90, 213, 0.15);
}

.system-card h3 {
  font-size: 1.25rem;
  margin-bottom: 12px;
  color: #2d3748;
  font-weight: 700;
  transition: color 0.3s ease;
  letter-spacing: -0.3px;
}

.system-card:hover h3 {
  color: #805ad5;
}

.system-card p {
  color: #718096;
  margin-bottom: 28px;
  font-size: 0.9rem;
  line-height: 1.6;
}

.card-hover {
  position: absolute;
  bottom: -60px;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, rgba(128, 90, 213, 0.1) 0%, rgba(237, 137, 54, 0.05) 100%);
  color: #805ad5;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.4s ease;
  border-top: 1px solid rgba(128, 90, 213, 0.15);
}

.system-card:hover .card-hover {
  bottom: 0;
}

.hover-text {
  font-weight: 600;
  font-size: 0.9rem;
}

.hover-arrow {
  font-size: 1.3rem;
  font-weight: bold;
  transition: transform 0.3s ease;
}

.system-card:hover .hover-arrow {
  transform: translateX(6px);
}

/* 页面底部 */
.center-footer {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(20px);
  border-top: 1px solid rgba(128, 90, 213, 0.1);
  padding: 48px;
  margin-top: 64px;
  position: relative;
  z-index: 1;
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
  font-size: 1.25rem;
  margin-bottom: 8px;
  font-weight: 700;
  color: #805ad5;
}

.footer-info p {
  margin: 0;
  color: #718096;
  font-size: 0.875rem;
}

.footer-links {
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
}

.footer-links a {
  color: #718096;
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.3s ease;
  position: relative;
}

.footer-links a:hover {
  color: #805ad5;
}

.footer-links a::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0;
  height: 1px;
  background: #805ad5;
  transition: width 0.3s ease;
}

.footer-links a:hover::after {
  width: 100%;
}

.copyright {
  text-align: center;
  margin: 0;
  color: #a0aec0;
  font-size: 0.8rem;
  padding-top: 24px;
  border-top: 1px solid rgba(128, 90, 213, 0.1);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .center-header {
    padding: 20px 24px;
  }
  
  .header-content {
    flex-direction: column;
    gap: 20px;
    align-items: flex-start;
  }
  
  .hero-title {
    font-size: 2rem;
  }
  
  .main-title {
    font-size: 2.5rem;
    letter-spacing: 4px;
  }
  
  .subtitle {
    font-size: 1.1rem;
  }
  
  .hero-description {
    font-size: 1rem;
  }
  
  .center-content {
    padding: 0 24px;
    grid-template-columns: 1fr;
  }
  
  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .footer-content {
    flex-direction: column;
    text-align: center;
  }
  
  .footer-links {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .hero-title {
    font-size: 1.5rem;
  }
  
  .main-title {
    font-size: 2rem;
    letter-spacing: 2px;
  }
  
  .subtitle {
    font-size: 0.9rem;
  }
  
  .hero-buttons {
    flex-direction: column;
  }
  
  .primary-button,
  .outline-button {
    width: 100%;
    justify-content: center;
  }
  
  .stats-section {
    grid-template-columns: 1fr;
  }
}

/* 夜间模式样式 */
.night-mode {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #e0e0e0;
}

.night-mode .center-header {
  background: rgba(26, 26, 46, 0.8);
  border-bottom: 1px solid rgba(100, 149, 237, 0.2);
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.3);
}

.night-mode .logo-text h2 {
  color: #6495ed;
}

.night-mode .logo-text p {
  color: #b0c4de;
}

.night-mode .decoration-circle.circle-1 {
  background: linear-gradient(135deg, #6495ed 0%, #4169e1 100%);
}

.night-mode .decoration-circle.circle-2 {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
}

.night-mode .decoration-circle.circle-3 {
  background: linear-gradient(135deg, #4ecdc4 0%, #45b7d1 100%);
}

.night-mode .badge {
  background: rgba(100, 149, 237, 0.1);
  border: 1px solid rgba(100, 149, 237, 0.3);
  color: #6495ed;
}

.night-mode .hero-title {
  color: #e0e0e0;
}

.night-mode .main-title {
  background: linear-gradient(135deg, #6495ed 0%, #4ecdc4 50%, #ff6b6b 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 4px 20px rgba(100, 149, 237, 0.4);
}

.night-mode .subtitle {
  color: #b0c4de;
}

.night-mode .hero-description {
  color: #b0c4de;
}

.night-mode .primary-button {
  background: linear-gradient(135deg, #6495ed 0%, #4ecdc4 100%);
  box-shadow: 0 4px 15px rgba(100, 149, 237, 0.4);
}

.night-mode .primary-button:hover {
  box-shadow: 0 8px 25px rgba(100, 149, 237, 0.6);
}

.night-mode .outline-button {
  color: #6495ed;
  border: 2px solid #6495ed;
}

.night-mode .outline-button:hover {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .secondary-button {
  color: #6495ed;
  border: 1px solid #6495ed;
}

.night-mode .secondary-button:hover {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .mode-toggle {
  background: rgba(100, 149, 237, 0.1);
  border: 1px solid rgba(100, 149, 237, 0.3);
  color: #6495ed;
}

.night-mode .mode-toggle:hover {
  background: rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .stat-card {
  background: rgba(26, 26, 46, 0.8);
  border: 1px solid rgba(100, 149, 237, 0.2);
  backdrop-filter: blur(10px);
}

.night-mode .stat-card:hover {
  box-shadow: 0 8px 25px rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .stat-label {
  color: #b0c4de;
}

.night-mode .system-card {
  background: rgba(26, 26, 46, 0.8);
  border: 1px solid rgba(100, 149, 237, 0.2);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
}

.night-mode .system-card:hover {
  box-shadow: 0 12px 40px rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .system-card h3 {
  color: #e0e0e0;
}

.night-mode .system-card:hover h3 {
  color: #6495ed;
}

.night-mode .system-card p {
  color: #b0c4de;
}

.night-mode .card-icon {
  color: #6495ed;
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .system-card:hover .card-icon {
  background: rgba(100, 149, 237, 0.2);
}

.night-mode .card-hover {
  background: linear-gradient(135deg, rgba(100, 149, 237, 0.1) 0%, rgba(78, 205, 196, 0.05) 100%);
  color: #6495ed;
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

.night-mode .center-footer {
  background: rgba(26, 26, 46, 0.9);
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

.night-mode .footer-info h4 {
  color: #6495ed;
}

.night-mode .footer-info p {
  color: #b0c4de;
}

.night-mode .footer-links a {
  color: #b0c4de;
}

.night-mode .footer-links a:hover {
  color: #6495ed;
}

.night-mode .footer-links a::after {
  background: #6495ed;
}

.night-mode .copyright {
  color: #6495ed;
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

/* 夜间模式动画效果 */
.night-mode .main-title {
  animation: night-glow 2s ease-in-out infinite alternate;
}

@keyframes night-glow {
  from {
    text-shadow: 0 4px 20px rgba(100, 149, 237, 0.4);
  }
  to {
    text-shadow: 0 4px 30px rgba(100, 149, 237, 0.8), 0 0 40px rgba(78, 205, 196, 0.4);
  }
}
</style>