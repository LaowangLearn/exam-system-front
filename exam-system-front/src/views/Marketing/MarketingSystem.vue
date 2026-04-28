<template>
  <div class="marketing-container">
    <button class="back-btn" @click="goBack">返回中心</button>
    <div class="system-header">
      <h1>营销客户类</h1>
      <p>全渠道客户营销与管理平台</p>
    </div>

    <div class="system-grid">
      <div class="system-card crm-card" @click="navigateTo('/crm')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h3>CRM客户管理</h3>
        <p>客户信息管理、商机追踪、销售漏斗、客户分析</p>
        <div class="card-tags">
          <span class="tag">客户</span>
          <span class="tag">商机</span>
          <span class="tag">销售</span>
        </div>
      </div>

      <div class="system-card campaign-card" @click="navigateTo('/marketing/campaign')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14.017 21v-7.391c0-5.704 3.731-9.57 8.983-10.609l.995 2.151c-2.432.917-3.995 3.638-3.995 5.849h4v10h-9.983zm-14.017 0v-7.391c0-5.704 3.748-9.57 9-10.609l.996 2.151c-2.433.917-3.996 3.638-3.996 5.849h3.983v10h-9.983z"></path>
          </svg>
        </div>
        <h3>营销活动管理</h3>
        <p>活动策划、优惠券发放、渠道投放、效果追踪</p>
        <div class="card-tags">
          <span class="tag">活动</span>
          <span class="tag">优惠券</span>
          <span class="tag">投放</span>
        </div>
      </div>

      <div class="system-card channel-card" @click="navigateTo('/marketing/channel')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="3"></circle>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
          </svg>
        </div>
        <h3>渠道管理</h3>
        <p>多渠道整合、渠道绩效、合作伙伴管理、佣金结算</p>
        <div class="card-tags">
          <span class="tag">渠道</span>
          <span class="tag">合作</span>
          <span class="tag">绩效</span>
        </div>
      </div>

      <div class="system-card service-card" @click="navigateTo('/marketing/service')">
        <div class="card-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h3>客户服务工单</h3>
        <p>工单创建、分配处理、SLA管理、客户反馈</p>
        <div class="card-tags">
          <span class="tag">工单</span>
          <span class="tag">服务</span>
          <span class="tag">反馈</span>
        </div>
      </div>
    </div>

    <div class="system-features">
      <h2>核心能力</h2>
      <div class="features-grid">
        <div class="feature-item">
          <h4>客户全生命周期管理</h4>
          <p>从获客到留存的完整管理链路</p>
        </div>
        <div class="feature-item">
          <h4>智能营销自动化</h4>
          <p>自动化营销流程，提升转化效率</p>
        </div>
        <div class="feature-item">
          <h4>多渠道数据整合</h4>
          <p>统一数据视图，精准客户画像</p>
        </div>
        <div class="feature-item">
          <h4>实时服务响应</h4>
          <p>高效工单处理，提升客户满意度</p>
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
.marketing-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  padding: 40px 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  padding: 12px 30px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.6);
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

.card-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  background: var(--accent-bg);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--accent-color);
  transition: all 0.3s ease;
}

.system-card:hover .card-icon {
  transform: scale(1.1);
}

.system-card h3 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 0 0 15px;
}

.system-card p {
  color: #64748b;
  line-height: 1.6;
  margin-bottom: 20px;
}

.card-tags {
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

.crm-card { 
  --accent-color: #667eea; 
  --accent-bg: #e0e7ff;
}
.crm-card:hover { border-bottom: 4px solid #667eea; }

.campaign-card { 
  --accent-color: #ec4899; 
  --accent-bg: #fce7f3;
}
.campaign-card:hover { border-bottom: 4px solid #ec4899; }

.channel-card { 
  --accent-color: #8b5cf6; 
  --accent-bg: #f3e8ff;
}
.channel-card:hover { border-bottom: 4px solid #8b5cf6; }

.service-card { 
  --accent-color: #14b8a6; 
  --accent-bg: #ccfbf1;
}
.service-card:hover { border-bottom: 4px solid #14b8a6; }

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
  color: #ec4899;
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