import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/',
    component: () => import('@/views/layout/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '工作台', icon: 'Odometer' }
      },
      {
        path: 'grid',
        name: 'Grid',
        component: () => import('@/views/layout/BlankLayout.vue'),
        meta: { title: '网格化治理', icon: 'Grid' },
        children: [
          {
            path: 'cell',
            name: 'GridCell',
            component: () => import('@/views/grid/cell/index.vue'),
            meta: { title: '网格单元' }
          },
          {
            path: 'event',
            name: 'GridEvent',
            component: () => import('@/views/grid/event/index.vue'),
            meta: { title: '网格事件' }
          },
          {
            path: 'inspection',
            name: 'GridInspection',
            component: () => import('@/views/grid/inspection/index.vue'),
            meta: { title: '巡检记录' }
          }
        ]
      },
      {
        path: 'petition',
        name: 'Petition',
        component: () => import('@/views/layout/BlankLayout.vue'),
        meta: { title: '综治信访', icon: 'ChatLineSquare' },
        children: [
          {
            path: 'register',
            name: 'PetitionRegister',
            component: () => import('@/views/petition/register/index.vue'),
            meta: { title: '信访登记' }
          },
          {
            path: 'work',
            name: 'PetitionWork',
            component: () => import('@/views/petition/work/index.vue'),
            meta: { title: '办理工单' }
          }
        ]
      },
      {
        path: 'security',
        name: 'Security',
        component: () => import('@/views/layout/BlankLayout.vue'),
        meta: { title: '智慧安防', icon: 'VideoCamera' },
        children: [
          {
            path: 'device',
            name: 'SecurityDevice',
            component: () => import('@/views/security/device/index.vue'),
            meta: { title: '设备管理' }
          },
          {
            path: 'alarm',
            name: 'SecurityAlarm',
            component: () => import('@/views/security/alarm/index.vue'),
            meta: { title: '报警记录' }
          },
          {
            path: 'monitor',
            name: 'SecurityMonitor',
            component: () => import('@/views/security/monitor/index.vue'),
            meta: { title: '实时监控' }
          }
        ]
      },
      {
        path: 'document',
        name: 'Document',
        component: () => import('@/views/layout/BlankLayout.vue'),
        meta: { title: '公文机要', icon: 'Document' },
        children: [
          {
            path: 'list',
            name: 'DocumentList',
            component: () => import('@/views/document/list/index.vue'),
            meta: { title: '公文列表' }
          },
          {
            path: 'create',
            name: 'DocumentCreate',
            component: () => import('@/views/document/create/index.vue'),
            meta: { title: '起草公文' }
          },
          {
            path: 'approval',
            name: 'DocumentApproval',
            component: () => import('@/views/document/approval/index.vue'),
            meta: { title: '审批管理' }
          }
        ]
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
