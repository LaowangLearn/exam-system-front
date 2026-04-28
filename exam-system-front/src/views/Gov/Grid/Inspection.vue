<template>
  <div class="gov-grid-inspection">
    <div class="page-header">
      <h1>巡检记录管理</h1>
    </div>
    
    <el-card>
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="inspectionCode" label="巡检编号" width="150" />
        <el-table-column prop="gridderName" label="网格员" width="100" />
        <el-table-column prop="cellName" label="巡检网格" width="150" />
        <el-table-column prop="inspectionType" label="巡检类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.inspectionType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="durationMinutes" label="时长(分钟)" width="100" align="center" />
        <el-table-column prop="eventCount" label="发现问题数" width="100" align="center">
          <template #default="{ row }">
            <el-tag type="warning">{{ row.eventCount }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const tableData = ref([
  {
    id: 1,
    inspectionCode: 'XJ20260428001',
    gridderName: '王五',
    cellName: '网格A1',
    inspectionType: 1,
    startTime: '2026-04-28 08:00',
    durationMinutes: 45,
    eventCount: 2,
    status: 1
  },
  {
    id: 2,
    inspectionCode: 'XJ20260428002',
    gridderName: '赵六',
    cellName: '网格A2',
    inspectionType: 1,
    startTime: '2026-04-28 09:30',
    durationMinutes: 60,
    eventCount: 0,
    status: 1
  },
  {
    id: 3,
    inspectionCode: 'XJ20260427001',
    gridderName: '李四',
    cellName: '第一社区',
    inspectionType: 2,
    startTime: '2026-04-27 14:00',
    durationMinutes: 90,
    eventCount: 3,
    status: 1
  }
])

const handleView = (row) => {
  alert(`查看巡检记录: ${row.inspectionCode}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '日常巡检', 2: '专项巡检', 3: '夜间巡检' }
  return types[type] || '其他'
}
</script>

<style scoped>
.gov-grid-inspection {
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
</style>
