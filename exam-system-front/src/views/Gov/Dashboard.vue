<template>
  <div class="gov-dashboard">
    <button class="back-btn" @click="goBack">返回政务事业单位类</button>
    <div class="dashboard-header">
      <h1>政务一体化平台</h1>
      <p>统一政务服务与管理中心</p>
    </div>
    
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon grid-icon">📊</div>
        <div class="stat-info">
          <div class="stat-value">128</div>
          <div class="stat-label">网格事件</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon petition-icon">📝</div>
        <div class="stat-info">
          <div class="stat-value">86</div>
          <div class="stat-label">信访工单</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon security-icon">🔒</div>
        <div class="stat-info">
          <div class="stat-value">256</div>
          <div class="stat-label">安防设备</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon document-icon">📄</div>
        <div class="stat-info">
          <div class="stat-value">156</div>
          <div class="stat-label">公文数量</div>
        </div>
      </div>
    </div>
    
    <div class="quick-nav">
      <h2>快速导航</h2>
      <div class="nav-grid">
        <div class="nav-card" @click="$router.push('/gov/grid/cell')">
          <div class="nav-icon">🧩</div>
          <div class="nav-title">网格化治理</div>
          <div class="nav-desc">网格单元管理</div>
        </div>
        <div class="nav-card" @click="$router.push('/gov/petition/register')">
          <div class="nav-icon">📋</div>
          <div class="nav-title">综治信访</div>
          <div class="nav-desc">信访登记办理</div>
        </div>
        <div class="nav-card" @click="$router.push('/gov/security/monitor')">
          <div class="nav-icon">🎥</div>
          <div class="nav-title">智慧安防</div>
          <div class="nav-desc">视频监控告警</div>
        </div>
        <div class="nav-card" @click="$router.push('/gov/document/list')">
          <div class="nav-icon">📑</div>
          <div class="nav-title">公文机要</div>
          <div class="nav-desc">公文审批管理</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}
</script>

<style scoped>
.gov-dashboard {
  padding: 24px;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
}

.dashboard-header {
  margin-bottom: 32px;
}

.dashboard-header h1 {
  font-size: 2rem;
  color: #1a365d;
  margin: 0 0 8px 0;
}

.dashboard-header p {
  color: #64748b;
  margin: 0;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 32px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.grid-icon {
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
}

.petition-icon {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
}

.security-icon {
  background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%);
}

.document-icon {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 4px 0;
}

.stat-label {
  font-size: 0.875rem;
  color: #64748b;
  margin: 0;
}

.quick-nav {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.quick-nav h2 {
  font-size: 1.25rem;
  color: #1e293b;
  margin: 0 0 20px 0;
}

.nav-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

.nav-card {
  background: #f8fafc;
  border-radius: 10px;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}

.nav-card:hover {
  background: #eff6ff;
  border-color: #3b82f6;
  transform: translateY(-2px);
}

.nav-icon {
  font-size: 2rem;
  margin-bottom: 12px;
}

.nav-title {
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 4px;
}

.nav-desc {
  font-size: 0.8rem;
  color: #64748b;
}
</style>
