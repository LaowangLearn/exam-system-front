<template>
  <div class="module-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <div class="header-content">
        <div class="header-top">
          <button class="back-button" @click="goBack">返回中心</button>
        </div>
        <h1 class="page-title">考试系统</h1>
        <p class="page-subtitle">选择您需要的考试功能</p>
      </div>
    </div>
    
    <!-- 模块卡片网格 -->
    <div class="module-grid">
      <!-- 考试管理模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
          </svg>
        </div>
        <h3 class="module-title">考试管理</h3>
        <p class="module-desc">创建、编辑和管理考试</p>
      </div>
      
      <!-- 题库管理模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"></circle>
            <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path>
            <path d="M12 17h.01"></path>
          </svg>
        </div>
        <h3 class="module-title">题库管理</h3>
        <p class="module-desc">管理考试题目和题库</p>
      </div>
      
      <!-- 成绩管理模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          </svg>
        </div>
        <h3 class="module-title">成绩管理</h3>
        <p class="module-desc">查看和管理考试成绩</p>
      </div>
      
      <!-- 考生管理模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3 class="module-title">考生管理</h3>
        <p class="module-desc">管理考生信息和账号</p>
      </div>
      
      <!-- 考试统计模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 3v18h18"></path>
            <path d="M19 9H5"></path>
            <path d="M16 14H5"></path>
            <path d="M12 19H5"></path>
          </svg>
        </div>
        <h3 class="module-title">考试统计</h3>
        <p class="module-desc">考试数据统计分析</p>
      </div>
      
      <!-- 系统设置模块 -->
      <div class="module-card" @click="handleNavigate('/exams')">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="3"></circle>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
          </svg>
        </div>
        <h3 class="module-title">系统设置</h3>
        <p class="module-desc">考试系统配置管理</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const handleNavigate = (path: string) => {
  router.push(path)
}

const goBack = () => {
  router.push('/')
}
</script>

<style scoped>
.module-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 40px 20px;
}

/* 页面头部 */
.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px;
}

.header-top {
  margin-bottom: 20px;
  text-align: center;
}

.back-button {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.3s ease;
}

.back-button:hover {
  background: #764ba2;
}

.page-title {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.page-subtitle {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 20px;
}

/* 模块网格 */
.module-grid {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

/* 模块卡片 */
.module-card {
  background: white;
  border-radius: 12px;
  padding: 30px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

.module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

/* 模块图标 */
.module-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: #667eea;
  font-size: 48px;
  line-height: 48px;
  height: 48px;
}

.module-card:hover .module-icon {
  transform: scale(1.1);
  transition: transform 0.3s ease;
}

/* 模块标题和描述 */
.module-title {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 10px;
}

.module-desc {
  color: #666;
  line-height: 1.5;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .module-container {
    padding: 30px 20px;
  }
  
  .page-header {
    margin-bottom: 30px;
  }
  
  .page-title {
    font-size: 2rem;
  }
  
  .header-content {
    padding: 0 20px;
  }
  
  .module-grid {
    padding: 0 20px;
    grid-template-columns: 1fr;
  }
  
  .module-card {
    padding: 24px;
  }
}


</style>