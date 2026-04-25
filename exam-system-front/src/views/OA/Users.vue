<template>
  <div class="oa-users">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>用户管理</h1>
      <button class="add-btn" @click="openAddUserDialog">添加用户</button>
    </div>

    <div class="search-bar">
      <input type="text" v-model="searchKeyword" placeholder="搜索用户名或姓名" />
      <button class="search-btn" @click="searchUsers">搜索</button>
    </div>

    <div class="user-list">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>姓名</th>
            <th>邮箱</th>
            <th>电话</th>
            <th>部门</th>
            <th>职位</th>
            <th>角色</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.phone }}</td>
            <td>{{ getDepartmentName(user.departmentId) }}</td>
            <td>{{ user.position }}</td>
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

    <!-- 添加/编辑用户对话框 -->
    <div v-if="showUserDialog" class="dialog-overlay" @click="closeUserDialog">
      <div class="dialog" @click.stop>
        <h2>{{ editingUser ? '编辑用户' : '添加用户' }}</h2>
        <form @submit.prevent="saveUser">
          <div class="form-group">
            <label>用户名</label>
            <input type="text" v-model="form.username" required />
          </div>
          <div class="form-group">
            <label>姓名</label>
            <input type="text" v-model="form.name" required />
          </div>
          <div class="form-group">
            <label>邮箱</label>
            <input type="email" v-model="form.email" />
          </div>
          <div class="form-group">
            <label>电话</label>
            <input type="tel" v-model="form.phone" />
          </div>
          <div class="form-group">
            <label>部门</label>
            <select v-model="form.departmentId">
              <option value="">选择部门</option>
              <option v-for="dept in departments" :key="dept.id" :value="dept.id">
                {{ dept.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>职位</label>
            <input type="text" v-model="form.position" />
          </div>
          <div class="form-group">
            <label>角色</label>
            <select v-model="form.role">
              <option value="USER">普通用户</option>
              <option value="MANAGER">管理员</option>
              <option value="ADMIN">超级管理员</option>
            </select>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="form.status">
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
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/enterprise')
}

// 用户列表
const users = ref([
  { id: 1, username: 'admin', name: '管理员', email: 'admin@example.com', phone: '13800138000', departmentId: 1, position: '总经理', role: 'ADMIN', status: 'ACTIVE' },
  { id: 2, username: 'tech', name: '技术经理', email: 'tech@example.com', phone: '13800138001', departmentId: 2, position: '技术经理', role: 'MANAGER', status: 'ACTIVE' },
  { id: 3, username: 'user1', name: '普通用户', email: 'user1@example.com', phone: '13800138002', departmentId: 2, position: '开发工程师', role: 'USER', status: 'ACTIVE' }
])

// 部门列表
const departments = ref([
  { id: 1, name: '总经办' },
  { id: 2, name: '技术部' },
  { id: 3, name: '人事部' },
  { id: 4, name: '财务部' }
])

// 搜索关键字
const searchKeyword = ref('')

// 对话框状态
const showUserDialog = ref(false)
const editingUser = ref(false)
const form = ref({
  id: 0,
  username: '',
  name: '',
  email: '',
  phone: '',
  departmentId: 0,
  position: '',
  role: 'USER',
  status: 'ACTIVE'
})

// 打开添加用户对话框
const openAddUserDialog = () => {
  editingUser.value = false
  form.value = {
    id: 0,
    username: '',
    name: '',
    email: '',
    phone: '',
    departmentId: 0,
    position: '',
    role: 'USER',
    status: 'ACTIVE'
  }
  showUserDialog.value = true
}

// 打开编辑用户对话框
const openEditUserDialog = (user: any) => {
  editingUser.value = true
  form.value = { ...user }
  showUserDialog.value = true
}

// 关闭用户对话框
const closeUserDialog = () => {
  showUserDialog.value = false
}

// 保存用户
const saveUser = () => {
  if (editingUser.value) {
    // 编辑现有用户
    const index = users.value.findIndex(u => u.id === form.value.id)
    if (index !== -1) {
      users.value[index] = { ...form.value }
    }
  } else {
    // 添加新用户
    const newUser = {
      ...form.value,
      id: users.value.length + 1
    }
    users.value.push(newUser)
  }
  closeUserDialog()
}

// 删除用户
const deleteUser = (id: number) => {
  if (confirm('确定要删除这个用户吗？')) {
    users.value = users.value.filter(u => u.id !== id)
  }
}

// 搜索用户
const searchUsers = () => {
  // 这里可以实现搜索逻辑
  console.log('搜索关键字:', searchKeyword.value)
}

// 获取部门名称
const getDepartmentName = (departmentId: number) => {
  const dept = departments.value.find(d => d.id === departmentId)
  return dept ? dept.name : '未知'
}

onMounted(() => {
  // 加载用户和部门数据
  console.log('加载用户数据')
})
</script>

<style scoped>
.oa-users {
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

.user-list {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.user-list table {
  width: 100%;
  border-collapse: collapse;
}

.user-list th, .user-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.user-list th {
  background: #f8f9fa;
  font-weight: bold;
  color: #333;
}

.user-list tr:hover {
  background: #f8f9fa;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.ACTIVE {
  background: #d4edda;
  color: #155724;
}

.status.INACTIVE {
  background: #f8d7da;
  color: #721c24;
}

.edit-btn, .delete-btn {
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

/* 对话框样式 */
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

.cancel-btn, .save-btn {
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

@media (max-width: 768px) {
  .user-list table {
    font-size: 0.8rem;
  }

  .user-list th, .user-list td {
    padding: 8px;
  }

  .edit-btn, .delete-btn {
    padding: 3px 6px;
    font-size: 0.7rem;
  }
}
</style>