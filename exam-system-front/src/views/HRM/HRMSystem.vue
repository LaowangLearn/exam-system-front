<template>
  <div class="hrm-system-container">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="system-header">
      <h1>HRM人事管理系统</h1>
      <p>人力资源管理平台</p>
    </div>

    <div class="module-grid">
      <div class="module-card" @click="activeModule = 'employees'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
          </svg>
        </div>
        <h3>员工信息</h3>
        <p>员工档案、合同管理</p>
      </div>

      <div class="module-card" @click="activeModule = 'recruitment'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
            <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
          </svg>
        </div>
        <h3>招聘管理</h3>
        <p>职位申请、面试流程</p>
      </div>

      <div class="module-card" @click="activeModule = 'training'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
            <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
          </svg>
        </div>
        <h3>培训管理</h3>
        <p>培训计划、参与记录</p>
      </div>

      <div class="module-card" @click="activeModule = 'performance'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"></polyline>
          </svg>
        </div>
        <h3>绩效评估</h3>
        <p>考核评分、改进建议</p>
      </div>

      <div class="module-card" @click="activeModule = 'salary'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="1" x2="12" y2="23"></line>
            <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
          </svg>
        </div>
        <h3>薪酬管理</h3>
        <p>工资发放、奖金管理</p>
      </div>

      <div class="module-card" @click="activeModule = 'attendance'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
        </div>
        <h3>考勤管理</h3>
        <p>打卡记录、请假管理</p>
      </div>
    </div>

    <div class="module-content" v-if="activeModule">
      <h2>{{ getModuleTitle() }}</h2>
      <p>API接口: /hrm/{{ activeModule }}</p>
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
    employees: '员工信息管理',
    recruitment: '招聘申请管理',
    training: '培训计划管理',
    performance: '绩效评估管理',
    salary: '薪酬发放管理',
    attendance: '考勤记录管理'
  }
  return titles[activeModule.value] || ''
}
</script>

<style scoped>
.hrm-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
  color: #f5576c;
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
  color: #f5576c;
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
