<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>审批管理</span>
      </template>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="docCode" label="公文编号" width="180" />
        <el-table-column prop="docTitle" label="公文标题" min-width="250" />
        <el-table-column prop="docType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.docType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="issueUserName" label="拟稿人" width="100" />
        <el-table-column prop="currentApprovalNode" label="当前节点" width="120" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="success" size="small" @click="handleApprove(row)">审批</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'

const tableData = ref([
  { docCode: 'GOV-2026-002', docTitle: '关于表彰先进集体的决定', docType: 'DECISION', issueUserName: '李四', currentApprovalNode: '办公室审核' },
  { docCode: 'GOV-2026-004', docTitle: '关于拨付专项资金的函', docType: 'LETTER', issueUserName: '赵六', currentApprovalNode: '领导签发' }
])

const handleView = (row) => ElMessage.info('查看: ' + row.docCode)
const handleApprove = (row) => ElMessage.success('审批通过: ' + row.docCode)

onMounted(async () => {
  try {
    const res = await axios.get('/api/document/pending/approvals')
    if (res.data?.data?.length) {
      tableData.value = res.data.data
    }
  } catch {}
})
</script>

<style scoped>
.page-container { width: 100%; }
</style>
