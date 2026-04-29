<template>
  <div class="gov-petition-register">
    <button class="back-btn" @click="goBack">返回政务事业单位类</button>
    <div class="page-header">
      <h1>信访登记管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">新增登记</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="search-bar">
        <el-input placeholder="信访编号/信访人" v-model="searchText" style="width: 300px;" />
        <el-select v-model="searchType" placeholder="信访类型">
          <el-option :label="label" :value="value" v-for="item in typeOptions" :key="item.value" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="petitionCode" label="信访编号" width="180" />
        <el-table-column prop="petitionerName" label="信访人" width="100" />
        <el-table-column prop="petitionType" label="信访类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.petitionType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="petitionChannel" label="渠道" width="100">
          <template #default="{ row }">
            <el-tag>{{ getChannelLabel(row.petitionChannel) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="petitionTitle" label="信访标题" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="登记时间" width="160" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <el-button size="mini" @click="handleView(row)">查看</el-button>
            <el-button size="mini" type="primary" @click="handleAccept(row)" v-if="row.status === 1">受理</el-button>
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

const searchType = ref('')

const typeOptions = [
  { label: '全部', value: '' },
  { label: '来信', value: '1' },
  { label: '来访', value: '2' },
  { label: '来电', value: '3' },
  { label: '网上信访', value: '4' }
]

const tableData = ref([
  {
    id: 1,
    petitionCode: 'XF20260428001',
    petitionerName: '张先生',
    petitionType: 1,
    petitionChannel: 1,
    petitionTitle: '关于小区物业管理问题的投诉',
    status: 2,
    createTime: '2026-04-28 09:00'
  },
  {
    id: 2,
    petitionCode: 'XF20260428002',
    petitionerName: '李女士',
    petitionType: 2,
    petitionChannel: 2,
    petitionTitle: '咨询社保政策',
    status: 1,
    createTime: '2026-04-28 10:30'
  },
  {
    id: 3,
    petitionCode: 'XF20260427001',
    petitionerName: '王先生',
    petitionType: 4,
    petitionChannel: 4,
    petitionTitle: '建议增加社区文化活动',
    status: 3,
    createTime: '2026-04-27 15:00'
  }
])

const handleSearch = () => {
  console.log('搜索:', searchText.value, searchType.value)
}

const handleAdd = () => {
  alert('新增信访登记')
}

const handleView = (row) => {
  alert(`查看信访: ${row.petitionCode}`)
}

const handleAccept = (row) => {
  alert(`受理信访: ${row.petitionCode}`)
}

const getTypeLabel = (type) => {
  const types = { 1: '来信', 2: '来访', 3: '来电', 4: '网上信访' }
  return types[type] || '其他'
}

const getChannelLabel = (channel) => {
  const channels = { 1: '来信', 2: '来访', 3: '来电', 4: '网络' }
  return channels[channel] || '其他'
}

const getStatusLabel = (status) => {
  const statuses = { 1: '待受理', 2: '处理中', 3: '已答复', 4: '已结案' }
  return statuses[status] || '未知'
}

const getStatusType = (status) => {
  const types = { 1: 'warning', 2: 'primary', 3: 'success', 4: 'info' }
  return types[status] || 'info'
}
</script>

<style scoped>
.gov-petition-register {
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
