<template>
  <div class="scm-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M20 7h-9"></path>
            <path d="M7 10V5a2 2 0 0 1 2-2h6l4 4v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-6l4-4z"></path>
            <polyline points="16 16 16 10 22 10"></polyline>
          </svg>
        </div>
        <h1>SCM供应链管理系统</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新增供应商</button>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchName" placeholder="搜索供应商名称..." @keyup.enter="loadSuppliers" />
      </div>
      <select v-model="searchRating" @change="loadSuppliers">
        <option value="">全部评级</option>
        <option value="A">A级</option>
        <option value="B">B级</option>
        <option value="C">C级</option>
      </select>
      <button class="search-btn" @click="loadSuppliers">搜索</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M20 7h-9"></path>
            <path d="M7 10V5a2 2 0 0 1 2-2h6l4 4v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-6l4-4z"></path>
            <polyline points="16 16 16 10 22 10"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>供应商总数</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon success">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22 4 12 14.01 9 11.01"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.active }}</h3>
          <p>活跃供应商</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M12 8v4l3 3"></path>
            <circle cx="12" cy="12" r="10"></circle>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.aLevel }}</h3>
          <p>A级供应商</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon info">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M12 20V10"></path>
            <path d="M18 20V4"></path>
            <path d="M6 20v-6"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.purchaseAmount }}</h3>
          <p>累计采购额</p>
        </div>
      </div>
    </div>

    <div class="table-container">
      <div v-if="loading" class="loading-overlay">
        <div class="loading-spinner"></div>
        <span>加载中...</span>
      </div>
      <table v-else>
        <thead>
          <tr>
            <th @click="sortTable('supplierCode')" class="sortable">供应商编号</th>
            <th @click="sortTable('name')" class="sortable">供应商名称</th>
            <th>联系人</th>
            <th>电话</th>
            <th>评级</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="suppliers.length === 0" class="empty-row">
            <td colspan="7" class="empty-cell">暂无数据</td>
          </tr>
          <tr v-for="supplier in suppliers" :key="supplier.id" class="data-row">
            <td>{{ supplier.supplierCode }}</td>
            <td class="name-cell">{{ supplier.name }}</td>
            <td>{{ supplier.contactName }}</td>
            <td>{{ supplier.phone }}</td>
            <td><span class="rating-tag" :class="supplier.rating">{{ supplier.rating }}</span></td>
            <td><span class="status-tag" :class="supplier.status">{{ getStatusName(supplier.status) }}</span></td>
            <td class="actions">
              <button class="action-btn view" @click="viewSupplier(supplier)">查看</button>
              <button class="action-btn edit" @click="editSupplier(supplier)">编辑</button>
              <button class="action-btn delete" @click="deleteSupplier(supplier.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="suppliers.length > 0" class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1" class="page-btn">上一页</button>
        <span class="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
        <button @click="nextPage" :disabled="currentPage === totalPages" class="page-btn">下一页</button>
      </div>
    </div>

    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ editingSupplier ? '编辑供应商' : '新增供应商' }}</h2>
        <form @submit.prevent="saveSupplier">
          <div class="form-row">
            <div class="form-group">
              <label>供应商名称</label>
              <input v-model="formData.name" required />
            </div>
            <div class="form-group">
              <label>联系人</label>
              <input v-model="formData.contactName" />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>电话</label>
              <input v-model="formData.phone" />
            </div>
            <div class="form-group">
              <label>邮箱</label>
              <input v-model="formData.email" />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>评级</label>
              <select v-model="formData.rating">
                <option value="A">A级</option>
                <option value="B">B级</option>
                <option value="C">C级</option>
              </select>
            </div>
            <div class="form-group">
              <label>状态</label>
              <select v-model="formData.status">
                <option value="ACTIVE">启用</option>
                <option value="INACTIVE">停用</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label>地址</label>
            <textarea v-model="formData.address"></textarea>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal">取消</button>
            <button type="submit">保存</button>
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
const suppliers = ref<any[]>([])
const stats = ref({ total: 0, active: 0, aLevel: 0, purchaseAmount: '¥0' })
const searchName = ref('')
const searchRating = ref('')
const showAddModal = ref(false)
const editingSupplier = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('supplierCode')
const sortOrder = ref('asc')

const formData = ref({
  name: '',
  contactName: '',
  phone: '',
  email: '',
  rating: 'A',
  status: 'ACTIVE',
  address: ''
})

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    ACTIVE: '活跃',
    INACTIVE: '停用'
  }
  return names[status] || status
}

const loadSuppliers = async () => {
  loading.value = true
  try {
    const response = await fetch(`/scm/suppliers/search?name=${searchName.value}&rating=${searchRating.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    suppliers.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    suppliers.value = [
      { id: 1, supplierCode: 'SUP202602090001', name: '华为技术有限公司', contactName: '陈经理', phone: '13900139001', rating: 'A', status: 'ACTIVE' },
      { id: 2, supplierCode: 'SUP202602090002', name: '阿里巴巴集团', contactName: '周主管', phone: '13900139002', rating: 'A', status: 'ACTIVE' },
      { id: 3, supplierCode: 'SUP202602090003', name: '腾讯科技', contactName: '吴经理', phone: '13900139003', rating: 'B', status: 'ACTIVE' },
      { id: 4, supplierCode: 'SUP202602090004', name: '字节跳动', contactName: '郑总', phone: '13900139004', rating: 'A', status: 'INACTIVE' },
      { id: 5, supplierCode: 'SUP202602090005', name: '小米科技', contactName: '王经理', phone: '13900139005', rating: 'B', status: 'ACTIVE' },
      { id: 6, supplierCode: 'SUP202602090006', name: '京东集团', contactName: '李总监', phone: '13900139006', rating: 'A', status: 'ACTIVE' }
    ]
    stats.value = { total: 6, active: 5, aLevel: 4, purchaseAmount: '¥3,200,000' }
    totalPages.value = 1
  }
  loading.value = false
}

const sortTable = (field: string) => {
  if (sortField.value === field) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortField.value = field
    sortOrder.value = 'asc'
  }
  loadSuppliers()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadSuppliers()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadSuppliers()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewSupplier = (supplier: any) => {
  console.log('View supplier:', supplier)
}

const editSupplier = (supplier: any) => {
  editingSupplier.value = supplier
  formData.value = {
    name: supplier.name,
    contactName: supplier.contactName,
    phone: supplier.phone,
    email: supplier.email || '',
    rating: supplier.rating,
    status: supplier.status,
    address: supplier.address || ''
  }
  showAddModal.value = true
}

const deleteSupplier = async (id: number) => {
  if (confirm('确定删除该供应商？')) {
    try {
      await fetch(`/scm/suppliers/${id}`, { method: 'DELETE' })
      loadSuppliers()
    } catch (error) {
      suppliers.value = suppliers.value.filter(s => s.id !== id)
    }
  }
}

const saveSupplier = async () => {
  try {
    if (editingSupplier.value) {
      await fetch(`/scm/suppliers/${editingSupplier.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...formData.value, id: editingSupplier.value.id })
      })
    } else {
      await fetch('/scm/suppliers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadSuppliers()
  } catch (error) {
    closeModal()
    loadSuppliers()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingSupplier.value = null
  formData.value = {
    name: '',
    contactName: '',
    phone: '',
    email: '',
    rating: 'A',
    status: 'ACTIVE',
    address: ''
  }
}

onMounted(() => {
  loadSuppliers()
})
</script>

<style scoped>
.scm-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f3ff 0%, #f5f7fa 50%, #f0f9ff 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #8b5cf6 0%, #6366f1 100%);
  color: white;
  border: none;
  padding: 12px 28px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.4);
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 8px;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(139, 92, 246, 0.6);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 20px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.header-title {
  display: flex;
  align-items: center;
  gap: 15px;
}

.title-icon {
  color: #8b5cf6;
  font-size: 24px;
}

.page-header h1 {
  font-size: 1.75rem;
  color: #1f2937;
  margin: 0;
  font-weight: 700;
}

.add-btn {
  background: linear-gradient(135deg, #8b5cf6 0%, #6366f1 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(139, 92, 246, 0.4);
}

.search-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 25px;
  align-items: center;
}

.search-input-group {
  flex: 1;
  max-width: 350px;
  position: relative;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #9ca3af;
}

.search-input-group input {
  width: 100%;
  padding: 12px 15px 12px 45px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.search-input-group input:focus {
  outline: none;
  border-color: #8b5cf6;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.search-bar select {
  padding: 12px 20px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 1rem;
  min-width: 140px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-bar select:focus {
  outline: none;
  border-color: #8b5cf6;
}

.search-btn {
  padding: 12px 28px;
  background: linear-gradient(135deg, #8b5cf6 0%, #6366f1 100%);
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(139, 92, 246, 0.4);
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  margin-bottom: 25px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  transition: all 0.3s ease;
}

.stat-icon.primary {
  background: linear-gradient(135deg, #8b5cf6 0%, #6366f1 100%);
}

.stat-icon.success {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
}

.stat-icon.warning {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
}

.stat-icon.info {
  background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%);
}

.stat-content h3 {
  font-size: 1.75rem;
  margin: 0;
  color: #1f2937;
  font-weight: 700;
}

.stat-content p {
  margin: 6px 0 0 0;
  color: #6b7280;
  font-size: 0.9rem;
}

.table-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06);
  overflow: hidden;
  position: relative;
}

.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e5e7eb;
  border-top-color: #8b5cf6;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

table {
  width: 100%;
  border-collapse: collapse;
}

thead {
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
}

th {
  padding: 14px 16px;
  text-align: left;
  font-weight: 600;
  color: #374151;
  font-size: 0.9rem;
}

th.sortable {
  cursor: pointer;
  user-select: none;
}

th.sortable:hover {
  background: rgba(139, 92, 246, 0.1);
}

th.sortable::after {
  content: ' ↕';
  font-size: 0.7rem;
  color: #9ca3af;
  margin-left: 4px;
}

td {
  padding: 14px 16px;
  text-align: left;
  border-bottom: 1px solid #f3f4f6;
  color: #4b5563;
}

.data-row:hover {
  background: #f9fafb;
}

.name-cell {
  font-weight: 600;
  color: #1f2937;
}

.rating-tag, .status-tag {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.rating-tag.A {
  background: #d1fae5;
  color: #059669;
}

.rating-tag.B {
  background: #dbeafe;
  color: #2563eb;
}

.rating-tag.C {
  background: #fef3c7;
  color: #d97706;
}

.status-tag.ACTIVE {
  background: #d1fae5;
  color: #059669;
}

.status-tag.INACTIVE {
  background: #f3f4f6;
  color: #6b7280;
}

.empty-row {
  text-align: center;
}

.empty-cell {
  padding: 40px;
  color: #9ca3af;
}

.actions {
  display: flex;
  gap: 8px;
}

.action-btn {
  padding: 7px 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.8rem;
  font-weight: 500;
  transition: all 0.2s ease;
}

.action-btn.view {
  background: #e0e7ff;
  color: #4338ca;
}

.action-btn.view:hover {
  background: #c7d2fe;
}

.action-btn.edit {
  background: #fef3c7;
  color: #d97706;
}

.action-btn.edit:hover {
  background: #fde68a;
}

.action-btn.delete {
  background: #fee2e2;
  color: #dc2626;
}

.action-btn.delete:hover {
  background: #fecaca;
}

.pagination {
  padding: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  border-top: 1px solid #f3f4f6;
}

.page-btn {
  padding: 8px 16px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  background: white;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s ease;
}

.page-btn:hover:not(:disabled) {
  background: #f3f4f6;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  font-size: 0.9rem;
  color: #6b7280;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}

.modal-content {
  background: white;
  border-radius: 8px;
  padding: 20px;
  width: 90%;
  max-width: 500px;
}

.modal-content h2 {
  margin-top: 0;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
}

.form-group input, .form-group select, .form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-group textarea {
  min-height: 80px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.form-actions button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.form-actions button[type="button"] {
  background: #e2e8f0;
}

.form-actions button[type="submit"] {
  background: #8b5cf6;
  color: white;
}
</style>