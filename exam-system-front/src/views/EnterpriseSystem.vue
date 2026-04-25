<template>
  <div class="enterprise-system-container">
    <button class="back-btn" @click="goBack">返回中心</button>
    <div class="system-header">
      <h1>企业管理系统</h1>
      <p>2026新版统一管理平台 - 通用办公类子系统集成</p>
    </div>

    <div class="system-grid">
      <div class="system-card oa-system" @click="navigateTo('/oa')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="3" y1="9" x2="21" y2="9"></line>
            <line x1="9" y1="21" x2="9" y2="9"></line>
          </svg>
        </div>
        <h3>OA协同办公系统</h3>
        <p>公文管理、审批流程、日程安排、部门管理、用户权限</p>
        <div class="system-tags">
          <span class="tag">审批流</span>
          <span class="tag">日程</span>
          <span class="tag">公文</span>
        </div>
      </div>

      <div class="system-card hrm-system" @click="navigateTo('/hrm')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>HRM人事管理系统</h3>
        <p>员工信息、招聘培训、绩效评估、薪酬管理、考勤打卡</p>
        <div class="system-tags">
          <span class="tag">招聘</span>
          <span class="tag">绩效</span>
          <span class="tag">薪酬</span>
        </div>
      </div>

      <div class="system-card finance-system" @click="navigateTo('/finance')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="1" x2="12" y2="23"></line>
            <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
          </svg>
        </div>
        <h3>财务管控系统</h3>
        <p>预算管理、费用报销、凭证管理、银行账户、税务申报</p>
        <div class="system-tags">
          <span class="tag">预算</span>
          <span class="tag">报销</span>
          <span class="tag">凭证</span>
        </div>
      </div>

      <div class="system-card archive-system" @click="navigateTo('/archive')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>电子档案系统</h3>
        <p>档案分类、档案存储、档案借阅、操作日志、安全管控</p>
        <div class="system-tags">
          <span class="tag">分类</span>
          <span class="tag">借阅</span>
          <span class="tag">日志</span>
        </div>
      </div>

      <div class="system-card message-system" @click="navigateTo('/message')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>企业沟通系统</h3>
        <p>内部消息、公告通知、讨论组、群聊沟通、实时协作</p>
        <div class="system-tags">
          <span class="tag">消息</span>
          <span class="tag">公告</span>
          <span class="tag">群聊</span>
        </div>
      </div>

      <div class="system-card asset-system" @click="navigateTo('/asset')">
        <div class="system-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
            <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
          </svg>
        </div>
        <h3>固定资产系统</h3>
        <p>资产分类、资产领用、维修管理、报废处理、折旧计算</p>
        <div class="system-tags">
          <span class="tag">资产</span>
          <span class="tag">领用</span>
          <span class="tag">维修</span>
        </div>
      </div>
    </div>

    <div class="system-features">
      <h2>系统特性</h2>
      <div class="features-grid">
        <div class="feature-item">
          <h4>私有化部署</h4>
          <p>支持本地部署，数据完全自主控制</p>
        </div>
        <div class="feature-item">
          <h4>国产化信创适配</h4>
          <p>兼容国产操作系统和数据库</p>
        </div>
        <div class="feature-item">
          <h4>AI轻量化赋能</h4>
          <p>智能助手辅助办公，提升效率</p>
        </div>
        <div class="feature-item">
          <h4>微服务架构</h4>
          <p>模块解耦，弹性扩展，按需集成</p>
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

const navigateTo = (path: string) => {
  router.push(path)
}
</script>

<style scoped>
.enterprise-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 40px 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 12px 30px;
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

.system-header {
  text-align: center;
  margin-bottom: 50px;
}

.system-header h1 {
  font-size: 3rem;
  color: #fff;
  margin-bottom: 15px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.system-header p {
  font-size: 1.3rem;
  color: #94a3b8;
  margin-bottom: 25px;
}

.system-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 30px;
  max-width: 1400px;
  margin: 0 auto 50px;
}

.system-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  padding: 35px;
  text-align: center;
  cursor: pointer;
  transition: all 0.4s ease;
  position: relative;
  overflow: hidden;
}

.system-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(90deg, transparent, var(--accent-color), transparent);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.system-card:hover::before {
  opacity: 1;
}

.system-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.system-card h3 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 20px 0 15px;
}

.system-card p {
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 20px;
}

.system-tags {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.tag {
  background: #f1f5f9;
  color: #475569;
  padding: 6px 14px;
  border-radius: 15px;
  font-size: 0.85rem;
  font-weight: 500;
}

.oa-system { --accent-color: #667eea; }
.oa-system:hover { border-bottom: 4px solid #667eea; }

.hrm-system { --accent-color: #f093fb; }
.hrm-system:hover { border-bottom: 4px solid #f093fb; }

.finance-system { --accent-color: #4ade80; }
.finance-system:hover { border-bottom: 4px solid #4ade80; }

.archive-system { --accent-color: #fbbf24; }
.archive-system:hover { border-bottom: 4px solid #fbbf24; }

.message-system { --accent-color: #38bdf8; }
.message-system:hover { border-bottom: 4px solid #38bdf8; }

.asset-system { --accent-color: #fb7185; }
.asset-system:hover { border-bottom: 4px solid #fb7185; }

.system-features {
  max-width: 1400px;
  margin: 0 auto;
  padding: 40px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  backdrop-filter: blur(10px);
}

.system-features h2 {
  text-align: center;
  color: #fff;
  font-size: 2rem;
  margin-bottom: 40px;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
}

.feature-item {
  background: rgba(255, 255, 255, 0.1);
  padding: 25px;
  border-radius: 15px;
  text-align: center;
}

.feature-item h4 {
  color: #667eea;
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.feature-item p {
  color: #94a3b8;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .system-header h1 {
    font-size: 2rem;
  }

  .system-grid {
    grid-template-columns: 1fr;
  }

  .system-card {
    padding: 25px;
  }
}
</style>
