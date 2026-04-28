<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>信访登记管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增信访
          </el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="信访标题">
          <el-input v-model="searchForm.title" placeholder="请输入信访标题" clearable />
        </el-form-item>
        <el-form-item label="信访类型">
          <el-select v-model="searchForm.petitionType" placeholder="请选择" clearable>
            <el-option label="来信" value="LETTER" />
            <el-option label="来访" value="VISIT" />
            <el-option label="邮件" value="EMAIL" />
            <el-option label="电话" value="TELEPHONE" />
            <el-option label="短信" value="SMS" />
            <el-option label="网购" value="INTERNET" />
          </el-select>
        </el-form-item>
        <el-form-item label="信访类别">
          <el-select v-model="searchForm.petitionCategory" placeholder="请选择" clearable>
            <el-option label="建议" value="SUGGESTION" />
            <el-option label="投诉" value="COMPLAINT" />
            <el-option label="请求" value="REQUEST" />
            <el-option label="举报" value="REPORT" />
            <el-option label="咨询" value="CONSULTATION" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.currentStatus" placeholder="请选择" clearable>
            <el-option label="待受理" value="PENDING" />
            <el-option label="已受理" value="ACCEPTED" />
            <el-option label="办理中" value="HANDLING" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已关闭" value="CLOSED" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="petitionCode" label="信访编号" width="180" />
        <el-table-column prop="title" label="信访标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="petitionType" label="信访类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.petitionType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="petitionCategory" label="信访类别" width="100">
          <template #default="{ row }">
            <el-tag type="info">{{ getCategoryLabel(row.petitionCategory) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="petitionerName" label="信访人" width="100" />
        <el-table-column prop="petitionerType" label="信访人类别" width="120">
          <template #default="{ row }">
            <el-tag v-if="row.isKeyCase" type="danger" size="small">重点</el-tag>
            <el-tag v-else type="info" size="small">普通</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="currentStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.currentStatus)">{{ getStatusLabel(row.currentStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="登记时间" width="160" />
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="success" size="small" v-if="row.currentStatus === 'PENDING'" @click="handleAccept(row)">受理</el-button>
            <el-button link type="warning" size="small" v-if="row.currentStatus === 'ACCEPTED'" @click="handleAssign(row)">分派</el-button>
            <el-button link type="primary" size="small" v-if="row.currentStatus === 'HANDLING'" @click="handleComplete(row)">办结</el-button>
            <el-button link type="info" size="small" v-if="row.currentStatus === 'COMPLETED'" @click="handleEvaluate(row)">评价</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pagination"
        v-model:current-page="pagination.pageNum"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="信访类型" prop="petitionType">
              <el-select v-model="formData.petitionType" placeholder="请选择">
                <el-option label="来信" value="LETTER" />
                <el-option label="来访" value="VISIT" />
                <el-option label="邮件" value="EMAIL" />
                <el-option label="电话" value="TELEPHONE" />
                <el-option label="短信" value="SMS" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="信访类别" prop="petitionCategory">
              <el-select v-model="formData.petitionCategory" placeholder="请选择">
                <el-option label="建议" value="SUGGESTION" />
                <el-option label="投诉" value="COMPLAINT" />
                <el-option label="请求" value="REQUEST" />
                <el-option label="举报" value="REPORT" />
                <el-option label="咨询" value="CONSULTATION" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="信访标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入信访标题" />
        </el-form-item>
        <el-form-item label="信访内容" prop="content">
          <el-input v-model="formData.content" type="textarea" :rows="5" placeholder="请输入信访内容" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="信访人" prop="petitionerName">
              <el-input v-model="formData.petitionerName" placeholder="请输入信访人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="petitionerPhone">
              <el-input v-model="formData.petitionerPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="信访人地址" prop="petitionerAddress">
          <el-input v-model="formData.petitionerAddress" placeholder="请输入信访人地址" />
        </el-form-item>
        <el-form-item label="发生地点" prop="happenPlace">
          <el-input v-model="formData.happenPlace" placeholder="请输入信访发生地点" />
        </el-form-item>
        <el-form-item label="是否重点督办">
          <el-switch v-model="formData.isKeyCase" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from '@/utils/axios'

const searchForm = reactive({ title: '', petitionType: '', petitionCategory: '', currentStatus: '' })

const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const formData = reactive({
  petitionId: null, petitionType: 'LETTER', petitionCategory: 'NORMAL', title: '', content: '',
  petitionerName: '', petitionerPhone: '', petitionerAddress: '', happenPlace: '', isKeyCase: 0
})

const formRules = {
  title: [{ required: true, message: '请输入信访标题', trigger: 'blur' }],
  petitionType: [{ required: true, message: '请选择信访类型', trigger: 'change' }],
  petitionerName: [{ required: true, message: '请输入信访人姓名', trigger: 'blur' }]
}

const getTypeLabel = (type) => {
  const map = { LETTER: '来信', VISIT: '来访', EMAIL: '邮件', TELEPHONE: '电话', SMS: '短信', INTERNET: '网购' }
  return map[type] || type
}

const getCategoryLabel = (cat) => {
  const map = { SUGGESTION: '建议', COMPLAINT: '投诉', REQUEST: '请求', REPORT: '举报', CONSULTATION: '咨询' }
  return map[cat] || cat
}

const getStatusLabel = (status) => {
  const map = { PENDING: '待受理', ACCEPTED: '已受理', HANDLING: '办理中', COMPLETED: '已完成', CLOSED: '已关闭' }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = { PENDING: 'warning', ACCEPTED: 'primary', HANDLING: 'warning', COMPLETED: 'success', CLOSED: 'info' }
  return map[status] || ''
}

const fetchData = async () => {
  try {
    const params = { pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...searchForm }
    const res = await axios.get('/api/petition/register/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch {
    tableData.value = [
      { petitionId: 1, petitionCode: 'P20260428001', title: '小区噪音扰民投诉', petitionType: 'VISIT', petitionCategory: 'COMPLAINT', petitionerName: '王先生', isKeyCase: 0, currentStatus: 'PENDING', createTime: '2026-04-28 10:30' },
      { petitionId: 2, petitionCode: 'P20260428002', title: '违法建筑举报', petitionType: 'LETTER', petitionCategory: 'REPORT', petitionerName: '李女士', isKeyCase: 1, currentStatus: 'HANDLING', createTime: '2026-04-28 09:15' }
    ]
    pagination.total = 2
  }
}

const handleSearch = () => { pagination.pageNum = 1; fetchData() }
const handleReset = () => { Object.assign(searchForm, { title: '', petitionType: '', petitionCategory: '', currentStatus: '' }); handleSearch() }
const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleAdd = () => {
  dialogTitle.value = '新增信访'
  Object.assign(formData, { petitionId: null, petitionType: 'LETTER', petitionCategory: 'NORMAL', title: '', content: '', petitionerName: '', petitionerPhone: '', petitionerAddress: '', happenPlace: '', isKeyCase: 0 })
  dialogVisible.value = true
}

const handleView = (row) => {
  dialogTitle.value = '信访详情'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleAccept = async (row) => {
  try {
    await axios.put(`/api/petition/register/accept/${row.petitionId}`, { acceptDepartmentName: '信访办' })
    ElMessage.success('受理成功')
    fetchData()
  } catch { ElMessage.error('受理失败') }
}

const handleAssign = async (row) => {
  await ElMessageBox.prompt('请输入办理单位', '分派信访', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/petition/register/assign/${row.petitionId}`, { handleDepartmentName: value })
        ElMessage.success('分派成功')
        fetchData()
      } catch { ElMessage.error('分派失败') }
    })
}

const handleComplete = async (row) => {
  await ElMessageBox.prompt('请输入办理结果', '办结信访', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/petition/register/complete/${row.petitionId}`, { handleResult: value })
        ElMessage.success('办结成功')
        fetchData()
      } catch { ElMessage.error('办结失败') }
    })
}

const handleEvaluate = async (row) => {
  await ElMessageBox.prompt('请输入满意度评分(1-5)', '评价信访', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/petition/register/evaluate/${row.petitionId}`, { satisfactionScore: parseInt(value) })
        ElMessage.success('评价成功')
        fetchData()
      } catch { ElMessage.error('评价失败') }
    })
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (formData.petitionId) {
        await axios.put('/api/petition/register', formData)
      } else {
        await axios.post('/api/petition/register', formData)
      }
      ElMessage.success('操作成功')
      dialogVisible.value = false
      fetchData()
    } catch { ElMessage.error('操作失败') }
  })
}

onMounted(() => fetchData())
</script>

<style scoped>
.page-container { width: 100%; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
