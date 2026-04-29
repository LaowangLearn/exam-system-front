<template>
  <div class="gov-document-approval">
    <button class="back-btn" @click="goBack">�?返回政务事业单位�?/button>
    <div class="page-header">
      <h1>公文审批</h1>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="公文标题/文号" v-model="searchText" style="width: 300px;" />
        <el-select v-model="searchStatus" placeholder="审批状�?>
          <el-option :label="label" :value="value" v-for="item in statusOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="docCode" label="文号" width="150" />
        <el-table-column prop="docTitle" label="标题" />
        <el-table-column prop="docType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.docType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="currentStep" label="当前环节" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStepLabel(row.currentStep) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nextApprover" label="下一审批�? width="120" />
        <el-table-column prop="createUser" label="拟稿�? width="100" />
        <el-table-column prop="submitTime" label="提交时间" width="160" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看详情</el-button>
            <el-button size="mini" type="primary" @click="handleApprove(row)" v-if="canApprove(row)">审批</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 审批弹窗 -->
    <el-dialog title="公文审批" :visible.sync="dialogVisible">
      <el-form :model="approvalForm" label-width="80px">
        <el-form-item label="审批意见">
          <el-textarea v-model="approvalForm.comment" :rows="4" placeholder="请输入审批意�? />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="danger" @click="handleReject">驳回</el-button>
        <el-button type="primary" @click="handleAgree">同意</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const searchText = ref('')
const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const searchStatus = ref('')
const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const dialogVisible = ref(false)

const approvalForm = reactive({
  comment: ''
})

const statusOptions = [
  { label: '全部', value: '' },
  { label: '待审�?, value: '2' },
  { label: '已通过', value: '3' },
  { label: '已驳�?, value: '4' }
]

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const tableData = ref([
  {
    id: 1,
    docCode: 'XX�?026�?�?,
    docTitle: '关于开展安全生产检查的通知',
    docType: 1,
    currentStep: 2,
    nextApprover: '李主�?,
    createUser: '张三',
    submitTime: '2026-04-28 09:00',
    status: 2,
    isMine: true
  },
  {
    id: 2,
    docCode: 'XX�?026�?�?,
    docTitle: '2026年第一季度工作报告',
    docType: 2,
    currentStep: 1,
    nextApprover: '王科�?,
    createUser: '李四',
    submitTime: '2026-04-28 10:30',
    status: 2,
    isMine: false
  },
  {
    id: 3,
    docCode: 'XX�?026�?�?,
    docTitle: '关于申请经费的请�?,
    docType: 3,
    currentStep: 3,
    nextApprover: '-',
    createUser: '王五',
    submitTime: '2026-04-27 14:00',
    status: 3,
    isMine: false
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchStatus.value)
}

const handleView = (row) => {
  alert(`查看公文: ${row.docTitle}`)
}

const handleApprove = (row) => {
  dialogVisible.value = true
}

const handleAgree = () => {
  alert('审批通过')
  dialogVisible.value = false
}

const handleReject = () => {
  alert('审批驳回')
  dialogVisible.value = false
}

const canApprove = (row) => {
  return row.status === 2 && row.isMine
}

const getTypeLabel = (type) => {
  const types = { 1: '通知', 2: '报告', 3: '请示', 4: '批复', 5: '�? }
  return types[type] || '其他'
}

const getStepLabel = (step) => {
  const steps = { 1: '科室初审', 2: '部门审核', 3: '领导审批', 4: '发布' }
  return steps[step] || '未知'
}

const getStatusType = (status) => {
  const types = { 2: 'warning', 3: 'success', 4: 'danger' }
  return types[status] || 'info'
}
</script>

<style scoped>
.gov-document-approval {
  padding: 24px;
}

.page-header {
  margin-bottom: 24px;
}

.page-header h1 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 0;
}

.search-bar {
  display: flex;
  gap: 16px;
  margin-bottom: 20px;
  align-items: center;
}
</style>
