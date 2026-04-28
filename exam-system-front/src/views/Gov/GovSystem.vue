<template>
  <div class="gov-container">
    <button class="back-btn" @click="goBack">返回中心</button>
    <div class="system-header">
      <h1>政务事业单位类</h1>
      <p>政务一体化综合管理平台</p>
    </div>

    <div class="system-grid">
      <div class="system-card integration-card" @click="navigateTo('/gov/dashboard')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
            <path d="M2 17l10 5 10-5"></path>
            <path d="M2 12l10 5 10-5"></path>
          </svg>
        </div>
        <h3>政务一体化平台</h3>
        <p>统一政务服务入口、数据整合、协同办公、综合管理</p>
        <div class="card-tags">
          <span class="tag">统一入口</span>
          <span class="tag">数据整合</span>
          <span class="tag">协同办公</span>
        </div>
      </div>

      <div class="system-card grid-card" @click="navigateTo('/gov/grid/cell')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="7" height="7"></rect>
            <rect x="14" y="3" width="7" height="7"></rect>
            <rect x="14" y="14" width="7" height="7"></rect>
            <rect x="3" y="14" width="7" height="7"></rect>
          </svg>
        </div>
        <h3>网格化治理系统</h3>
        <p>网格单元管理、事件上报分派、巡检管理、基层治理</p>
        <div class="card-tags">
          <span class="tag">网格管理</span>
          <span class="tag">事件处理</span>
          <span class="tag">基层治理</span>
        </div>
      </div>

      <div class="system-card petition-card" @click="navigateTo('/gov/petition/register')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <h3>综治信访工单系统</h3>
        <p>信访登记受理、工单分派办理、满意度评价、数据统计</p>
        <div class="card-tags">
          <span class="tag">信访登记</span>
          <span class="tag">工单办理</span>
          <span class="tag">满意度</span>
        </div>
      </div>

      <div class="system-card security-card" @click="navigateTo('/gov/security/monitor')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            <circle cx="12" cy="10" r="3"></circle>
          </svg>
        </div>
        <h3>智慧安防平台</h3>
        <p>视频监控、设备管理、实时告警、AI智能分析</p>
        <div class="card-tags">
          <span class="tag">视频监控</span>
          <span class="tag">智能告警</span>
          <span class="tag">AI分析</span>
        </div>
      </div>

      <div class="system-card document-card" @click="navigateTo('/gov/document/list')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="16" y1="13" x2="8" y2="13"></line>
            <line x1="16" y1="17" x2="8" y2="17"></line>
            <polyline points="10 9 9 9 8 9"></polyline>
          </svg>
        </div>
        <h3>公文机要系统</h3>
        <p>公文起草审批、国密加密、电子签章、档案管理</p>
        <div class="card-tags">
          <span class="tag">公文处理</span>
          <span class="tag">国密加密</span>
          <span class="tag">档案管理</span>
        </div>
      </div>
    </div>

    <div class="system-features">
      <h2>核心能力</h2>
      <div class="features-grid">
        <div class="feature-item">
          <h4>国产化信创适配</h4>
          <p>支持国产数据库、中间件、操作系统</p>
        </div>
        <div class="feature-item">
          <h4>等保三级合规</h4>
          <p>符合国家信息安全等级保护三级要求</p>
        </div>
        <div class="feature-item">
          <h4>国密加密保障</h4>
          <p>SM2/SM3/SM4国密算法加密保护</p>
        </div>
        <div class="feature-item">
          <h4>轻量化AI赋能</h4>
          <p>本地AI分析，离线部署支持</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/')
}

const navigateTo = (path: string) => {
  router.push(path)
}
</script>

<style scoped>
.gov-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 40px 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  color: white;
  border: none;
  padding: 12px 30px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(59, 130, 246, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(59, 130, 246, 0.6);
}

.system-header {
  text-align: center;
  margin-bottom: 50px;
}

.system-header h1 {
  font-size: 3rem;
  color: #fff;
  margin-bottom: 15px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.system-header p {
  font-size: 1.3rem;
  color: #94a3b8;
}

.system-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
  max-width: 1400px;
  margin: 0 auto 50px;
}

.system-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 35px;
  text-align: center;
  cursor: pointer;
  transition: all 0.4s ease;
  position: relative;
  overflow: hidden;
}

.system-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, transparent, var(--accent-color), transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.system-card:hover::before {
  opacity: 1;
}

.system-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.card-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  background: var(--accent-bg);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--accent-color);
  transition: all 0.3s ease;
}

.system-card:hover .card-icon {
  transform: scale(1.1);
}

.system-card h3 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 0 0 15px;
}

.system-card p {
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 20px;
}

.card-tags {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.tag {
  background: #f1f5f9;
  color: #475569;
  padding: 6px 14px;
  border-radius: 15px;
  font-size: 0.85rem;
  font-weight: 500;
}

.integration-card { 
  --accent-color: #3b82f6; 
  --accent-bg: #dbeafe;
}
.integration-card:hover { border-bottom: 4px solid #3b82f6; }

.grid-card { 
  --accent-color: #10b981; 
  --accent-bg: #d1fae5;
}
.grid-card:hover { border-bottom: 4px solid #10b981; }

.petition-card { 
  --accent-color: #f59e0b; 
  --accent-bg: #fef3c7;
}
.petition-card:hover { border-bottom: 4px solid #f59e0b; }

.security-card { 
  --accent-color: #ef4444; 
  --accent-bg: #fee2e2;
}
.security-card:hover { border-bottom: 4px solid #ef4444; }

.document-card { 
  --accent-color: #8b5cf6; 
  --accent-bg: #f3e8ff;
}
.document-card:hover { border-bottom: 4px solid #8b5cf6; }

.system-features {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  backdrop-filter: blur(10px);
}

.system-features h2 {
  text-align: center;
  color: #fff;
  font-size: 2rem;
  margin-bottom: 40px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
}

.feature-item {
  background: rgba(255, 255, 255, 0.1);
  padding: 25px;
  border-radius: 15px;
  text-align: center;
}

.feature-item h4 {
  color: #3b82f6;
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.feature-item p {
  color: #94a3b8;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .system-header h1 {
    font-size: 2rem;
  }

  .system-grid {
    grid-template-columns: 1fr;
  }

  .system-card {
    padding: 25px;
  }
}
</style>
