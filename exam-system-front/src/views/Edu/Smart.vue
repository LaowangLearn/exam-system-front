<template>
  <div class="smart-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>智慧校园系统</h1>
    </div>
    
    <div class="page-content">
      <div class="dashboard-section">
        <div class="section-header">
          <h2>实时监控</h2>
          <div class="live-badge">
            <span class="live-dot"></span>
            实时
          </div>
        </div>
        
        <div class="monitor-grid">
          <div class="monitor-card large">
            <div class="card-header">
              <span class="camera-icon">📹</span>
              <span>主入口监控</span>
            </div>
            <div class="camera-feed">
              <div class="feed-placeholder">
                <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
                  <circle cx="12" cy="13" r="4"></circle>
                </svg>
                <span>实时画面</span>
              </div>
              <div class="feed-info">
                <span>设备在线</span>
                <span>HD 1080P</span>
              </div>
            </div>
          </div>
          
          <div class="monitor-card">
            <div class="card-header">
              <span class="camera-icon">🏢</span>
              <span>教学楼A栋</span>
            </div>
            <div class="camera-feed small">
              <div class="feed-placeholder">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
                  <circle cx="12" cy="13" r="4"></circle>
                </svg>
              </div>
              <div class="feed-info">在线</div>
            </div>
          </div>
          
          <div class="monitor-card">
            <div class="card-header">
              <span class="camera-icon">🎾</span>
              <span>运动场</span>
            </div>
            <div class="camera-feed small">
              <div class="feed-placeholder">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path>
                  <circle cx="12" cy="13" r="4"></circle>
                </svg>
              </div>
              <div class="feed-info">在线</div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-icon">🔒</div>
          <div class="stat-info">
            <div class="stat-value">{{ securityStats.alerts }}</div>
            <div class="stat-label">今日告警</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">📱</div>
          <div class="stat-info">
            <div class="stat-value">{{ securityStats.devices }}</div>
            <div class="stat-label">设备总数</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">✅</div>
          <div class="stat-info">
            <div class="stat-value">{{ securityStats.onlineRate }}%</div>
            <div class="stat-label">在线率</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon">⚡</div>
          <div class="stat-info">
            <div class="stat-value">{{ energyStats.todayUsage }}</div>
            <div class="stat-label">今日能耗(kWh)</div>
          </div>
        </div>
      </div>
      
      <div class="content-grid">
        <div class="panel">
          <div class="panel-header">
            <h3>设备管理</h3>
            <select v-model="deviceFilter" class="filter-select">
              <option value="all">全部设备</option>
              <option value="camera">摄像头</option>
              <option value="access">门禁</option>
              <option value="sensor">传感器</option>
            </select>
          </div>
          <div class="device-list">
            <div v-for="device in filteredDevices" :key="device.id" class="device-item">
              <div class="device-icon" :class="device.type">
                {{ getDeviceIcon(device.type) }}
              </div>
              <div class="device-info">
                <div class="device-name">{{ device.name }}</div>
                <div class="device-location">{{ device.location }}</div>
              </div>
              <div :class="['device-status', device.status]">
                {{ device.status === 'online' ? '在线' : '离线' }}
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel">
          <div class="panel-header">
            <h3>告警记录</h3>
          </div>
          <div class="alarm-list">
            <div v-for="alarm in alarms" :key="alarm.id" :class="['alarm-item', alarm.level]">
              <div class="alarm-icon">{{ getAlarmIcon(alarm.level) }}</div>
              <div class="alarm-info">
                <div class="alarm-title">{{ alarm.title }}</div>
                <div class="alarm-time">{{ alarm.time }}</div>
              </div>
              <button class="alarm-action">处理</button>
            </div>
          </div>
        </div>
        
        <div class="panel full-width">
          <div class="panel-header">
            <h3>能耗监控</h3>
            <div class="time-range">
              <button :class="['range-btn', { active: timeRange === 'day' }]" @click="timeRange = 'day'">今日</button>
              <button :class="['range-btn', { active: timeRange === 'week' }]" @click="timeRange = 'week'">本周</button>
              <button :class="['range-btn', { active: timeRange === 'month' }]" @click="timeRange = 'month'">本月</button>
            </div>
          </div>
          <div class="chart-container">
            <div class="chart-bars">
              <div v-for="(item, index) in energyChartData" :key="index" class="bar-item">
                <div class="bar-wrapper">
                  <div class="bar" :style="{ height: item.percent + '%' }"></div>
                </div>
                <div class="bar-label">{{ item.label }}</div>
              </div>
            </div>
          </div>
          <div class="energy-summary">
            <div class="summary-item">
              <span class="summary-label">峰值功率</span>
              <span class="summary-value">234.5 kW</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">平均功率</span>
              <span class="summary-value">156.2 kW</span>
            </div>
            <div class="summary-item">
              <span class="summary-label">同比变化</span>
              <span class="summary-value positive">-12.5%</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/education')
}

const deviceFilter = ref('all')
const timeRange = ref('day')

const securityStats = ref({
  alerts: 3,
  devices: 56,
  onlineRate: 98
})

const energyStats = ref({
  todayUsage: 1256.8
})

const devices = ref([
  { id: 1, name: '主入口摄像头', location: '南门入口', type: 'camera', status: 'online' },
  { id: 2, name: '教学楼A栋门禁', location: '教学楼A栋', type: 'access', status: 'online' },
  { id: 3, name: '图书馆传感器', location: '图书馆', type: 'sensor', status: 'online' },
  { id: 4, name: '体育馆摄像头', location: '体育馆', type: 'camera', status: 'offline' },
  { id: 5, name: '宿舍区门禁', location: '学生宿舍1号楼', type: 'access', status: 'online' },
  { id: 6, name: '食堂传感器', location: '第一食堂', type: 'sensor', status: 'online' }
])

const filteredDevices = computed(() => {
  if (deviceFilter.value === 'all') return devices.value
  return devices.value.filter(d => d.type === deviceFilter.value)
})

const alarms = ref([
  { id: 1, title: '门禁异常开启', time: '10:35', level: 'warning' },
  { id: 2, title: '烟雾传感器触发', time: '09:20', level: 'danger' },
  { id: 3, title: '设备离线告警', time: '08:45', level: 'info' }
])

const energyChartData = ref([
  { label: '00:00', percent: 30 },
  { label: '04:00', percent: 20 },
  { label: '08:00', percent: 65 },
  { label: '12:00', percent: 85 },
  { label: '16:00', percent: 70 },
  { label: '20:00', percent: 55 },
  { label: '24:00', percent: 35 }
])

const getDeviceIcon = (type) => {
  const icons = { camera: '📹', access: '🚪', sensor: '📡' }
  return icons[type] || '📱'
}

const getAlarmIcon = (level) => {
  const icons = { danger: '🚨', warning: '⚠️', info: 'ℹ️' }
  return icons[level] || '📢'
}
</script>

<style scoped>
.smart-page {
  min-height: 100vh;
  background: #f5f7fa;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #f093fb;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.page-content {
  padding: 20px 30px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-header h2 {
  margin: 0;
  font-size: 18px;
  color: #333;
}

.live-badge {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 14px;
  background: #e8f5e9;
  border-radius: 20px;
  font-size: 13px;
  color: #2e7d32;
}

.live-dot {
  width: 8px;
  height: 8px;
  background: #4caf50;
  border-radius: 50%;
  animation: pulse 1.5s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.monitor-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
}

.monitor-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
}

.monitor-card.large {
  grid-column: span 1;
}

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px 20px;
  background: #f8f9fa;
}

.camera-icon {
  font-size: 20px;
}

.camera-feed {
  padding: 20px;
  background: #1a1a1a;
  min-height: 250px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.camera-feed.small {
  min-height: 150px;
}

.feed-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  color: rgba(255,255,255,0.5);
}

.feed-placeholder svg {
  opacity: 0.5;
}

.feed-info {
  display: flex;
  gap: 20px;
  margin-top: 15px;
  color: rgba(255,255,255,0.6);
  font-size: 12px;
}

.stats-section {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
  margin-bottom: 20px;
}

.stat-card {
  background: white;
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
  color: #333;
}

.stat-label {
  font-size: 12px;
  color: #999;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.panel {
  background: white;
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
  color: #333;
}

.filter-select {
  padding: 8px 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 13px;
}

.device-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.device-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: #f8f9fa;
  border-radius: 8px;
}

.device-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.device-icon.camera { background: #e3f2fd; }
.device-icon.access { background: #e8f5e9; }
.device-icon.sensor { background: #fff3e0; }

.device-info {
  flex: 1;
}

.device-name {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.device-location {
  font-size: 12px;
  color: #999;
}

.device-status {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.device-status.online {
  background: #e8f5e9;
  color: #2e7d32;
}

.device-status.offline {
  background: #fce4ec;
  color: #c2185b;
}

.alarm-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.alarm-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 8px;
}

.alarm-item.danger { background: #fff5f5; }
.alarm-item.warning { background: #fffaf0; }
.alarm-item.info { background: #f0fff4; }

.alarm-icon {
  font-size: 20px;
}

.alarm-info {
  flex: 1;
}

.alarm-title {
  font-size: 14px;
  font-weight: 500;
}

.alarm-time {
  font-size: 12px;
  color: #999;
}

.alarm-action {
  padding: 6px 14px;
  background: #667eea;
  border: none;
  border-radius: 6px;
  color: white;
  font-size: 12px;
  cursor: pointer;
}

.time-range {
  display: flex;
  gap: 8px;
}

.range-btn {
  padding: 6px 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 12px;
}

.range-btn.active {
  background: #667eea;
  border-color: #667eea;
  color: white;
}

.chart-container {
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
  margin-bottom: 20px;
}

.chart-bars {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 150px;
}

.bar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.bar-wrapper {
  width: 30px;
  height: 120px;
  background: #eee;
  border-radius: 4px;
  display: flex;
  align-items: flex-end;
}

.bar {
  width: 100%;
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  border-radius: 4px;
  transition: height 0.5s;
}

.bar-label {
  font-size: 11px;
  color: #999;
}

.energy-summary {
  display: flex;
  justify-content: space-around;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.summary-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.summary-label {
  font-size: 12px;
  color: #999;
}

.summary-value {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.summary-value.positive {
  color: #4caf50;
}

@media (max-width: 900px) {
  .monitor-grid {
    grid-template-columns: 1fr;
  }
  
  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .content-grid {
    grid-template-columns: 1fr;
  }
  
  .panel.full-width {
    grid-column: span 1;
  }
}
</style>