<template>
  <div class="gov-document-list">
    <button class="back-btn" @click="goBack">�?返回政务事业单位�?/button>
    <div class="page-header">
      <h1>公文列表</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleCreate">新建公文</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="公文标题/文号" v-model="searchText" style="width: 300px;" />
        <el-select v-model="searchType" placeholder="公文类型">
          <el-option :label="label" :value="value" v-for="item in typeOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="docCode" label="文号" width="150" />
        <el-table-column prop="docTitle" label="标题" />
        <el-table-column prop="docType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.docType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="docLevel" label="密级" width="80">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.docLevel)">{{ getLevelLabel(row.docLevel) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状�? width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createUser" label="拟稿�? width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看</el-button>
            <el-button size="mini" type="primary" @click="handleEdit(row)" v-if="row.status === 1">编辑</el-button>
            <el-button size="mini" type="success" @click="handleSubmit(row)" v-if="row.status === 1">提交</el-button>
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
  { label: '通知', value: '1' },
  { label: '报告', value: '2' },
  { label: '请示', value: '3' },
  { label: '批复', value: '4' }
]

const router = useRouter()

const goBack = () => {
  router.push('/gov')
}

const tableData = ref([
  {
    id: 1,
    docCode: 'XX�?026�?�?,
    docTitle: '关于开展安全生产检查的通知',
    docType: 1,
    docLevel: 2,
    status: 2,
    createUser: '张三',
    createTime: '2026-04-28 09:00'
  },
  {
    id: 2,
    docCode: 'XX�?026�?�?,
    docTitle: '2026年第一季度工作报告',
    docType: 2,
    docLevel: 3,
    status: 1,
    createUser: '李四',
    createTime: '2026-04-28 10:30'
  },
  {
    id: 3,
    docCode: 'XX�?026�?�?,
    docTitle: '关于申请经费的请�?,
    docType: 3,
    docLevel: 3,
    status: 3,
    createUser: '王五',
    createTime: '2026-04-27 14:00'
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchType.value)
}

const handleCreate = () => {
  alert('新建公文')
}

const handleView = (row) => {
  alert(`查看公文: ${row.docTitle}`)
}

const handleEdit = (row) => {
  alert(`编辑公文: ${row.docTitle}`)
}

const handleSubmit = (row) => {
  alert(`提交公文: ${row.docTitle}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '通知', 2: '报告', 3: '请示', 4: '批复', 5: '�? }
  return types[type] || '其他'
}

const getLevelLabel = (level) => {
  const levels = { 1: '绝密', 2: '机密', 3: '秘密', 4: '普�? }
  return levels[level] || '普�?
}

const getLevelType = (level) => {
  const types = { 1: 'danger', 2: 'danger', 3: 'warning', 4: 'info' }
  return types[level] || 'info'
}

const getStatusLabel = (status) => {
  const statuses = { 1: '草稿', 2: '审批�?, 3: '已发�?, 4: '已归�?, 5: '已撤�? }
  return statuses[status] || '未知'
}

const getStatusType = (status) => {
  const types = { 1: 'info', 2: 'primary', 3: 'success', 4: 'info', 5: 'warning' }
  return types[status] || 'info'
}
</script>

<style scoped>
.gov-document-list {
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
