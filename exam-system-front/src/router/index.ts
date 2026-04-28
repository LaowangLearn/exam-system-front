import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import ModuleSelect from '../views/ModuleSelect.vue'
import ExamList from '../views/ExamList.vue'
import ExamEdit from '../views/ExamEdit.vue'
import Center from '../views/Center.vue'
import CenterNew from '../views/CenterNew.vue'
import OASystem from '../views/OASystem.vue'
import EnterpriseSystem from '../views/EnterpriseSystem.vue'
import PersonalCenter from '../views/PersonalCenter.vue'
import HISSystem from '../views/HISSystem.vue'
import Dashboard from '../views/OA/Dashboard.vue'
import Users from '../views/OA/Users.vue'
import Departments from '../views/OA/Departments.vue'
import Documents from '../views/OA/Documents.vue'
import Approvals from '../views/OA/Approvals.vue'
import Schedules from '../views/OA/Schedules.vue'
import SystemSettings from '../views/SystemSettings.vue'
import GameCenter from '../views/GameCenter.vue'
import TetrisGame from '../views/TetrisGame.vue'
import TetrisMobile from '../views/TetrisMobile.vue'
import TankBattle from '../views/TankBattle.vue'
import TankBattleMobile from '../views/TankBattleMobile.vue'
import LandlordGame from '../views/LandlordGame.vue'
import OtherPlatforms from '../views/OtherPlatforms.vue'
import LogAuditSystem from '../views/LogAuditSystem.vue'
import LogAuditSystemNew from '../views/LogAuditSystemNew.vue'
import HRMSystem from '../views/HRM/HRMSystem.vue'
import FinanceSystem from '../views/Finance/FinanceSystem.vue'
import ArchiveSystem from '../views/Archive/ArchiveSystem.vue'
import MessageSystem from '../views/Message/MessageSystem.vue'
import AssetSystem from '../views/Asset/AssetSystem.vue'
import APISystem from '../views/APISystem.vue'
import CustomerList from '../views/CRM/CustomerList.vue'
import SupplierList from '../views/SCM/SupplierList.vue'
import ProductList from '../views/Shop/ProductList.vue'
import MemberList from '../views/Member/MemberList.vue'
import MarketingSystem from '../views/Marketing/MarketingSystem.vue'
import CampaignList from '../views/Marketing/CampaignList.vue'
import ChannelList from '../views/Marketing/ChannelList.vue'
import ServiceList from '../views/Marketing/ServiceList.vue'
// 政务系统组件
const GovSystem = () => import('../views/Gov/GovSystem.vue')
const GovDashboard = () => import('../views/Gov/Dashboard.vue')
const GridCell = () => import('../views/Gov/Grid/Cell.vue')
const GridEvent = () => import('../views/Gov/Grid/Event.vue')
const GridInspection = () => import('../views/Gov/Grid/Inspection.vue')
const PetitionRegister = () => import('../views/Gov/Petition/Register.vue')
const PetitionWork = () => import('../views/Gov/Petition/Work.vue')
const SecurityDevice = () => import('../views/Gov/Security/Device.vue')
const SecurityAlarm = () => import('../views/Gov/Security/Alarm.vue')
const SecurityMonitor = () => import('../views/Gov/Security/Monitor.vue')
const DocumentList = () => import('../views/Gov/Document/List.vue')
const DocumentCreate = () => import('../views/Gov/Document/Create.vue')
const DocumentApproval = () => import('../views/Gov/Document/Approval.vue')


// 路由规则
const routes = [
  {
    path: '/',
    name: 'CenterNew',
    component: CenterNew
  },
  {
    path: '/old-center',
    name: 'Center',
    component: Center
  },
  {
    path: '/home',
    redirect: '/'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/modules',
    name: 'ModuleSelect',
    component: ModuleSelect,
    meta: { requiresAuth: true }
  },
  {
    path: '/exams',
    name: 'ExamList',
    component: ExamList,
    meta: { requiresAuth: true }
  },
  {
    path: '/exams/edit/:id?',
    name: 'ExamEdit',
    component: ExamEdit,
    meta: { requiresAuth: true }
  },
  {
    path: '/oa',
    name: 'OASystem',
    component: OASystem
  },
  {
    path: '/oa/dashboard',
    name: 'Dashboard',
    component: Dashboard
  },
  {
    path: '/oa/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/oa/departments',
    name: 'Departments',
    component: Departments
  },
  {
    path: '/oa/documents',
    name: 'Documents',
    component: Documents
  },
  {
    path: '/oa/approvals',
    name: 'Approvals',
    component: Approvals
  },
  {
    path: '/oa/schedules',
    name: 'Schedules',
    component: Schedules
  },
  {
    path: '/system-settings',
    name: 'SystemSettings',
    component: SystemSettings
  },
  {
    path: '/personal',
    name: 'PersonalCenter',
    component: PersonalCenter
  },
  {
    path: '/his',
    name: 'HISSystem',
    component: HISSystem
  },
  {
    path: '/game-center',
    name: 'GameCenter',
    component: GameCenter
  },
  {
    path: '/game-center/tetris',
    name: 'TetrisGame',
    component: TetrisGame
  },
  {
    path: '/game-center/tetris-mobile',
    name: 'TetrisMobile',
    component: TetrisMobile
  },
  {
    path: '/game-center/tank-battle',
    name: 'TankBattle',
    component: TankBattle
  },
  {
    path: '/game-center/tank-battle-mobile',
    name: 'TankBattleMobile',
    component: TankBattleMobile
  },
  {
    path: '/game-center/landlord',
    name: 'LandlordGame',
    component: LandlordGame
  },
  {
    path: '/game-center/netris',
    redirect: '/game-center/tetris'
  },
  {
    path: '/other-platforms',
    name: 'OtherPlatforms',
    component: OtherPlatforms
  },
  {
    path: '/log-audit',
    name: 'LogAuditSystem',
    component: LogAuditSystem
  },
  {
    path: '/log-audit-new',
    name: 'LogAuditSystemNew',
    component: LogAuditSystemNew
  },
  {
    path: '/enterprise',
    name: 'EnterpriseSystem',
    component: EnterpriseSystem
  },
  {
    path: '/hrm',
    name: 'HRMSystem',
    component: HRMSystem
  },
  {
    path: '/finance',
    name: 'FinanceSystem',
    component: FinanceSystem
  },
  {
    path: '/archive',
    name: 'ArchiveSystem',
    component: ArchiveSystem
  },
  {
    path: '/message',
    name: 'MessageSystem',
    component: MessageSystem
  },
  {
    path: '/asset',
    name: 'AssetSystem',
    component: AssetSystem
  },
  {
    path: '/api',
    name: 'APISystem',
    component: APISystem
  },
  {
    path: '/crm',
    name: 'CustomerList',
    component: CustomerList
  },
  {
    path: '/scm',
    name: 'SupplierList',
    component: SupplierList
  },
  {
    path: '/shop',
    name: 'ProductList',
    component: ProductList
  },
  {
    path: '/member',
    name: 'MemberList',
    component: MemberList
  },
  {
    path: '/marketing',
    name: 'MarketingSystem',
    component: MarketingSystem
  },
  {
    path: '/marketing/campaign',
    name: 'CampaignList',
    component: CampaignList
  },
  {
    path: '/marketing/channel',
    name: 'ChannelList',
    component: ChannelList
  },
  {
    path: '/marketing/service',
    name: 'ServiceList',
    component: ServiceList
  },
  // 政务系统路由
  {
    path: '/gov',
    name: 'GovSystem',
    component: GovSystem
  },
  {
    path: '/gov/dashboard',
    name: 'GovDashboard',
    component: GovDashboard
  },
  {
    path: '/gov/grid/cell',
    name: 'GridCell',
    component: GridCell
  },
  {
    path: '/gov/grid/event',
    name: 'GridEvent',
    component: GridEvent
  },
  {
    path: '/gov/grid/inspection',
    name: 'GridInspection',
    component: GridInspection
  },
  {
    path: '/gov/petition/register',
    name: 'PetitionRegister',
    component: PetitionRegister
  },
  {
    path: '/gov/petition/work',
    name: 'PetitionWork',
    component: PetitionWork
  },
  {
    path: '/gov/security/device',
    name: 'SecurityDevice',
    component: SecurityDevice
  },
  {
    path: '/gov/security/alarm',
    name: 'SecurityAlarm',
    component: SecurityAlarm
  },
  {
    path: '/gov/security/monitor',
    name: 'SecurityMonitor',
    component: SecurityMonitor
  },
  {
    path: '/gov/document/list',
    name: 'DocumentList',
    component: DocumentList
  },
  {
    path: '/gov/document/create',
    name: 'DocumentCreate',
    component: DocumentCreate
  },
  {
    path: '/gov/document/approval',
    name: 'DocumentApproval',
    component: DocumentApproval
  },
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫（简单认证）
router.beforeEach((to, _from, next) => {
  const username = localStorage.getItem('username')
  console.log('路由守卫触发:', to.path, 'requiresAuth:', to.meta.requiresAuth, 'username:', username)
  if (to.meta.requiresAuth && !username) {
    console.log('重定向到登录页')
    next('/login')
  } else {
    next()
  }
})

export default router