<template>
  <div class="member-container">
    <button class="back-btn" @click="goBack">← 返回营销客户类</button>
    <div class="page-header">
      <div class="header-title">
        <div class="title-icon">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
          </svg>
        </div>
        <h1>会员管理系统</h1>
      </div>
      <button class="add-btn" @click="showAddModal = true">+ 新增会员</button>
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
          <p>会员总数</p>
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
          <p>活跃会员</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon warning">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M11.08 9H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-5.08"></path>
            <path d="M18 5V3a2 2 0 00-2-2h-2a2 2 0 00-2 2v2"></path>
            <circle cx="18" cy="18" r="2"></circle>
            <circle cx="6" cy="18" r="2"></circle>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalPoints }}</h3>
          <p>累计积分</p>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon info">
          <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2">
            <path d="M12 8v4l3 3"></path>
            <circle cx="12" cy="12" r="10"></circle>
          </svg>
        </div>
        <div class="stat-content">
          <h3>{{ stats.totalConsume }}</h3>
          <p>累计消费</p>
        </div>
      </div>
    </div>

    <div class="search-bar">
      <div class="search-input-group">
        <svg class="search-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="11" cy="11" r="8"></circle>
          <path d="M21 21l-4.35-4.35"></path>
        </svg>
        <input v-model="searchPhone" placeholder="搜索手机号..." @keyup.enter="loadMembers" />
      </div>
      <select v-model="searchLevel" @change="loadMembers">
        <option value="">全部等级</option>
        <option value="VIP">VIP会员</option>
        <option value="GOLD">黄金会员</option>
        <option value="SILVER">白银会员</option>
        <option value="BRONZE">青铜会员</option>
      </select>
      <button class="search-btn" @click="loadMembers">搜索</button>
    </div>

    <div class="table-container">
      <div v-if="loading" class="loading-overlay">
        <div class="loading-spinner"></div>
        <span>加载中...</span>
      </div>
      <table v-else>
        <thead>
          <tr>
            <th @click="sortTable('memberNo')" class="sortable">会员编号</th>
            <th @click="sortTable('name')" class="sortable">会员姓名</th>
            <th>手机号</th>
            <th>等级</th>
            <th>积分</th>
            <th>累计消费</th>
            <th>订单数</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="members.length === 0" class="empty-row">
            <td colspan="9" class="empty-cell">暂无数据</td>
          </tr>
          <tr v-for="member in members" :key="member.id" class="data-row">
            <td>{{ member.memberNo }}</td>
            <td class="name-cell">{{ member.name }}</td>
            <td>{{ member.phone }}</td>
            <td><span class="level-tag" :class="member.level">{{ getLevelName(member.level) }}</span></td>
            <td>{{ member.points }}</td>
            <td>¥{{ member.totalConsume }}</td>
            <td>{{ member.orderCount }}</td>
            <td><span class="status-tag" :class="member.status">{{ getStatusName(member.status) }}</span></td>
            <td class="actions">
              <button class="action-btn view" @click="viewMember(member)">查看</button>
              <button class="action-btn edit" @click="editMember(member)">编辑</button>
              <button class="action-btn points" @click="addPoints(member)">充值积分</button>
              <button class="action-btn delete" @click="deleteMember(member.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="members.length > 0" class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1" class="page-btn">上一页</button>
        <span class="page-info">第 {{ currentPage }} / {{ totalPages }} 页</span>
        <button @click="nextPage" :disabled="currentPage === totalPages" class="page-btn">下一页</button>
      </div>
    </div>

    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ editingMember ? '编辑会员' : '新增会员' }}</h2>
        <form @submit.prevent="saveMember">
          <div class="form-row">
            <div class="form-group">
              <label>会员姓名</label>
              <input v-model="formData.name" required />
            </div>
            <div class="form-group">
              <label>手机号</label>
              <input v-model="formData.phone" required />
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label>邮箱</label>
              <input v-model="formData.email" />
            </div>
            <div class="form-group">
              <label>性别</label>
              <select v-model="formData.gender">
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label>备注</label>
            <textarea v-model="formData.remark"></textarea>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal">取消</button>
            <button type="submit">保存</button>
          </div>
        </form>
      </div>
    </div>

    <div v-if="showPointsModal" class="modal-overlay" @click.self="closePointsModal">
      <div class="modal-content">
        <h2>充值积分</h2>
        <p>会员：{{ selectedMember?.name }} ({{ selectedMember?.phone }})</p>
        <p>当前积分：{{ selectedMember?.points }}</p>
        <form @submit.prevent="savePoints">
          <div class="form-group">
            <label>充值积分</label>
            <input v-model="pointsForm.points" type="number" required />
          </div>
          <div class="form-group">
            <label>来源</label>
            <select v-model="pointsForm.source">
              <option value="充值">充值</option>
              <option value="活动奖励">活动奖励</option>
              <option value="消费返利">消费返利</option>
            </select>
          </div>
          <div class="form-group">
            <label>备注</label>
            <input v-model="pointsForm.remark" />
          </div>
          <div class="form-actions">
            <button type="button" @click="closePointsModal">取消</button>
            <button type="submit">确认充值</button>
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
const members = ref<any[]>([])
const stats = ref({ total: 0, active: 0, totalPoints: 0, totalConsume: '¥0' })
const searchPhone = ref('')
const searchLevel = ref('')
const showAddModal = ref(false)
const showPointsModal = ref(false)
const editingMember = ref<any>(null)
const selectedMember = ref<any>(null)
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const totalPages = ref(1)
const sortField = ref('memberNo')
const sortOrder = ref('asc')

const formData = ref({
  name: '',
  phone: '',
  email: '',
  gender: '男',
  remark: ''
})
const pointsForm = ref({
  points: '',
  source: '充值',
  remark: ''
})

const getLevelName = (level: string) => {
  const names: Record<string, string> = {
    VIP: 'VIP会员',
    GOLD: '黄金会员',
    SILVER: '白银会员',
    BRONZE: '青铜会员'
  }
  return names[level] || level
}

const getStatusName = (status: string) => {
  const names: Record<string, string> = {
    ACTIVE: '活跃',
    INACTIVE: '休眠'
  }
  return names[status] || status
}

const loadMembers = async () => {
  loading.value = true
  try {
    const response = await fetch(`/member/members/search?phone=${searchPhone.value}&level=${searchLevel.value}&page=${currentPage.value}&size=${pageSize.value}&sort=${sortField.value},${sortOrder.value}`)
    const result = await response.json()
    members.value = result.data || result
    totalPages.value = result.totalPages || 1
  } catch (error) {
    members.value = [
      { id: 1, memberNo: 'MEM202602090001', name: '张三', phone: '13800138001', level: 'VIP', points: 10000, totalConsume: 50000, orderCount: 20, status: 'ACTIVE' },
      { id: 2, memberNo: 'MEM202602090002', name: '李四', phone: '13800138002', level: 'GOLD', points: 5000, totalConsume: 20000, orderCount: 10, status: 'ACTIVE' },
      { id: 3, memberNo: 'MEM202602090003', name: '王五', phone: '13800138003', level: 'SILVER', points: 2000, totalConsume: 8000, orderCount: 5, status: 'ACTIVE' },
      { id: 4, memberNo: 'MEM202602090004', name: '赵六', phone: '13800138004', level: 'BRONZE', points: 500, totalConsume: 2000, orderCount: 2, status: 'INACTIVE' },
      { id: 5, memberNo: 'MEM202602090005', name: '孙七', phone: '13800138005', level: 'VIP', points: 15000, totalConsume: 80000, orderCount: 35, status: 'ACTIVE' },
      { id: 6, memberNo: 'MEM202602090006', name: '周八', phone: '13800138006', level: 'GOLD', points: 8000, totalConsume: 35000, orderCount: 15, status: 'ACTIVE' }
    ]
    stats.value = { total: 6, active: 5, totalPoints: 40500, totalConsume: '¥195,000' }
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
  loadMembers()
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadMembers()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    loadMembers()
  }
}

const goBack = () => {
  router.push('/marketing')
}

const viewMember = (member: any) => {
  console.log('View member:', member)
}

const editMember = (member: any) => {
  editingMember.value = member
  formData.value = {
    name: member.name,
    phone: member.phone,
    email: member.email || '',
    gender: member.gender || '男',
    remark: ''
  }
  showAddModal.value = true
}

const addPoints = (member: any) => {
  selectedMember.value = member
  pointsForm.value = {
    points: '',
    source: '充值',
    remark: ''
  }
  showPointsModal.value = true
}

const deleteMember = async (id: number) => {
  if (confirm('确定删除该会员？')) {
    try {
      await fetch(`/member/members/${id}`, { method: 'DELETE' })
      loadMembers()
    } catch (error) {
      members.value = members.value.filter(m => m.id !== id)
    }
  }
}

const saveMember = async () => {
  try {
    if (editingMember.value) {
      await fetch(`/member/members/${editingMember.value.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...formData.value, id: editingMember.value.id })
      })
    } else {
      await fetch('/member/members', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData.value)
      })
    }
    closeModal()
    loadMembers()
  } catch (error) {
    closeModal()
    loadMembers()
  }
}

const savePoints = async () => {
  try {
    await fetch(`/member/members/${selectedMember.value.id}/points/add?points=${pointsForm.value.points}&source=${pointsForm.value.source}&remark=${pointsForm.value.remark}`, {
      method: 'POST'
    })
    closePointsModal()
    loadMembers()
  } catch (error) {
    closePointsModal()
    loadMembers()
  }
}

const closeModal = () => {
  showAddModal.value = false
  editingMember.value = null
  formData.value = {
    name: '',
    phone: '',
    email: '',
    gender: '男',
    remark: ''
  }
}

const closePointsModal = () => {
  showPointsModal.value = false
  selectedMember.value = null
  pointsForm.value = {
    points: '',
    source: '充值',
    remark: ''
  }
}

onMounted(() => {
  loadMembers()
})
</script>

<style scoped>
.member-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f0fdf4 0%, #f5f7fa 50%, #ecfdf5 100%);
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
  border-color: #14b8a6;
  box-shadow: 0 0 0 3px rgba(20, 184, 166, 0.1);
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

.level-tag.GOLD {
  background: #fcd34d;
  color: #b45309;
}

.level-tag.SILVER {
  background: #e5e7eb;
  color: #4b5563;
}

.level-tag.BRONZE {
  background: #fed7aa;
  color: #92400e;
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

.action-btn.points {
  background: #fef3c7;
  color: #d97706;
}

.action-btn.points:hover {
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
  background: #14b8a6;
  color: white;
}
</style>