<template>
  <div class="finance-system-container">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="system-header">
      <h1>财务管控系统</h1>
      <p>财务管理与预算控制平台</p>
    </div>

    <div class="module-grid">
      <div class="module-card" @click="activeModule = 'budget'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
            <line x1="3" y1="9" x2="21" y2="9"></line>
          </svg>
        </div>
        <h3>预算管理</h3>
        <p>预算编制、执行跟踪</p>
      </div>

      <div class="module-card" @click="activeModule = 'expense'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
          </svg>
        </div>
        <h3>费用报销</h3>
        <p>报销申请、审批流程</p>
      </div>

      <div class="module-card" @click="activeModule = 'voucher'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
            <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
          </svg>
        </div>
        <h3>凭证管理</h3>
        <p>会计凭证、账务处理</p>
      </div>

      <div class="module-card" @click="activeModule = 'bank'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="1" x2="12" y2="23"></line>
            <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
          </svg>
        </div>
        <h3>银行账户</h3>
        <p>账户余额、交易记录</p>
      </div>

      <div class="module-card" @click="activeModule = 'transaction'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <polyline points="23 6 13.5 15.5 8.5 10.5 1 18"></polyline>
            <polyline points="17 6 23 6 23 12"></polyline>
          </svg>
        </div>
        <h3>银行流水</h3>
        <p>收支明细、对账管理</p>
      </div>

      <div class="module-card" @click="activeModule = 'tax'">
        <div class="module-icon">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"></circle>
            <line x1="12" y1="8" x2="12" y2="12"></line>
            <line x1="12" y1="16" x2="12.01" y2="16"></line>
          </svg>
        </div>
        <h3>税务申报</h3>
        <p>税务计算、申报管理</p>
      </div>
    </div>

    <div class="module-content" v-if="activeModule">
      <h2>{{ getModuleTitle() }}</h2>
      <p>API接口: /finance/{{ activeModule }}</p>
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
    budget: '预算管理',
    expense: '费用报销',
    voucher: '凭证管理',
    bank: '银行账户',
    transaction: '银行流水',
    tax: '税务申报'
  }
  return titles[activeModule.value] || ''
}
</script>

<style scoped>
.finance-system-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #4ade80 0%, #22c55e 100%);
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
  color: #22c55e;
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
  color: #22c55e;
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
