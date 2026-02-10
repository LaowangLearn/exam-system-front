<template>
  <div class="center-container">
    <div class="center-header">
      <h1>综合系统中心</h1>
      <p>欢迎使用系统管理平台</p>
      <el-button type="danger" @click="clearStorage">清除登录状态</el-button>
    </div>
    
    <div class="center-content">
      <div class="system-card" @click="goToExamSystem">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <h3>考试系统</h3>
        <p>在线考试管理平台</p>
        <div class="card-footer">
          <span class="arrow">→</span>
        </div>
      </div>
      
      <div class="system-card" @click="goToOASystem">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
          </svg>
        </div>
        <h3>OA系统</h3>
        <p>办公自动化管理平台</p>
        <div class="card-footer">
          <span class="arrow">→</span>
        </div>
      </div>
      
      <div class="system-card" @click="goToPersonalCenter">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <h3>个人中心</h3>
        <p>个人信息管理中心</p>
        <div class="card-footer">
          <span class="arrow">→</span>
        </div>
      </div>
      
      <div class="system-card" @click="goToHISSystem">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 8h-14"></path>
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          </svg>
        </div>
        <h3>HIS系统</h3>
        <p>医院信息管理系统</p>
        <div class="card-footer">
          <span class="arrow">→</span>
        </div>
      </div>
    </div>
    
    <div class="center-footer">
      <p>© 2026 系统管理平台</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElButton, ElMessage } from 'element-plus'

const router = useRouter()

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

const clearStorage = () => {
  localStorage.removeItem('username')
  ElMessage.success('登录状态已清除')
}
</script>

<style scoped>
.center-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.center-header {
  text-align: center;
  margin-bottom: 40px;
  color: white;
}

.center-header button {
  margin-top: 20px;
}

.center-header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
  font-weight: 600;
}

.center-header p {
  font-size: 1.2rem;
  opacity: 0.9;
}

.center-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
  max-width: 800px;
  width: 100%;
}

.system-card {
  background: white;
  border-radius: 16px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.system-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
}

.system-icon {
  margin-bottom: 20px;
  color: #667eea;
}

.system-card h3 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #333;
}

.system-card p {
  color: #666;
  margin-bottom: 20px;
}

.card-footer {
  position: absolute;
  bottom: 0;
  right: 0;
  background: #667eea;
  color: white;
  padding: 10px 20px;
  border-top-left-radius: 16px;
  font-size: 1.2rem;
  font-weight: bold;
}

.center-footer {
  margin-top: 40px;
  color: white;
  opacity: 0.8;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .center-header h1 {
    font-size: 2rem;
  }
  
  .center-content {
    grid-template-columns: 1fr;
  }
}
</style>