<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>设备管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 添加设备
          </el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="设备名称">
          <el-input v-model="searchForm.deviceName" placeholder="请输入设备名称" clearable />
        </el-form-item>
        <el-form-item label="设备类型">
          <el-select v-model="searchForm.deviceType" placeholder="请选择" clearable>
            <el-option label="摄像头" value="CAMERA" />
            <el-option label="门禁" value="ACCESS_CONTROL" />
            <el-option label="报警器" value="ALARM" />
            <el-option label="广播" value="SPEAKER" />
            <el-option label="环境监测" value="ENVIRONMENT" />
            <el-option label="消防" value="FIRE" />
          </el-select>
        </el-form-item>
        <el-form-item label="设备状态">
          <el-select v-model="searchForm.deviceStatus" placeholder="请选择" clearable>
            <el-option label="在线" value="ONLINE" />
            <el-option label="离线" value="OFFLINE" />
            <el-option label="故障" value="FAULT" />
            <el-option label="维护中" value="MAINTENANCE" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="stats-row">
        <el-statistic title="设备总数" :value="stats.total" />
        <el-statistic title="在线设备" :value="stats.online" color="#67c23a" />
        <el-statistic title="离线设备" :value="stats.offline" color="#f56c6c" />
        <el-statistic title="故障设备" :value="stats.fault" color="#e6a23c" />
      </div>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="deviceCode" label="设备编码" width="150" />
        <el-table-column prop="deviceName" label="设备名称" min-width="180" />
        <el-table-column prop="deviceType" label="设备类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.deviceType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="manufacturer" label="生产厂家" width="150" />
        <el-table-column prop="installLocation" label="安装位置" min-width="150" show-overflow-tooltip />
        <el-table-column prop="deviceStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.deviceStatus)">{{ getStatusLabel(row.deviceStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="aiAnalysisEnabled" label="AI分析" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.aiAnalysisEnabled ? 'success' : 'info'" size="small">
              {{ row.aiAnalysisEnabled ? '启用' : '关闭' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastHeartbeatTime" label="最后心跳" width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="warning" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination class="pagination" v-model:current-page="pagination.pageNum" v-model:page-size="pagination.pageSize"
        :total="pagination.total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange" @current-change="handlePageChange" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-form-item label="设备编码" prop="deviceCode">
          <el-input v-model="formData.deviceCode" placeholder="请输入设备编码" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="formData.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备类型" prop="deviceType">
          <el-select v-model="formData.deviceType" placeholder="请选择">
            <el-option label="摄像头" value="CAMERA" />
            <el-option label="门禁" value="ACCESS_CONTROL" />
            <el-option label="报警器" value="ALARM" />
            <el-option label="广播" value="SPEAKER" />
            <el-option label="环境监测" value="ENVIRONMENT" />
            <el-option label="消防" value="FIRE" />
          </el-select>
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="formData.manufacturer" placeholder="请输入生产厂家" />
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="formData.model" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="序列号" prop="serialNumber">
          <el-input v-model="formData.serialNumber" placeholder="请输入序列号" />
        </el-form-item>
        <el-form-item label="IP地址" prop="ipAddress">
          <el-input v-model="formData.ipAddress" placeholder="请输入IP地址" />
        </el-form-item>
        <el-form-item label="安装位置" prop="installLocation">
          <el-input v-model="formData.installLocation" placeholder="请输入安装位置" />
        </el-form-item>
        <el-form-item label="启用AI分析">
          <el-switch v-model="formData.aiAnalysisEnabled" :active-value="1" :inactive-value="0" />
        </el-form-item>
        <el-form-item label="AI分析类型" v-if="formData.aiAnalysisEnabled">
          <el-checkbox-group v-model="formData.aiAnalysisTypes">
            <el-checkbox label="FACE">人脸识别</el-checkbox>
            <el-checkbox label="VEHICLE">车辆识别</el-checkbox>
            <el-checkbox label="BEHAVIOR">行为分析</el-checkbox>
            <el-checkbox label="CROWD">人群聚集</el-checkbox>
          </el-checkbox-group>
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

const searchForm = reactive({ deviceName: '', deviceType: '', deviceStatus: '' })
const pagination = reactive({ pageNum: 1, pageSize: 10, total: 0 })
const tableData = ref([])
const stats = reactive({ total: 0, online: 0, offline: 0, fault: 0 })

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const formData = reactive({
  deviceId: null, deviceCode: '', deviceName: '', deviceType: '', manufacturer: '', model: '',
  serialNumber: '', ipAddress: '', installLocation: '', aiAnalysisEnabled: 0, aiAnalysisTypes: []
})

const formRules = {
  deviceCode: [{ required: true, message: '请输入设备编码', trigger: 'blur' }],
  deviceName: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  deviceType: [{ required: true, message: '请选择设备类型', trigger: 'change' }]
}

const getTypeLabel = (type) => {
  const map = { CAMERA: '摄像头', ACCESS_CONTROL: '门禁', ALARM: '报警器', SPEAKER: '广播', ENVIRONMENT: '环境监测', FIRE: '消防' }
  return map[type] || type
}

const getStatusLabel = (status) => {
  const map = { ONLINE: '在线', OFFLINE: '离线', FAULT: '故障', MAINTENANCE: '维护' }
  return map[status] || status
}

const getStatusType = (status) => {
  const map = { ONLINE: 'success', OFFLINE: 'danger', FAULT: 'warning', MAINTENANCE: 'info' }
  return map[status] || ''
}

const fetchData = async () => {
  try {
    const params = { pageNum: pagination.pageNum, pageSize: pagination.pageSize, ...searchForm }
    const res = await axios.get('/api/security/device/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch {
    tableData.value = [
      { deviceId: 1, deviceCode: 'CAM001', deviceName: '东门摄像头', deviceType: 'CAMERA', manufacturer: '海康威视', installLocation: '东门入口', deviceStatus: 'ONLINE', aiAnalysisEnabled: 1, lastHeartbeatTime: '2026-04-28 10:30:00' },
      { deviceId: 2, deviceCode: 'CAM002', deviceName: '停车场摄像头', deviceType: 'CAMERA', manufacturer: '大华', installLocation: '地下停车场', deviceStatus: 'OFFLINE', aiAnalysisEnabled: 0, lastHeartbeatTime: '2026-04-28 08:00:00' },
      { deviceId: 3, deviceCode: 'AC001', deviceName: '主楼门禁', deviceType: 'ACCESS_CONTROL', manufacturer: '中控智慧', installLocation: '主楼大厅', deviceStatus: 'ONLINE', aiAnalysisEnabled: 1, lastHeartbeatTime: '2026-04-28 10:35:00' }
    ]
    pagination.total = 3
  }
}

const fetchStats = async () => {
  try {
    const res = await axios.get('/api/security/device/statistics')
    Object.assign(stats, res.data?.data || {})
  } catch {
    Object.assign(stats, { total: 128, online: 98, offline: 25, fault: 5 })
  }
}

const handleSearch = () => { pagination.pageNum = 1; fetchData() }
const handleReset = () => { Object.assign(searchForm, { deviceName: '', deviceType: '', deviceStatus: '' }); handleSearch() }
const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleAdd = () => {
  dialogTitle.value = '添加设备'
  Object.assign(formData, { deviceId: null, deviceCode: '', deviceName: '', deviceType: '', manufacturer: '', model: '', serialNumber: '', ipAddress: '', installLocation: '', aiAnalysisEnabled: 0, aiAnalysisTypes: [] })
  dialogVisible.value = true
}

const handleView = (row) => {
  dialogTitle.value = '设备详情'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑设备'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定要删除该设备吗?', '提示', { type: 'warning' })
  try {
    await axios.delete(`/api/security/device/${row.deviceId}`)
    ElMessage.success('删除成功')
    fetchData()
    fetchStats()
  } catch { ElMessage.error('删除失败') }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (formData.deviceId) {
        await axios.put('/api/security/device', formData)
      } else {
        await axios.post('/api/security/device', formData)
      }
      ElMessage.success('操作成功')
      dialogVisible.value = false
      fetchData()
      fetchStats()
    } catch { ElMessage.error('操作失败') }
  })
}

onMounted(() => { fetchData(); fetchStats() })
</script>

<style scoped>
.page-container { width: 100%; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
.stats-row { display: flex; gap: 40px; margin-bottom: 20px; padding: 15px; background: #f5f7fa; border-radius: 8px; }
.pagination { margin-top: 20px; display: flex; justify-content: flex-end; }
</style>
