<template>
  <div class="campaign-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14.017 21v-7.391c0-5.704 3.731-9.57 8.983-10.609l.995 2.151c-2.432.917-3.995 3.638-3.995 5.849h4v10h-9.983zm-14.017 0v-7.391c0-5.704 3.748-9.57 9-10.609l.996 2.151c-2.433.917-3.996 3.638-3.996 5.849h3.983v10h-9.983z"></path>
          </svg>
        </div>
        <h1>营销活动管理</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新建活动</button>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchName" placeholder="搜索活动名称..." @keyup.enter="loadCampaigns" />
      </div>
      <select v-model="searchStatus" @change="loadCampaigns">
        <option value="">全部状态</option>
        <option value="DRAFT">草稿</option>
        <option value="ACTIVE">进行中</option>
        <option value="ENDED">已结束</option>
      </select>
      <button class="search-btn" @click="loadCampaigns">搜索</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="stat-icon primary">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M14.017 21v-7.391c0-5.704 3.731-9.57 8.983-10.609l.995 2.151c-2.432.917-3.995 3.638-3.995 5.849h4v10h-9.983zm-14.017 0v-7.391c0-5.704 3.748-9.57 9-10.609l.996 2.151c-2.433.917-3.996 3.638-3.996 5.849h3.983v10h-9.983z"></path>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.total }}</h3>
          <p>活动总数</p>
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
          <p>进行中</p>
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
          <h3>{{ stats.participants }}</h3>
          <p>参与人次</p>
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
          <h3>{{ stats.conversion }}</h3>
          <p>转化率</p>
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
            <th class="sortable" @click="sortTable('name')">活动名称 ↕</th>
            <th>活动类型</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>状态</th>
            <th>参与人数</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="!loading && campaigns.length === 0" class="empty-row">
            <td colspan="7" class="empty-cell">暂无活动数据</td>
          </tr>
          <tr v-for="campaign in campaigns" :key="campaign.id" class="data-row">
            <td class="name-cell">{{ campaign.name }}</td>
            <td>
              <span :class="['type-tag', campaign.type]">{{ getTypeName(campaign.type) }}</span>
            </td>
            <td>{{ formatDate(campaign.startDate) }}</td>
            <td>{{ formatDate(campaign.endDate) }}</td>
            <td>
              <span :class="['status-tag', campaign.status]">{{ getStatusName(campaign.status) }}</span>
            </td>
            <td>{{ campaign.participants }}</td>
            <td class="actions">
              <button class="action-btn view" @click="viewCampaign(campaign)">查看</button>
              <button class="action-btn edit" @click="editCampaign(campaign)">编辑</button>
              <button class="action-btn delete" @click="deleteCampaign(campaign.id)">删除</button>
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
        <h2>{{ editingCampaign ? '编辑活动' : '新建活动' }}</h2>
        <form @submit.prevent="saveCampaign">
          <div class="form-group">
            <label>活动名称</label>
            <input v-model="formData.name" type="text" required />
          </div>
          <div class="form-group">
            <label>活动类型</label>
            <select v-model="formData.type">
              <option value="COUPON">优惠券</option>
              <option value="DISCOUNT">折扣活动</option>
              <option value="GIFT">赠品活动</option>
              <option value="SIGNUP">签到活动</option>
            </select>
          </div>
          <div class="form-group">
            <label>开始时间</label>
            <input v-model="formData.startDate" type="datetime-local" required />
          </div>
          <div class="form-group">
            <label>结束时间</label>
            <input v-model="formData.endDate" type="datetime-local" required />
          </div>
          <div class="form-group">
            <label>活动描述</label>
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
const campaigns = ref<any[]>([])
const stats = ref({ total: 0, active: 0, participants: 0, conversion: '0%' })
const searchName = ref('')
const searchStatus = ref('')
const showAddModal = ref(false)
const editingCampaign = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('name')
const sortOrder = ref('asc')

const formData = ref({
  name: '',
  type: 'COUPON',
  startDate: '',
  endDate: '',
  description: ''
})

const getTypeName = (type: string) => {
  const names: Record<string, string> = {
    COUPON: '优惠券',
    DISCOUNT: '折扣活动',
    GIFT: '赠品活动',
    SIGNUP: '签到活动'
  }
  return names[type] || type
}

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    DRAFT: '草稿',
    ACTIVE: '进行中',
    ENDED: '已结束'
  }
  return names[status] || status
}

const formatDate = (date: string) => {
  if (!date) return '-'
  return new Date(date).toLocaleString('zh-CN')
}

const loadCampaigns = async () => {
  loading.value = true
  try {
    const response = await fetch(`/marketing/campaigns/search?name=${searchName.value}&status=${searchStatus.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    campaigns.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    campaigns.value = [
      { id: 1, name: '春节促销活动', type: 'DISCOUNT', status: 'ACTIVE', startDate: '2026-01-20T00:00:00', endDate: '2026-02-15T23:59:59', participants: 12580 },
      { id: 2, name: '新人优惠券礼包', type: 'COUPON', status: 'ACTIVE', startDate: '2026-01-01T00:00:00', endDate: '2026-12-31T23:59:59', participants: 8920 },
      { id: 3, name: '会员生日礼包', type: 'GIFT', status: 'ACTIVE', startDate: '2026-01-01T00:00:00', endDate: '2026-12-31T23:59:59', participants: 3450 },
      { id: 4, name: '周末签到有礼', type: 'SIGNUP', status: 'ACTIVE', startDate: '2026-04-01T00:00:00', endDate: '2026-06-30T23:59:59', participants: 5680 },
      { id: 5, name: '五一特惠活动', type: 'DISCOUNT', status: 'DRAFT', startDate: '2026-05-01T00:00:00', endDate: '2026-05-07T23:59:59', participants: 0 },
      { id: 6, name: '年终感恩回馈', type: 'COUPON', status: 'ENDED', startDate: '2025-12-01T00:00:00', endDate: '2025-12-31T23:59:59', participants: 15680 }
    ]
    stats.value = { total: 6, active: 4, participants: 46210, conversion: '23.5%' }
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
  loadCampaigns()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadCampaigns()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadCampaigns()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewCampaign = (campaign: any) => {
  console.log('View campaign:', campaign)
}

const editCampaign = (campaign: any) => {
  editingCampaign.value = campaign
  formData.value = {
    name: campaign.name,
    type: campaign.type,
    startDate: campaign.startDate?.replace('T', ' ').substring(0, 16) || '',
    endDate: campaign.endDate?.replace('T', ' ').substring(0, 16) || '',
    description: campaign.description || ''
  }
  showAddModal.value = true
}

const deleteCampaign = async (id: number) => {
  if (confirm('确定要删除这个活动吗？')) {
    try {
      await fetch(`/marketing/campaigns/${id}`, { method: 'DELETE' })
      loadCampaigns()
    } catch (error) {
      loadCampaigns()
    }
  }
}

const saveCampaign = async () => {
  try {
    if (editingCampaign.value) {
      await fetch(`/marketing/campaigns/${editingCampaign.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    } else {
      await fetch('/marketing/campaigns', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadCampaigns()
  } catch (error) {
    closeModal()
    loadCampaigns()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingCampaign.value = null
  formData.value = {
    name: '',
    type: 'COUPON',
    startDate: '',
    endDate: '',
    description: ''
  }
}

onMounted(() => {
  loadCampaigns()
})
</script>

<style scoped>
.campaign-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #fdf2f8 0%, #f5f7fa 50%, #fce7f3 100%);
  padding: 30px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  padding: 12px 28px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.4);
  z-index: 10;
  display: flex;
  align-items: center;
  gap: 8px;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.6);
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
  color: #ec4899;
  font-size: 24px;
}

.page-header h1 {
  font-size: 1.75rem;
  color: #1f2937;
  margin: 0;
  font-weight: 700;
}

.add-btn {
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
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
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.3);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.4);
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
  border-color: #ec4899;
  box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.1);
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
  border-color: #ec4899;
}

.search-btn {
  padding: 12px 28px;
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.3);
}

.search-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(236, 72, 153, 0.4);
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
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
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
  border-top-color: #ec4899;
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
  background: linear-gradient(135deg, #fdf2f8 0%, #fce7f3 100%);
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
  background: rgba(236, 72, 153, 0.1);
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

.type-tag.COUPON {
  background: #dbeafe;
  color: #2563eb;
}

.type-tag.DISCOUNT {
  background: #d1fae5;
  color: #059669;
}

.type-tag.GIFT {
  background: #fef3c7;
  color: #d97706;
}

.type-tag.SIGNUP {
  background: #f3e8ff;
  color: #8b5cf6;
}

.status-tag.DRAFT {
  background: #f3f4f6;
  color: #6b7280;
}

.status-tag.ACTIVE {
  background: #d1fae5;
  color: #059669;
}

.status-tag.ENDED {
  background: #fecaca;
  color: #dc2626;
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
  border-color: #ec4899;
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
  background: linear-gradient(135deg, #ec4899 0%, #f43f5e 100%);
  color: white;
}

.form-actions button[type="submit"]:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(236, 72, 153, 0.4);
}

@media (max-width: 768px) {
  .campaign-container {
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