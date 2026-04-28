<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon size="32"><<Government /></el-icon>
        <span>政务平台</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        router
        class="aside-menu"
        background-color="#1a1a2e"
        text-color="#ccc"
        active-text-color="#409eff"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>工作台</span>
        </el-menu-item>

        <el-sub-menu index="grid">
          <template #title>
            <el-icon><Grid /></el-icon>
            <span>网格化治理</span>
          </template>
          <el-menu-item index="/grid/cell">网格单元</el-menu-item>
          <el-menu-item index="/grid/event">网格事件</el-menu-item>
          <el-menu-item index="/grid/inspection">巡检记录</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="petition">
          <template #title>
            <el-icon><ChatLineSquare /></el-icon>
            <span>综治信访</span>
          </template>
          <el-menu-item index="/petition/register">信访登记</el-menu-item>
          <el-menu-item index="/petition/work">办理工单</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="security">
          <template #title>
            <el-icon><VideoCamera /></el-icon>
            <span>智慧安防</span>
          </template>
          <el-menu-item index="/security/device">设备管理</el-menu-item>
          <el-menu-item index="/security/alarm">报警记录</el-menu-item>
          <el-menu-item index="/security/monitor">实时监控</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="document">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>公文机要</span>
          </template>
          <el-menu-item index="/document/list">公文列表</el-menu-item>
          <el-menu-item index="/document/create">起草公文</el-menu-item>
          <el-menu-item index="/document/approval">审批管理</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in breadcrumbs" :key="item">{{ item }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              <span>{{ userStore.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="settings">设置</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const breadcrumbs = computed(() => {
  const matched = route.matched.filter(item => item.meta && item.meta.title)
  return matched.map(item => item.meta.title)
})

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
    ElMessage.success('已退出登录')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background: #1a1a2e;
  overflow-x: hidden;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid #2a2a4e;
}

.logo .el-icon {
  margin-right: 8px;
  color: #409eff;
}

.aside-menu {
  border-right: none;
}

.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.header-left {
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 10px;
}

.user-info .el-icon {
  margin-right: 5px;
}

.main {
  background: #f5f7fa;
  padding: 20px;
}
</style>
