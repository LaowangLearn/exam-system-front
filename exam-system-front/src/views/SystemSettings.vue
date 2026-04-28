<template>
  <div class="system-settings-container">
    <button class="back-btn" @click="goBack">返回中心</button>
    <div class="settings-header">
      <h1>系统设置</h1>
      <p>系统配置与管理中心</p>
    </div>
    
    <div class="settings-content">
      <div class="settings-nav">
        <ul>
          <li :class="{ active: activeTab === 'users' }" @click="activeTab = 'users'">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
            </svg>
            <span>用户管理</span>
          </li>
          <li :class="{ active: activeTab === 'permissions' }" @click="activeTab = 'permissions'">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
            <span>权限管理</span>
          </li>
          <li :class="{ active: activeTab === 'roles' }" @click="activeTab = 'roles'">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            </svg>
            <span>角色管理</span>
          </li>
          <li :class="{ active: activeTab === 'system' }" @click="activeTab = 'system'">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="3"></circle>
              <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
            </svg>
            <span>系统配置</span>
          </li>
        </ul>
      </div>
      
      <div class="settings-main">
        <div v-if="activeTab === 'users'" class="tab-content">
          <div class="content-header">
            <h2>用户管理</h2>
            <button class="add-btn" @click="openAddUserDialog">添加用户</button>
          </div>

          <div class="search-bar">
            <input type="text" v-model="searchKeyword" placeholder="搜索用户名或姓名" />
            <button class="search-btn" @click="searchUsers">搜索</button>
          </div>

          <div class="user-list">
            <div v-if="loading" class="loading">加载中...</div>
            <table v-else>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>用户名</th>
                  <th>姓名</th>
                  <th>邮箱</th>
                  <th>电话</th>
                  <th>角色</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="users.length === 0">
                  <td colspan="8" class="empty">暂无用户数据</td>
                </tr>
                <tr v-else v-for="user in users" :key="user.id">
                  <td>{{ user.id }}</td>
                  <td>{{ user.username }}</td>
                  <td>{{ user.name }}</td>
                  <td>{{ user.email }}</td>
                  <td>{{ user.phone }}</td>
                  <td>{{ user.role }}</td>
                  <td>
                    <span class="status" :class="user.status">{{ user.status }}</span>
                  </td>
                  <td>
                    <button class="edit-btn" @click="openEditUserDialog(user)">编辑</button>
                    <button class="delete-btn" @click="deleteUser(user.id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div v-if="showUserDialog" class="dialog-overlay" @click="closeUserDialog">
            <div class="dialog" @click.stop>
              <h2>{{ editingUser ? '编辑用户' : '添加用户' }}</h2>
              <form @submit.prevent="saveUser">
                <div class="form-group">
                  <label>用户名</label>
                  <input type="text" v-model="userForm.username" required />
                </div>
                <div class="form-group">
                  <label>姓名</label>
                  <input type="text" v-model="userForm.name" required />
                </div>
                <div class="form-group">
                  <label>邮箱</label>
                  <input type="email" v-model="userForm.email" />
                </div>
                <div class="form-group">
                  <label>电话</label>
                  <input type="tel" v-model="userForm.phone" />
                </div>
                <div class="form-group">
                  <label>角色</label>
                  <select v-model="userForm.role">
                    <option value="USER">普通用户</option>
                    <option value="MANAGER">管理员</option>
                    <option value="ADMIN">超级管理员</option>
                  </select>
                </div>
                <div class="form-group">
                  <label>状态</label>
                  <select v-model="userForm.status">
                    <option value="ACTIVE">激活</option>
                    <option value="INACTIVE">禁用</option>
                  </select>
                </div>
                <div class="dialog-actions">
                  <button type="button" class="cancel-btn" @click="closeUserDialog">取消</button>
                  <button type="submit" class="save-btn">保存</button>
                </div>
              </form>
            </div>
          </div>
        </div>

        <div v-if="activeTab === 'permissions'" class="tab-content">
          <div class="content-header">
            <h2>权限管理</h2>
            <button class="add-btn" @click="openAddPermissionDialog">添加权限</button>
          </div>

          <div class="search-bar">
            <input type="text" v-model="searchKeyword" placeholder="搜索权限名称或编码" />
            <button class="search-btn" @click="searchPermissions">搜索</button>
          </div>

          <div class="permission-list">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>权限名称</th>
                  <th>权限编码</th>
                  <th>权限类型</th>
                  <th>所属模块</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="permission in permissions" :key="permission.id">
                  <td>{{ permission.id }}</td>
                  <td>{{ permission.name }}</td>
                  <td>{{ permission.code }}</td>
                  <td>{{ permission.type }}</td>
                  <td>{{ permission.module }}</td>
                  <td>
                    <span class="status" :class="permission.status">{{ permission.status }}</span>
                  </td>
                  <td>
                    <button class="edit-btn" @click="openEditPermissionDialog(permission)">编辑</button>
                    <button class="delete-btn" @click="deletePermission(permission.id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div v-if="showPermissionDialog" class="dialog-overlay" @click="closePermissionDialog">
            <div class="dialog" @click.stop>
              <h2>{{ editingPermission ? '编辑权限' : '添加权限' }}</h2>
              <form @submit.prevent="savePermission">
                <div class="form-group">
                  <label>权限名称</label>
                  <input type="text" v-model="permissionForm.name" required />
                </div>
                <div class="form-group">
                  <label>权限编码</label>
                  <input type="text" v-model="permissionForm.code" required />
                </div>
                <div class="form-group">
                  <label>权限类型</label>
                  <select v-model="permissionForm.type">
                    <option value="菜单">菜单</option>
                    <option value="按钮">按钮</option>
                    <option value="API">API</option>
                    <option value="数据">数据</option>
                  </select>
                </div>
                <div class="form-group">
                  <label>所属模块</label>
                  <select v-model="permissionForm.module">
                    <option value="用户管理">用户管理</option>
                    <option value="权限管理">权限管理</option>
                    <option value="角色管理">角色管理</option>
                    <option value="系统配置">系统配置</option>
                  </select>
                </div>
                <div class="form-group">
                  <label>状态</label>
                  <select v-model="permissionForm.status">
                    <option value="启用">启用</option>
                    <option value="禁用">禁用</option>
                  </select>
                </div>
                <div class="dialog-actions">
                  <button type="button" class="cancel-btn" @click="closePermissionDialog">取消</button>
                  <button type="submit" class="save-btn">保存</button>
                </div>
              </form>
            </div>
          </div>
        </div>

        <div v-if="activeTab === 'roles'" class="tab-content">
          <div class="content-header">
            <h2>角色管理</h2>
            <button class="add-btn" @click="openAddRoleDialog">添加角色</button>
          </div>

          <div class="role-list">
            <div v-for="role in roles" :key="role.id" class="role-card">
              <h3>{{ role.name }}</h3>
              <p>{{ role.description }}</p>
              <div class="permission-tags">
                <span v-for="perm in role.permissions" :key="perm" class="perm-tag">
                  {{ perm }}
                </span>
              </div>
              <div class="role-actions">
                <button class="edit-btn" @click="editRole(role)">编辑</button>
                <button class="assign-btn" @click="assignPermissions(role)">分配权限</button>
              </div>
            </div>
          </div>

          <div v-if="showRoleDialog" class="dialog-overlay" @click="closeRoleDialog">
            <div class="dialog" @click.stop>
              <h2>{{ editingRole ? '编辑角色' : '添加角色' }}</h2>
              <form @submit.prevent="saveRole">
                <div class="form-group">
                  <label>角色名称</label>
                  <input type="text" v-model="roleForm.name" required />
                </div>
                <div class="form-group">
                  <label>角色描述</label>
                  <input type="text" v-model="roleForm.description" />
                </div>
                <div class="dialog-actions">
                  <button type="button" class="cancel-btn" @click="closeRoleDialog">取消</button>
                  <button type="submit" class="save-btn">保存</button>
                </div>
              </form>
            </div>
          </div>

          <div v-if="showAssignDialog" class="dialog-overlay" @click="closeAssignDialog">
            <div class="dialog assign-dialog" @click.stop>
              <h2>分配权限 - {{ currentRole?.name }}</h2>
              <div class="permission-checkboxes">
                <label v-for="perm in permissions" :key="perm.id" class="checkbox-item">
                  <input type="checkbox" v-model="selectedPermissions" :value="perm.name" />
                  <span>{{ perm.name }}</span>
                </label>
              </div>
              <div class="dialog-actions">
                <button type="button" class="cancel-btn" @click="closeAssignDialog">取消</button>
                <button type="button" class="save-btn" @click="saveRolePermissions">保存</button>
              </div>
            </div>
          </div>
        </div>

        <div v-if="activeTab === 'system'" class="tab-content">
          <div class="content-header">
            <h2>系统配置</h2>
          </div>

          <div class="system-config">
            <div class="config-section">
              <h3>基本设置</h3>
              <div class="form-group">
                <label>系统名称</label>
                <input type="text" v-model="systemConfig.name" />
              </div>
              <div class="form-group">
                <label>系统描述</label>
                <input type="text" v-model="systemConfig.description" />
              </div>
              <div class="form-group">
                <label>系统版本</label>
                <input type="text" v-model="systemConfig.version" disabled />
              </div>
            </div>

            <div class="config-section">
              <h3>安全设置</h3>
              <div class="form-group">
                <label>密码最小长度</label>
                <input type="number" v-model="systemConfig.minPasswordLength" />
              </div>
              <div class="form-group">
                <label>会话超时时间（分钟）</label>
                <input type="number" v-model="systemConfig.sessionTimeout" />
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="systemConfig.enableTwoFactorAuth" />
                  启用双因素认证
                </label>
              </div>
            </div>

            <div class="config-section">
              <h3>通知设置</h3>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="systemConfig.enableEmailNotification" />
                  启用邮件通知
                </label>
              </div>
              <div class="form-group">
                <label>
                  <input type="checkbox" v-model="systemConfig.enableSmsNotification" />
                  启用短信通知
                </label>
              </div>
            </div>

            <div class="config-actions">
              <button class="save-btn" @click="saveSystemConfig">保存配置</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import userService, { User } from '../services/userService'

const router = useRouter()
const activeTab = ref('users')
const searchKeyword = ref('')
const loading = ref(false)

const users = ref<User[]>([])

const permissions = ref([
  { id: 1, name: '用户查看', code: 'user:view', type: '菜单', module: '用户管理', status: '启用' },
  { id: 2, name: '用户添加', code: 'user:add', type: '按钮', module: '用户管理', status: '启用' },
  { id: 3, name: '用户编辑', code: 'user:edit', type: '按钮', module: '用户管理', status: '启用' },
  { id: 4, name: '用户删除', code: 'user:delete', type: '按钮', module: '用户管理', status: '启用' },
  { id: 5, name: '权限查看', code: 'perm:view', type: '菜单', module: '权限管理', status: '启用' },
  { id: 6, name: '权限分配', code: 'perm:assign', type: '按钮', module: '权限管理', status: '启用' }
])

const roles = ref([
  { id: 1, name: '超级管理员', description: '拥有系统所有权限', permissions: ['用户查看', '用户添加', '用户编辑', '用户删除', '权限查看', '权限分配'] },
  { id: 2, name: '管理员', description: '拥有大部分管理权限', permissions: ['用户查看', '用户添加', '用户编辑', '权限查看'] },
  { id: 3, name: '普通用户', description: '拥有基本查看权限', permissions: ['用户查看', '权限查看'] }
])

const systemConfig = ref({
  name: '综合系统管理平台',
  description: '企业级综合管理系统',
  version: '1.0.0',
  minPasswordLength: 6,
  sessionTimeout: 30,
  enableTwoFactorAuth: false,
  enableEmailNotification: true,
  enableSmsNotification: false
})

const showUserDialog = ref(false)
const showPermissionDialog = ref(false)
const showRoleDialog = ref(false)
const showAssignDialog = ref(false)
const editingUser = ref(false)
const editingPermission = ref(false)
const editingRole = ref(false)
const currentRole = ref<any>(null)
const selectedPermissions = ref<string[]>([])

const userForm = ref({
  id: 0,
  username: '',
  name: '',
  email: '',
  phone: '',
  role: 'USER',
  status: 'ACTIVE'
})

// 加载用户列表
const loadUsers = async () => {
  try {
    loading.value = true
    const response = await userService.getUsers()
    users.value = response
  } catch (error) {
    console.error('加载用户失败:', error)
  } finally {
    loading.value = false
  }
}

// 组件挂载时加载用户
onMounted(() => {
  if (activeTab.value === 'users') {
    loadUsers()
  }
})

const permissionForm = ref({
  id: 0,
  name: '',
  code: '',
  type: '菜单',
  module: '用户管理',
  status: '启用'
})

const roleForm = ref({
  id: 0,
  name: '',
  description: ''
})

const goBack = () => {
  router.push('/')
}

const searchUsers = async () => {
  try {
    loading.value = true
    const allUsers = await userService.getUsers()
    if (searchKeyword.value) {
      users.value = allUsers.filter(user => 
        user.username.includes(searchKeyword.value) || 
        user.name.includes(searchKeyword.value)
      )
    } else {
      users.value = allUsers
    }
  } catch (error) {
    console.error('搜索用户失败:', error)
  } finally {
    loading.value = false
  }
}

const searchPermissions = () => {
  console.log('搜索权限:', searchKeyword.value)
}

const openAddUserDialog = () => {
  editingUser.value = false
  userForm.value = { id: 0, username: '', name: '', email: '', phone: '', role: 'USER', status: 'ACTIVE' }
  showUserDialog.value = true
}

const openEditUserDialog = (user: User) => {
  editingUser.value = true
  userForm.value = { ...user }
  showUserDialog.value = true
}

const closeUserDialog = () => {
  showUserDialog.value = false
}

const saveUser = async () => {
  try {
    loading.value = true
    if (editingUser.value) {
      await userService.updateUser(userForm.value.id, userForm.value)
    } else {
      await userService.addUser(userForm.value)
    }
    closeUserDialog()
    await loadUsers() // 重新加载用户列表
  } catch (error) {
    console.error('保存用户失败:', error)
  } finally {
    loading.value = false
  }
}

const deleteUser = async (id: number) => {
  if (confirm('确定要删除这个用户吗？')) {
    try {
      loading.value = true
      await userService.deleteUser(id)
      await loadUsers() // 重新加载用户列表
    } catch (error) {
      console.error('删除用户失败:', error)
    } finally {
      loading.value = false
    }
  }
}

// 监听标签切换，当切换到用户管理时加载用户
watch(activeTab, (newTab) => {
  if (newTab === 'users') {
    loadUsers()
  }
})

const openAddPermissionDialog = () => {
  editingPermission.value = false
  permissionForm.value = { id: 0, name: '', code: '', type: '菜单', module: '用户管理', status: '启用' }
  showPermissionDialog.value = true
}

const openEditPermissionDialog = (permission: any) => {
  editingPermission.value = true
  permissionForm.value = { ...permission }
  showPermissionDialog.value = true
}

const closePermissionDialog = () => {
  showPermissionDialog.value = false
}

const savePermission = () => {
  if (editingPermission.value) {
    const index = permissions.value.findIndex(p => p.id === permissionForm.value.id)
    if (index !== -1) {
      permissions.value[index] = { ...permissionForm.value }
    }
  } else {
    const newPermission = { ...permissionForm.value, id: permissions.value.length + 1 }
    permissions.value.push(newPermission)
  }
  closePermissionDialog()
}

const deletePermission = (id: number) => {
  if (confirm('确定要删除这个权限吗？')) {
    permissions.value = permissions.value.filter(p => p.id !== id)
  }
}

const openAddRoleDialog = () => {
  editingRole.value = false
  roleForm.value = { id: 0, name: '', description: '' }
  showRoleDialog.value = true
}

const editRole = (role: any) => {
  editingRole.value = true
  roleForm.value = { ...role }
  showRoleDialog.value = true
}

const closeRoleDialog = () => {
  showRoleDialog.value = false
}

const saveRole = () => {
  if (editingRole.value) {
    const index = roles.value.findIndex(r => r.id === roleForm.value.id)
    if (index !== -1) {
      roles.value[index] = { ...roleForm.value, permissions: roles.value[index].permissions }
    }
  } else {
    const newRole = { ...roleForm.value, id: roles.value.length + 1, permissions: [] }
    roles.value.push(newRole)
  }
  closeRoleDialog()
}

const assignPermissions = (role: any) => {
  currentRole.value = role
  selectedPermissions.value = [...role.permissions]
  showAssignDialog.value = true
}

const closeAssignDialog = () => {
  showAssignDialog.value = false
  currentRole.value = null
  selectedPermissions.value = []
}

const saveRolePermissions = () => {
  if (currentRole.value) {
    const roleIndex = roles.value.findIndex(r => r.id === currentRole.value.id)
    if (roleIndex !== -1) {
      roles.value[roleIndex].permissions = [...selectedPermissions.value]
    }
  }
  closeAssignDialog()
}

const saveSystemConfig = () => {
  alert('系统配置已保存')
}
</script>

<style scoped>
.system-settings-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  padding: 40px 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
}

.settings-header {
  text-align: center;
  margin-bottom: 40px;
}

.settings-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.settings-header p {
  font-size: 1.2rem;
  color: #666;
}

.settings-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  gap: 30px;
}

.settings-nav {
  flex: 0 0 250px;
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  height: fit-content;
}

.settings-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.settings-nav li {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 5px;
  color: #333;
  font-weight: 500;
}

.settings-nav li:hover {
  background: #f8f9fa;
}

.settings-nav li.active {
  background: #667eea;
  color: white;
}

.settings-main {
  flex: 1;
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.content-header h2 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

.add-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.add-btn:hover {
  background: #764ba2;
}

.search-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.search-bar input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.search-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.user-list,
.permission-list {
  margin-bottom: 20px;
}

.user-list table,
.permission-list table {
  width: 100%;
  border-collapse: collapse;
}

.user-list th,
.user-list td,
.permission-list th,
.permission-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.user-list th,
.permission-list th {
  background: #f8f9fa;
  font-weight: bold;
  color: #333;
}

.user-list tr:hover,
.permission-list tr:hover {
  background: #f8f9fa;
}

.loading {
  text-align: center;
  padding: 40px;
  color: #667eea;
  font-size: 1.1rem;
}

.empty {
  text-align: center;
  padding: 40px;
  color: #6c757d;
  font-style: italic;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.ACTIVE,
.status.启用 {
  background: #d4edda;
  color: #155724;
}

.status.INACTIVE,
.status.禁用 {
  background: #f8d7da;
  color: #721c24;
}

.edit-btn,
.delete-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
  margin-right: 5px;
}

.edit-btn {
  background: #667eea;
  color: white;
}

.delete-btn {
  background: #dc3545;
  color: white;
}

.role-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.role-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
}

.role-card h3 {
  margin-top: 0;
  margin-bottom: 10px;
  color: #333;
}

.role-card p {
  color: #666;
  margin-bottom: 15px;
}

.permission-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 15px;
}

.perm-tag {
  background: #e3f2fd;
  color: #1976d2;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 0.85rem;
}

.role-actions {
  display: flex;
  gap: 10px;
}

.assign-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.assign-btn:hover {
  background: #764ba2;
}

.system-config {
  max-width: 800px;
}

.config-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
}

.config-section h3 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
  font-weight: bold;
}

.form-group input[type="text"],
.form-group input[type="number"],
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group input[type="checkbox"] {
  margin-right: 10px;
}

.config-actions {
  text-align: right;
}

.save-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.dialog {
  background: white;
  border-radius: 8px;
  padding: 30px;
  width: 90%;
  max-width: 500px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.dialog h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.cancel-btn {
  background: #6c757d;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.assign-dialog {
  max-width: 600px;
}

.permission-checkboxes {
  max-height: 300px;
  overflow-y: auto;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 15px;
}

.checkbox-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 0;
  cursor: pointer;
}

.checkbox-item:hover {
  background: #f8f9fa;
}

.checkbox-item input[type="checkbox"] {
  width: auto;
}

@media (max-width: 768px) {
  .settings-content {
    flex-direction: column;
  }

  .settings-nav {
    flex: none;
    width: 100%;
  }

  .user-list table,
  .permission-list table {
    font-size: 0.8rem;
  }

  .user-list th,
  .user-list td,
  .permission-list th,
  .permission-list td {
    padding: 8px;
  }

  .edit-btn,
  .delete-btn {
    padding: 3px 6px;
    font-size: 0.7rem;
  }

  .role-list {
    grid-template-columns: 1fr;
  }
}
</style>
