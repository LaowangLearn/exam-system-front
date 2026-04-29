<template>
  <div class="edu-system">
    <div class="edu-header">
      <div class="header-left">
        <button class="back-btn" @click="goBack">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M15 18l-6-6 6-6" />
          </svg>
          返回
        </button>
        <div class="header-title">
          <h1>校园教育类系统</h1>
          <p>智慧校园综合管理平台</p>
        </div>
      </div>
      <div class="header-right">
        <span class="system-badge">v3.0.0</span>
      </div>
    </div>
    
    <div class="edu-content">
      <div class="module-grid">
        <div class="module-card" @click="goToEducation">
          <div class="card-icon education-icon">📚</div>
          <h3>教务管理系统</h3>
          <p>课程管理、选课系统、成绩管理、学籍管理</p>
          <div class="card-stats">
            <span class="stat-item">
              <strong>120+</strong>
              <span>课程</span>
            </span>
            <span class="stat-item">
              <strong>3500+</strong>
              <span>学生</span>
            </span>
          </div>
        </div>
        
        <div class="module-card" @click="goToSmart">
          <div class="card-icon smart-icon">🏫</div>
          <h3>智慧校园系统</h3>
          <p>校园安防、设施管理、能耗监控、后勤服务</p>
          <div class="card-stats">
            <span class="stat-item">
              <strong>50+</strong>
              <span>设备</span>
            </span>
            <span class="stat-item">
              <strong>100%</strong>
              <span>在线</span>
            </span>
          </div>
        </div>
        
        <div class="module-card" @click="goToHome">
          <div class="card-icon home-icon">👨‍👩‍👧</div>
          <h3>家校互通系统</h3>
          <p>家校沟通、作业发布、成绩通知、家长端</p>
          <div class="card-stats">
            <span class="stat-item">
              <strong>85%</strong>
              <span>家长注册</span>
            </span>
            <span class="stat-item">
              <strong>2000+</strong>
              <span>消息</span>
            </span>
          </div>
        </div>
      </div>
      
      <div class="quick-stats">
        <div class="stat-card">
          <div class="stat-icon">📊</div>
          <div class="stat-info">
            <div class="stat-value">98.5%</div>
            <div class="stat-label">系统可用率</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">⏱️</div>
          <div class="stat-info">
            <div class="stat-value">24/7</div>
            <div class="stat-label">全天候服务</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🔒</div>
          <div class="stat-info">
            <div class="stat-value">等保三级</div>
            <div class="stat-label">安全认证</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🤖</div>
          <div class="stat-info">
            <div class="stat-value">AI赋能</div>
            <div class="stat-label">智能辅助</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/')
}

const goToEducation = () => {
  router.push('/education/education')
}

const goToSmart = () => {
  router.push('/education/smart')
}

const goToHome = () => {
  router.push('/education/home')
}
</script>

<style scoped>
.edu-system {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.edu-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: rgba(255, 255, 255, 0.2);
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.3);
}

.header-title h1 {
  margin: 0;
  font-size: 28px;
  color: white;
}

.header-title p {
  margin: 5px 0 0;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

.system-badge {
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  color: white;
  font-size: 14px;
}

.edu-content {
  max-width: 1400px;
  margin: 0 auto;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-bottom: 30px;
}

.module-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
}

.module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.card-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 32px;
  margin-bottom: 16px;
}

.education-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.smart-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.home-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.module-card h3 {
  margin: 0 0 8px;
  font-size: 20px;
  color: #333;
}

.module-card p {
  margin: 0 0 16px;
  color: #666;
  font-size: 14px;
  line-height: 1.5;
}

.card-stats {
  display: flex;
  gap: 20px;
}

.stat-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-item strong {
  font-size: 18px;
  color: #333;
}

.stat-item span {
  font-size: 12px;
  color: #999;
}

.quick-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  font-size: 32px;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

@media (max-width: 1200px) {
  .module-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .module-grid {
    grid-template-columns: 1fr;
  }
  
  .quick-stats {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>