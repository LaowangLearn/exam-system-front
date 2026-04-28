<template>
  <div class="channel-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="3"></circle>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
          </svg>
        </div>
        <h1>渠道管理</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新增渠道</button>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchName" placeholder="搜索渠道名称..." @keyup.enter="loadChannels" />
      </div>
      <select v-model="searchType" @change="loadChannels">
        <option value="">全部类型</option>
        <option value="ONLINE">线上渠道</option>
        <option value="OFFLINE">线下渠道</option>
        <option value="PARTNER">合作伙伴</option>
      </select>
      <button class="search-btn" @click="loadChannels">搜索</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <circle cx="12" cy="12" r="3"></circle>
            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>渠道总数</p>
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
          <p>活跃渠道</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M20 7h-9"></path>
            <path d="M7 10V5a2 2 0 0 1 2-2h6l4 4v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-6l4-4z"></path>
            <polyline points="16 16 16 10 22 10"></polyline>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.revenue }}</h3>
          <p>渠道收入</p>
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
          <h3>{{ stats.commission }}</h3>
          <p>佣金支出</p>
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
            <th class="sortable" @click="sortTable('name')">渠道名称 ↕</th>
            <th>渠道类型</th>
            <th>负责人</th>
            <th>联系方式</th>
            <th>状态</th>
            <th>收入/佣金</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="!loading && channels.length === 0" class="empty-row">
            <td colspan="7" class="empty-cell">暂无渠道数据</td>
          </tr>
          <tr v-for="channel in channels" :key="channel.id" class="data-row">
            <td class="name-cell">{{ channel.name }}</td>
            <td>
              <span :class="['type-tag', channel.type]">{{ getTypeName(channel.type) }}</span>
            </td>
            <td>{{ channel.contact }}</td>
            <td>{{ channel.phone }}</td>
            <td>
              <span :class="['status-tag', channel.status]">{{ getStatusName(channel.status) }}</span>
            </td>
            <td>
              <div class="revenue-info">
                <span class="revenue">收入: {{ channel.revenue }}</span>
                <span class="commission">佣金: {{ channel.commission }}</span>
              </div>
            </td>
            <td class="actions">
              <button class="action-btn view" @click="viewChannel(channel)">查看</button>
              <button class="action-btn edit" @click="editChannel(channel)">编辑</button>
              <button class="action-btn delete" @click="deleteChannel(channel.id)">删除</button>
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
        <h2>{{ editingChannel ? '编辑渠道' : '新增渠道' }}</h2>
        <form @submit.prevent="saveChannel">
          <div class="form-group">
            <label>渠道名称</label>
            <input v-model="formData.name" type="text" required />
          </div>
          <div class="form-group">
            <label>渠道类型</label>
            <select v-model="formData.type">
              <option value="ONLINE">线上渠道</option>
              <option value="OFFLINE">线下渠道</option>
              <option value="PARTNER">合作伙伴</option>
            </select>
          </div>
          <div class="form-group">
            <label>负责人</label>
            <input v-model="formData.contact" type="text" />
          </div>
          <div class="form-group">
            <label>联系电话</label>
            <input v-model="formData.phone" type="tel" />
          </div>
          <div class="form-group">
            <label>渠道描述</label>
            <textarea v-model="formData.description"></textarea>
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
const channels = ref<any[]>([])
const stats = ref({ total: 0, active: 0, revenue: '¥0', commission: '¥0' })
const searchName = ref('')
const searchType = ref('')
const showAddModal = ref(false)
const editingChannel = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('name')
const sortOrder = ref('asc')

const formData = ref({
  name: '',
  type: 'ONLINE',
  contact: '',
  phone: '',
  description: ''
})

const getTypeName = (type: string) => {
  const names: Record<string, string> = {
    ONLINE: '线上渠道',
    OFFLINE: '线下渠道',
    PARTNER: '合作伙伴'
  }
  return names[type] || type
}

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    ACTIVE: '活跃',
    INACTIVE: '停用'
  }
  return names[status] || status
}

const loadChannels = async () => {
  loading.value = true
  try {
    const response = await fetch(`/marketing/channels/search?name=${searchName.value}&type=${searchType.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    channels.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    channels.value = [
      { id: 1, name: '官方网站', type: 'ONLINE', contact: '张三', phone: '13800138001', status: 'ACTIVE', revenue: '¥500,000', commission: '¥50,000' },
      { id: 2, name: '微信公众号', type: 'ONLINE', contact: '李四', phone: '13800138002', status: 'ACTIVE', revenue: '¥800,000', commission: '¥80,000' },
      { id: 3, name: '淘宝旗舰店', type: 'ONLINE', contact: '王五', phone: '13800138003', status: 'ACTIVE', revenue: '¥1,200,000', commission: '¥120,000' },
      { id: 4, name: '线下门店', type: 'OFFLINE', contact: '赵六', phone: '13800138004', status: 'ACTIVE', revenue: '¥600,000', commission: '¥30,000' },
      { id: 5, name: '代理商A', type: 'PARTNER', contact: '孙七', phone: '13800138005', status: 'ACTIVE', revenue: '¥400,000', commission: '¥80,000' },
      { id: 6, name: '京东自营', type: 'ONLINE', contact: '周八', phone: '13800138006', status: 'INACTIVE', revenue: '¥300,000', commission: '¥30,000' }
    ]
    stats.value = { total: 6, active: 5, revenue: '¥3,800,000', commission: '¥390,000' }
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
  loadChannels()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadChannels()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadChannels()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewChannel = (channel: any) => {
  console.log('View channel:', channel)
}

const editChannel = (channel: any) => {
  editingChannel.value = channel
  formData.value = {
    name: channel.name,
    type: channel.type,
    contact: channel.contact,
    phone: channel.phone,
    description: channel.description || ''
  }
  showAddModal.value = true
}

const deleteChannel = async (id: number) => {
  if (confirm('确定要删除这个渠道吗？')) {
    try {
      await fetch(`/marketing/channels/${id}`, { method: 'DELETE' })
      loadChannels()
    } catch (error) {
      loadChannels()
    }
  }
}

const saveChannel = async () => {
  try {
    if (editingChannel.value) {
      await fetch(`/marketing/channels/${editingChannel.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    } else {
      await fetch('/marketing/channels', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadChannels()
  } catch (error) {
    closeModal()
    loadChannels()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingChannel.value = null
  formData.value = {
    name: '',
    type: 'ONLINE',
    contact: '',
    phone: '',
    description: ''
  }
}

onMounted(() => {
  loadChannels()
})
</script>

<style scoped>
.channel-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f3e8ff 0%, #f5f7fa 50%, #ede9fe 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
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
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
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
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
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
}

.stat-icon.primary {
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
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
  background: linear-gradient(135deg, #f3e8ff 0%, #ede9fe 100%);
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

.type-tag, .status-tag {
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.type-tag.ONLINE {
  background: #dbeafe;
  color: #2563eb;
}

.type-tag.OFFLINE {
  background: #fef3c7;
  color: #d97706;
}

.type-tag.PARTNER {
  background: #f3e8ff;
  color: #8b5cf6;
}

.status-tag.ACTIVE {
  background: #d1fae5;
  color: #059669;
}

.status-tag.INACTIVE {
  background: #f3f4f6;
  color: #6b7280;
}

.revenue-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 0.85rem;
}

.revenue {
  color: #059669;
}

.commission {
  color: #d97706;
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
  border-color: #8b5cf6;
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
  background: linear-gradient(135deg, #8b5cf6 0%, #7c3aed 100%);
  color: white;
}

.form-actions button[type="submit"]:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.4);
}

@media (max-width: 768px) {
  .channel-container {
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