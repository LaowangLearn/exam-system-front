import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import ModuleSelect from '../views/ModuleSelect.vue'
import ExamList from '../views/ExamList.vue'
import ExamEdit from '../views/ExamEdit.vue'
import Center from '../views/Center.vue'
import OASystem from '../views/OASystem.vue'
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

// 路由规则
const routes = [
  {
    path: '/',
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
    path: '/game-center/netris',
    redirect: '/game-center/tetris'
  }
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