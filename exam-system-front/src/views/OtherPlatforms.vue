<template>
  <div class="other-platforms-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <div class="header-content">
        <div class="header-top">
          <button class="back-button" @click="goBack">返回中心</button>
        </div>
        <h1 class="page-title">其他平台</h1>
        <p class="page-subtitle">访问更多系统平台</p>
      </div>
    </div>
    
    <!-- 选项卡区域 -->
    <div class="tabs-container">
      <div class="tabs-header">
        <div 
          v-for="tab in tabs" 
          :key="tab.id"
          class="tab-item"
          :class="{ active: activeTab === tab.id }"
          @click="activeTab = tab.id"
        >
          {{ tab.name }}
        </div>
      </div>
      
      <!-- 选项卡内容 -->
      <div class="tabs-content">
        <!-- 安全软件选项卡 -->
        <div v-if="activeTab === 'security'" class="tab-panel">
          <div class="security-software-section">
            <h3>安全软件</h3>
            <div class="software-grid">
              <div class="software-card">
                <div class="software-icon">
                  <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                  </svg>
                </div>
                <h4>杀毒软件</h4>
                <p>实时防护系统安全</p>
              </div>
              
              <div class="software-card">
                <div class="software-icon">
                  <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                    <line x1="3" y1="9" x2="21" y2="9"></line>
                    <line x1="9" y1="21" x2="9" y2="9"></line>
                  </svg>
                </div>
                <h4>防火墙</h4>
                <p>网络安全防护</p>
              </div>
              
              <div class="software-card">
                <div class="software-icon">
                  <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="12" y1="8" x2="12" y2="12"></line>
                    <line x1="12" y1="16" x2="12.01" y2="16"></line>
                  </svg>
                </div>
                <h4>安全扫描</h4>
                <p>系统漏洞检测</p>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 安全审计平台选项卡 -->
        <div v-else-if="activeTab === 'audit'" class="tab-panel">
          <h3>安全审计平台</h3>
          <div class="audit-buttons">
            <button class="audit-button" @click="goToLogAudit">日志审计系统（旧版）</button>
            <button class="audit-button new" @click="goToLogAuditNew">日志审计系统（新版）</button>
          </div>
        </div>
        
        <div v-else-if="activeTab === 'monitor'" class="tab-panel">
          <h3>监控平台</h3>
          <p>监控功能开发中...</p>
        </div>
      </div>
    </div>
    
    <div class="page-footer">
      <p>© 2026 系统管理平台</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const router = useRouter()
const activeTab = ref('security')

const tabs = [
  { id: 'security', name: '安全软件' },
  { id: 'audit', name: '安全审计平台' },
  { id: 'monitor', name: '监控平台' }
]

const goBack = () => {
  router.push('/')
}

const goToLogAudit = () => {
  router.push('/log-audit')
}

const goToLogAuditNew = () => {
  router.push('/log-audit-new')
}
</script>

<style scoped>
.other-platforms-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 40px 20px;
}

/* 页面头部 */
.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 30px;
}

.header-top {
  margin-bottom: 20px;
  text-align: center;
}

.back-button {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  transition: background 0.3s ease;
}

.back-button:hover {
  background: #764ba2;
}

.page-title {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.page-subtitle {
  font-size: 1.2rem;
  color: #666;
  margin-bottom: 20px;
}

/* 选项卡容器 */
.tabs-container {
  max-width: 1200px;
  margin: 0 auto;
  background: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

/* 选项卡头部 */
.tabs-header {
  display: flex;
  border-bottom: 2px solid #f0f0f0;
  background: #f9f9f9;
}

.tab-item {
  flex: 1;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 500;
  color: #666;
  transition: all 0.3s ease;
  border-bottom: 3px solid transparent;
}

.tab-item:hover {
  color: #667eea;
  background: #f0f4ff;
}

.tab-item.active {
  color: #667eea;
  border-bottom-color: #667eea;
  background: white;
}

/* 选项卡内容 */
.tabs-content {
  padding: 40px;
}

.tab-panel {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 安全软件部分 */
.security-software-section h3 {
  font-size: 1.8rem;
  color: #333;
  margin-bottom: 30px;
  text-align: center;
}

/* 审计按钮样式 */
.audit-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
  flex-wrap: wrap;
}

.audit-button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 16px 32px;
  border-radius: 12px;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
  min-width: 220px;
}

.audit-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
  background: linear-gradient(135deg, #764ba2 0%, #667eea 100%);
}

.audit-button.new {
  background: linear-gradient(135deg, #e63946 0%, #c1121f 100%);
  box-shadow: 0 4px 15px rgba(230, 57, 70, 0.3);
}

.audit-button.new:hover {
  background: linear-gradient(135deg, #c1121f 0%, #e63946 100%);
  box-shadow: 0 8px 25px rgba(230, 57, 70, 0.4);
}

.software-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 24px;
  margin-top: 30px;
}

.software-card {
  background: #f9f9f9;
  border-radius: 12px;
  padding: 30px;
  text-align: center;
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.software-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  border-color: #667eea;
}

.software-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: #667eea;
  font-size: 48px;
  line-height: 48px;
  height: 48px;
}

.software-card h4 {
  font-size: 1.3rem;
  color: #333;
  margin-bottom: 10px;
}

.software-card p {
  color: #666;
  line-height: 1.5;
}

/* 页面底部 */
.page-footer {
  margin-top: 40px;
  text-align: center;
  color: #666;
  font-size: 0.9rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .other-platforms-container {
    padding: 30px 20px;
  }
  
  .page-header {
    margin-bottom: 30px;
  }
  
  .page-title {
    font-size: 2rem;
  }
  
  .header-content {
    padding: 0 20px;
  }
  
  .tabs-container {
    margin: 0 20px;
  }
  
  .tabs-content {
    padding: 24px;
  }
  
  .software-grid {
    grid-template-columns: 1fr;
  }
  
  .software-card {
    padding: 24px;
  }
}
</style>