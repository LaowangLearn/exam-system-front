<template>
  <div class="gov-petition-work">
    <button class="back-btn" @click="goBack">�?返回政务事业单位�?/button>
    <div class="page-header">
      <h1>办理工单管理</h1>
    </div>
    
    <el-card>
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="workCode" label="办理编号" width="180" />
        <el-table-column prop="petitionCode" label="关联信访" width="180" />
        <el-table-column prop="workPhase" label="办理阶段" width="120">
          <template #default="{ row }">
            <el-tag>{{ getPhaseLabel(row.workPhase) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="departmentName" label="办理单位" width="150" />
        <el-table-column prop="userName" label="承办�? width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column prop="deadline" label="截止时间" width="160" />
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
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const tableData = ref([
  {
    id: 1,
    workCode: 'GD20260428001',
    petitionCode: 'XF20260428001',
    workPhase: 2,
    departmentName: '物业�?,
    userName: '陈主�?,
    createTime: '2026-04-28 09:30',
    deadline: '2026-05-05 09:30'
  },
  {
    id: 2,
    workCode: 'GD20260428002',
    petitionCode: 'XF20260428002',
    workPhase: 1,
    departmentName: '社保中心',
    userName: '王科�?,
    createTime: '2026-04-28 11:00',
    deadline: '2026-05-01 11:00'
  },
  {
    id: 3,
    workCode: 'GD20260427001',
    petitionCode: 'XF20260427001',
    workPhase: 3,
    departmentName: '文体局',
    userName: '李主�?,
    createTime: '2026-04-27 15:30',
    deadline: '2026-05-04 15:30'
  }
])

const handleView = (row) => {
  alert(`查看工单: ${row.workCode}`)
}

const getPhaseLabel = (phase) => {
  const phases = { 1: '待分�?, 2: '办理�?, 3: '待审�?, 4: '已完�? }
  return phases[phase] || '未知'
}
</script>

<style scoped>
.gov-petition-work {
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
