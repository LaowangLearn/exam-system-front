<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>报警记录</span>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="告警标题">
          <el-input v-model="searchForm.alarmTitle" placeholder="请输入告警标题" clearable />
        </el-form-item>
        <el-form-item label="告警类型">
          <el-select v-model="searchForm.alarmType" placeholder="请选择" clearable>
            <el-option label="入侵告警" value="INTRUSION" />
            <el-option label="人群聚集" value="CROWD" />
            <el-option label="周界入侵" value="PERIMETER" />
            <el-option label="人脸识别" value="FACE" />
            <el-option label="车辆识别" value="VEHICLE" />
            <el-option label="火情告警" value="FIRE" />
          </el-select>
        </el-form-item>
        <el-form-item label="告警级别">
          <el-select v-model="searchForm.alarmLevel" placeholder="请选择" clearable>
            <el-option label="低" value="LOW" />
            <el-option label="中" value="MEDIUM" />
            <el-option label="高" value="HIGH" />
            <el-option label="危急" value="CRITICAL" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.currentStatus" placeholder="请选择" clearable>
            <el-option label="待处理" value="PENDING" />
            <el-option label="已分派" value="DISPATCHED" />
            <el-option label="已到场" value="ARRIVED" />
            <el-option label="处理中" value="HANDLING" />
            <el-option label="已解决" value="RESOLVED" />
            <el-option label="已关闭" value="CLOSED" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="alarmCode" label="告警编号" width="150" />
        <el-table-column prop="alarmTitle" label="告警标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="alarmType" label="告警类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.alarmType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="alarmLevel" label="级别" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.alarmLevel)" size="small">{{ row.alarmLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="deviceName" label="关联设备" width="150" />
        <el-table-column prop="locationAddress" label="告警位置" min-width="150" show-overflow-tooltip />
        <el-table-column prop="alarmTime" label="告警时间" width="160" />
        <el-table-column prop="currentStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.currentStatus)">{{ getStatusLabel(row.currentStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="success" size="small" v-if="row.currentStatus === 'PENDING'" @click="handleDispatch(row)">分派</el-button>
            <el-button link type="warning" size="small" v-if="row.currentStatus === 'DISPATCHED'" @click="handleArrive(row)">到场</el-button>
            <el-button link type="primary" size="small" v-if="row.currentStatus === 'ARRIVED' || row.currentStatus === 'HANDLING'" @click="handleResolve(row)">处理</el-button>
            <el-button link type="info" size="small" v-if="row.currentStatus === 'RESOLVED'" @click="handleClose(row)">关闭</el-button>
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
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from '@/utils/axios'

const searchForm = reactive({ alarmTitle: '', alarmType: '', alarmLevel: '', currentStatus: '' })
const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])

const getTypeLabel = (type) => {
  const map = { INTRUSION: '入侵', CROWD: '聚集', PERIMETER: '周界', FACE: '人脸', VEHICLE: '车辆', FIRE: '火情' }
  return map[type] || type
}

const getLevelType = (level) => {
  const map = { LOW: 'info', MEDIUM: 'warning', HIGH: 'danger', CRITICAL: 'danger' }
  return map[level] || ''
}

const getStatusLabel = (status) => {
  const map = { PENDING: '待处理', DISPATCHED: '已分派', ARRIVED: '已到场', HANDLING: '处理中', RESOLVED: '已解决', CLOSED: '已关闭' }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = { PENDING: 'danger', DISPATCHED: 'warning', ARRIVED: 'warning', HANDLING: 'primary', RESOLVED: 'success', CLOSED: 'info' }
  return map[status] || ''
}

const fetchData = async () => {
  try {
    const params = { pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...searchForm }
    const res = await axios.get('/api/security/alarm/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch {
    tableData.value = [
      { alarmId: 1, alarmCode: 'A20260428001', alarmTitle: '周界入侵告警', alarmType: 'INTRUSION', alarmLevel: 'HIGH', deviceName: '周界摄像头01', locationAddress: '围墙东侧', alarmTime: '2026-04-28 10:25:00', currentStatus: 'PENDING' },
      { alarmId: 2, alarmCode: 'A20260428002', alarmTitle: '人员聚集告警', alarmType: 'CROWD', alarmLevel: 'MEDIUM', deviceName: '广场摄像头02', locationAddress: '中心广场', alarmTime: '2026-04-28 09:45:00', currentStatus: 'DISPATCHED' },
      { alarmId: 3, alarmCode: 'A20260428003', alarmTitle: '火情告警', alarmType: 'FIRE', alarmLevel: 'CRITICAL', deviceName: '烟感探测器01', locationAddress: '3号楼', alarmTime: '2026-04-28 08:30:00', currentStatus: 'RESOLVED' }
    ]
    pagination.total = 3
  }
}

const handleSearch = () => { pagination.pageNum = 1; fetchData() }
const handleReset = () => { Object.assign(searchForm, { alarmTitle: '', alarmType: '', alarmLevel: '', currentStatus: '' }); handleSearch() }
const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleView = (row) => ElMessage.info('查看告警详情: ' + row.alarmCode)

const handleDispatch = async (row) => {
  await ElMessageBox.prompt('请输入处理单位名称', '分派告警', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/security/alarm/dispatch/${row.alarmId}`, { handleDepartmentName: value })
        ElMessage.success('分派成功')
        fetchData()
      } catch { ElMessage.error('分派失败') }
    })
}

const handleArrive = async (row) => {
  try {
    await axios.put(`/api/security/alarm/arrive/${row.alarmId}`)
    ElMessage.success('已到场')
    fetchData()
  } catch { ElMessage.error('操作失败') }
}

const handleResolve = async (row) => {
  await ElMessageBox.prompt('请输入处理结果', '处理告警', { confirmButtonText: '确定', cancelButtonText: '取消' })
    .then(async ({ value }) => {
      try {
        await axios.put(`/api/security/alarm/resolve/${row.alarmId}`, { handleResult: '真警', handleDetail: value })
        ElMessage.success('处理完成')
        fetchData()
      } catch { ElMessage.error('处理失败') }
    })
}

const handleClose = async (row) => {
  try {
    await axios.put(`/api/security/alarm/close/${row.alarmId}`)
    ElMessage.success('已关闭')
    fetchData()
  } catch { ElMessage.error('关闭失败') }
}

onMounted(() => fetchData())
</script>

<style scoped>
.page-container { width: 100%; }
.search-form { margin-bottom: 20px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
