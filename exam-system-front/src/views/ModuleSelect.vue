<template>
  <div class="module-container">
    <div class="grid-bg"></div>

    <div class="page-header">
      <div class="header-content">
        <div class="header-top">
          <button class="back-button" @click="goBack">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="19" y1="12" x2="5" y2="12"></line>
              <polyline points="12 19 5 12 12 5"></polyline>
            </svg>
            返回中心
          </button>
        </div>
        <h1 class="page-title">考试系统</h1>
        <p class="page-subtitle">选择您需要的考试功能</p>
      </div>
    </div>

    <div class="module-grid">
      <div class="module-card" v-for="(item, index) in modules" :key="index" @click="handleNavigate(item.path)">
        <div class="card-glow"></div>
        <div class="module-icon" v-html="item.icon"></div>
        <h3 class="module-title">{{ item.title }}</h3>
        <p class="module-desc">{{ item.description }}</p>
        <div class="card-footer">
          <span>进入</span>
          <span class="arrow">→</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const modules = [
  {
    title: '考试管理',
    description: '创建、编辑和管理考试',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path></svg>'
  },
  {
    title: '题库管理',
    description: '管理考试题目和题库',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"></circle><path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path><path d="M12 17h.01"></path></svg>'
  },
  {
    title: '成绩管理',
    description: '查看和管理考试成绩',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path></svg>'
  },
  {
    title: '考生管理',
    description: '管理考生信息和账号',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M23 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>'
  },
  {
    title: '考试统计',
    description: '考试数据统计分析',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 3v18h18"></path><path d="M19 9H5"></path><path d="M16 14H5"></path><path d="M12 19H5"></path></svg>'
  },
  {
    title: '系统设置',
    description: '考试系统配置管理',
    path: '/exams',
    icon: '<svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="3"></circle><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path></svg>'
  }
]

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
  background: linear-gradient(135deg, #0f0f1a 0%, #1a1a2e 50%, #16213e 100%);
  padding: 40px 20px;
  position: relative;
  overflow: hidden;
}

.page-header {
  text-align: center;
  margin-bottom: 50px;
  position: relative;
  z-index: 10;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px;
}

.header-top {
  margin-bottom: 24px;
  text-align: center;
}

.back-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(0, 240, 255, 0.08);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
  padding: 10px 24px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.back-button:hover {
  background: rgba(0, 240, 255, 0.15);
  border-color: rgba(0, 240, 255, 0.5);
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
  transform: translateX(-4px);
}

.back-button svg {
  transition: transform 0.3s ease;
}

.back-button:hover svg {
  transform: translateX(-3px);
}

.page-title {
  font-size: 2.5rem;
  color: #ffffff;
  margin-bottom: 12px;
  font-weight: 700;
  letter-spacing: 3px;
}

.page-subtitle {
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 24px;
}

.module-grid {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 30px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  position: relative;
  z-index: 10;
}

.module-card {
  position: relative;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  padding: 36px 28px;
  text-align: center;
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  overflow: hidden;
}

.module-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1) 0%, rgba(102, 126, 234, 0.1) 100%);
  opacity: 0;
  transition: opacity 0.4s ease;
}

.module-card:hover {
  transform: translateY(-8px);
  border-color: rgba(0, 240, 255, 0.25);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3), 0 0 30px rgba(0, 240, 255, 0.1);
}

.module-card:hover::before {
  opacity: 1;
}

.card-glow {
  position: absolute;
  top: -60px;
  left: -60px;
  width: 120px;
  height: 120px;
  background: radial-gradient(circle, rgba(0, 240, 255, 0.15) 0%, transparent 70%);
  pointer-events: none;
  transition: all 0.5s ease;
}

.module-card:hover .card-glow {
  transform: scale(1.5);
}

.module-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 72px;
  height: 72px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.15) 0%, rgba(102, 126, 234, 0.15) 100%);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 18px;
  color: #00f0ff;
  margin-bottom: 24px;
  transition: all 0.4s ease;
}

.module-card:hover .module-icon {
  transform: scale(1.1) rotate(5deg);
  border-color: rgba(0, 240, 255, 0.4);
  box-shadow: 0 0 30px rgba(0, 240, 255, 0.25);
}

.module-title {
  font-size: 1.25rem;
  color: #ffffff;
  margin-bottom: 10px;
  font-weight: 600;
  transition: color 0.3s ease;
}

.module-card:hover .module-title {
  color: #00f0ff;
}

.module-desc {
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9rem;
  line-height: 1.6;
}

.card-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: 20px;
  padding-top: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.06);
  font-size: 0.85rem;
  color: rgba(0, 240, 255, 0.7);
  opacity: 0;
  transform: translateY(10px);
  transition: all 0.4s ease;
}

.module-card:hover .card-footer {
  opacity: 1;
  transform: translateY(0);
}

.arrow {
  transition: transform 0.3s ease;
}

.module-card:hover .arrow {
  transform: translateX(4px);
}

@media (max-width: 768px) {
  .page-title {
    font-size: 2rem;
    letter-spacing: 1px;
  }

  .module-grid {
    grid-template-columns: 1fr;
  }
}
</style>