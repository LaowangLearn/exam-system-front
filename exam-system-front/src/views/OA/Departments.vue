<template>
  <div class="oa-departments">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>部门管理</h1>
      <button class="add-btn" @click="openAddDepartmentDialog">添加部门</button>
    </div>

    <div class="department-tree">
      <div class="tree-container">
        <div v-for="dept in rootDepartments" :key="dept.id" class="department-node">
          <div class="department-header" @click="toggleDepartment(dept.id)">
            <span class="toggle-icon">{{ expandedDepartments.includes(dept.id) ? '▼' : '▶' }}</span>
            <span class="department-name">{{ dept.name }}</span>
            <div class="department-actions">
              <button class="edit-btn" @click.stop="openEditDepartmentDialog(dept)">编辑</button>
              <button class="delete-btn" @click.stop="deleteDepartment(dept.id)">删除</button>
              <button class="add-sub-btn" @click.stop="openAddSubDepartmentDialog(dept.id)">添加子部门</button>
            </div>
          </div>
          <div v-if="expandedDepartments.includes(dept.id)" class="sub-departments">
            <div v-for="subDept in getSubDepartments(dept.id)" :key="subDept.id" class="department-node sub-node">
              <div class="department-header">
                <span class="department-name">{{ subDept.name }}</span>
                <div class="department-actions">
                  <button class="edit-btn" @click.stop="openEditDepartmentDialog(subDept)">编辑</button>
                  <button class="delete-btn" @click.stop="deleteDepartment(subDept.id)">删除</button>
                  <button class="add-sub-btn" @click.stop="openAddSubDepartmentDialog(subDept.id)">添加子部门</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑部门对话框 -->
    <div v-if="showDepartmentDialog" class="dialog-overlay" @click="closeDepartmentDialog">
      <div class="dialog" @click.stop>
        <h2>{{ editingDepartment ? '编辑部门' : (parentDepartmentId ? '添加子部门' : '添加部门') }}</h2>
        <form @submit.prevent="saveDepartment">
          <div class="form-group">
            <label>部门名称</label>
            <input type="text" v-model="form.name" required />
          </div>
          <div class="form-group" v-if="!parentDepartmentId">
            <label>上级部门</label>
            <select v-model="form.parentId">
              <option value="">无（顶级部门）</option>
              <option v-for="dept in departments" :key="dept.id" :value="dept.id">
                {{ dept.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>部门描述</label>
            <textarea v-model="form.description" rows="3"></textarea>
          </div>
          <div class="dialog-actions">
            <button type="button" class="cancel-btn" @click="closeDepartmentDialog">取消</button>
            <button type="submit" class="save-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/enterprise')
}

// 部门列表
const departments = ref([
  { id: 1, name: '总经办', parentId: null, description: '公司总部' },
  { id: 2, name: '技术部', parentId: 1, description: '负责技术开发' },
  { id: 3, name: '人事部', parentId: 1, description: '负责人员管理' },
  { id: 4, name: '财务部', parentId: 1, description: '负责财务管理' }
])

// 展开的部门
const expandedDepartments = ref([1])

// 对话框状态
const showDepartmentDialog = ref(false)
const editingDepartment = ref(false)
const parentDepartmentId = ref<number | null>(null)
const form = ref({
  id: 0,
  name: '',
  parentId: null,
  description: ''
})

// 根部门（没有上级部门的部门）
const rootDepartments = computed(() => {
  return departments.value.filter(dept => dept.parentId === null)
})

// 切换部门展开/折叠
const toggleDepartment = (id: number) => {
  if (expandedDepartments.value.includes(id)) {
    expandedDepartments.value = expandedDepartments.value.filter(deptId => deptId !== id)
  } else {
    expandedDepartments.value.push(id)
  }
}

// 获取子部门
const getSubDepartments = (parentId: number) => {
  return departments.value.filter(dept => dept.parentId === parentId)
}

// 打开添加部门对话框
const openAddDepartmentDialog = () => {
  editingDepartment.value = false
  parentDepartmentId.value = null
  form.value = {
    id: 0,
    name: '',
    parentId: null,
    description: ''
  }
  showDepartmentDialog.value = true
}

// 打开添加子部门对话框
const openAddSubDepartmentDialog = (parentId: number) => {
  editingDepartment.value = false
  parentDepartmentId.value = parentId
  form.value = {
    id: 0,
    name: '',
    parentId: parentId,
    description: ''
  }
  showDepartmentDialog.value = true
}

// 打开编辑部门对话框
const openEditDepartmentDialog = (dept: any) => {
  editingDepartment.value = true
  parentDepartmentId.value = null
  form.value = { ...dept }
  showDepartmentDialog.value = true
}

// 关闭部门对话框
const closeDepartmentDialog = () => {
  showDepartmentDialog.value = false
}

// 保存部门
const saveDepartment = () => {
  if (editingDepartment.value) {
    // 编辑现有部门
    const index = departments.value.findIndex(d => d.id === form.value.id)
    if (index !== -1) {
      departments.value[index] = { ...form.value }
    }
  } else {
    // 添加新部门
    const newDept = {
      ...form.value,
      id: departments.value.length + 1
    }
    departments.value.push(newDept)
  }
  closeDepartmentDialog()
}

// 删除部门
const deleteDepartment = (id: number) => {
  // 检查是否有子部门
  const hasSubDepartments = departments.value.some(dept => dept.parentId === id)
  if (hasSubDepartments) {
    alert('该部门下有子部门，无法删除')
    return
  }

  if (confirm('确定要删除这个部门吗？')) {
    departments.value = departments.value.filter(d => d.id !== id)
  }
}

onMounted(() => {
  // 加载部门数据
  console.log('加载部门数据')
})
</script>

<style scoped>
.oa-departments {
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

.department-tree {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.tree-container {
  margin-left: 20px;
}

.department-node {
  margin-bottom: 10px;
}

.department-header {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.2s;
}

.department-header:hover {
  background: #f8f9fa;
}

.toggle-icon {
  width: 20px;
  text-align: center;
  font-size: 0.8rem;
  color: #666;
}

.department-name {
  flex: 1;
  font-size: 1rem;
  color: #333;
}

.department-actions {
  display: flex;
  gap: 5px;
}

.edit-btn, .delete-btn, .add-sub-btn {
  padding: 3px 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.7rem;
}

.edit-btn {
  background: #667eea;
  color: white;
}

.delete-btn {
  background: #dc3545;
  color: white;
}

.add-sub-btn {
  background: #28a745;
  color: white;
}

.sub-departments {
  margin-left: 20px;
  margin-top: 10px;
}

.sub-node {
  margin-left: 20px;
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
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group textarea {
  resize: vertical;
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
  .department-actions {
    flex-wrap: wrap;
  }

  .edit-btn, .delete-btn, .add-sub-btn {
    font-size: 0.6rem;
    padding: 2px 6px;
  }
}
</style>