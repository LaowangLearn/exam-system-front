<template>
  <div class="gov-security-monitor">
    <button class="back-btn" @click="goBack">�?返回政务事业单位�?/button>
    <div class="page-header">
      <h1>实时监控</h1>
      <div class="header-stats">
        <span class="stat-item">在线设备: <strong>{{ onlineCount }}</strong></span>
        <span class="stat-item">离线设备: <strong>{{ offlineCount }}</strong></span>
        <span class="stat-item">今日告警: <strong>{{ todayAlarmCount }}</strong></span>
      </div>
    </div>
    
    <div class="monitor-grid">
      <div class="monitor-item" v-for="camera in cameras" :key="camera.id">
        <div class="monitor-header">
          <span class="camera-name">{{ camera.name }}</span>
          <span :class="['status-indicator', camera.status === 1 ? 'online' : 'offline']">
            {{ camera.status === 1 ? '在线' : '离线' }}
          </span>
        </div>
        <div class="monitor-screen">
          <div class="video-placeholder" v-if="camera.status === 1">
            <span class="video-icon">📹</span>
            <span class="video-label">实时画面</span>
          </div>
          <div class="video-offline" v-else>
            <span>设备离线</span>
          </div>
        </div>
        <div class="monitor-controls">
          <el-button size="mini" @click="handleView(camera)">全屏</el-button>
          <el-button size="mini" @click="handleRecord(camera)">录像</el-button>
        </div>
      </div>
    </div>
    
    <!-- 告警列表 -->
    <el-card title="最新告�? class="alarm-panel">
      <div class="alarm-list">
        <div class="alarm-item" v-for="alarm in recentAlarms" :key="alarm.id">
          <span :class="['alarm-level', `level-${alarm.level}`]">{{ getLevelLabel(alarm.level) }}</span>
          <span class="alarm-content">{{ alarm.content }}</span>
          <span class="alarm-time">{{ alarm.time }}</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const cameras = ref([
  { id: 1, name: '东门入口', status: 1 },
  { id: 2, name: '北门入口', status: 1 },
  { id: 3, name: '1号楼大堂', status: 1 },
  { id: 4, name: '地下车库', status: 0 },
  { id: 5, name: '小区广场', status: 1 },
  { id: 6, name: '消防通道', status: 1 }
])

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const recentAlarms = ref([
  { id: 1, level: 1, content: '东门摄像头检测到可疑人员', time: '08:30:25' },
  { id: 2, level: 2, content: '1号楼门禁连续刷卡失败', time: '09:15:10' },
  { id: 3, level: 3, content: '地下车库摄像头离�?, time: '10:00:15' }
])

const onlineCount = computed(() => cameras.value.filter(c => c.status === 1).length)
const offlineCount = computed(() => cameras.value.filter(c => c.status === 0).length)
const todayAlarmCount = computed(() => recentAlarms.value.length)

const handleView = (camera) => {
  alert(`全屏查看: ${camera.name}`)
}

const handleRecord = (camera) => {
  alert(`开始录�? ${camera.name}`)
}

const getLevelLabel = (level) => {
  const levels = { 1: '紧�?, 2: '重要', 3: '一�? }
  return levels[level] || '未知'
}
</script>

<style scoped>
.gov-security-monitor {
  padding: 24px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.page-header h1 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 0;
}

.header-stats {
  display: flex;
  gap: 24px;
}

.stat-item {
  font-size: 0.9rem;
  color: #64748b;
}

.stat-item strong {
  color: #1e293b;
}

.monitor-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 24px;
}

.monitor-item {
  background: #1e293b;
  border-radius: 12px;
  overflow: hidden;
}

.monitor-header {
  display: flex;
  justify-content: space-between;
  padding: 12px 16px;
  background: #334155;
}

.camera-name {
  color: #e2e8f0;
  font-weight: 500;
}

.status-indicator {
  font-size: 0.8rem;
  padding: 4px 12px;
  border-radius: 12px;
}

.status-indicator.online {
  background: #10b981;
  color: white;
}

.status-indicator.offline {
  background: #ef4444;
  color: white;
}

.monitor-screen {
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #0f172a;
}

.video-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: #64748b;
}

.video-icon {
  font-size: 3rem;
  margin-bottom: 12px;
}

.video-label {
  font-size: 0.9rem;
}

.video-offline {
  color: #ef4444;
  font-size: 1rem;
}

.monitor-controls {
  padding: 12px 16px;
  background: #334155;
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.alarm-panel {
  background: #f8fafc;
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
  background: white;
  border-radius: 8px;
}

.alarm-level {
  font-size: 0.75rem;
  padding: 4px 10px;
  border-radius: 8px;
  font-weight: 500;
}

.alarm-level.level-1 {
  background: #fee2e2;
  color: #dc2626;
}

.alarm-level.level-2 {
  background: #fef3c7;
  color: #d97706;
}

.alarm-level.level-3 {
  background: #dbeafe;
  color: #2563eb;
}

.alarm-content {
  flex: 1;
  color: #334155;
}

.alarm-time {
  color: #94a3b8;
  font-size: 0.85rem;
}
</style>
