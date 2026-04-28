<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>巡检记录</span>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="inspectionCode" label="巡检编号" width="150" />
        <el-table-column prop="gridderName" label="网格员" width="100" />
        <el-table-column prop="gridCellName" label="巡检网格" width="150" />
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
import { ElMessage } from 'element-plus'

const tableData = ref([
  { inspectionCode: 'I20260428001', gridderName: '张三', gridCellName: '望京街道A区', inspectionType: 'ROUTINE', startTime: '2026-04-28 08:00', durationMinutes: 120, eventCount: 2, status: 1 },
  { inspectionCode: 'I20260428002', gridderName: '李四', gridCellName: '望京街道B区', inspectionType: 'SPECIAL', startTime: '2026-04-28 09:30', durationMinutes: 90, eventCount: 0, status: 1 }
])

const getTypeLabel = (type) => {
  const map = { ROUTINE: '例行巡检', SPECIAL: '专项巡检', EMERGENCY: '应急巡检', VIDEO: '视频巡检' }
  return map[type] || type
}

const handleView = (row) => ElMessage.info('查看巡检记录: ' + row.inspectionCode)
</script>

<style scoped>
.page-container { width: 100%; }
</style>
