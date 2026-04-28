<template>
  <div class="gov-security-alarm">
    <div class="page-header">
      <h1>告警记录管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleRefresh">刷新</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="告警编号" v-model="searchText" style="width: 200px;" />
        <el-select v-model="searchLevel" placeholder="告警等级">
          <el-option :label="label" :value="value" v-for="item in levelOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="alarmCode" label="告警编号" width="180" />
        <el-table-column prop="deviceName" label="关联设备" width="150" />
        <el-table-column prop="alarmType" label="告警类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.alarmType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="alarmLevel" label="告警等级" width="100">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.alarmLevel)">{{ getLevelLabel(row.alarmLevel) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="alarmContent" label="告警内容" />
        <el-table-column prop="alarmTime" label="告警时间" width="160" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看</el-button>
            <el-button size="mini" type="primary" @click="handleHandle(row)" v-if="row.status === 1">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchText = ref('')
const searchLevel = ref('')

const levelOptions = [
  { label: '全部', value: '' },
  { label: '紧急', value: '1' },
  { label: '重要', value: '2' },
  { label: '一般', value: '3' }
]

const tableData = ref([
  {
    id: 1,
    alarmCode: 'ALM20260428001',
    deviceName: '小区东门摄像头',
    alarmType: 1,
    alarmLevel: 1,
    alarmContent: '检测到可疑人员徘徊',
    alarmTime: '2026-04-28 08:30:25',
    status: 1
  },
  {
    id: 2,
    alarmCode: 'ALM20260428002',
    deviceName: '1号门禁控制器',
    alarmType: 2,
    alarmLevel: 2,
    alarmContent: '连续三次刷卡失败',
    alarmTime: '2026-04-28 09:15:10',
    status: 2
  },
  {
    id: 3,
    alarmCode: 'ALM20260427001',
    deviceName: '消防报警主机',
    alarmType: 3,
    alarmLevel: 1,
    alarmContent: '烟雾探测器报警',
    alarmTime: '2026-04-27 16:45:30',
    status: 3
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchLevel.value)
}

const handleRefresh = () => {
  console.log('刷新告警列表')
}

const handleView = (row) => {
  alert(`查看告警: ${row.alarmCode}`)
}

const handleHandle = (row) => {
  alert(`处理告警: ${row.alarmCode}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '入侵检测', 2: '门禁异常', 3: '消防告警', 4: '设备故障' }
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
  const statuses = { 1: '待处理', 2: '处理中', 3: '已处置' }
  return statuses[status] || '未知'
}

const getStatusType = (status) => {
  const types = { 1: 'danger', 2: 'primary', 3: 'success' }
  return types[status] || 'info'
}
</script>

<style scoped>
.gov-security-alarm {
  padding: 24px;
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
</style>
