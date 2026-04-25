<template>
  <div class="archive-system-container">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="system-header">
      <h1>电子档案系统</h1>
      <p>档案存储与借阅管理平台</p>
    </div>

    <div class="module-grid">
      <div class="module-card" @click="activeModule = 'category'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>档案分类</h3>
        <p>分类管理、权限设置</p>
      </div>

      <div class="module-card" @click="activeModule = 'archive'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
          </svg>
        </div>
        <h3>档案管理</h3>
        <p>档案存储、检索查阅</p>
      </div>

      <div class="module-card" @click="activeModule = 'borrow'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>档案借阅</h3>
        <p>借阅申请、审批管理</p>
      </div>

      <div class="module-card" @click="activeModule = 'log'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="16" y1="13" x2="8" y2="13"></line>
            <line x1="16" y1="17" x2="8" y2="17"></line>
          </svg>
        </div>
        <h3>操作日志</h3>
        <p>操作记录、审计追踪</p>
      </div>
    </div>

    <div class="module-content" v-if="activeModule">
      <h2>{{ getModuleTitle() }}</h2>
      <p>API接口: /archive/{{ activeModule }}</p>
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
    category: '档案分类管理',
    archive: '档案信息管理',
    borrow: '档案借阅管理',
    log: '操作日志管理'
  }
  return titles[activeModule.value] || ''
}
</script>

<style scoped>
.archive-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #fbbf24 0%, #f59e0b 100%);
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
  color: #f59e0b;
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
  color: #f59e0b;
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
