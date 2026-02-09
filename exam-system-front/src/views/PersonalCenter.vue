<template>
  <div class="personal-container">
    <div class="personal-header">
      <h1>个人中心</h1>
      <p>个人信息管理中心</p>
      <button class="back-btn" @click="goBack">返回中心</button>
    </div>
    
    <div class="personal-content">
      <div class="profile-section">
        <div class="profile-card">
          <div class="avatar">
            <svg width="100" height="100" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
              <circle cx="12" cy="7" r="4"></circle>
            </svg>
          </div>
          <h2>用户名称</h2>
          <p class="user-role">系统用户</p>
        </div>
      </div>
      
      <div class="info-grid">
        <div class="info-card">
          <h3>基本信息</h3>
          <div class="info-item">
            <label>姓名</label>
            <span>张三</span>
          </div>
          <div class="info-item">
            <label>邮箱</label>
            <span>zhangsan@example.com</span>
          </div>
          <div class="info-item">
            <label>电话</label>
            <span>13800138000</span>
          </div>
          <div class="info-item">
            <label>部门</label>
            <span>技术部</span>
          </div>
        </div>
        
        <div class="info-card">
          <h3>账户设置</h3>
          <div class="info-item">
            <label>用户名</label>
            <span>admin</span>
          </div>
          <div class="info-item">
            <label>注册时间</label>
            <span>2026-01-01</span>
          </div>
          <div class="info-item">
            <label>最后登录</label>
            <span>2026-02-09</span>
          </div>
          <button class="action-btn">修改密码</button>
        </div>
        
        <div class="info-card">
          <h3>系统通知</h3>
          <div class="notification-item">
            <span class="notification-date">2026-02-08</span>
            <p>系统将于2月10日进行维护升级</p>
          </div>
          <div class="notification-item">
            <span class="notification-date">2026-02-05</span>
            <p>您有新的审批待处理</p>
          </div>
          <div class="notification-item">
            <span class="notification-date">2026-02-01</span>
            <p>新年系统使用指南已发布</p>
          </div>
        </div>
        
        <div class="info-card">
          <h3>最近活动</h3>
          <div class="activity-item">
            <span class="activity-date">2026-02-09</span>
            <p>登录系统</p>
          </div>
          <div class="activity-item">
            <span class="activity-date">2026-02-08</span>
            <p>修改个人信息</p>
          </div>
          <div class="activity-item">
            <span class="activity-date">2026-02-07</span>
            <p>提交审批申请</p>
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
.personal-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  padding: 40px 20px;
}

.personal-header {
  text-align: center;
  margin-bottom: 40px;
  color: white;
}

.personal-header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.personal-header p {
  font-size: 1.2rem;
  opacity: 0.9;
  margin-bottom: 20px;
}

.back-btn {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.back-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

.personal-content {
  max-width: 1200px;
  margin: 0 auto;
}

.profile-section {
  display: flex;
  justify-content: center;
  margin-bottom: 40px;
}

.profile-card {
  background: white;
  border-radius: 16px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  min-width: 300px;
}

.avatar {
  margin-bottom: 20px;
  color: #f5576c;
}

.profile-card h2 {
  font-size: 1.8rem;
  color: #333;
  margin-bottom: 5px;
}

.user-role {
  color: #666;
  font-size: 1rem;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 30px;
}

.info-card {
  background: white;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.info-card h3 {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 2px solid #f5f5f5;
}

.info-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.info-item label {
  color: #666;
  font-weight: 500;
}

.info-item span {
  color: #333;
}

.action-btn {
  background: #f5576c;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  margin-top: 20px;
  transition: background 0.3s ease;
}

.action-btn:hover {
  background: #e0455a;
}

.notification-item,
.activity-item {
  margin-bottom: 15px;
  padding: 10px;
  background: #f9f9f9;
  border-radius: 8px;
}

.notification-date,
.activity-date {
  display: block;
  font-size: 0.8rem;
  color: #999;
  margin-bottom: 5px;
}

.notification-item p,
.activity-item p {
  color: #333;
  margin: 0;
}

@media (max-width: 768px) {
  .personal-header h1 {
    font-size: 2rem;
  }
  
  .info-grid {
    grid-template-columns: 1fr;
  }
  
  .profile-card {
    padding: 30px 20px;
  }
}
</style>