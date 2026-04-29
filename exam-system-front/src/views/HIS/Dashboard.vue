<template>
  <div class="dashboard-container">
    <div class="page-header">
      <button class="back-btn" @click="goBack">返回</button>
      <h1>数据统计</h1>
    </div>
    
    <div class="summary-cards">
      <div class="summary-card">
        <div class="card-icon visits-icon">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
          </svg>
        </div>
        <div class="card-content">
          <span class="card-value">{{ stats.todayVisits }}</span>
          <span class="card-label">今日就诊</span>
        </div>
      </div>
      
      <div class="summary-card">
        <div class="card-icon income-icon">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="1" x2="12" y2="23"></line>
            <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
          </svg>
        </div>
        <div class="card-content">
          <span class="card-value">¥{{ stats.todayIncome }}</span>
          <span class="card-label">今日收入</span>
        </div>
      </div>
      
      <div class="summary-card">
        <div class="card-icon patients-icon">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="card-content">
          <span class="card-value">{{ stats.totalPatients }}</span>
          <span class="card-label">累计患者</span>
        </div>
      </div>
      
      <div class="summary-card">
        <div class="card-icon beds-icon">
          <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M3 21v-8a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v8"></path>
            <path d="M21 10V7a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v3"></path>
          </svg>
        </div>
        <div class="card-content">
          <span class="card-value">{{ stats.inpatientCount }}/{{ stats.totalBeds }}</span>
          <span class="card-label">住院人数</span>
        </div>
      </div>
    </div>
    
    <div class="charts-row">
      <div class="chart-card">
        <h3>本周就诊趋势</h3>
        <div class="bar-chart">
          <div v-for="(item, index) in weeklyData" :key="index" class="bar-item">
            <div class="bar" :style="{ height: item.percent + '%' }">
              <span class="bar-value">{{ item.value }}</span>
            </div>
            <span class="bar-label">{{ item.day }}</span>
          </div>
        </div>
      </div>
      
      <div class="chart-card">
        <h3>科室就诊分布</h3>
        <div class="pie-chart">
          <div class="pie">
            <div class="pie-slice" style="--percent: 35%; --color: #667eea;"></div>
            <div class="pie-slice" style="--percent: 25%; --color: #f093fb;"></div>
            <div class="pie-slice" style="--percent: 20%; --color: #4facfe;"></div>
            <div class="pie-slice" style="--percent: 15%; --color: #43e97b;"></div>
            <div class="pie-center">
              <span class="pie-total">{{ stats.todayVisits }}</span>
              <span class="pie-label">今日就诊</span>
            </div>
          </div>
          <div class="pie-legend">
            <div class="legend-item"><span class="legend-color" style="background: #667eea;"></span><span>内科</span><span>35%</span></div>
            <div class="legend-item"><span class="legend-color" style="background: #f093fb;"></span><span>外科</span><span>25%</span></div>
            <div class="legend-item"><span class="legend-color" style="background: #4facfe;"></span><span>儿科</span><span>20%</span></div>
            <div class="legend-item"><span class="legend-color" style="background: #43e97b;"></span><span>妇产科</span><span>15%</span></div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="bottom-cards">
      <div class="detail-card">
        <h3>今日挂号明细</h3>
        <div class="detail-list">
          <div class="detail-row" v-for="item in registrationDetail" :key="item.department">
            <span class="detail-name">{{ item.department }}</span>
            <span class="detail-value">{{ item.count }}人</span>
            <span class="detail-percent">{{ item.percent }}%</span>
          </div>
        </div>
      </div>
      
      <div class="detail-card">
        <h3>收入构成</h3>
        <div class="detail-list">
          <div class="detail-row" v-for="item in incomeDetail" :key="item.type">
            <span class="detail-name">{{ item.type }}</span>
            <span class="detail-value">¥{{ item.amount }}</span>
            <span class="detail-percent">{{ item.percent }}%</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const stats = reactive({
  todayVisits: 128,
  todayIncome: 12580.50,
  totalPatients: 8562,
  inpatientCount: 45,
  totalBeds: 100
})

const weeklyData = reactive([
  { day: '周一', value: 95, percent: 74 },
  { day: '周二', value: 110, percent: 85 },
  { day: '周三', value: 85, percent: 66 },
  { day: '周四', value: 120, percent: 93 },
  { day: '周五', value: 128, percent: 100 },
  { day: '周六', value: 75, percent: 58 },
  { day: '周日', value: 68, percent: 53 }
])

const registrationDetail = reactive([
  { department: '内科', count: 45, percent: 35 },
  { department: '外科', count: 32, percent: 25 },
  { department: '儿科', count: 26, percent: 20 },
  { department: '妇产科', count: 20, percent: 16 },
  { department: '急诊科', count: 5, percent: 4 }
])

const incomeDetail = reactive([
  { type: '挂号费', amount: 1920, percent: 15 },
  { type: '药费', amount: 5430, percent: 43 },
  { type: '检查费', amount: 3145, percent: 25 },
  { type: '治疗费', amount: 2085.5, percent: 17 }
])

const goBack = () => {
  router.push('/his')
}

onMounted(() => {})
</script>

<style scoped>
.dashboard-container {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
}

.back-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
}

.page-header h1 {
  font-size: 1.8rem;
  color: #333;
}

.summary-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.summary-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.card-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.visits-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.income-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  color: white;
}

.patients-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: white;
}

.beds-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  color: white;
}

.card-value {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  display: block;
}

.card-label {
  color: #666;
  font-size: 0.9rem;
}

.charts-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.chart-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.chart-card h3 {
  margin: 0 0 20px 0;
  color: #333;
}

.bar-chart {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 200px;
  padding-top: 20px;
}

.bar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
  flex: 1;
}

.bar {
  width: 30px;
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px 8px 0 0;
  position: relative;
  min-height: 20px;
}

.bar-value {
  position: absolute;
  top: -25px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 0.8rem;
  font-weight: bold;
}

.bar-label {
  font-size: 0.8rem;
  color: #666;
}

.pie-chart {
  display: flex;
  align-items: center;
  gap: 30px;
}

.pie {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background: conic-gradient(
    #667eea 0deg 126deg,
    #f093fb 126deg 216deg,
    #4facfe 216deg 288deg,
    #43e97b 288deg 360deg
  );
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.pie-center {
  width: 80px;
  height: 80px;
  background: white;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.pie-total {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.pie-label {
  font-size: 0.7rem;
  color: #666;
}

.pie-legend {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.legend-color {
  width: 12px;
  height: 12px;
  border-radius: 3px;
}

.bottom-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.detail-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.detail-card h3 {
  margin: 0 0 15px 0;
  color: #333;
}

.detail-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.detail-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.detail-row:last-child {
  border-bottom: none;
}

.detail-name {
  color: #333;
}

.detail-value {
  font-weight: bold;
  color: #667eea;
}

.detail-percent {
  color: #999;
  font-size: 0.8rem;
}
</style>
