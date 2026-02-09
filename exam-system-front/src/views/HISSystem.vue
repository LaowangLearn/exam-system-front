<template>
  <div class="his-container">
    <div class="his-header">
      <h1>HIS系统</h1>
      <p>医院信息管理系统</p>
      <button class="back-btn" @click="goBack">返回中心</button>
    </div>
    
    <div class="his-content">
      <div class="department-tabs">
        <button class="tab-btn active">门诊管理</button>
        <button class="tab-btn">住院管理</button>
        <button class="tab-btn">药房管理</button>
        <button class="tab-btn">医技管理</button>
        <button class="tab-btn">财务统计</button>
      </div>
      
      <div class="his-dashboard">
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                <circle cx="9" cy="7" r="4"></circle>
              </svg>
            </div>
            <div class="stat-info">
              <h3>今日门诊</h3>
              <p class="stat-value">128</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M3 7h18"></path>
                <path d="M18 7v14"></path>
                <path d="M18 3v4"></path>
                <path d="M6 7v14"></path>
              </svg>
            </div>
            <div class="stat-info">
              <h3>住院人数</h3>
              <p class="stat-value">45</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
              </svg>
            </div>
            <div class="stat-info">
              <h3>急诊人数</h3>
              <p class="stat-value">23</p>
            </div>
          </div>
          
          <div class="stat-card">
            <div class="stat-icon">
              <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="2" x2="12" y2="6"></line>
                <line x1="12" y1="18" x2="12" y2="22"></line>
                <line x1="4.93" y1="4.93" x2="7.76" y2="7.76"></line>
                <line x1="16.24" y1="16.24" x2="19.07" y2="19.07"></line>
                <line x1="2" y1="12" x2="6" y2="12"></line>
                <line x1="18" y1="12" x2="22" y2="12"></line>
                <line x1="4.93" y1="19.07" x2="7.76" y2="16.24"></line>
                <line x1="16.24" y1="7.76" x2="19.07" y2="4.93"></line>
              </svg>
            </div>
            <div class="stat-info">
              <h3>手术安排</h3>
              <p class="stat-value">12</p>
            </div>
          </div>
        </div>
        
        <div class="schedule-section">
          <h3>今日医生排班</h3>
          <div class="schedule-table">
            <div class="schedule-header">
              <span>科室</span>
              <span>医生</span>
              <span>时间段</span>
              <span>诊室</span>
              <span>状态</span>
            </div>
            <div class="schedule-row">
              <span>内科</span>
              <span>王医生</span>
              <span>08:00-12:00</span>
              <span>内科1号</span>
              <span class="status available">可预约</span>
            </div>
            <div class="schedule-row">
              <span>外科</span>
              <span>李医生</span>
              <span>09:00-13:00</span>
              <span>外科2号</span>
              <span class="status available">可预约</span>
            </div>
            <div class="schedule-row">
              <span>儿科</span>
              <span>张医生</span>
              <span>10:00-14:00</span>
              <span>儿科3号</span>
              <span class="status full">已满</span>
            </div>
            <div class="schedule-row">
              <span>妇产科</span>
              <span>刘医生</span>
              <span>14:00-18:00</span>
              <span>妇产科1号</span>
              <span class="status available">可预约</span>
            </div>
          </div>
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
</script>

<style scoped>
.his-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%);
  padding: 40px 20px;
}

.his-header {
  text-align: center;
  margin-bottom: 40px;
}

.his-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.his-header p {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 20px;
}

.back-btn {
  background: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.3s ease;
}

.back-btn:hover {
  background: #45a049;
}

.his-content {
  max-width: 1200px;
  margin: 0 auto;
}

.department-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 30px;
  flex-wrap: wrap;
}

.tab-btn {
  background: white;
  border: 2px solid #4CAF50;
  color: #4CAF50;
  padding: 12px 24px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.tab-btn:hover {
  background: #4CAF50;
  color: white;
}

.tab-btn.active {
  background: #4CAF50;
  color: white;
}

.his-dashboard {
  background: white;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: #f9f9f9;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  transition: transform 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-icon {
  color: #4CAF50;
}

.stat-info h3 {
  font-size: 1rem;
  color: #666;
  margin: 0 0 5px 0;
}

.stat-value {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.schedule-section h3 {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 20px;
}

.schedule-table {
  background: #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}

.schedule-header {
  background: #4CAF50;
  color: white;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  padding: 15px;
  font-weight: bold;
}

.schedule-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
  align-items: center;
}

.schedule-row:last-child {
  border-bottom: none;
}

.schedule-row:hover {
  background: #f0f0f0;
}

.status {
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: bold;
  text-align: center;
}

.status.available {
  background: #e8f5e8;
  color: #2e7d32;
}

.status.full {
  background: #ffebee;
  color: #c62828;
}

@media (max-width: 768px) {
  .his-header h1 {
    font-size: 2rem;
  }
  
  .department-tabs {
    justify-content: center;
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .schedule-header,
  .schedule-row {
    grid-template-columns: 1fr;
    gap: 10px;
    text-align: center;
  }
}
</style>