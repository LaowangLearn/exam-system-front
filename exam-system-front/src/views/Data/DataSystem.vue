<template>
  <div class="data-system">
    <div class="data-header">
      <div class="header-left">
        <button class="back-btn" @click="goBack">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M15 18l-6-6 6-6" />
          </svg>
          返回
        </button>
        <div class="header-title">
          <h1>数据中台</h1>
          <p>大数据分析与智能处理平台</p>
        </div>
      </div>
      <div class="header-right">
        <span class="system-badge">v3.0.0</span>
      </div>
    </div>
    
    <div class="data-content">
      <div class="module-grid">
        <div class="module-card bigdata-card" @click="goToBigData">
          <div class="card-icon">
            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="18" y1="20" x2="18" y2="10"></line>
              <line x1="12" y1="20" x2="12" y2="4"></line>
              <line x1="6" y1="20" x2="6" y2="16"></line>
            </svg>
          </div>
          <h3>大数据平台</h3>
          <p>离线批处理、实时流处理、数据仓库、数据湖</p>
          <div class="card-features">
            <span>Hadoop</span>
            <span>Spark</span>
            <span>Flink</span>
          </div>
        </div>
        
        <div class="module-card ai-card" @click="goToAIAgent">
          <div class="card-icon">
            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="9" cy="9" r="7"></circle>
              <path d="M9 16c4 0 7-3 7-7"></path>
            </svg>
          </div>
          <h3>AI智能Agent</h3>
          <p>智能调度、自动化流程、自然语言交互、决策辅助</p>
          <div class="card-features">
            <span>LLM</span>
            <span>Agent</span>
            <span>RAG</span>
          </div>
        </div>
        
        <div class="module-card lowcode-card" @click="goToLowCode">
          <div class="card-icon">
            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="3" y="3" width="7" height="7"></rect>
              <rect x="14" y="3" width="7" height="7"></rect>
              <rect x="14" y="14" width="7" height="7"></rect>
              <rect x="3" y="14" width="7" height="7"></rect>
            </svg>
          </div>
          <h3>低代码平台</h3>
          <p>可视化开发、组件拖拽、流程编排、快速构建</p>
          <div class="card-features">
            <span>表单</span>
            <span>流程</span>
            <span>报表</span>
          </div>
        </div>
        
        <div class="module-card xinchuang-card" @click="goToXinChuang">
          <div class="card-icon">
            <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
              <polyline points="2 17 12 22 22 17"></polyline>
              <path d="M2 12l10 5 10-5"></path>
            </svg>
          </div>
          <h3>信创适配</h3>
          <p>国产化替代、国密加密、信创认证、兼容适配</p>
          <div class="card-features">
            <span>麒麟</span>
            <span>统信</span>
            <span>达梦</span>
          </div>
        </div>
      </div>
      
      <div class="stats-overview">
        <div class="stat-card">
          <div class="stat-icon">📊</div>
          <div class="stat-info">
            <div class="stat-value">100TB+</div>
            <div class="stat-label">数据处理量</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">⚡</div>
          <div class="stat-info">
            <div class="stat-value">50ms</div>
            <div class="stat-label">平均延迟</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🔄</div>
          <div class="stat-info">
            <div class="stat-value">24/7</div>
            <div class="stat-label">实时处理</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">🔒</div>
          <div class="stat-info">
            <div class="stat-value">等保三级</div>
            <div class="stat-label">安全认证</div>
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

const goToBigData = () => {
  router.push('/data/bigdata')
}

const goToAIAgent = () => {
  router.push('/data/ai')
}

const goToLowCode = () => {
  router.push('/data/lowcode')
}

const goToXinChuang = () => {
  router.push('/data/xinchuang')
}
</script>

<style scoped>
.data-system {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 20px;
}

.data-header {
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
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.2);
}

.header-title h1 {
  margin: 0;
  font-size: 28px;
  color: white;
}

.header-title p {
  margin: 5px 0 0;
  color: rgba(255, 255, 255, 0.6);
  font-size: 14px;
}

.system-badge {
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  color: white;
  font-size: 14px;
}

.data-content {
  max-width: 1400px;
  margin: 0 auto;
}

.module-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
  margin-bottom: 30px;
}

.module-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  padding: 24px;
  cursor: pointer;
  transition: all 0.3s;
}

.module-card:hover {
  transform: translateY(-5px);
  border-color: rgba(255, 255, 255, 0.3);
}

.bigdata-card:hover {
  background: linear-gradient(135deg, rgba(99, 102, 241, 0.2) 0%, transparent 100%);
}

.ai-card:hover {
  background: linear-gradient(135deg, rgba(168, 85, 247, 0.2) 0%, transparent 100%);
}

.lowcode-card:hover {
  background: linear-gradient(135deg, rgba(34, 197, 94, 0.2) 0%, transparent 100%);
}

.xinchuang-card:hover {
  background: linear-gradient(135deg, rgba(239, 68, 68, 0.2) 0%, transparent 100%);
}

.card-icon {
  color: #60a5fa;
  margin-bottom: 16px;
}

.bigdata-card .card-icon { color: #60a5fa; }
.ai-card .card-icon { color: #a855f7; }
.lowcode-card .card-icon { color: #22c55e; }
.xinchuang-card .card-icon { color: #ef4444; }

.module-card h3 {
  margin: 0 0 8px;
  font-size: 20px;
  color: white;
}

.module-card p {
  margin: 0 0 16px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 14px;
}

.card-features {
  display: flex;
  gap: 8px;
}

.card-features span {
  padding: 4px 12px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
}

.stats-overview {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}

.stat-card {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
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
  font-size: 24px;
  font-weight: bold;
  color: white;
}

.stat-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

@media (max-width: 900px) {
  .module-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-overview {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>