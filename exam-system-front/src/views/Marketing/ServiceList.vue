<template>
  <div class="service-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <h1>客户服务工单</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新建工单</button>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchKeyword" placeholder="搜索工单编号/客户名称..." @keyup.enter="loadServices" />
      </div>
      <select v-model="searchStatus" @change="loadServices">
        <option value="">全部状态</option>
        <option value="PENDING">待处理</option>
        <option value="PROCESSING">处理中</option>
        <option value="RESOLVED">已解决</option>
        <option value="CLOSED">已关闭</option>
      </select>
      <select v-model="searchPriority" @change="loadServices">
        <option value="">全部优先级</option>
        <option value="LOW">低</option>
        <option value="MEDIUM">中</option>
        <option value="HIGH">高</option>
        <option value="URGENT">紧急</option>
      </select>
      <button class="search-btn" @click="loadServices">搜索</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>工单总数</p>
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
          <h3>{{ stats.resolved }}</h3>
          <p>已解决</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.processing }}</h3>
          <p>处理中</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon info">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <circle cx="12" cy="12" r="10"></circle>
            <polyline points="12 6 12 12 16 14"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.slaRate }}%</h3>
          <p>SLA达标率</p>
        </div>
      </div>
    </div>

    <div class="table-container">
      <div v-if="loading" class="loading-overlay">
        <div class="loading-spinner"></div>
        <p>加载中...</p>
      </div>
      <table>
        <thead>
          <tr>
            <th class="sortable" @click="sortTable('ticketNo')">工单编号 ↕</th>
            <th>客户名称</th>
            <th>工单类型</th>
            <th>优先级</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="!loading && services.length === 0" class="empty-row">
            <td colspan="7" class="empty-cell">暂无工单数据</td>
          </tr>
          <tr v-for="service in services" :key="service.id" class="data-row">
            <td class="name-cell">{{ service.ticketNo }}</td>
            <td>{{ service.customerName }}</td>
            <td>
              <span :class="['type-tag', service.type]">{{ getTypeName(service.type) }}</span>
            </td>
            <td>
              <span :class="['priority-tag', service.priority]">{{ getPriorityName(service.priority) }}</span>
            </td>
            <td>
              <span :class="['status-tag', service.status]">{{ getStatusName(service.status) }}</span>
            </td>
            <td>{{ formatDate(service.createdAt) }}</td>
            <td class="actions">
              <button class="action-btn view" @click="viewService(service)">查看</button>
              <button class="action-btn edit" @click="editService(service)">处理</button>
              <button class="action-btn delete" @click="deleteService(service.id)">关闭</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button class="page-btn" :disabled="currentPage <= 1" @click="prevPage">上一页</button>
        <span class="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
        <button class="page-btn" :disabled="currentPage >= totalPages" @click="nextPage">下一页</button>
      </div>
    </div>

    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ editingService ? '处理工单' : '新建工单' }}</h2>
        <form @submit.prevent="saveService">
          <div class="form-group">
            <label>客户名称</label>
            <input v-model="formData.customerName" type="text" required />
          </div>
          <div class="form-group">
            <label>工单类型</label>
            <select v-model="formData.type">
              <option value="COMPLAINT">投诉</option>
              <option value="INQUIRY">咨询</option>
              <option value="SUGGESTION">建议</option>
              <option value="BUG">问题反馈</option>
            </select>
          </div>
          <div class="form-group">
            <label>优先级</label>
            <select v-model="formData.priority">
              <option value="LOW">低</option>
              <option value="MEDIUM">中</option>
              <option value="HIGH">高</option>
              <option value="URGENT">紧急</option>
            </select>
          </div>
          <div class="form-group">
            <label>问题描述</label>
            <textarea v-model="formData.description" required></textarea>
          </div>
          <div class="form-group" v-if="editingService">
            <label>处理结果</label>
            <textarea v-model="formData.result"></textarea>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal">取消</button>
            <button type="submit">{{ editingService ? '保存处理结果' : '提交工单' }}</button>
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
const services = ref<any[]>([])
const stats = ref({ total: 0, resolved: 0, processing: 0, slaRate: 0 })
const searchKeyword = ref('')
const searchStatus = ref('')
const searchPriority = ref('')
const showAddModal = ref(false)
const editingService = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('ticketNo')
const sortOrder = ref('desc')

const formData = ref({
  customerName: '',
  type: 'INQUIRY',
  priority: 'MEDIUM',
  description: '',
  result: ''
})

const getTypeName = (type: string) => {
  const names: Record<string, string> = {
    COMPLAINT: '投诉',
    INQUIRY: '咨询',
    SUGGESTION: '建议',
    BUG: '问题反馈'
  }
  return names[type] || type
}

const getPriorityName = (priority: string) => {
  const names: Record<string, string> = {
    LOW: '低',
    MEDIUM: '中',
    HIGH: '高',
    URGENT: '紧急'
  }
  return names[priority] || priority
}

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    PENDING: '待处理',
    PROCESSING: '处理中',
    RESOLVED: '已解决',
    CLOSED: '已关闭'
  }
  return names[status] || status
}

const formatDate = (date: string) => {
  if (!date) return '-'
  return new Date(date).toLocaleString('zh-CN')
}

const loadServices = async () => {
  loading.value = true
  try {
    const response = await fetch(`/marketing/services/search?keyword=${searchKeyword.value}&status=${searchStatus.value}&priority=${searchPriority.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    services.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    services.value = [
      { id: 1, ticketNo: 'TICKET20260428001', customerName: '张三', type: 'COMPLAINT', priority: 'HIGH', status: 'PROCESSING', createdAt: '2026-04-28T09:30:00' },
      { id: 2, ticketNo: 'TICKET20260428002', customerName: '李四', type: 'INQUIRY', priority: 'MEDIUM', status: 'PENDING', createdAt: '2026-04-28T10:15:00' },
      { id: 3, ticketNo: 'TICKET20260427003', customerName: '王五', type: 'BUG', priority: 'URGENT', status: 'RESOLVED', createdAt: '2026-04-27T14:20:00' },
      { id: 4, ticketNo: 'TICKET20260427004', customerName: '赵六', type: 'SUGGESTION', priority: 'LOW', status: 'RESOLVED', createdAt: '2026-04-27T11:00:00' },
      { id: 5, ticketNo: 'TICKET20260426005', customerName: '孙七', type: 'INQUIRY', priority: 'MEDIUM', status: 'CLOSED', createdAt: '2026-04-26T16:45:00' },
      { id: 6, ticketNo: 'TICKET20260426006', customerName: '周八', type: 'COMPLAINT', priority: 'HIGH', status: 'PROCESSING', createdAt: '2026-04-26T09:00:00' }
    ]
    stats.value = { total: 6, resolved: 2, processing: 2, slaRate: 95 }
    totalPages.value = 1
  }
  loading.value = false
}

const sortTable = (field: string) => {
  if (sortField.value === field) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortField.value = field
    sortOrder.value = 'desc'
  }
  loadServices()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadServices()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadServices()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewService = (service: any) => {
  console.log('View service:', service)
}

const editService = (service: any) => {
  editingService.value = service
  formData.value = {
    customerName: service.customerName,
    type: service.type,
    priority: service.priority,
    description: service.description || '',
    result: ''
  }
  showAddModal.value = true
}

const deleteService = async (id: number) => {
  if (confirm('确定要关闭这个工单吗？')) {
    try {
      await fetch(`/marketing/services/${id}/close`, { method: 'POST' })
      loadServices()
    } catch (error) {
      loadServices()
    }
  }
}

const saveService = async () => {
  try {
    if (editingService.value) {
      await fetch(`/marketing/services/${editingService.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    } else {
      await fetch('/marketing/services', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadServices()
  } catch (error) {
    closeModal()
    loadServices()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingService.value = null
  formData.value = {
    customerName: '',
    type: 'INQUIRY',
    priority: 'MEDIUM',
    description: '',
    result: ''
  }
}

onMounted(() => {
  loadServices()
})
</script>

<style scoped>
.service-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #ccfbf1 0%, #f5f7fa 50%, #d1fae5 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #14b8a6 0%, #0d9488 100%);
  color: white;
  border: none;
  padding: 12px 28px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(20, 184, 166, 0.4);
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 8px;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(20, 184, 166, 0.6);
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
  color: #14b8a6;
  font-size: 24px;
}

.page-header h1 {
  font-size: 1.75rem;
  color: #1f2937;
  margin: 0;
  font-weight: 700;
}

.add-btn {
  background: linear-gradient(135deg, #14b8a6 0%, #0d9488 100%);
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
  box-shadow: 0 4px 15px rgba(20, 184, 166, 0.3);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(20, 184, 166, 0.4);
}

.search-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 25px;
  align-items: center;
  flex-wrap: wrap;
}

.search-input-group {
  flex: 1;
  max-width: 300px;
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
  border-color: #14b8a6;
  box-shadow: 0 0 0 3px rgba(20, 184, 166, 0.1);
}

.search-bar select {
  padding: 12px 20px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 1rem;
  min-width: 120px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.search-bar select:focus {
  outline: none;
  border-color: #14b8a6;
}

.search-btn {
  padding: 12px 28px;
  background: linear-gradient(135deg, #14b8a6 0%, #0d9488 100%);
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(20, 184, 166, 0.3);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(20, 184, 166, 0.4);
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
}

.stat-icon.primary {
  background: linear-gradient(135deg, #14b8a6 0%, #0d9488 100%);
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
  border-top-color: #14b8a6;
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
  background: linear-gradient(135deg, #ccfbf1 0%, #d1fae5 100%);
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
  background: rgba(20, 184, 166, 0.1);
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

.type-tag, .priority-tag, .status-tag {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.type-tag.COMPLAINT {
  background: #fee2e2;
  color: #dc2626;
}

.type-tag.INQUIRY {
  background: #dbeafe;
  color: #2563eb;
}

.type-tag.SUGGESTION {
  background: #d1fae5;
  color: #059669;
}

.type-tag.BUG {
  background: #fef3c7;
  color: #d97706;
}

.priority-tag.LOW {
  background: #f3f4f6;
  color: #6b7280;
}

.priority-tag.MEDIUM {
  background: #dbeafe;
  color: #2563eb;
}

.priority-tag.HIGH {
  background: #fef3c7;
  color: #d97706;
}

.priority-tag.URGENT {
  background: #fee2e2;
  color: #dc2626;
}

.status-tag.PENDING {
  background: #fef3c7;
  color: #d97706;
}

.status-tag.PROCESSING {
  background: #dbeafe;
  color: #2563eb;
}

.status-tag.RESOLVED {
  background: #d1fae5;
  color: #059669;
}

.status-tag.CLOSED {
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
  gap: 6px;
  flex-wrap: wrap;
}

.action-btn {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.75rem;
  font-weight: 500;
  transition: all 0.2s ease;
}

.action-btn.view {
  background: #d1fae5;
  color: #059669;
}

.action-btn.view:hover {
  background: #a7f3d0;
}

.action-btn.edit {
  background: #dbeafe;
  color: #2563eb;
}

.action-btn.edit:hover {
  background: #bfdbfe;
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
  border-radius: 12px;
  padding: 30px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-content h2 {
  margin: 0 0 25px;
  color: #1f2937;
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #374151;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 12px;
  border: 2px solid #e5e7eb;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #14b8a6;
}

.form-group textarea {
  resize: vertical;
  min-height: 80px;
}

.form-actions {
  display: flex;
  gap: 15px;
  justify-content: flex-end;
  margin-top: 25px;
}

.form-actions button {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
}

.form-actions button[type="button"] {
  background: #f3f4f6;
  color: #6b7280;
}

.form-actions button[type="button"]:hover {
  background: #e5e7eb;
}

.form-actions button[type="submit"] {
  background: linear-gradient(135deg, #14b8a6 0%, #0d9488 100%);
  color: white;
}

.form-actions button[type="submit"]:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(20, 184, 166, 0.4);
}

@media (max-width: 768px) {
  .service-container {
    padding: 20px;
  }

  .search-bar {
    flex-direction: column;
    align-items: stretch;
  }

  .search-input-group {
    max-width: 100%;
  }
}
</style>