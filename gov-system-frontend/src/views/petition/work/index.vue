<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>办理工单</span>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="workCode" label="办理编号" width="180" />
        <el-table-column prop="petitionCode" label="关联信访" width="180" />
        <el-table-column prop="workPhase" label="办理阶段" width="120">
          <template #default="{ row }">
            <el-tag>{{ getPhaseLabel(row.workPhase) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="departmentName" label="办理单位" width="150" />
        <el-table-column prop="userName" label="承办人" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
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
  { workCode: 'W20260428001', petitionCode: 'P20260428002', workPhase: 'INVESTIGATION', departmentName: '城管执法局', userName: '工作人员A', createTime: '2026-04-28 10:00' },
  { workCode: 'W20260428002', petitionCode: 'P20260428003', workPhase: 'COORDINATION', departmentName: '市场监管局', userName: '工作人员B', createTime: '2026-04-28 09:00' }
])

const getPhaseLabel = (phase) => {
  const map = { INVESTIGATION: '调查', COORDINATION: '协调', DECISION: '决定', RESPONSE: '答复', FOLLOWUP: '跟踪', CLOSURE: '结案' }
  return map[phase] || phase
}

const handleView = (row) => ElMessage.info('查看办理工单: ' + row.workCode)
</script>

<style scoped>
.page-container { width: 100%; }
</style>
