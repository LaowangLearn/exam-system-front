<template>
  <div class="bigdata-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>大数据平台</h1>
    </div>
    
    <div class="page-content">
      <div class="dashboard-section">
        <div class="section-header">
          <h2>数据概览</h2>
        </div>
        <div class="stats-row">
          <div class="stat-card">
            <div class="stat-icon">📦</div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalData }}</div>
              <div class="stat-label">数据总量</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">⚡</div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.realtimeSpeed }}</div>
              <div class="stat-label">实时处理速度</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📊</div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.tasks }}</div>
              <div class="stat-label">任务数</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">✅</div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.successRate }}%</div>
              <div class="stat-label">成功率</div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="content-grid">
        <div class="panel">
          <div class="panel-header">
            <h3>数据管道</h3>
            <button class="add-btn">+ 新建管道</button>
          </div>
          <div class="pipeline-list">
            <div v-for="pipe in pipelines" :key="pipe.id" :class="['pipeline-item', pipe.status]">
              <div class="pipe-icon">{{ pipe.icon }}</div>
              <div class="pipe-info">
                <div class="pipe-name">{{ pipe.name }}</div>
                <div class="pipe-meta">{{ pipe.source }} → {{ pipe.target }}</div>
              </div>
              <div :class="['pipe-status', pipe.status]">
                {{ pipe.status === 'running' ? '运行中' : pipe.status === 'success' ? '完成' : '失败' }}
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>实时监控</h3>
          </div>
          <div class="monitor-chart">
            <div class="chart-title">实时数据流量</div>
            <div class="chart-bars">
              <div v-for="(val, idx) in realtimeData" :key="idx" class="bar-wrapper">
                <div class="bar" :style="{ height: val + '%' }"></div>
              </div>
            </div>
            <div class="chart-labels">
              <span>00:00</span>
              <span>06:00</span>
              <span>12:00</span>
              <span>18:00</span>
              <span>24:00</span>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>数据任务</h3>
          </div>
          <div class="task-list">
            <div v-for="task in tasks" :key="task.id" class="task-item">
              <div class="task-header">
                <span class="task-name">{{ task.name }}</span>
                <span :class="['task-priority', task.priority]">{{ task.priority }}</span>
              </div>
              <div class="task-progress">
                <div class="progress-bar">
                  <div class="progress-fill" :style="{ width: task.progress + '%' }"></div>
                </div>
                <span class="progress-text">{{ task.progress }}%</span>
              </div>
              <div class="task-info">
                <span>{{ task.startTime }}</span>
                <span>{{ task.duration }}</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>数据质量</h3>
          </div>
          <div class="quality-grid">
            <div v-for="item in qualityMetrics" :key="item.name" class="quality-item">
              <div class="quality-icon">{{ item.icon }}</div>
              <div class="quality-info">
                <div class="quality-name">{{ item.name }}</div>
                <div :class="['quality-value', item.status]">{{ item.value }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/data')
}

const stats = ref({
  totalData: '100TB',
  realtimeSpeed: '100K/s',
  tasks: 128,
  successRate: 99.5
})

const pipelines = ref([
  { id: 1, name: '用户行为数据', source: 'Kafka', target: 'HDFS', status: 'running', icon: '🔄' },
  { id: 2, name: '订单数据同步', source: 'MySQL', target: 'Hive', status: 'success', icon: '✅' },
  { id: 3, name: '日志采集', source: 'Filebeat', target: 'ES', status: 'running', icon: '🔄' },
  { id: 4, name: '数据清洗任务', source: 'HDFS', target: 'Hive', status: 'failed', icon: '❌' }
])

const tasks = ref([
  { id: 1, name: '每日数据汇总', priority: 'high', progress: 75, startTime: '00:00', duration: '2h 30m' },
  { id: 2, name: '用户画像更新', priority: 'medium', progress: 45, startTime: '02:00', duration: '1h 15m' },
  { id: 3, name: '报表生成', priority: 'low', progress: 90, startTime: '06:00', duration: '30m' }
])

const qualityMetrics = ref([
  { name: '数据完整性', value: '99.8%', status: 'good', icon: '✅' },
  { name: '数据准确性', value: '99.5%', status: 'good', icon: '📊' },
  { name: '数据一致性', value: '98.9%', status: 'medium', icon: '🔄' },
  { name: '数据时效性', value: '99.2%', status: 'good', icon: '⏱️' }
])

const realtimeData = ref([30, 45, 60, 80, 95, 70, 55, 40])

let interval = null

onMounted(() => {
  interval = setInterval(() => {
    realtimeData.value = realtimeData.value.map(() => Math.floor(Math.random() * 60) + 30)
  }, 2000)
})

onUnmounted(() => {
  if (interval) clearInterval(interval)
})
</script>

<style scoped>
.bigdata-page {
  min-height: 100vh;
  background: #0f172a;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: #1e293b;
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #3b82f6;
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
}

.section-header h2 {
  margin: 0 0 20px;
  font-size: 18px;
  color: white;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 20px;
}

.stat-card {
  background: #1e293b;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  font-size: 28px;
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
  color: #94a3b8;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.panel {
  background: #1e293b;
  border-radius: 12px;
  padding: 20px;
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

.add-btn {
  padding: 8px 16px;
  background: #3b82f6;
  border: none;
  border-radius: 6px;
  color: white;
  cursor: pointer;
  font-size: 13px;
}

.pipeline-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.pipeline-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: #0f172a;
  border-radius: 8px;
}

.pipeline-item.running { border-left: 4px solid #22c55e; }
.pipeline-item.success { border-left: 4px solid #3b82f6; }
.pipeline-item.failed { border-left: 4px solid #ef4444; }

.pipe-icon {
  font-size: 20px;
}

.pipe-info {
  flex: 1;
}

.pipe-name {
  font-size: 14px;
  color: white;
  font-weight: 500;
}

.pipe-meta {
  font-size: 12px;
  color: #94a3b8;
}

.pipe-status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.pipe-status.running { background: rgba(34, 197, 94, 0.2); color: #22c55e; }
.pipe-status.success { background: rgba(59, 130, 246, 0.2); color: #3b82f6; }
.pipe-status.failed { background: rgba(239, 68, 68, 0.2); color: #ef4444; }

.monitor-chart {
  padding: 20px;
  background: #0f172a;
  border-radius: 8px;
}

.chart-title {
  font-size: 14px;
  color: #94a3b8;
  margin-bottom: 20px;
}

.chart-bars {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 150px;
}

.bar-wrapper {
  width: 20px;
  height: 100%;
  background: rgba(255,255,255,0.1);
  border-radius: 4px;
  display: flex;
  align-items: flex-end;
}

.bar {
  width: 100%;
  background: linear-gradient(180deg, #3b82f6 0%, #1d4ed8 100%);
  border-radius: 4px;
  transition: height 0.5s;
}

.chart-labels {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

.chart-labels span {
  font-size: 11px;
  color: #64748b;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.task-item {
  padding: 15px;
  background: #0f172a;
  border-radius: 8px;
}

.task-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.task-name {
  font-size: 14px;
  color: white;
}

.task-priority {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 11px;
}

.task-priority.high { background: rgba(239, 68, 68, 0.2); color: #ef4444; }
.task-priority.medium { background: rgba(251, 191, 36, 0.2); color: #f59e0b; }
.task-priority.low { background: rgba(59, 130, 246, 0.2); color: #3b82f6; }

.task-progress {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 8px;
}

.progress-bar {
  flex: 1;
  height: 6px;
  background: rgba(255,255,255,0.1);
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #3b82f6 0%, #22c55e 100%);
  border-radius: 3px;
}

.progress-text {
  font-size: 12px;
  color: #94a3b8;
  width: 40px;
}

.task-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #64748b;
}

.quality-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.quality-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px;
  background: #0f172a;
  border-radius: 8px;
}

.quality-icon {
  font-size: 20px;
}

.quality-info {
  display: flex;
  flex-direction: column;
}

.quality-name {
  font-size: 13px;
  color: #94a3b8;
}

.quality-value {
  font-size: 18px;
  font-weight: bold;
}

.quality-value.good { color: #22c55e; }
.quality-value.medium { color: #f59e0b; }

@media (max-width: 900px) {
  .stats-row {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>