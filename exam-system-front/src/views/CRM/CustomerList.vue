<template>
  <div class="crm-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <h1>CRM客户管理系统</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新增客户</button>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchName" placeholder="搜索客户名称..." @keyup.enter="loadCustomers" />
      </div>
      <select v-model="searchIndustry" @change="loadCustomers">
        <option value="">全部分类</option>
        <option value="IT">IT行业</option>
        <option value="金融">金融</option>
        <option value="制造">制造业</option>
        <option value="零售">零售业</option>
      </select>
      <button class="search-btn" @click="loadCustomers">搜索</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
            <circle cx="9" cy="7" r="4"></circle>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>总客户数</p>
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
          <p>活跃客户</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.vip }}</h3>
          <p>VIP客户</p>
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
          <h3>{{ stats.totalAmount }}</h3>
          <p>累计成交额</p>
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
            <th @click="sortTable('customerCode')" class="sortable">客户编号</th>
            <th @click="sortTable('name')" class="sortable">客户名称</th>
            <th>联系人</th>
            <th>电话</th>
            <th>行业</th>
            <th>等级</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="customers.length === 0" class="empty-row">
            <td colspan="8" class="empty-cell">暂无数据</td>
          </tr>
          <tr v-for="customer in customers" :key="customer.id" class="data-row">
            <td>{{ customer.customerCode }}</td>
            <td class="name-cell">{{ customer.name }}</td>
            <td>{{ customer.contactName }}</td>
            <td>{{ customer.phone }}</td>
            <td>{{ customer.industry }}</td>
            <td><span class="level-tag" :class="customer.level">{{ getLevelName(customer.level) }}</span></td>
            <td><span class="status-tag" :class="customer.status">{{ getStatusName(customer.status) }}</span></td>
            <td class="actions">
              <button class="action-btn view" @click="viewCustomer(customer)">查看</button>
              <button class="action-btn edit" @click="editCustomer(customer)">编辑</button>
              <button class="action-btn delete" @click="deleteCustomer(customer.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="customers.length > 0" class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1" class="page-btn">上一页</button>
        <span class="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
        <button @click="nextPage" :disabled="currentPage === totalPages" class="page-btn">下一页</button>
      </div>
    </div>

    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ editingCustomer ? '编辑客户' : '新增客户' }}</h2>
        <form @submit.prevent="saveCustomer">
          <div class="form-row">
            <div class="form-group">
              <label>客户名称</label>
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
              <label>行业</label>
              <select v-model="formData.industry">
                <option value="IT">IT行业</option>
                <option value="金融">金融</option>
                <option value="制造">制造业</option>
                <option value="零售">零售业</option>
              </select>
            </div>
            <div class="form-group">
              <label>等级</label>
              <select v-model="formData.level">
                <option value="VIP">VIP</option>
                <option value="NORMAL">普通</option>
                <option value="POTENTIAL">潜力</option>
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
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const customers = ref<any[]>([])
const stats = ref({ total: 0, active: 0, vip: 0, totalAmount: '¥0' })
const searchName = ref('')
const searchIndustry = ref('')
const showAddModal = ref(false)
const editingCustomer = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('customerCode')
const sortOrder = ref('asc')

const formData = ref({
  name: '',
  contactName: '',
  phone: '',
  email: '',
  industry: 'IT',
  level: 'NORMAL',
  address: ''
})

const getLevelName = (level: string) => {
  const names: Record<string, string> = {
    VIP: 'VIP',
    NORMAL: '普通',
    POTENTIAL: '潜力'
  }
  return names[level] || level
}

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    ACTIVE: '活跃',
    POTENTIAL: '潜在',
    INACTIVE: '休眠'
  }
  return names[status] || status
}

const loadCustomers = async () => {
  loading.value = true
  try {
    const response = await fetch(`/crm/customers/search?name=${searchName.value}&industry=${searchIndustry.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    customers.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    customers.value = [
      { id: 1, customerCode: 'CUST202602090001', name: '北京科技有限公司', contactName: '张三', phone: '13800138001', industry: 'IT', level: 'VIP', status: 'ACTIVE' },
      { id: 2, customerCode: 'CUST202602090002', name: '上海金融集团', contactName: '李四', phone: '13800138002', industry: '金融', level: 'VIP', status: 'ACTIVE' },
      { id: 3, customerCode: 'CUST202602090003', name: '广州制造企业', contactName: '王五', phone: '13800138003', industry: '制造', level: 'NORMAL', status: 'ACTIVE' },
      { id: 4, customerCode: 'CUST202602090004', name: '深圳零售连锁', contactName: '赵六', phone: '13800138004', industry: '零售', level: 'POTENTIAL', status: 'POTENTIAL' },
      { id: 5, customerCode: 'CUST202602090005', name: '杭州电商公司', contactName: '孙七', phone: '13800138005', industry: 'IT', level: 'NORMAL', status: 'ACTIVE' },
      { id: 6, customerCode: 'CUST202602090006', name: '成都物流集团', contactName: '周八', phone: '13800138006', industry: '制造', level: 'VIP', status: 'ACTIVE' }
    ]
    stats.value = { total: 6, active: 5, vip: 3, totalAmount: '¥2,850,000' }
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
  loadCustomers()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadCustomers()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadCustomers()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewCustomer = (customer: any) => {
  console.log('View customer:', customer)
}

const editCustomer = (customer: any) => {
  editingCustomer.value = customer
  formData.value = {
    name: customer.name,
    contactName: customer.contactName,
    phone: customer.phone,
    email: customer.email || '',
    industry: customer.industry,
    level: customer.level,
    address: customer.address || ''
  }
  showAddModal.value = true
}

const deleteCustomer = async (id: number) => {
  if (confirm('确定删除该客户？')) {
    try {
      await fetch(`/crm/customers/${id}`, { method: 'DELETE' })
      loadCustomers()
    } catch (error) {
      customers.value = customers.value.filter(c => c.id !== id)
    }
  }
}

const saveCustomer = async () => {
  try {
    if (editingCustomer.value) {
      await fetch(`/crm/customers/${editingCustomer.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...formData.value, id: editingCustomer.value.id })
      })
    } else {
      await fetch('/crm/customers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadCustomers()
  } catch (error) {
    closeModal()
    loadCustomers()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingCustomer.value = null
  formData.value = {
    name: '',
    contactName: '',
    phone: '',
    email: '',
    industry: 'IT',
    level: 'NORMAL',
    address: ''
  }
}

onMounted(() => {
  loadCustomers()
})
</script>

<style scoped>
.crm-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f0f5ff 0%, #f5f7fa 50%, #fff5f5 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 12px 28px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 8px;
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
  color: #667eea;
  font-size: 24px;
}

.page-header h1 {
  font-size: 1.75rem;
  color: #1f2937;
  margin: 0;
  font-weight: 700;
}

.add-btn {
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
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
  box-shadow: 0 4px 15px rgba(16, 185, 129, 0.3);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(16, 185, 129, 0.4);
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
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
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
  border-color: #667eea;
}

.search-btn {
  padding: 12px 28px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
  border-top-color: #667eea;
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
  position: relative;
}

th.sortable {
  cursor: pointer;
  user-select: none;
}

th.sortable:hover {
  background: rgba(102, 126, 234, 0.1);
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

.level-tag, .status-tag {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.level-tag.VIP {
  background: #fef3c7;
  color: #d97706;
}

.level-tag.NORMAL {
  background: #dbeafe;
  color: #2563eb;
}

.level-tag.POTENTIAL {
  background: #d1fae5;
  color: #059669;
}

.status-tag.ACTIVE {
  background: #d1fae5;
  color: #059669;
}

.status-tag.POTENTIAL {
  background: #fef3c7;
  color: #d97706;
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
  background: #667eea;
  color: white;
}
</style>