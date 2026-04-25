<template>
  <div class="oa-dashboard">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="dashboard-header">
      <h1>OA系统仪表盘</h1>
      <p>欢迎回来，{{ username }}</p>
    </div>

    <div class="dashboard-stats">
      <div class="stat-card">
        <div class="stat-icon">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ userCount }}</h3>
          <p>总用户数</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 6h18"></path>
            <path d="M3 12h18"></path>
            <path d="M3 18h18"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ departmentCount }}</h3>
          <p>总部门数</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="16" y1="2" x2="16" y2="6"></line>
            <line x1="8" y1="2" x2="8" y2="6"></line>
            <line x1="3" y1="10" x2="21" y2="10"></line>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ documentCount }}</h3>
          <p>总公文数</p>
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-icon">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ approvalCount }}</h3>
          <p>待审批数</p>
        </div>
      </div>
    </div>

    <div class="dashboard-sections">
      <div class="section">
        <h2>最近公文</h2>
        <div class="document-list">
          <div v-for="doc in recentDocuments" :key="doc.id" class="document-item">
            <h4>{{ doc.title }}</h4>
            <p>{{ doc.createTime }}</p>
            <span class="status" :class="doc.status">{{ doc.status }}</span>
          </div>
        </div>
      </div>

      <div class="section">
        <h2>今日日程</h2>
        <div class="schedule-list">
          <div v-for="schedule in todaySchedules" :key="schedule.id" class="schedule-item">
            <h4>{{ schedule.title }}</h4>
            <p>{{ schedule.startTime }} - {{ schedule.endTime }}</p>
            <span class="location">{{ schedule.location }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const username = localStorage.getItem('username') || '用户'

// 统计数据
const userCount = ref(0)
const departmentCount = ref(0)
const documentCount = ref(0)
const approvalCount = ref(0)

// 最近公文
const recentDocuments = ref([
  { id: 1, title: '关于2026年工作计划的通知', status: 'APPROVED', createTime: '2026-02-09 10:00' },
  { id: 2, title: '员工福利政策调整方案', status: 'PENDING', createTime: '2026-02-08 15:30' },
  { id: 3, title: '技术部门人员调整通知', status: 'DRAFT', createTime: '2026-02-07 09:15' }
])

// 今日日程
const todaySchedules = ref([
  { id: 1, title: '周例会', startTime: '09:00', endTime: '10:00', location: '会议室A' },
  { id: 2, title: '项目评审会', startTime: '14:00', endTime: '16:00', location: '会议室B' }
])

const goBack = () => {
  router.push('/enterprise')
}

onMounted(() => {
  // 模拟数据加载
  userCount.value = 50
  departmentCount.value = 8
  documentCount.value = 120
  approvalCount.value = 5
})
</script>

<style scoped>
.oa-dashboard {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.dashboard-header {
  margin-bottom: 30px;
}

.dashboard-header h1 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 10px;
}

.dashboard-header p {
  font-size: 1rem;
  color: #666;
}

.dashboard-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 50px;
  height: 50px;
  background: #667eea;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 20px;
}

.stat-content h3 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

.stat-content p {
  font-size: 0.9rem;
  color: #666;
  margin: 5px 0 0 0;
}

.dashboard-sections {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 30px;
}

.section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.section h2 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.document-list, .schedule-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.document-item, .schedule-item {
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.document-item h4, .schedule-item h4 {
  font-size: 1rem;
  color: #333;
  margin: 0 0 5px 0;
}

.document-item p, .schedule-item p {
  font-size: 0.8rem;
  color: #666;
  margin: 0 0 5px 0;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.APPROVED {
  background: #d4edda;
  color: #155724;
}

.status.PENDING {
  background: #fff3cd;
  color: #856404;
}

.status.DRAFT {
  background: #e2e3e5;
  color: #383d41;
}

.location {
  font-size: 0.8rem;
  color: #667eea;
}

@media (max-width: 768px) {
  .dashboard-stats {
    grid-template-columns: 1fr;
  }

  .dashboard-sections {
    grid-template-columns: 1fr;
  }
}
</style>