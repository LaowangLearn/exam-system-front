import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import ModuleSelect from '../views/ModuleSelect.vue'
import ExamList from '../views/ExamList.vue'
import ExamEdit from '../views/ExamEdit.vue'
import Center from '../views/Center.vue'
import OASystem from '../views/OASystem.vue'
import PersonalCenter from '../views/PersonalCenter.vue'
import HISSystem from '../views/HISSystem.vue'

// 路由规则
const routes = [
  {
    path: '/',
    name: 'Center',
    component: Center,
    meta: { requiresAuth: true }
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
    path: '/personal',
    name: 'PersonalCenter',
    component: PersonalCenter
  },
  {
    path: '/his',
    name: 'HISSystem',
    component: HISSystem
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