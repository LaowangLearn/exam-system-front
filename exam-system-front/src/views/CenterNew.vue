<template>
  <div class="center-container" :class="{ 'night-mode': !isDayMode }">
    <!-- 装饰性圆形 -->
    <div class="decoration-circle circle-1"></div>
    <div class="decoration-circle circle-2"></div>
    <div class="decoration-circle circle-3"></div>
    
    <!-- 页面头部 -->
    <div class="center-header">
      <div class="header-content">
        <div class="logo">
          <div class="logo-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
              <polyline points="2 17 12 22 22 17"></polyline>
              <polyline points="2 12 12 17 22 12"></polyline>
            </svg>
          </div>
          <div class="logo-text">
            <h2>系统管理平台</h2>
            <p>高效管理，智能办公</p>
          </div>
        </div>
        <div class="header-nav">
          <nav class="nav-links">
            <a href="#" class="nav-link active">首页</a>
            <a href="#" class="nav-link">系统服务</a>
            <a href="#" class="nav-link" @click.prevent="goToGameCenter">游戏中心</a>
            <a href="#" class="nav-link">关于我们</a>
            <a href="#" class="nav-link">联系我们</a>
          </nav>
        </div>
        <div class="header-right">
          <button class="mode-toggle" @click="toggleDayMode">
            <span class="mode-icon">{{ isDayMode ? '🌙' : '☀️' }}</span>
            <span class="mode-text">{{ isDayMode ? '夜间模式' : '白天模式' }}</span>
          </button>
          <button class="secondary-button" @click="switchToOld">切换到旧版本</button>
          <div class="user-profile" @click="toggleUserMenu">
            <div class="user-avatar">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
              </svg>
            </div>
            <div class="user-info">
              <span class="user-name">张三</span>
              <span class="user-role">管理员</span>
            </div>
            <svg class="user-arrow" :class="{ 'rotated': showUserMenu }" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="6 9 12 15 18 9"></polyline>
            </svg>
            
            <!-- 下拉菜单 -->
            <div v-if="showUserMenu" class="user-dropdown">
              <div class="dropdown-header">
                <div class="dropdown-avatar">
                  <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg>
                </div>
                <div class="dropdown-user-info">
                  <span class="dropdown-name">张三</span>
                  <span class="dropdown-email">zhangsan@example.com</span>
                </div>
              </div>
              <div class="dropdown-divider"></div>
              <div class="dropdown-menu">
                <div class="dropdown-item" @click="goToPersonalCenter">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg>
                  <span>个人信息</span>
                </div>
                <div class="dropdown-item" @click="goToChangePassword">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                    <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                  </svg>
                  <span>修改密码</span>
                </div>
                <div class="dropdown-item" @click="goToSystemSettings">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="3"></circle>
                    <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
                  </svg>
                  <span>系统设置</span>
                </div>
                <div class="dropdown-item" @click="showVersionModal = true">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
                    <circle cx="9" cy="7" r="4"></circle>
                    <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                  </svg>
                  <span>版本信息</span>
                  <span class="version-badge">v2.0.0</span>
                </div>
                <div class="dropdown-divider"></div>
                <div class="dropdown-item logout" @click="handleLogout">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                  </svg>
                  <span>退出登录</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Hero区域 -->
      <div class="hero-section">
        <!-- 粒子背景 -->
        <div class="particles-container">
          <div v-for="n in 20" :key="n" class="particle" :style="{
            left: `${Math.random() * 100}%`,
            top: `${Math.random() * 100}%`,
            animationDelay: `${Math.random() * 5}s`,
            animationDuration: `${3 + Math.random() * 4}s`,
            width: `${4 + Math.random() * 8}px`,
            height: `${4 + Math.random() * 8}px`
          }"></div>
        </div>
        
        <!-- 光环效果 -->
        <div class="ring ring-1"></div>
        <div class="ring ring-2"></div>
        
        <div class="badge">
          <span class="badge-icon">✨</span>
          <span>Welcome to 澄心若素</span>
        </div>
        <h1 class="hero-title">
          <span class="title-wrapper">
            <span class="gradient-text main-title">澄心若素</span>
            <span class="title-glow"></span>
          </span>
          <span class="subtitle">· 纯净心灵，简约生活</span>
        </h1>
        <p class="hero-description">
          澄心若素，希望每一个人都能保持内心的纯净与宁静，在快节奏的生活中找到属于自己的平衡与美好。
          我们致力于为您提供简单、高效、优雅的系统管理体验，让每一次使用都成为一种享受。
        </p>
        <div class="hero-buttons">
          <button class="primary-button" @click="goToEnterpriseSystem">
            <span class="btn-icon">✨</span>
            开始体验
          </button>
          <button class="outline-button" @click="goToExamSystem">
            <span class="btn-icon">🎯</span>
            了解更多
          </button>
        </div>
      </div>
      
      <!-- 统计数据区域 -->
      <div class="stats-section">
        <div class="stat-card">
          <div class="stat-value gradient-text">22°C</div>
          <div class="stat-label">北京市天气</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">7</div>
          <div class="stat-label">系统数量</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">{{ currentDate }}</div>
          <div class="stat-label">今天日期时间</div>
        </div>
        <div class="stat-card">
          <div class="stat-value gradient-text">99.9%</div>
          <div class="stat-label">系统可用率</div>
        </div>
      </div>
    </div>
    
    <!-- 主内容区 -->
    <div class="center-content">
      <div class="system-category">
        <h2>通用办公类</h2>
        <p>企业日常运营管理系统</p>
      </div>
      
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
    
    <!-- 页面底部 -->
    <div class="center-footer">
      <div class="footer-content">
        <div class="footer-info">
          <h4>系统管理平台</h4>
          <p>提供全方位的系统管理解决方案</p>
        </div>
        <div class="footer-links">
          <a href="#">关于我们</a>
          <a href="#">帮助中心</a>
          <a href="#">联系我们</a>
        </div>
      </div>
      <p class="copyright">© 2026 系统管理平台</p>
    </div>
  </div>
  
  <!-- 版本信息弹窗 -->
  <div v-if="showVersionModal" class="version-modal-overlay" @click.self="showVersionModal = false">
    <div class="version-modal">
      <div class="modal-header">
        <div class="modal-title">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M22 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
          <span>系统版本信息</span>
        </div>
        <button class="modal-close" @click="showVersionModal = false">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>
      <div class="modal-content">
        <div class="version-badge-large">v2.0.0</div>
        <div class="version-info">
          <p><strong>系统名称</strong>：澄心若素 - 系统管理平台</p>
          <p><strong>当前版本</strong>：v2.0.0</p>
          <p><strong>更新时间</strong>：2026年04月28日</p>
          <p><strong>开发状态</strong>：已完成</p>
        </div>
        <div class="version-changes">
          <h4>版本更新日志</h4>
          <div class="change-list">
            <div class="change-item">
              <span class="change-tag major">重大更新</span>
              <span class="change-desc">政务事业单位类系统开发完成</span>
            </div>
            <div class="change-item">
              <span class="change-tag feature">新增功能</span>
              <span class="change-desc">网格化治理系统、综治信访工单系统、智慧安防平台、公文机要系统</span>
            </div>
            <div class="change-item">
              <span class="change-tag ui">UI优化</span>
              <span class="change-desc">首页用户菜单优化、粒子背景效果、返回按钮统一</span>
            </div>
            <div class="change-item">
              <span class="change-tag bug">修复</span>
              <span class="change-desc">修复按钮点击失效问题</span>
            </div>
          </div>
        </div>
        
        <!-- 历史更新明细 -->
        <div class="history-section">
          <button class="history-toggle" @click="showHistoryDetail = !showHistoryDetail">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="12" y1="5" x2="12" y2="19"></line>
              <line x1="5" y1="12" x2="19" y2="12"></line>
            </svg>
            <span>{{ showHistoryDetail ? '收起' : '查看历史更新明细' }}</span>
          </button>
          
          <div v-if="showHistoryDetail" class="history-content">
            <div v-for="(version, index) in versionHistory" :key="index" class="history-version">
              <div class="version-header">
                <span class="version-number">{{ version.version }}</span>
                <span class="version-date">{{ version.date }}</span>
                <span class="version-type" :class="version.typeClass">{{ version.type }}</span>
              </div>
              <div v-for="(module, mIndex) in version.modules" :key="mIndex" class="version-module">
                <div class="module-title">{{ module.title }}</div>
                <ul class="module-changes">
                  <li v-for="(change, cIndex) in module.changes" :key="cIndex">{{ change }}</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="modal-btn" @click="showVersionModal = false">关闭</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ref, onMounted, onUnmounted } from 'vue'

const router = useRouter()
const isDayMode = ref(false)
const currentDate = ref('')
const showUserMenu = ref(false)
const showVersionModal = ref(false)
const showHistoryDetail = ref(false)

const versionHistory = ref([
  {
    version: 'v2.0.0',
    date: '2026年04月28日',
    type: '大改版/重构',
    typeClass: 'type-major',
    modules: [
      {
        title: '政务事业单位类系统开发',
        changes: [
          '政务一体化平台创建：支持国密SM2/SM3/SM4加密、等保三级合规设计、适配国产化数据库',
          '网格化治理系统：网格单元管理、网格事件管理、巡检记录管理、AI智能分派建议',
          '综治信访工单系统：信访登记管理、受理分派办理流程、AI相似案例推荐、满意度评价',
          '智慧安防平台：设备管理、实时告警处理、AI人脸识别/车辆识别/行为分析、实时监控大屏',
          '公文机要系统：公文起草编辑、多级审批流程、发布归档撤回、国密加密存储'
        ]
      },
      {
        title: '首页用户菜单优化',
        changes: [
          '个人中心入口移至右上角头像区域',
          '添加下拉菜单（个人信息、修改密码、系统设置、退出登录、版本信息）',
          '添加粒子背景和光环装饰效果，优化标题光晕效果'
        ]
      },
      {
        title: '全系统返回按钮优化',
        changes: [
          '所有页面添加左上角返回按钮',
          '统一按钮样式（蓝色渐变、悬停效果）'
        ]
      }
    ]
  },
  {
    version: 'v1.2.0',
    date: '2026年04月26日',
    type: '加新功能',
    typeClass: 'type-feature',
    modules: [
      {
        title: '营销客户类子模块完善',
        changes: [
          '营销活动管理页面：活动列表展示、搜索筛选、新建编辑删除',
          '渠道管理页面：渠道信息管理、收入统计、佣金结算',
          '客户服务工单页面：工单创建处理、状态追踪、SLA管理'
        ]
      },
      {
        title: '业务系统UI/UX优化',
        changes: [
          '统一返回按钮链接到营销客户类模块',
          '添加页面头部图标和标题样式',
          '增强搜索栏功能、添加加载状态和空数据提示',
          '实现可排序表头和分页控件'
        ]
      },
      {
        title: '营销客户类模块创建',
        changes: [
          '创建营销客户类模块入口页面',
          '包含CRM客户管理、营销活动管理、渠道管理、客户服务工单',
          '采用深色主题设计，卡片式布局'
        ]
      },
      {
        title: '首页Hero区域主题优化',
        changes: [
          '主标题"澄心若素"采用4.5rem超大字体，渐变色彩',
          '副标题"· 纯净心灵，简约生活"',
          '徽章文字更新为"Welcome to 澄心若素"'
        ]
      },
      {
        title: '首页日/夜间模式切换功能',
        changes: [
          '白天模式：浅色渐变背景，紫粉色系配色',
          '夜间模式：深蓝色调渐变背景，玻璃效果卡片',
          '手动切换按钮 + 自动判断模式（7:00-19:00为白天模式）',
          '保存用户偏好到localStorage'
        ]
      },
      {
        title: '企业管理系统整合',
        changes: [
          '创建统一的企业管理系统集成页面',
          '整合六大办公子系统（OA、HRM、财务、档案、沟通、资产）'
        ]
      },
      {
        title: '游戏中心界面优化',
        changes: [
          '优化坦克大战版本选择界面按钮样式',
          '去除白色方块背景',
          '提升界面视觉一致性'
        ]
      },
      {
        title: '俄罗斯方块游戏优化',
        changes: [
          '上下方向键用于顺时针/逆时针旋转',
          '复活后保留游戏板下方方块，只清除最上面6行'
        ]
      },
      {
        title: '后端新增模块',
        changes: [
          'CRM客户管理后端',
          'SCM供应链管理后端',
          '电商商城后端',
          '会员管理后端',
          'AI本地赋能模块（文本摘要、情感分析、回复建议、关键词提取）'
        ]
      }
    ]
  },
  {
    version: 'v1.1.0',
    date: '2026年04月20日',
    type: '加新功能',
    typeClass: 'type-feature',
    modules: [
      {
        title: '俄罗斯方块游戏布局优化',
        changes: [
          '添加全局滚动限制',
          '优化容器布局，减少内边距提高适配性',
          '调整方块尺寸为28px×28px',
          '添加响应式设计和触摸设备支持'
        ]
      },
      {
        title: '俄罗斯方块游戏复活功能',
        changes: [
          '添加复活次数状态变量（默认66次）',
          '游戏结束时显示复活按钮和剩余次数',
          '开始新游戏和重新开始时重置复活次数'
        ]
      }
    ]
  },
  {
    version: 'v1.0.1',
    date: '2026年02月09日',
    type: '改bug小优化',
    typeClass: 'type-bug',
    modules: [
      {
        title: '配置文件更新',
        changes: [
          '前端vite.config.ts优化构建配置（添加22行，删除2行）',
          '后端pom.xml调整项目依赖配置',
          '后端application.yml更新应用程序配置',
          '添加Windows系统启动脚本start.bat',
          '更新PowerShell启动脚本run.ps1'
        ]
      }
    ]
  },
  {
    version: 'v1.0.0',
    date: '2026年02月09日之前',
    type: '新项目上线',
    typeClass: 'type-new',
    modules: [
      {
        title: '系统初始化',
        changes: [
          '考试系统基础框架搭建',
          '前端Vue 3项目初始化',
          '后端Spring Boot项目初始化',
          '数据库设计与初始化'
        ]
      }
    ]
  }
])
let modeCheckInterval: number | null = null
let dateUpdateInterval: number | null = null

// 检查当前时间并设置模式
const checkModeByTime = () => {
  const now = new Date()
  const hour = now.getHours()
  // 7:00-19:00 为白天模式，其他时间为夜间模式
  isDayMode.value = hour >= 7 && hour < 19
  // 保存模式到本地存储
  localStorage.setItem('systemMode', isDayMode.value ? 'day' : 'night')
}

// 更新当前日期时间
const updateCurrentDate = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  currentDate.value = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

// 切换模式
const toggleMode = () => {
  isDayMode.value = !isDayMode.value
  // 保存模式到本地存储
  localStorage.setItem('systemMode', isDayMode.value ? 'day' : 'night')
}

const handleClickOutside = (event: MouseEvent) => {
  const target = event.target as HTMLElement
  if (!target.closest('.user-profile')) {
    showUserMenu.value = false
  }
}

// 页面加载时检查本地存储的模式设置
onMounted(() => {
  // 从本地存储读取模式设置
  const savedMode = localStorage.getItem('systemMode')
  if (savedMode) {
    isDayMode.value = savedMode === 'day'
  } else {
    // 首次加载时根据时间设置模式
    checkModeByTime()
  }
  
  // 初始更新日期时间
  updateCurrentDate()
  
  // 设置定时检查，每小时检查一次
  modeCheckInterval = window.setInterval(checkModeByTime, 60 * 60 * 1000)
  
  // 设置定时更新日期时间，每秒更新一次
  dateUpdateInterval = window.setInterval(updateCurrentDate, 1000)
  
  // 添加点击外部关闭菜单
  document.addEventListener('click', handleClickOutside)
})

// 组件卸载时清除定时器
onUnmounted(() => {
  if (modeCheckInterval) {
    clearInterval(modeCheckInterval)
  }
  if (dateUpdateInterval) {
    clearInterval(dateUpdateInterval)
  }
  // 移除点击外部关闭菜单
  document.removeEventListener('click', handleClickOutside)
})

const goToExamSystem = () => {
  router.push('/modules')
}

const goToOASystem = () => {
  router.push('/oa')
}

const goToPersonalCenter = () => {
  router.push('/personal')
}

const goToHISSystem = () => {
  router.push('/his')
}

const goToSystemSettings = () => {
  router.push('/system-settings')
}

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

const goToChangePassword = () => {
  showUserMenu.value = false
  router.push('/personal#change-password')
}

const handleLogout = () => {
  showUserMenu.value = false
  localStorage.removeItem('username')
  ElMessage.success('已成功退出登录')
  router.push('/login')
}

const goToGameCenter = () => {
  router.push('/game-center')
}

const goToEnterpriseSystem = () => {
  router.push('/enterprise')
}

const goToAPISystem = () => {
  router.push('/api')
}

const goToMarketingSystem = () => {
  router.push('/marketing')
}

const goToGovSystem = () => {
  router.push('/gov')
}

const goToGovIntegration = () => {
  router.push('/gov')
}

const goToEMRSystem = () => {
  router.push('/his/medicalRecord')
}

const goToLISSystem = () => {
  router.push('/his')
}

const goToPACSSystem = () => {
  router.push('/his')
}

const goToGridGovernance = () => {
  router.push('/gov/grid/cell')
}

const goToPetitionWorkOrder = () => {
  router.push('/gov/petition/register')
}

const goToSmartSecurity = () => {
  router.push('/gov/security/monitor')
}

const goToDocumentSystem = () => {
  router.push('/gov/document/list')
}

const switchToOld = () => {
  router.push('/old-center')
}

const clearStorage = () => {
  localStorage.removeItem('username')
  ElMessage.success('登录状态已清除')
}
</script>

<style scoped>
/* 全局样式 */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

/* 页面容器 */
.center-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #f5f0fa 0%, #f8f5fc 50%, #faf7fe 100%);
  padding: 0;
  font-family: 'Inter', sans-serif;
  color: #2d3748;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

/* 装饰性圆形 */
.decoration-circle {
  position: absolute;
  border-radius: 50%;
  opacity: 0.3;
  z-index: 0;
  pointer-events: none;
}

.circle-1 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  top: 10%;
  left: -100px;
  filter: blur(60px);
}

.circle-2 {
  width: 400px;
  height: 400px;
  background: linear-gradient(135deg, #ed8936 0%, #ecc94b 100%);
  bottom: 10%;
  right: -150px;
  filter: blur(80px);
}

.circle-3 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #4299e1 0%, #667eea 100%);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  filter: blur(100px);
}

/* 页面头部 */
.center-header {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(128, 90, 213, 0.1);
  padding: 24px 48px;
  position: relative;
  z-index: 10;
  box-shadow: 0 4px 30px rgba(128, 90, 213, 0.05);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto 48px;
}

.header-nav {
  flex: 1;
  margin-left: 60px;
}

.nav-links {
  display: flex;
  gap: 32px;
  list-style: none;
  padding: 0;
  margin: 0;
}

.nav-link {
  text-decoration: none;
  color: #4a5568;
  font-weight: 500;
  font-size: 1rem;
  position: relative;
  padding: 8px 0;
  transition: color 0.2s ease;
}

.nav-link:hover {
  color: #805ad5;
}

.nav-link.active {
  color: #805ad5;
}

.nav-link.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 3px;
  background: linear-gradient(90deg, #805ad5, #d53f8c);
  border-radius: 2px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 16px;
  background: rgba(128, 90, 213, 0.1);
  border-radius: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid rgba(128, 90, 213, 0.2);
}

.user-profile:hover {
  background: rgba(128, 90, 213, 0.2);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(128, 90, 213, 0.2);
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.user-name {
  font-weight: 600;
  color: #2d3748;
  font-size: 0.95rem;
}

.user-role {
  font-size: 0.75rem;
  color: #718096;
}

.user-arrow {
  color: #a0aec0;
  transition: transform 0.3s ease;
}

.user-profile:hover .user-arrow {
  transform: rotate(180deg);
}

.user-arrow.rotated {
  transform: rotate(180deg);
}

.user-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  min-width: 240px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  border: 1px solid rgba(0, 0, 0, 0.08);
  z-index: 1000;
  overflow: hidden;
}

.dropdown-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: linear-gradient(135deg, rgba(128, 90, 213, 0.05) 0%, rgba(213, 63, 140, 0.05) 100%);
}

.dropdown-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.dropdown-user-info {
  display: flex;
  flex-direction: column;
}

.dropdown-name {
  font-weight: 600;
  color: #2d3748;
  font-size: 0.95rem;
}

.dropdown-email {
  font-size: 0.8rem;
  color: #718096;
}

.dropdown-divider {
  height: 1px;
  background: rgba(0, 0, 0, 0.06);
  margin: 0;
}

.dropdown-menu {
  padding: 4px 0;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  color: #4a5568;
}

.dropdown-item:hover {
  background: rgba(128, 90, 213, 0.08);
  color: #805ad5;
}

.dropdown-item.logout {
  color: #e53e3e;
}

.dropdown-item.logout:hover {
  background: rgba(229, 62, 62, 0.1);
  color: #e53e3e;
}

.logo {
  display: flex;
  align-items: center;
  gap: 16px;
}

.logo-icon {
  color: #805ad5;
  font-size: 48px;
  transition: all 0.3s ease;
}

.logo-text h2 {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 700;
  color: #805ad5;
  letter-spacing: -0.5px;
}

.logo-text p {
  margin: 4px 0 0;
  font-size: 0.875rem;
  color: #718096;
  letter-spacing: 0.3px;
}

/* Hero区域 */
.hero-section {
  text-align: center;
  max-width: 900px;
  margin: 0 auto 48px;
  position: relative;
  z-index: 10;
  overflow: hidden;
}

/* 粒子背景 */
.particles-container {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1;
}

.particle {
  position: absolute;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(128, 90, 213, 0.6) 0%, rgba(213, 63, 140, 0.3) 50%, transparent 100%);
  animation: float-particle cubic-bezier(0.25, 0.46, 0.45, 0.94) infinite;
  box-shadow: 0 0 8px rgba(128, 90, 213, 0.4), 0 0 16px rgba(213, 63, 140, 0.2);
}

@keyframes float-particle {
  0%, 100% {
    transform: translateY(0) translateX(0) scale(1);
    opacity: 0.2;
  }
  25% {
    transform: translateY(-30px) translateX(15px) scale(1.05);
    opacity: 0.6;
  }
  50% {
    transform: translateY(-50px) translateX(0) scale(0.95);
    opacity: 0.4;
  }
  75% {
    transform: translateY(-25px) translateX(-12px) scale(1.02);
    opacity: 0.5;
  }
}

/* 光环效果 */
.ring {
  position: absolute;
  border-radius: 50%;
  border: 1px solid rgba(128, 90, 213, 0.15);
  animation: rotate-ring linear infinite;
}

.ring-1 {
  width: 400px;
  height: 400px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-duration: 20s;
}

.ring-2 {
  width: 500px;
  height: 500px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-duration: 30s;
  animation-direction: reverse;
  border-width: 1px;
  border-color: rgba(213, 63, 140, 0.1);
}

@keyframes rotate-ring {
  from {
    transform: translate(-50%, -50%) rotate(0deg);
  }
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

.badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(128, 90, 213, 0.1);
  border: 1px solid rgba(128, 90, 213, 0.2);
  padding: 8px 20px;
  border-radius: 50px;
  font-size: 0.875rem;
  color: #805ad5;
  margin-bottom: 24px;
}

.badge-icon {
  font-size: 1rem;
}

.hero-title {
  font-size: 3.5rem;
  font-weight: 800;
  color: #2d3748;
  margin-bottom: 16px;
  letter-spacing: -1px;
  line-height: 1.2;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.title-wrapper {
  position: relative;
  display: inline-block;
}

.main-title {
  font-size: 4.5rem;
  font-weight: 900;
  letter-spacing: 8px;
  position: relative;
  z-index: 1;
  filter: drop-shadow(0 4px 20px rgba(128, 90, 213, 0.3));
}

.title-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 120%;
  height: 120%;
  background: radial-gradient(ellipse at center, rgba(128, 90, 213, 0.25) 0%, rgba(213, 63, 140, 0.15) 40%, transparent 70%);
  filter: blur(30px);
  opacity: 0.7;
  pointer-events: none;
}

.subtitle {
  font-size: 1.5rem;
  font-weight: 500;
  color: #718096;
  letter-spacing: 2px;
}

.gradient-text {
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 50%, #ed8936 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-description {
  font-size: 1.25rem;
  color: #718096;
  line-height: 1.6;
  margin-bottom: 32px;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}

.hero-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
  position: relative;
  z-index: 10;
}

/* 按钮样式 */
.primary-button {
  background: linear-gradient(135deg, #805ad5 0%, #ed8936 100%);
  color: white;
  border: none;
  padding: 14px 32px;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(128, 90, 213, 0.3);
}

.primary-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(128, 90, 213, 0.4);
}

.outline-button {
  background: transparent;
  color: #805ad5;
  border: 2px solid #805ad5;
  padding: 14px 32px;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
}

.outline-button:hover {
  background: rgba(128, 90, 213, 0.1);
  transform: translateY(-2px);
}

.secondary-button {
  background: transparent;
  color: #805ad5;
  border: 1px solid #805ad5;
  padding: 10px 24px;
  border-radius: 12px;
  font-size: 0.9rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.secondary-button:hover {
  background: rgba(128, 90, 213, 0.1);
}

.mode-toggle {
  background: rgba(128, 90, 213, 0.1);
  border: 1px solid rgba(128, 90, 213, 0.2);
  color: #805ad5;
  padding: 10px 20px;
  border-radius: 20px;
  font-size: 0.875rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.mode-toggle:hover {
  background: rgba(128, 90, 213, 0.15);
  border-color: rgba(128, 90, 213, 0.3);
}

.btn-icon {
  font-size: 1.1rem;
}

/* 统计数据区域 */
.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.stat-card {
  background: white;
  border: 1px solid rgba(128, 90, 213, 0.15);
  border-radius: 16px;
  padding: 28px 24px;
  text-align: center;
  transition: all 0.3s ease;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(128, 90, 213, 0.1);
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 800;
  margin-bottom: 8px;
  line-height: 1.2;
}

.stat-label {
  font-size: 0.75rem;
  color: #718096;
  font-weight: 600;
  letter-spacing: 1px;
}

/* 主内容区 */
.center-content {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  max-width: 1200px;
  width: 100%;
  margin: 48px auto;
  padding: 0 48px;
  position: relative;
  z-index: 1;
}

/* 系统分类标题 */
.system-category {
  grid-column: 1 / -1;
  text-align: center;
  margin-bottom: 8px;
  padding: 20px;
}

.system-category h2 {
  font-size: 1.75rem;
  margin: 0 0 6px 0;
  color: #805ad5;
  font-weight: 700;
  letter-spacing: -0.5px;
}

.system-category p {
  margin: 0;
  color: #718096;
  font-size: 1rem;
}

/* 系统卡片 */
.system-card {
  background: white;
  border: 1px solid rgba(128, 90, 213, 0.15);
  border-radius: 16px;
  padding: 40px 32px;
  text-align: center;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.4s ease;
  box-shadow: 0 4px 20px rgba(128, 90, 213, 0.05);
}

.system-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 40px rgba(128, 90, 213, 0.15);
  border-color: rgba(128, 90, 213, 0.3);
}

.card-icon {
  margin-bottom: 24px;
  color: #805ad5;
  transition: all 0.3s ease;
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  background: rgba(128, 90, 213, 0.1);
  border-radius: 20px;
}

.system-card:hover .card-icon {
  transform: scale(1.1);
  background: rgba(128, 90, 213, 0.15);
}

.system-card h3 {
  font-size: 1.25rem;
  margin-bottom: 12px;
  color: #2d3748;
  font-weight: 700;
  transition: color 0.3s ease;
  letter-spacing: -0.3px;
}

.system-card:hover h3 {
  color: #805ad5;
}

.system-card p {
  color: #718096;
  margin-bottom: 28px;
  font-size: 0.9rem;
  line-height: 1.6;
}

.card-hover {
  position: absolute;
  bottom: -60px;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, rgba(128, 90, 213, 0.1) 0%, rgba(237, 137, 54, 0.05) 100%);
  color: #805ad5;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.4s ease;
  border-top: 1px solid rgba(128, 90, 213, 0.15);
}

.system-card:hover .card-hover {
  bottom: 0;
}

.hover-text {
  font-weight: 600;
  font-size: 0.9rem;
}

.hover-arrow {
  font-size: 1.3rem;
  font-weight: bold;
  transition: transform 0.3s ease;
}

.system-card:hover .hover-arrow {
  transform: translateX(6px);
}

/* 医疗行业卡片 */
.medical-card {
  background: linear-gradient(135deg, rgba(67, 233, 123, 0.05) 0%, rgba(56, 249, 215, 0.05) 100%);
  border-color: rgba(67, 233, 123, 0.3);
}

.medical-card .card-icon {
  background: linear-gradient(135deg, rgba(67, 233, 123, 0.15) 0%, rgba(56, 249, 215, 0.15) 100%);
  color: #43e97b;
}

.medical-card h3 {
  color: #22c55e;
}

.medical-card:hover h3 {
  color: #16a34a;
}

.medical-card:hover {
  border-color: rgba(67, 233, 123, 0.5);
  box-shadow: 0 12px 40px rgba(67, 233, 123, 0.15);
}

.medical-subcards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  margin-bottom: 20px;
}

.subcard {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.subcard:hover {
  background: rgba(67, 233, 123, 0.1);
  transform: translateY(-2px);
  border-color: rgba(67, 233, 123, 0.3);
}

.subcard-icon {
  font-size: 1.5rem;
}

.subcard span {
  font-size: 0.75rem;
  color: #4a5568;
  font-weight: 500;
}

.subcard:hover span {
  color: #22c55e;
}

/* 页面底部 */
.center-footer {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(20px);
  border-top: 1px solid rgba(128, 90, 213, 0.1);
  padding: 48px;
  margin-top: 64px;
  position: relative;
  z-index: 1;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto 32px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 40px;
}

.footer-info h4 {
  font-size: 1.25rem;
  margin-bottom: 8px;
  font-weight: 700;
  color: #805ad5;
}

.footer-info p {
  margin: 0;
  color: #718096;
  font-size: 0.875rem;
}

.footer-links {
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
}

.footer-links a {
  color: #718096;
  text-decoration: none;
  font-size: 0.875rem;
  transition: all 0.3s ease;
  position: relative;
}

.footer-links a:hover {
  color: #805ad5;
}

.footer-links a::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0;
  height: 1px;
  background: #805ad5;
  transition: width 0.3s ease;
}

.footer-links a:hover::after {
  width: 100%;
}

.copyright {
  text-align: center;
  margin: 0;
  color: #a0aec0;
  font-size: 0.8rem;
  padding-top: 24px;
  border-top: 1px solid rgba(128, 90, 213, 0.1);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .center-header {
    padding: 20px 24px;
  }
  
  .header-content {
    flex-direction: column;
    gap: 20px;
    align-items: flex-start;
  }
  
  .hero-title {
    font-size: 2rem;
  }
  
  .main-title {
    font-size: 2.5rem;
    letter-spacing: 4px;
  }
  
  .subtitle {
    font-size: 1.1rem;
  }
  
  .hero-description {
    font-size: 1rem;
  }
  
  .center-content {
    padding: 0 24px;
    grid-template-columns: 1fr;
  }
  
  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .footer-content {
    flex-direction: column;
    text-align: center;
  }
  
  .footer-links {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .hero-title {
    font-size: 1.5rem;
  }
  
  .main-title {
    font-size: 2rem;
    letter-spacing: 2px;
  }
  
  .subtitle {
    font-size: 0.9rem;
  }
  
  .hero-buttons {
    flex-direction: column;
  }
  
  .primary-button,
  .outline-button {
    width: 100%;
    justify-content: center;
  }
  
  .stats-section {
    grid-template-columns: 1fr;
  }
}

/* 夜间模式样式 */
.night-mode {
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  color: #e0e0e0;
}

.night-mode .center-header {
  background: rgba(26, 26, 46, 0.8);
  border-bottom: 1px solid rgba(100, 149, 237, 0.2);
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.3);
}

.night-mode .logo-text h2 {
  color: #6495ed;
}

.night-mode .logo-text p {
  color: #b0c4de;
}

.night-mode .nav-link {
  color: #b0c4de;
}

.night-mode .nav-link:hover {
  color: #6495ed;
}

.night-mode .nav-link.active {
  color: #6495ed;
}

.night-mode .nav-link.active::after {
  background: linear-gradient(90deg, #6495ed, #4ecdc4);
}

.night-mode .decoration-circle.circle-1 {
  background: linear-gradient(135deg, #6495ed 0%, #4169e1 100%);
}

.night-mode .decoration-circle.circle-2 {
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a6f 100%);
}

.night-mode .decoration-circle.circle-3 {
  background: linear-gradient(135deg, #4ecdc4 0%, #45b7d1 100%);
}

.night-mode .badge {
  background: rgba(100, 149, 237, 0.1);
  border: 1px solid rgba(100, 149, 237, 0.3);
  color: #6495ed;
}

.night-mode .hero-title {
  color: #e0e0e0;
}

.night-mode .main-title {
  background: linear-gradient(135deg, #6495ed 0%, #4ecdc4 50%, #ff6b6b 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 4px 20px rgba(100, 149, 237, 0.4);
}

.night-mode .subtitle {
  color: #b0c4de;
}

.night-mode .hero-description {
  color: #b0c4de;
}

.night-mode .primary-button {
  background: linear-gradient(135deg, #6495ed 0%, #4ecdc4 100%);
  box-shadow: 0 4px 15px rgba(100, 149, 237, 0.4);
}

.night-mode .primary-button:hover {
  box-shadow: 0 8px 25px rgba(100, 149, 237, 0.6);
}

.night-mode .outline-button {
  color: #6495ed;
  border: 2px solid #6495ed;
}

.night-mode .outline-button:hover {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .secondary-button {
  color: #6495ed;
  border: 1px solid #6495ed;
}

.night-mode .secondary-button:hover {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .mode-toggle {
  background: rgba(100, 149, 237, 0.1);
  border: 1px solid rgba(100, 149, 237, 0.3);
  color: #6495ed;
}

.night-mode .mode-toggle:hover {
  background: rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .stat-card {
  background: rgba(26, 26, 46, 0.8);
  border: 1px solid rgba(100, 149, 237, 0.2);
  backdrop-filter: blur(10px);
}

.night-mode .stat-card:hover {
  box-shadow: 0 8px 25px rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .stat-label {
  color: #b0c4de;
}

.night-mode .system-card {
  background: rgba(26, 26, 46, 0.8);
  border: 1px solid rgba(100, 149, 237, 0.2);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
}

.night-mode .system-card:hover {
  box-shadow: 0 12px 40px rgba(100, 149, 237, 0.2);
  border-color: rgba(100, 149, 237, 0.4);
}

.night-mode .system-card h3 {
  color: #e0e0e0;
}

.night-mode .system-card:hover h3 {
  color: #6495ed;
}

.night-mode .system-card p {
  color: #b0c4de;
}

.night-mode .card-icon {
  color: #6495ed;
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .system-card:hover .card-icon {
  background: rgba(100, 149, 237, 0.2);
}

.night-mode .card-hover {
  background: linear-gradient(135deg, rgba(100, 149, 237, 0.1) 0%, rgba(78, 205, 196, 0.05) 100%);
  color: #6495ed;
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

.night-mode .center-footer {
  background: rgba(26, 26, 46, 0.9);
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

.night-mode .footer-info h4 {
  color: #6495ed;
}

.night-mode .footer-info p {
  color: #b0c4de;
}

.night-mode .footer-links a {
  color: #b0c4de;
}

.night-mode .footer-links a:hover {
  color: #6495ed;
}

.night-mode .footer-links a::after {
  background: #6495ed;
}

.night-mode .copyright {
  color: #6495ed;
  border-top: 1px solid rgba(100, 149, 237, 0.2);
}

/* 夜间模式动画效果 */
.night-mode .main-title {
  animation: night-glow 2s ease-in-out infinite alternate;
}

@keyframes night-glow {
  from {
    text-shadow: 0 4px 20px rgba(100, 149, 237, 0.4);
  }
  to {
    text-shadow: 0 4px 30px rgba(100, 149, 237, 0.6), 0 0 40px rgba(78, 205, 196, 0.3);
  }
}

/* 版本徽章 */
.version-badge {
  margin-left: auto;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  color: white;
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 0.7rem;
  font-weight: 600;
}

/* 版本信息弹窗 */
.version-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
  animation: fade-in 0.2s ease;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.version-modal {
  background: white;
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  animation: slide-up 0.3s ease;
}

@keyframes slide-up {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
}

.modal-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 1.2rem;
  font-weight: 600;
  color: #2d3748;
}

.modal-close {
  background: none;
  border: none;
  color: #a0aec0;
  cursor: pointer;
  padding: 6px;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.modal-close:hover {
  background: rgba(0, 0, 0, 0.05);
  color: #4a5568;
}

.modal-content {
  padding: 24px;
}

.version-badge-large {
  display: inline-block;
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  color: white;
  padding: 8px 20px;
  border-radius: 30px;
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: 20px;
}

.version-info {
  background: rgba(128, 90, 213, 0.05);
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 20px;
}

.version-info p {
  margin: 8px 0;
  color: #4a5568;
}

.version-changes h4 {
  font-size: 1rem;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 16px;
}

.change-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.change-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.change-tag {
  padding: 4px 10px;
  border-radius: 6px;
  font-size: 0.75rem;
  font-weight: 600;
  flex-shrink: 0;
}

.change-tag.major {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

.change-tag.feature {
  background: rgba(34, 197, 94, 0.1);
  color: #22c55e;
}

.change-tag.ui {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.change-tag.bug {
  background: rgba(249, 115, 22, 0.1);
  color: #f97316;
}

.change-desc {
  color: #4a5568;
  font-size: 0.9rem;
}

.modal-footer {
  padding: 16px 24px;
  border-top: 1px solid rgba(0, 0, 0, 0.08);
  display: flex;
  justify-content: flex-end;
}

.modal-btn {
  background: linear-gradient(135deg, #805ad5 0%, #d53f8c 100%);
  color: white;
  border: none;
  padding: 10px 24px;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.modal-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(128, 90, 213, 0.4);
}

/* 历史更新明细 */
.history-section {
  margin-top: 20px;
  border-top: 1px solid rgba(0, 0, 0, 0.08);
  padding-top: 20px;
}

.history-toggle {
  display: flex;
  align-items: center;
  gap: 8px;
  background: transparent;
  border: 1px solid rgba(128, 90, 213, 0.3);
  color: #805ad5;
  padding: 10px 16px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s ease;
  width: 100%;
  justify-content: center;
}

.history-toggle:hover {
  background: rgba(128, 90, 213, 0.1);
  border-color: rgba(128, 90, 213, 0.5);
}

.history-content {
  margin-top: 16px;
  max-height: 400px;
  overflow-y: auto;
}

.history-version {
  background: rgba(0, 0, 0, 0.03);
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 12px;
}

.history-version:last-child {
  margin-bottom: 0;
}

.version-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
  flex-wrap: wrap;
}

.version-number {
  font-size: 1.1rem;
  font-weight: 700;
  color: #805ad5;
}

.version-date {
  font-size: 0.85rem;
  color: #718096;
}

.version-type {
  font-size: 0.75rem;
  padding: 3px 10px;
  border-radius: 12px;
  font-weight: 600;
}

.version-type.type-major {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

.version-type.type-feature {
  background: rgba(34, 197, 94, 0.1);
  color: #22c55e;
}

.version-type.type-bug {
  background: rgba(249, 115, 22, 0.1);
  color: #f97316;
}

.version-type.type-new {
  background: rgba(59, 130, 246, 0.1);
  color: #3b82f6;
}

.version-module {
  margin-bottom: 12px;
}

.version-module:last-child {
  margin-bottom: 0;
}

.module-title {
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 8px;
  font-size: 0.95rem;
}

.module-changes {
  list-style: none;
  padding-left: 0;
  margin: 0;
}

.module-changes li {
  padding: 6px 0;
  padding-left: 20px;
  position: relative;
  color: #4a5568;
  font-size: 0.85rem;
  line-height: 1.5;
}

.module-changes li::before {
  content: '•';
  position: absolute;
  left: 0;
  color: #805ad5;
  font-weight: bold;
}

/* 夜间模式历史明细 */
.night-mode .history-section {
  border-top-color: rgba(100, 149, 237, 0.2);
}

.night-mode .history-toggle {
  border-color: rgba(100, 149, 237, 0.3);
  color: #6495ed;
}

.night-mode .history-toggle:hover {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .history-version {
  background: rgba(100, 149, 237, 0.05);
}

.night-mode .version-number {
  color: #6495ed;
}

.night-mode .version-date {
  color: #b0c4de;
}

.night-mode .module-title {
  color: #e0e0e0;
}

.night-mode .module-changes li {
  color: #b0c4de;
}

.night-mode .module-changes li::before {
  color: #6495ed;
}

/* 夜间模式版本弹窗 */
.night-mode .version-modal {
  background: #1a1a2e;
}

.night-mode .modal-header {
  border-bottom-color: rgba(100, 149, 237, 0.2);
}

.night-mode .modal-title {
  color: #e0e0e0;
}

.night-mode .version-info {
  background: rgba(100, 149, 237, 0.1);
}

.night-mode .version-info p {
  color: #b0c4de;
}

.night-mode .version-changes h4 {
  color: #e0e0e0;
}

.night-mode .change-desc {
  color: #b0c4de;
}

</style>