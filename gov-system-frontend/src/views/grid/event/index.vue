<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>网格事件管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 上报事件
          </el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="事件标题">
          <el-input v-model="searchForm.eventTitle" placeholder="请输入事件标题" clearable />
        </el-form-item>
        <el-form-item label="事件类型">
          <el-select v-model="searchForm.eventType" placeholder="请选择" clearable>
            <el-option label="环境问题" value="ENVIRONMENT" />
            <el-option label="治安事件" value="SECURITY" />
            <el-option label="设施损坏" value="FACILITY" />
            <el-option label="信访事件" value="PETITION" />
            <el-option label="矛盾纠纷" value="DISPUTE" />
            <el-option label="其他" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item label="紧急程度">
          <el-select v-model="searchForm.urgentLevel" placeholder="请选择" clearable>
            <el-option label="较低" value="LOW" />
            <el-option label="一般" value="NORMAL" />
            <el-option label="紧急" value="URGENT" />
            <el-option label="很紧急" value="VERY_URGENT" />
            <el-option label="特别紧急" value="EXTREME" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.currentStatus" placeholder="请选择" clearable>
            <el-option label="待分派" value="PENDING" />
            <el-option label="已分派" value="ASSIGNED" />
            <el-option label="已受理" value="ACCEPTED" />
            <el-option label="处理中" value="HANDLING" />
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
        <el-table-column prop="eventCode" label="事件编号" width="150" />
        <el-table-column prop="eventTitle" label="事件标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="eventType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.eventType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="urgentLevel" label="紧急程度" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getUrgentType(row.urgentLevel)">{{ row.urgentLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="gridCellName" label="所属网格" width="120" />
        <el-table-column prop="reporterName" label="上报人" width="100" />
        <el-table-column prop="reportTime" label="上报时间" width="160" />
        <el-table-column prop="currentStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.currentStatus)">{{ getStatusLabel(row.currentStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="success" size="small" v-if="row.currentStatus === 'PENDING'" @click="handleAssign(row)">分派</el-button>
            <el-button link type="warning" size="small" v-if="row.currentStatus === 'ASSIGNED'" @click="handleAccept(row)">受理</el-button>
            <el-button link type="primary" size="small" v-if="row.currentStatus === 'ACCEPTED' || row.currentStatus === 'HANDLING'" @click="handleProcess(row)">处理</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="事件标题" prop="eventTitle">
          <el-input v-model="formData.eventTitle" placeholder="请输入事件标题" />
        </el-form-item>
        <el-form-item label="事件类型" prop="eventType">
          <el-select v-model="formData.eventType" placeholder="请选择">
            <el-option label="环境问题" value="ENVIRONMENT" />
            <el-option label="治安事件" value="SECURITY" />
            <el-option label="设施损坏" value="FACILITY" />
            <el-option label="信访事件" value="PETITION" />
            <el-option label="矛盾纠纷" value="DISPUTE" />
            <el-option label="其他" value="OTHER" />
          </el-select>
        </el-form-item>
        <el-form-item label="紧急程度" prop="urgentLevel">
          <el-select v-model="formData.urgentLevel" placeholder="请选择">
            <el-option label="较低" value="LOW" />
            <el-option label="一般" value="NORMAL" />
            <el-option label="紧急" value="URGENT" />
            <el-option label="很紧急" value="VERY_URGENT" />
            <el-option label="特别紧急" value="EXTREME" />
          </el-select>
        </el-form-item>
        <el-form-item label="所属网格" prop="gridCellId">
          <el-tree-select
            v-model="formData.gridCellId"
            :data="gridOptions"
            :props="{ label: 'cellName', value: 'cellId' }"
            placeholder="请选择网格"
            clearable
          />
        </el-form-item>
        <el-form-item label="事件地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入事件地址" />
        </el-form-item>
        <el-form-item label="事件描述" prop="description">
          <el-input v-model="formData.description" type="textarea" :rows="4" placeholder="请输入事件描述" />
        </el-form-item>
        <el-form-item label="上报人" prop="reporterName">
          <el-input v-model="formData.reporterName" placeholder="请输入上报人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="reporterPhone">
          <el-input v-model="formData.reporterPhone" placeholder="请输入联系电话" />
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

const searchForm = reactive({
  eventTitle: '',
  eventType: '',
  urgentLevel: '',
  currentStatus: ''
})

const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

const tableData = ref([])
const gridOptions = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const formData = reactive({
  eventId: null,
  eventTitle: '',
  eventType: '',
  urgentLevel: 'NORMAL',
  gridCellId: null,
  gridCellName: '',
  address: '',
  description: '',
  reporterName: '',
  reporterPhone: '',
  handleDepartmentId: null,
  handleDepartmentName: ''
})

const formRules = {
  eventTitle: [{ required: true, message: '请输入事件标题', trigger: 'blur' }],
  eventType: [{ required: true, message: '请选择事件类型', trigger: 'change' }],
  gridCellId: [{ required: true, message: '请选择所属网格', trigger: 'change' }]
}

const getTypeLabel = (type) => {
  const map = { ENVIRONMENT: '环境', SECURITY: '治安', FACILITY: '设施', PETITION: '信访', DISPUTE: '纠纷', OTHER: '其他' }
  return map[type] || type
}

const getUrgentType = (level) => {
  const map = { LOW: 'info', NORMAL: '', URGENT: 'warning', VERY_URGENT: 'danger', EXTREME: 'danger' }
  return map[level] || ''
}

const getStatusLabel = (status) => {
  const map = { PENDING: '待分派', ASSIGNED: '已分派', ACCEPTED: '已受理', HANDLING: '处理中', COMPLETED: '已完成', CLOSED: '已关闭' }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = { PENDING: 'warning', ASSIGNED: 'primary', ACCEPTED: 'primary', HANDLING: 'warning', COMPLETED: 'success', CLOSED: 'info' }
  return map[status] || ''
}

const fetchData = async () => {
  try {
    const params = { pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...searchForm }
    const res = await axios.get('/api/grid/event/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch (error) {
    tableData.value = [
      { eventId: 1, eventCode: 'E20260428001', eventTitle: '路灯损坏报修', eventType: 'FACILITY', urgentLevel: 'NORMAL', gridCellName: '望京街道A区', reporterName: '市民张', reportTime: '2026-04-28 10:30', currentStatus: 'PENDING' },
      { eventId: 2, eventCode: 'E20260428002', eventTitle: '井盖缺失隐患', eventType: 'FACILITY', urgentLevel: 'URGENT', gridCellName: '望京街道B区', reporterName: '网格员李', reportTime: '2026-04-28 09:15', currentStatus: 'ASSIGNED' },
      { eventId: 3, eventCode: 'E20260428003', eventTitle: '邻里纠纷调解', eventType: 'DISPUTE', urgentLevel: 'NORMAL', gridCellName: '望京街道A区', reporterName: '社区王', reportTime: '2026-04-28 08:00', currentStatus: 'COMPLETED' }
    ]
    pagination.total = 3
  }
}

const fetchGridOptions = async () => {
  try {
    const res = await axios.get('/api/grid/cell/list')
    gridOptions.value = res.data?.data || []
  } catch (error) {
    gridOptions.value = [{ cellId: 1, cellName: '朝阳区' }, { cellId: 2, cellName: '望京街道' }]
  }
}

const handleSearch = () => { pagination.pageNum = 1; fetchData() }
const handleReset = () => { Object.assign(searchForm, { eventTitle: '', eventType: '', urgentLevel: '', currentStatus: '' }); handleSearch() }
const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleAdd = () => {
  dialogTitle.value = '上报事件'
  Object.assign(formData, { eventId: null, eventTitle: '', eventType: '', urgentLevel: 'NORMAL', gridCellId: null, address: '', description: '', reporterName: '', reporterPhone: '' })
  dialogVisible.value = true
}

const handleView = (row) => {
  dialogTitle.value = '事件详情'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleAssign = async (row) => {
  await ElMessageBox.prompt('请输入办理单位名称', '分派事件', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/grid/event/assign/${row.eventId}`, { handleDepartmentName: value })
        ElMessage.success('分派成功')
        fetchData()
      } catch { ElMessage.error('分派失败') }
    })
}

const handleAccept = async (row) => {
  try {
    await axios.put(`/api/grid/event/accept/${row.eventId}`)
    ElMessage.success('受理成功')
    fetchData()
  } catch { ElMessage.error('受理失败') }
}

const handleProcess = async (row) => {
  await ElMessageBox.prompt('请输入处理结果', '处理事件', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/grid/event/complete/${row.eventId}`, { handleResult: value })
        ElMessage.success('处理完成')
        fetchData()
      } catch { ElMessage.error('操作失败') }
    })
}

const handleEvaluate = async (row) => {
  await ElMessageBox.prompt('请输入满意度评分(1-5)', '评价事件', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/grid/event/evaluate/${row.eventId}`, { satisfactionScore: parseInt(value) })
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
      if (formData.eventId) {
        await axios.put('/api/grid/event', formData)
      } else {
        await axios.post('/api/grid/event', formData)
      }
      ElMessage.success('操作成功')
      dialogVisible.value = false
      fetchData()
    } catch { ElMessage.error('操作失败') }
  })
}

onMounted(() => { fetchData(); fetchGridOptions() })
</script>

<style scoped>
.page-container { width: 100%; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
