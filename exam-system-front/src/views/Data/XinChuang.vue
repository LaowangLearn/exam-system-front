<template>
  <div class="xinchuang-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>信创国产化适配</h1>
    </div>
    
    <div class="page-content">
      <div class="overview-section">
        <div class="section-header">
          <h2>适配概览</h2>
        </div>
        <div class="overview-cards">
          <div class="overview-card">
            <div class="card-icon">✅</div>
            <div class="card-info">
              <div class="card-value">{{ overview.compatible }}</div>
              <div class="card-label">兼容组件</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="card-icon">🔄</div>
            <div class="card-info">
              <div class="card-value">{{ overview.inProgress }}</div>
              <div class="card-label">适配中</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="card-icon">⏳</div>
            <div class="card-info">
              <div class="card-value">{{ overview.pending }}</div>
              <div class="card-label">待适配</div>
            </div>
          </div>
          <div class="overview-card">
            <div class="card-icon">📊</div>
            <div class="card-info">
              <div class="card-value">{{ overview.percentage }}%</div>
              <div class="card-label">适配率</div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="content-grid">
        <div class="panel">
          <div class="panel-header">
            <h3>操作系统适配</h3>
          </div>
          <div class="os-list">
            <div v-for="os in osList" :key="os.id" class="os-item">
              <div class="os-icon">{{ os.icon }}</div>
              <div class="os-info">
                <div class="os-name">{{ os.name }}</div>
                <div class="os-version">{{ os.version }}</div>
              </div>
              <div :class="['os-status', os.status]">
                <span>{{ os.statusText }}</span>
                <span class="status-dot" :class="os.status"></span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>数据库适配</h3>
          </div>
          <div class="db-list">
            <div v-for="db in dbList" :key="db.id" class="db-item">
              <div class="db-icon">{{ db.icon }}</div>
              <div class="db-info">
                <div class="db-name">{{ db.name }}</div>
                <div class="db-desc">{{ db.desc }}</div>
              </div>
              <div :class="['db-badge', db.status]">{{ db.status === 'supported' ? '已支持' : '开发中' }}</div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>国密算法</h3>
          </div>
          <div class="crypto-list">
            <div v-for="crypto in cryptoList" :key="crypto.id" class="crypto-item">
              <div class="crypto-icon">{{ crypto.icon }}</div>
              <div class="crypto-info">
                <div class="crypto-name">{{ crypto.name }}</div>
                <div class="crypto-algo">{{ crypto.algo }}</div>
              </div>
              <div class="crypto-status">
                <span class="status-check">✓</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>中间件适配</h3>
          </div>
          <div class="middleware-list">
            <div v-for="mw in middlewareList" :key="mw.id" class="middleware-item">
              <div class="mw-info">
                <div class="mw-name">{{ mw.name }}</div>
                <div class="mw-type">{{ mw.type }}</div>
              </div>
              <div :class="['mw-status', mw.status]">{{ mw.statusText }}</div>
            </div>
          </div>
        </div>
        
        <div class="panel full-width">
          <div class="panel-header">
            <h3>适配检测报告</h3>
            <button class="run-btn" @click="runDetection">运行检测</button>
          </div>
          <div class="detection-result">
            <div class="result-header">
              <span class="result-title">系统检测结果</span>
              <span :class="['result-status', detectionStatus]">{{ detectionStatusText }}</span>
            </div>
            <div class="result-details">
              <div class="detail-item">
                <span class="detail-label">操作系统</span>
                <span :class="['detail-value', 'pass']">✓ 兼容</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">数据库</span>
                <span :class="['detail-value', 'pass']">✓ 兼容</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">中间件</span>
                <span :class="['detail-value', 'pass']">✓ 兼容</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">国密算法</span>
                <span :class="['detail-value', 'pass']">✓ 已启用</span>
              </div>
              <div class="detail-item">
                <span class="detail-label">安全合规</span>
                <span :class="['detail-value', 'pass']">✓ 通过</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/data')
}

const overview = ref({
  compatible: 15,
  inProgress: 3,
  pending: 2,
  percentage: 83
})

const osList = ref([
  { id: 1, name: '麒麟操作系统', version: 'V10', icon: '🐧', status: 'supported', statusText: '已兼容' },
  { id: 2, name: '统信UOS', version: '20', icon: '🐧', status: 'supported', statusText: '已兼容' },
  { id: 3, name: '中科方德', version: 'V5.0', icon: '🐧', status: 'in-progress', statusText: '适配中' },
  { id: 4, name: '深度Linux', version: '20.5', icon: '🐧', status: 'supported', statusText: '已兼容' }
])

const dbList = ref([
  { id: 1, name: '达梦数据库', desc: 'DM8', icon: '🗄️', status: 'supported' },
  { id: 2, name: '人大金仓', desc: 'KingbaseES', icon: '🗄️', status: 'supported' },
  { id: 3, name: '华为高斯', desc: 'GaussDB', icon: '🗄️', status: 'supported' },
  { id: 4, name: '南大通用', desc: 'GBase', icon: '🗄️', status: 'in-progress' }
])

const cryptoList = ref([
  { id: 1, name: 'SM2', algo: '非对称加密算法', icon: '🔐' },
  { id: 2, name: 'SM3', algo: '哈希算法', icon: '🔗' },
  { id: 3, name: 'SM4', algo: '对称加密算法', icon: '🔑' },
  { id: 4, name: 'SM9', algo: '标识密码算法', icon: '📛' }
])

const middlewareList = ref([
  { id: 1, name: '东方通TongWeb', type: '应用服务器', status: 'supported', statusText: '已兼容' },
  { id: 2, name: '金蝶中间件', type: '应用服务器', status: 'supported', statusText: '已兼容' },
  { id: 3, name: '中创中间件', type: '应用服务器', status: 'in-progress', statusText: '适配中' },
  { id: 4, name: '宝兰德', type: '应用服务器', status: 'supported', statusText: '已兼容' }
])

const detectionStatus = ref('pass')
const detectionStatusText = ref('检测通过')

const runDetection = () => {
  detectionStatus.value = 'running'
  detectionStatusText.value = '检测中...'
  
  setTimeout(() => {
    detectionStatus.value = 'pass'
    detectionStatusText.value = '检测通过'
  }, 2000)
}
</script>

<style scoped>
.xinchuang-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: rgba(255, 255, 255, 0.05);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #ef4444;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: white;
}

.page-content {
  padding: 20px 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.section-header h2 {
  margin: 0 0 20px;
  font-size: 18px;
  color: white;
}

.overview-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 20px;
}

.overview-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
}

.card-icon {
  font-size: 32px;
}

.card-info {
  display: flex;
  flex-direction: column;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  color: white;
}

.card-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.panel {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 20px;
}

.panel.full-width {
  grid-column: span 2;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.panel-header h3 {
  margin: 0;
  font-size: 16px;
  color: white;
}

.run-btn {
  padding: 8px 16px;
  background: #ef4444;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 13px;
}

.os-list, .db-list, .crypto-list, .middleware-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.os-item, .db-item, .crypto-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
}

.os-icon, .db-icon, .crypto-icon {
  font-size: 24px;
}

.os-info, .db-info, .crypto-info {
  flex: 1;
}

.os-name, .db-name, .crypto-name {
  font-size: 14px;
  color: white;
  font-weight: 500;
}

.os-version, .db-desc, .crypto-algo {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.os-status {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.os-status.supported {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.os-status.in-progress {
  background: rgba(251, 191, 36, 0.2);
  color: #f59e0b;
}

.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
}

.status-dot.supported { background: #22c55e; }
.status-dot.in-progress { background: #f59e0b; animation: blink 1s infinite; }

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.3; }
}

.db-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.db-badge.supported {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.db-badge.in-progress {
  background: rgba(251, 191, 36, 0.2);
  color: #f59e0b;
}

.crypto-status {
  color: #22c55e;
}

.status-check {
  font-size: 18px;
}

.middleware-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
}

.mw-info {
  display: flex;
  flex-direction: column;
}

.mw-name {
  font-size: 14px;
  color: white;
}

.mw-type {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.mw-status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.mw-status.supported {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.mw-status.in-progress {
  background: rgba(251, 191, 36, 0.2);
  color: #f59e0b;
}

.detection-result {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 10px;
  padding: 20px;
}

.result-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.result-title {
  font-size: 16px;
  color: white;
  font-weight: 500;
}

.result-status {
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 13px;
}

.result-status.pass {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.result-status.running {
  background: rgba(251, 191, 36, 0.2);
  color: #f59e0b;
}

.result-details {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 15px;
}

.detail-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 8px;
}

.detail-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 8px;
}

.detail-value {
  font-size: 14px;
  font-weight: 500;
}

.detail-value.pass {
  color: #22c55e;
}

@media (max-width: 900px) {
  .overview-cards {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .panel.full-width {
    grid-column: span 1;
  }
  
  .result-details {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>