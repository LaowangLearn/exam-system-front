<template>
  <div class="oa-approvals">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>审批管理</h1>
      <button class="add-btn" @click="openAddApprovalDialog">新建审批</button>
    </div>

    <div class="approval-filter">
      <div class="filter-row">
        <select v-model="typeFilter">
          <option value="">全部类型</option>
          <option value="DOCUMENT">公文审批</option>
          <option value="LEAVE">请假审批</option>
          <option value="REIMBURSEMENT">报销审批</option>
        </select>
        <select v-model="statusFilter">
          <option value="">全部状态</option>
          <option value="PENDING">待审批</option>
          <option value="APPROVED">已批准</option>
          <option value="REJECTED">已拒绝</option>
        </select>
        <button class="search-btn" @click="filterApprovals">筛选</button>
      </div>
    </div>

    <div class="approval-list">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>类型</th>
            <th>内容</th>
            <th>状态</th>
            <th>申请人</th>
            <th>审批人</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="approval in filteredApprovals" :key="approval.id">
            <td>{{ approval.id }}</td>
            <td>{{ approval.type }}</td>
            <td>{{ approval.content }}</td>
            <td>
              <span class="status" :class="approval.status">{{ approval.status }}</span>
            </td>
            <td>{{ approval.applicantName }}</td>
            <td>{{ approval.approverName }}</td>
            <td>{{ approval.createTime }}</td>
            <td>
              <button class="view-btn" @click="viewApproval(approval.id)">查看</button>
              <button v-if="approval.status === 'PENDING'" class="approve-btn" @click="approveApproval(approval.id)">批准</button>
              <button v-if="approval.status === 'PENDING'" class="reject-btn" @click="rejectApproval(approval.id)">拒绝</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 新建审批对话框 -->
    <div v-if="showApprovalDialog" class="dialog-overlay" @click="closeApprovalDialog">
      <div class="dialog" @click.stop>
        <h2>新建审批</h2>
        <form @submit.prevent="saveApproval">
          <div class="form-group">
            <label>类型</label>
            <select v-model="form.type" required>
              <option value="DOCUMENT">公文审批</option>
              <option value="LEAVE">请假审批</option>
              <option value="REIMBURSEMENT">报销审批</option>
            </select>
          </div>
          <div class="form-group">
            <label>内容</label>
            <textarea v-model="form.content" rows="5" required></textarea>
          </div>
          <div class="form-group">
            <label>审批人</label>
            <select v-model="form.approverId" required>
              <option value="">选择审批人</option>
              <option v-for="user in users" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div class="dialog-actions">
            <button type="button" class="cancel-btn" @click="closeApprovalDialog">取消</button>
            <button type="submit" class="submit-btn">提交</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 查看审批对话框 -->
    <div v-if="showViewDialog" class="dialog-overlay" @click="closeViewDialog">
      <div class="dialog" @click.stop>
        <h2>审批详情</h2>
        <div class="approval-meta">
          <p>类型：{{ viewApprovalData.type }}</p>
          <p>状态：{{ viewApprovalData.status }}</p>
          <p>申请人：{{ viewApprovalData.applicantName }}</p>
          <p>审批人：{{ viewApprovalData.approverName }}</p>
          <p>创建时间：{{ viewApprovalData.createTime }}</p>
          <p v-if="viewApprovalData.remark">审批意见：{{ viewApprovalData.remark }}</p>
        </div>
        <div class="approval-content">
          <h3>审批内容</h3>
          <p>{{ viewApprovalData.content }}</p>
        </div>
        <div class="dialog-actions">
          <button class="close-btn" @click="closeViewDialog">关闭</button>
        </div>
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

// 审批列表
const approvals = ref([
  { id: 1, type: 'DOCUMENT', content: '关于2026年工作计划的通知', status: 'PENDING', applicantId: 3, applicantName: '普通用户', approverId: 1, approverName: '管理员', createTime: '2026-02-09 10:00' },
  { id: 2, type: 'LEAVE', content: '请假申请：2026年2月10日至2月12日', status: 'APPROVED', applicantId: 3, applicantName: '普通用户', approverId: 2, approverName: '技术经理', createTime: '2026-02-08 14:30', remark: '同意请假' },
  { id: 3, type: 'REIMBURSEMENT', content: '报销申请：差旅费 1500元', status: 'REJECTED', applicantId: 3, applicantName: '普通用户', approverId: 4, approverName: '财务经理', createTime: '2026-02-07 09:15', remark: '缺少发票，请补充' }
])

// 用户列表
const users = ref([
  { id: 1, name: '管理员' },
  { id: 2, name: '技术经理' },
  { id: 3, name: '普通用户' },
  { id: 4, name: '财务经理' }
])

// 筛选条件
const typeFilter = ref('')
const statusFilter = ref('')

// 对话框状态
const showApprovalDialog = ref(false)
const showViewDialog = ref(false)
const form = ref({
  id: 0,
  type: '',
  content: '',
  status: 'PENDING',
  applicantId: 3,
  approverId: 0
})

const viewApprovalData = ref({
  id: 0,
  type: '',
  content: '',
  status: '',
  applicantName: '',
  approverName: '',
  createTime: '',
  remark: ''
})

// 过滤后的审批列表
const filteredApprovals = computed(() => {
  return approvals.value.filter(approval => {
    const matchesType = !typeFilter.value || approval.type === typeFilter.value
    const matchesStatus = !statusFilter.value || approval.status === statusFilter.value
    return matchesType && matchesStatus
  })
})

// 打开新建审批对话框
const openAddApprovalDialog = () => {
  form.value = {
    id: 0,
    type: '',
    content: '',
    status: 'PENDING',
    applicantId: 3,
    approverId: 0
  }
  showApprovalDialog.value = true
}

// 关闭审批对话框
const closeApprovalDialog = () => {
  showApprovalDialog.value = false
}

// 保存审批
const saveApproval = () => {
  const newApproval = {
    ...form.value,
    id: approvals.value.length + 1,
    applicantName: '普通用户',
    approverName: users.value.find(u => u.id === form.value.approverId)?.name || '',
    createTime: new Date().toLocaleString()
  }
  approvals.value.push(newApproval)
  closeApprovalDialog()
}

// 查看审批
const viewApproval = (id: number) => {
  const approval = approvals.value.find(a => a.id === id)
  if (approval) {
    viewApprovalData.value = { ...approval }
    showViewDialog.value = true
  }
}

// 关闭查看对话框
const closeViewDialog = () => {
  showViewDialog.value = false
}

// 批准审批
const approveApproval = (id: number) => {
  const approval = approvals.value.find(a => a.id === id)
  if (approval) {
    const remark = prompt('请输入审批意见：')
    approval.status = 'APPROVED'
    if (remark) {
      approval.remark = remark
    }
  }
}

// 拒绝审批
const rejectApproval = (id: number) => {
  const approval = approvals.value.find(a => a.id === id)
  if (approval) {
    const remark = prompt('请输入拒绝原因：')
    approval.status = 'REJECTED'
    if (remark) {
      approval.remark = remark
    }
  }
}

// 筛选审批
const filterApprovals = () => {
  // 筛选逻辑已在computed中实现
  console.log('筛选审批')
}

onMounted(() => {
  // 加载审批数据
  console.log('加载审批数据')
})
</script>

<style scoped>
.oa-approvals {
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

.approval-filter {
  margin-bottom: 20px;
  background: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-row {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-row select {
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

.approval-list {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.approval-list table {
  width: 100%;
  border-collapse: collapse;
}

.approval-list th, .approval-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.approval-list th {
  background: #f8f9fa;
  font-weight: bold;
  color: #333;
}

.approval-list tr:hover {
  background: #f8f9fa;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.PENDING {
  background: #fff3cd;
  color: #856404;
}

.status.APPROVED {
  background: #d4edda;
  color: #155724;
}

.status.REJECTED {
  background: #f8d7da;
  color: #721c24;
}

.view-btn, .approve-btn, .reject-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
  margin-right: 5px;
}

.view-btn {
  background: #17a2b8;
  color: white;
}

.approve-btn {
  background: #28a745;
  color: white;
}

.reject-btn {
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
  max-width: 600px;
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

.cancel-btn, .submit-btn, .close-btn {
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

.submit-btn {
  background: #667eea;
  color: white;
}

.close-btn {
  background: #667eea;
  color: white;
}

.approval-meta {
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 4px;
}

.approval-meta p {
  margin: 5px 0;
  color: #666;
}

.approval-content {
  margin-bottom: 20px;
}

.approval-content h3 {
  margin-top: 0;
  margin-bottom: 10px;
  color: #333;
}

.approval-content p {
  line-height: 1.6;
  color: #333;
}

@media (max-width: 768px) {
  .filter-row {
    flex-direction: column;
    align-items: stretch;
  }

  .approval-list table {
    font-size: 0.8rem;
  }

  .approval-list th, .approval-list td {
    padding: 8px;
  }

  .view-btn, .approve-btn, .reject-btn {
    padding: 3px 6px;
    font-size: 0.7rem;
  }
}
</style>