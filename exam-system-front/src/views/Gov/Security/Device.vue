<template>
  <div class="gov-security-device">
    <button class="back-btn" @click="goBack">�?返回政务事业单位�?/button>
    <div class="page-header">
      <h1>安防设备管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">添加设备</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="设备编号/设备名称" v-model="searchText" style="width: 300px;" />
        <el-select v-model="searchType" placeholder="设备类型">
          <el-option :label="label" :value="value" v-for="item in typeOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="deviceCode" label="设备编号" width="150" />
        <el-table-column prop="deviceName" label="设备名称" />
        <el-table-column prop="deviceType" label="设备类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.deviceType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="安装位置" width="150" />
        <el-table-column prop="status" label="状�? width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '在线' : '离线' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="installTime" label="安装时间" width="160" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看</el-button>
            <el-button size="mini" type="primary" @click="handleConfig(row)">配置</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
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

const searchType = ref('')

const typeOptions = [
  { label: '全部', value: '' },
  { label: '摄像�?, value: '1' },
  { label: '门禁', value: '2' },
  { label: '报警主机', value: '3' },
  { label: '消防设备', value: '4' }
]

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const tableData = ref([
  {
    id: 1,
    deviceCode: 'DEV001',
    deviceName: '小区东门摄像�?,
    deviceType: 1,
    location: '东门入口',
    status: 1,
    installTime: '2026-01-10'
  },
  {
    id: 2,
    deviceCode: 'DEV002',
    deviceName: '1号门禁控制器',
    deviceType: 2,
    location: '1号楼大堂',
    status: 1,
    installTime: '2026-01-15'
  },
  {
    id: 3,
    deviceCode: 'DEV003',
    deviceName: '消防报警主机',
    deviceType: 4,
    location: '消防控制�?,
    status: 1,
    installTime: '2026-02-01'
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchType.value)
}

const handleAdd = () => {
  alert('添加设备')
}

const handleView = (row) => {
  alert(`查看设备: ${row.deviceName}`)
}

const handleConfig = (row) => {
  alert(`配置设备: ${row.deviceName}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '摄像�?, 2: '门禁', 3: '报警主机', 4: '消防设备' }
  return types[type] || '其他'
}
</script>

<style scoped>
.gov-security-device {
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
