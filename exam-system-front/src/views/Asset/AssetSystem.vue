<template>
  <div class="asset-system-container">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="system-header">
      <h1>固定资产系统</h1>
      <p>资产全生命周期管理平台</p>
    </div>

    <div class="module-grid">
      <div class="module-card" @click="activeModule = 'category'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>资产分类</h3>
        <p>分类管理、折旧规则</p>
      </div>

      <div class="module-card" @click="activeModule = 'asset'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
            <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
          </svg>
        </div>
        <h3>资产管理</h3>
        <p>资产登记、信息维护</p>
      </div>

      <div class="module-card" @click="activeModule = 'requisition'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="12" y1="18" x2="12" y2="12"></line>
            <line x1="9" y1="15" x2="15" y2="15"></line>
          </svg>
        </div>
        <h3>资产领用</h3>
        <p>领用申请、审批流程</p>
      </div>

      <div class="module-card" @click="activeModule = 'maintenance'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
          </svg>
        </div>
        <h3>维修管理</h3>
        <p>维修记录、费用统计</p>
      </div>

      <div class="module-card" @click="activeModule = 'scrap'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="3 6 5 6 21 6"></polyline>
            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
          </svg>
        </div>
        <h3>报废处理</h3>
        <p>报废申请、审批处理</p>
      </div>
    </div>

    <div class="module-content" v-if="activeModule">
      <h2>{{ getModuleTitle() }}</h2>
      <p>API接口: /asset/{{ activeModule }}</p>
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
    category: '资产分类管理',
    asset: '资产管理',
    requisition: '资产领用管理',
    maintenance: '维修管理',
    scrap: '报废处理管理'
  }
  return titles[activeModule.value] || ''
}
</script>

<style scoped>
.asset-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #fb7185 0%, #f43f5e 100%);
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
  color: #f43f5e;
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
  color: #f43f5e;
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
