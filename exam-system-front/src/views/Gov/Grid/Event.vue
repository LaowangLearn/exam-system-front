<template>
  <div class="gov-grid-event">
    <button class="back-btn" @click="goBack">← 返回政务事业单位类</button>
    <div class="page-header">
      <h1>网格事件管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">上报事件</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="事件编号/事件描述" v-model="searchText" style="width: 300px;" />
        <el-select v-model="searchStatus" placeholder="状态">
          <el-option :label="label" :value="value" v-for="item in statusOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="eventCode" label="事件编号" width="150" />
        <el-table-column prop="eventTitle" label="事件标题" />
        <el-table-column prop="cellName" label="所属网格" width="120" />
        <el-table-column prop="eventType" label="事件类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.eventType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="eventLevel" label="等级" width="80">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.eventLevel)">{{ getLevelLabel(row.eventLevel) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reportTime" label="上报时间" width="160" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看</el-button>
            <el-button size="mini" type="primary" @click="handleProcess(row)" v-if="row.status === 1">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 事件详情弹窗 -->
    <el-dialog title="事件详情" :visible.sync="detailVisible">
      <el-form :model="detailData" label-width="100px">
        <el-form-item label="事件编号">
          <el-input :value="detailData.eventCode" disabled />
        </el-form-item>
        <el-form-item label="事件标题">
          <el-input :value="detailData.eventTitle" disabled />
        </el-form-item>
        <el-form-item label="事件描述">
          <el-textarea :value="detailData.eventDesc" :rows="3" disabled />
        </el-form-item>
        <el-form-item label="所属网格">
          <el-input :value="detailData.cellName" disabled />
        </el-form-item>
        <el-form-item label="事件类型">
          <el-tag>{{ getTypeLabel(detailData.eventType) }}</el-tag>
        </el-form-item>
        <el-form-item label="事件等级">
          <el-tag :type="getLevelType(detailData.eventLevel)">{{ getLevelLabel(detailData.eventLevel) }}</el-tag>
        </el-form-item>
        <el-form-item label="处理进度">
          <div v-for="(step, index) in processSteps" :key="index" class="process-step">
            <span :class="{ active: index <= detailData.currentStep }">●</span>
            <span>{{ step }}</span>
          </div>
        </el-form-item>
      </el-form>
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
const searchStatus = ref('')
const detailVisible = ref(false)
const detailData = reactive({})

const statusOptions = [
  { label: '全部', value: '' },
  { label: '待处理', value: '1' },
  { label: '处理中', value: '2' },
  { label: '已结案', value: '3' }
]

const processSteps = ['已上报', '已受理', '处理中', '已结案']

const tableData = ref([
  {
    id: 1,
    eventCode: 'EVT20260428001',
    eventTitle: '社区道路破损',
    eventDesc: 'XX小区东门入口处路面有多处坑洼，影响居民出行安全',
    cellName: '网格A1',
    eventType: 1,
    eventLevel: 2,
    status: 2,
    reportTime: '2026-04-28 09:30',
    currentStep: 2
  },
  {
    id: 2,
    eventCode: 'EVT20260428002',
    eventTitle: '高空抛物隐患',
    eventDesc: '居民反映15号楼有高空抛物现象',
    cellName: '网格A2',
    eventType: 2,
    eventLevel: 1,
    status: 1,
    reportTime: '2026-04-28 10:15',
    currentStep: 0
  },
  {
    id: 3,
    eventCode: 'EVT20260427001',
    eventTitle: '垃圾分类不规范',
    eventDesc: 'XX小区垃圾分类点有混投现象',
    cellName: '第二社区',
    eventType: 3,
    eventLevel: 3,
    status: 3,
    reportTime: '2026-04-27 14:20',
    currentStep: 3
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchStatus.value)
}

const handleAdd = () => {
  alert('上报事件')
}

const handleView = (row) => {
  Object.assign(detailData, row)
  detailVisible.value = true
}

const handleProcess = (row) => {
  alert(`处理事件: ${row.eventTitle}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '设施维护', 2: '安全隐患', 3: '环境卫生', 4: '民生服务' }
  return types[type] || '其他'
}

const getLevelLabel = (level) => {
  const levels = { 1: '紧急', 2: '重要', 3: '一般' }
  return levels[level] || '未知'
}

const getLevelType = (level) => {
  const types = { 1: 'danger', 2: 'warning', 3: 'info' }
  return types[level] || 'info'
}

const getStatusLabel = (status) => {
  const statuses = { 1: '待处理', 2: '处理中', 3: '已结案' }
  return statuses[status] || '未知'
}

const getStatusType = (status) => {
  const types = { 1: 'warning', 2: 'primary', 3: 'success' }
  return types[status] || 'info'
}
</script>

<style scoped>
.gov-grid-event {
  padding: 24px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(59, 130, 246, 0.4);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.process-step {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 8px;
  font-size: 14px;
}

.process-step span:first-child {
  width: 16px;
  height: 16px;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
}

.process-step.active span:first-child {
  background: #3b82f6;
  color: white;
}

.process-step:not(.active) span:first-child {
  background: #e2e8f0;
  color: #94a3b8;
}
</style>
