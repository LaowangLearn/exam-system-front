<template>
  <div class="oa-permissions">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>功能权限管理</h1>
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
            <th>创建时间</th>
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
            <td>{{ permission.createTime }}</td>
            <td>
              <button class="edit-btn" @click="openEditPermissionDialog(permission)">编辑</button>
              <button class="delete-btn" @click="deletePermission(permission.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="pagination">
      <button class="page-btn" :disabled="currentPage === 1" @click="prevPage">上一页</button>
      <span class="page-info">第 {{ currentPage }} 页 / 共 {{ totalPages }} 页</span>
      <button class="page-btn" :disabled="currentPage === totalPages" @click="nextPage">下一页</button>
    </div>

    <div class="role-permission-section">
      <h2>角色权限分配</h2>
      <div class="role-list">
        <div v-for="role in roles" :key="role.id" class="role-card">
          <h3>{{ role.name }}</h3>
          <p>{{ role.description }}</p>
          <div class="permission-tags">
            <span v-for="perm in role.permissions" :key="perm" class="perm-tag">
              {{ perm }}
            </span>
          </div>
          <button class="assign-btn" @click="assignPermissions(role)">分配权限</button>
        </div>
      </div>
    </div>

    <div v-if="showPermissionDialog" class="dialog-overlay" @click="closePermissionDialog">
      <div class="dialog" @click.stop>
        <h2>{{ editingPermission ? '编辑权限' : '添加权限' }}</h2>
        <form @submit.prevent="savePermission">
          <div class="form-group">
            <label>权限名称</label>
            <input type="text" v-model="form.name" required />
          </div>
          <div class="form-group">
            <label>权限编码</label>
            <input type="text" v-model="form.code" required />
          </div>
          <div class="form-group">
            <label>权限类型</label>
            <select v-model="form.type">
              <option value="菜单">菜单</option>
              <option value="按钮">按钮</option>
              <option value="API">API</option>
              <option value="数据">数据</option>
            </select>
          </div>
          <div class="form-group">
            <label>所属模块</label>
            <select v-model="form.module">
              <option value="用户管理">用户管理</option>
              <option value="部门管理">部门管理</option>
              <option value="公文管理">公文管理</option>
              <option value="审批管理">审批管理</option>
              <option value="日程管理">日程管理</option>
              <option value="权限管理">权限管理</option>
            </select>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="form.status">
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
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/enterprise')
}

const permissions = ref([
  { id: 1, name: '用户查看', code: 'user:view', type: '菜单', module: '用户管理', status: '启用', createTime: '2024-01-01' },
  { id: 2, name: '用户添加', code: 'user:add', type: '按钮', module: '用户管理', status: '启用', createTime: '2024-01-01' },
  { id: 3, name: '用户编辑', code: 'user:edit', type: '按钮', module: '用户管理', status: '启用', createTime: '2024-01-01' },
  { id: 4, name: '用户删除', code: 'user:delete', type: '按钮', module: '用户管理', status: '启用', createTime: '2024-01-01' },
  { id: 5, name: '部门查看', code: 'dept:view', type: '菜单', module: '部门管理', status: '启用', createTime: '2024-01-02' },
  { id: 6, name: '部门添加', code: 'dept:add', type: '按钮', module: '部门管理', status: '启用', createTime: '2024-01-02' },
  { id: 7, name: '公文查看', code: 'doc:view', type: '菜单', module: '公文管理', status: '启用', createTime: '2024-01-03' },
  { id: 8, name: '公文审批', code: 'doc:approve', type: '按钮', module: '公文管理', status: '启用', createTime: '2024-01-03' },
  { id: 9, name: '权限查看', code: 'perm:view', type: '菜单', module: '权限管理', status: '启用', createTime: '2024-01-04' },
  { id: 10, name: '权限分配', code: 'perm:assign', type: '按钮', module: '权限管理', status: '启用', createTime: '2024-01-04' }
])

const roles = ref([
  { id: 1, name: '超级管理员', description: '拥有系统所有权限', permissions: ['用户查看', '用户添加', '用户编辑', '用户删除', '部门查看', '部门添加', '公文查看', '公文审批', '权限查看', '权限分配'] },
  { id: 2, name: '管理员', description: '拥有大部分管理权限', permissions: ['用户查看', '用户添加', '用户编辑', '部门查看', '公文查看', '公文审批'] },
  { id: 3, name: '普通用户', description: '拥有基本查看权限', permissions: ['用户查看', '部门查看', '公文查看'] }
])

const searchKeyword = ref('')
const showPermissionDialog = ref(false)
const showAssignDialog = ref(false)
const editingPermission = ref(false)
const currentRole = ref<any>(null)
const selectedPermissions = ref<string[]>([])
const currentPage = ref(1)
const pageSize = ref(5)
const totalPages = ref(2)

const form = ref({
  id: 0,
  name: '',
  code: '',
  type: '菜单',
  module: '用户管理',
  status: '启用'
})

const openAddPermissionDialog = () => {
  editingPermission.value = false
  form.value = {
    id: 0,
    name: '',
    code: '',
    type: '菜单',
    module: '用户管理',
    status: '启用'
  }
  showPermissionDialog.value = true
}

const openEditPermissionDialog = (permission: any) => {
  editingPermission.value = true
  form.value = { ...permission }
  showPermissionDialog.value = true
}

const closePermissionDialog = () => {
  showPermissionDialog.value = false
}

const savePermission = () => {
  if (editingPermission.value) {
    const index = permissions.value.findIndex(p => p.id === form.value.id)
    if (index !== -1) {
      permissions.value[index] = { ...form.value, createTime: permissions.value[index].createTime }
    }
  } else {
    const newPermission = {
      ...form.value,
      id: permissions.value.length + 1,
      createTime: new Date().toISOString().split('T')[0]
    }
    permissions.value.push(newPermission)
  }
  closePermissionDialog()
}

const deletePermission = (id: number) => {
  if (confirm('确定要删除这个权限吗？')) {
    permissions.value = permissions.value.filter(p => p.id !== id)
  }
}

const searchPermissions = () => {
  console.log('搜索关键字:', searchKeyword.value)
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

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

onMounted(() => {
  console.log('加载权限数据')
})
</script>

<style scoped>
.oa-permissions {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
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

.permission-list {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin-bottom: 20px;
}

.permission-list table {
  width: 100%;
  border-collapse: collapse;
}

.permission-list th,
.permission-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.permission-list th {
  background: #f8f9fa;
  font-weight: bold;
  color: #333;
}

.permission-list tr:hover {
  background: #f8f9fa;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.启用 {
  background: #d4edda;
  color: #155724;
}

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

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
  margin-bottom: 40px;
}

.page-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.page-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.page-info {
  color: #666;
}

.role-permission-section {
  background: white;
  border-radius: 8px;
  padding: 30px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.role-permission-section h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
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

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
  font-weight: bold;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.cancel-btn,
.save-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.cancel-btn {
  background: #6c757d;
  color: white;
}

.save-btn {
  background: #667eea;
  color: white;
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
  .permission-list table {
    font-size: 0.8rem;
  }

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
