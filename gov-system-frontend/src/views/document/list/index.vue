<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>公文列表</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon> 起草公文
          </el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="公文标题">
          <el-input v-model="searchForm.docTitle" placeholder="请输入公文标题" clearable />
        </el-form-item>
        <el-form-item label="公文类型">
          <el-select v-model="searchForm.docType" placeholder="请选择" clearable>
            <el-option label="通知" value="NOTICE" />
            <el-option label="命令" value="COMMAND" />
            <el-option label="决定" value="DECISION" />
            <el-option label="公告" value="PROCLAMATION" />
            <el-option label="报告" value="REPORT" />
            <el-option label="议案" value="PROPOSAL" />
            <el-option label="函" value="LETTER" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-select v-model="searchForm.approvalStatus" placeholder="请选择" clearable>
            <el-option label="草稿" value="DRAFT" />
            <el-option label="审批中" value="APPROVING" />
            <el-option label="已批准" value="APPROVED" />
            <el-option label="已发布" value="PUBLISHED" />
            <el-option label="已归档" value="ARCHIVED" />
          </el-select>
        </el-form-item>
        <el-form-item label="密级">
          <el-select v-model="searchForm.secretLevel" placeholder="请选择" clearable>
            <el-option label="普通" value="0" />
            <el-option label="内部" value="1" />
            <el-option label="秘密" value="2" />
            <el-option label="机密" value="3" />
            <el-option label="绝密" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="docCode" label="公文编号" width="180" />
        <el-table-column prop="docTitle" label="公文标题" min-width="250" show-overflow-tooltip />
        <el-table-column prop="docType" label="公文类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.docType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="secretLevel" label="密级" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="getSecretType(row.secretLevel)" size="small">{{ getSecretLabel(row.secretLevel) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="urgencyLevel" label="紧急程度" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getUrgencyType(row.urgencyLevel)" size="small">{{ row.urgencyLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="issueDepartmentName" label="发文单位" width="150" />
        <el-table-column prop="issueUserName" label="拟稿人" width="100" />
        <el-table-column prop="issueDate" label="发文日期" width="120" />
        <el-table-column prop="approvalStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.approvalStatus)">{{ getStatusLabel(row.approvalStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="warning" size="small" v-if="row.approvalStatus === 'DRAFT'" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="success" size="small" v-if="row.approvalStatus === 'DRAFT'" @click="handleSubmit(row)">提交</el-button>
            <el-button link type="primary" size="small" v-if="row.approvalStatus === 'APPROVED'" @click="handlePublish(row)">发布</el-button>
            <el-button link type="info" size="small" v-if="row.approvalStatus === 'PUBLISHED'" @click="handleArchive(row)">归档</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="pagination" v-model:current-page="pagination.pageNum" v-model:page-size="pagination.pageSize"
        :total="pagination.total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange" @current-change="handlePageChange" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'

const router = useRouter()
const searchForm = reactive({ docTitle: '', docType: '', approvalStatus: '', secretLevel: '' })
const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])

const getTypeLabel = (type) => {
  const map = { NOTICE: '通知', COMMAND: '命令', DECISION: '决定', PROCLAMATION: '公告', REPORT: '报告', PROPOSAL: '议案', LETTER: '函' }
  return map[type] || type
}

const getSecretLabel = (level) => {
  const map = { 0: '普通', 1: '内部', 2: '秘密', 3: '机密', 4: '绝密' }
  return map[level] || level
}

const getSecretType = (level) => {
  const map = { 0: 'info', 1: '', 2: 'warning', 3: 'danger', 4: 'danger' }
  return map[level] || ''
}

const getUrgencyType = (level) => {
  const map = { LOW: 'info', NORMAL: '', URGENT: 'warning', VERY_URGENT: 'danger' }
  return map[level] || ''
}

const getStatusLabel = (status) => {
  const map = { DRAFT: '草稿', APPROVING: '审批中', APPROVED: '已批准', PUBLISHED: '已发布', ARCHIVED: '已归档', RECALLED: '已撤回' }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = { DRAFT: 'info', APPROVING: 'warning', APPROVED: 'success', PUBLISHED: 'primary', ARCHIVED: '', RECALLED: 'danger' }
  return map[status] || ''
}

const fetchData = async () => {
  try {
    const params = { pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...searchForm }
    const res = await axios.get('/api/document/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch {
    tableData.value = [
      { docId: 1, docCode: 'GOV-2026-001', docTitle: '关于开展安全生产大检查的通知', docType: 'NOTICE', secretLevel: 0, urgencyLevel: 'URGENT', issueDepartmentName: '区政府办公室', issueUserName: '张三', issueDate: '2026-04-28', approvalStatus: 'PUBLISHED' },
      { docId: 2, docCode: 'GOV-2026-002', docTitle: '关于表彰先进集体的决定', docType: 'DECISION', secretLevel: 0, urgencyLevel: 'NORMAL', issueDepartmentName: '区人社局', issueUserName: '李四', issueDate: '2026-04-27', approvalStatus: 'APPROVING' },
      { docId: 3, docCode: 'GOV-2026-003', docTitle: '2026年第一季度工作报告', docType: 'REPORT', secretLevel: 1, urgencyLevel: 'NORMAL', issueDepartmentName: '区政府办公室', issueUserName: '王五', issueDate: '2026-04-26', approvalStatus: 'DRAFT' }
    ]
    pagination.total = 3
  }
}

const handleSearch = () => { pagination.pageNum = 1; fetchData() }
const handleReset = () => { Object.assign(searchForm, { docTitle: '', docType: '', approvalStatus: '', secretLevel: '' }); handleSearch() }
const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleCreate = () => router.push('/document/create')
const handleView = (row) => ElMessage.info('查看公文: ' + row.docCode)
const handleEdit = (row) => ElMessage.info('编辑公文: ' + row.docCode)

const handleSubmit = async (row) => {
  try {
    await axios.put(`/api/document/submit/${row.docId}`)
    ElMessage.success('提交成功')
    fetchData()
  } catch { ElMessage.error('提交失败') }
}

const handlePublish = async (row) => {
  try {
    await axios.put(`/api/document/publish/${row.docId}`)
    ElMessage.success('发布成功')
    fetchData()
  } catch { ElMessage.error('发布失败') }
}

const handleArchive = async (row) => {
  try {
    await axios.put(`/api/document/archive/${row.docId}`)
    ElMessage.success('归档成功')
    fetchData()
  } catch { ElMessage.error('归档失败') }
}

onMounted(() => fetchData())
</script>

<style scoped>
.page-container { width: 100%; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
