<template>
  <div class="system-service-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>系统服务</h1>
    </div>
    
    <div class="page-content">
      <div class="section">
        <h2>通用办公类</h2>
        <p>企业日常运营管理系统</p>
        
        <div class="system-card" @click="goToEnterpriseSystem">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
              <line x1="3" y1="9" x2="21" y2="9"></line>
              <line x1="9" y1="21" x2="9" y2="9"></line>
            </svg>
          </div>
          <h3>通用办公类</h3>
          <p>六大办公子系统集成平台（包含OA功能）</p>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>营销客户类</h2>
        <p>全渠道客户营销与管理平台</p>
        
        <div class="system-card" @click="goToMarketingSystem">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
            </svg>
          </div>
          <h3>营销客户类</h3>
          <p>全渠道客户营销与管理平台</p>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>考试系统</h2>
        <p>在线考试管理平台</p>
        
        <div class="system-card" @click="goToExamSystem">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
              <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
          </div>
          <h3>考试系统</h3>
          <p>在线考试管理平台</p>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>公共 API</h2>
        <p>系统对外接口服务</p>
        
        <div class="system-card" @click="goToAPISystem">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M18 20V10"></path>
              <path d="M12 20V4"></path>
              <path d="M6 20v-6"></path>
            </svg>
          </div>
          <h3>公共 API</h3>
          <p>系统对外接口服务</p>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>政务事业单位类</h2>
        <p>政务一体化综合管理平台</p>
        
        <div class="system-card" @click="goToGovSystem">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
              <path d="M2 17l10 5 10-5"></path>
              <path d="M2 12l10 5 10-5"></path>
            </svg>
          </div>
          <h3>政务事业单位类</h3>
          <p>政务一体化综合管理平台</p>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>医疗行业类</h2>
        <p>医院信息化管理解决方案</p>
        
        <div class="system-card medical-card">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M19 8h-14"></path>
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            </svg>
          </div>
          <h3>医疗行业类</h3>
          <p>医院信息化管理解决方案</p>
          <div class="medical-subcards">
            <div class="subcard" @click.stop="goToHISSystem">
              <div class="subcard-icon his">🏥</div>
              <span>HIS医院核心</span>
            </div>
            <div class="subcard" @click.stop="goToEMRSystem">
              <div class="subcard-icon emr">📝</div>
              <span>EMR电子病历</span>
            </div>
            <div class="subcard" @click.stop="goToLISSystem">
              <div class="subcard-icon lis">🧪</div>
              <span>LIS检验系统</span>
            </div>
            <div class="subcard" @click.stop="goToPACSSystem">
              <div class="subcard-icon pacs">🩻</div>
              <span>PACS影像系统</span>
            </div>
          </div>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>校园教育类</h2>
        <p>智慧校园综合管理平台</p>
        
        <div class="system-card education-card">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
              <polyline points="22 17 12 22 2 17"></polyline>
              <line x1="2" y1="17" x2="2" y2="15"></line>
            </svg>
          </div>
          <h3>校园教育类</h3>
          <p>智慧校园综合管理平台</p>
          <div class="education-subcards">
            <div class="subcard" @click.stop="goToEduSystem('education')">
              <div class="subcard-icon education">📚</div>
              <span>教务管理系统</span>
            </div>
            <div class="subcard" @click.stop="goToEduSystem('smart')">
              <div class="subcard-icon smart">🏫</div>
              <span>智慧校园系统</span>
            </div>
            <div class="subcard" @click.stop="goToEduSystem('home')">
              <div class="subcard-icon home">👨‍👩‍👧</div>
              <span>家校互通系统</span>
            </div>
          </div>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
      
      <div class="section">
        <h2>数据中台</h2>
        <p>大数据分析与智能处理平台</p>
        
        <div class="system-card data-card">
          <div class="card-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"></circle>
              <polyline points="12 6 12 12 16 14"></polyline>
            </svg>
          </div>
          <h3>数据中台</h3>
          <p>大数据分析与处理平台</p>
          <div class="data-subcards">
            <div class="subcard" @click.stop="goToDataPlatform('bigdata')">
              <div class="subcard-icon bigdata">📊</div>
              <span>大数据平台</span>
            </div>
            <div class="subcard" @click.stop="goToDataPlatform('ai')">
              <div class="subcard-icon ai">🤖</div>
              <span>AI Agent办公</span>
            </div>
            <div class="subcard" @click.stop="goToDataPlatform('lowcode')">
              <div class="subcard-icon lowcode">🔧</div>
              <span>低代码平台</span>
            </div>
            <div class="subcard" @click.stop="goToDataPlatform('xinchuang')">
              <div class="subcard-icon xinchuang">🇨🇳</div>
              <span>信创适配</span>
            </div>
          </div>
          <div class="card-hover">
            <span class="hover-text">立即访问</span>
            <span class="hover-arrow">→</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/')
}

const goToHISSystem = () => {
  router.push('/his')
}

const goToEMRSystem = () => {
  router.push('/his')
}

const goToLISSystem = () => {
  router.push('/his')
}

const goToPACSSystem = () => {
  router.push('/his')
}

const goToEduSystem = (type) => {
  router.push('/education/' + type)
}

const goToDataPlatform = (type) => {
  router.push('/data/' + type)
}

const goToEnterpriseSystem = () => {
  router.push('/enterprise')
}

const goToMarketingSystem = () => {
  router.push('/marketing')
}

const goToExamSystem = () => {
  router.push('/exam')
}

const goToAPISystem = () => {
  router.push('/api')
}

const goToGovSystem = () => {
  router.push('/gov')
}
</script>

<style scoped>
.system-service-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8ec 100%);
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #6c5ce7;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.page-content {
  padding: 30px;
  max-width: 1200px;
  margin: 0 auto;
}

.section {
  margin-bottom: 40px;
}

.section h2 {
  margin: 0 0 8px;
  font-size: 20px;
  color: #333;
}

.section p {
  margin: 0 0 20px;
  color: #666;
  font-size: 14px;
}

.system-card {
  position: relative;
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.08);
  cursor: pointer;
  transition: all 0.3s;
}

.system-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.12);
}

.card-icon {
  color: #6c5ce7;
  margin-bottom: 16px;
}

.system-card h3 {
  margin: 0 0 8px;
  font-size: 18px;
  color: #333;
}

.system-card p {
  margin: 0 0 20px;
  color: #666;
  font-size: 14px;
}

.card-hover {
  position: absolute;
  bottom: 24px;
  right: 24px;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #6c5ce7;
  font-size: 14px;
  opacity: 0;
  transition: opacity 0.3s;
}

.system-card:hover .card-hover {
  opacity: 1;
}

.medical-card {
  border: 2px solid #27ae60;
}

.medical-card .card-icon { color: #27ae60; }
.medical-card h3 { color: #27ae60; }

.medical-subcards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.subcard {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  background: rgba(39, 174, 96, 0.1);
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.3s;
}

.subcard:hover {
  background: rgba(39, 174, 96, 0.2);
  transform: translateY(-2px);
}

.subcard-icon {
  font-size: 24px;
  margin-bottom: 6px;
}

.subcard span {
  font-size: 12px;
  color: #333;
}

.education-card {
  border: 2px solid #9b59b6;
}

.education-card .card-icon { color: #9b59b6; }
.education-card h3 { color: #9b59b6; }

.education-subcards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.education-card .subcard {
  background: rgba(155, 89, 182, 0.1);
}

.education-card .subcard:hover {
  background: rgba(155, 89, 182, 0.2);
}

.data-card {
  border: 2px solid #3498db;
}

.data-card .card-icon { color: #3498db; }
.data-card h3 { color: #3498db; }

.data-subcards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.data-card .subcard {
  background: rgba(52, 152, 219, 0.1);
}

.data-card .subcard:hover {
  background: rgba(52, 152, 219, 0.2);
}

@media (max-width: 900px) {
  .medical-subcards, .data-subcards {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .education-subcards {
    grid-template-columns: repeat(1, 1fr);
  }
}
</style>