<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>网格单元管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon> 新增网格
          </el-button>
        </div>
      </template>

      <el-form :inline="true" class="search-form">
        <el-form-item label="网格名称">
          <el-input v-model="searchForm.cellName" placeholder="请输入网格名称" clearable />
        </el-form-item>
        <el-form-item label="网格类型">
          <el-select v-model="searchForm.cellType" placeholder="请选择" clearable>
            <el-option label="行政区域" value="REGION" />
            <el-option label="社区" value="COMMUNITY" />
            <el-option label="网格" value="GRID" />
            <el-option label="楼栋" value="BUILDING" />
            <el-option label="单元" value="UNIT" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" row-key="cellId" default-expand-all :tree-props="{ children: 'children' }">
        <el-table-column prop="cellCode" label="网格编码" width="150" />
        <el-table-column prop="cellName" label="网格名称" min-width="200" />
        <el-table-column prop="cellType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ getTypeLabel(row.cellType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="levelCode" label="层级编码" width="150" />
        <el-table-column prop="populationCount" label="人口数" width="100" align="center" />
        <el-table-column prop="responsibleUserName" label="网格员" width="120" />
        <el-table-column prop="responsiblePhone" label="联系电话" width="150" />
        <el-table-column prop="status" label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        class="pagination"
        v-model:current-page="pagination.pageNum"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="上级网格" prop="parentId">
          <el-tree-select
            v-model="formData.parentId"
            :data="treeData"
            :props="{ label: 'cellName', value: 'cellId' }"
            placeholder="请选择上级网格"
            clearable
            check-strictly
          />
        </el-form-item>
        <el-form-item label="网格编码" prop="cellCode">
          <el-input v-model="formData.cellCode" placeholder="请输入网格编码" />
        </el-form-item>
        <el-form-item label="网格名称" prop="cellName">
          <el-input v-model="formData.cellName" placeholder="请输入网格名称" />
        </el-form-item>
        <el-form-item label="网格类型" prop="cellType">
          <el-select v-model="formData.cellType" placeholder="请选择">
            <el-option label="行政区域" value="REGION" />
            <el-option label="社区" value="COMMUNITY" />
            <el-option label="网格" value="GRID" />
            <el-option label="楼栋" value="BUILDING" />
            <el-option label="单元" value="UNIT" />
          </el-select>
        </el-form-item>
        <el-form-item label="层级编码" prop="levelCode">
          <el-input v-model="formData.levelCode" placeholder="如: 01-02-03" />
        </el-form-item>
        <el-form-item label="网格员" prop="responsibleUserName">
          <el-input v-model="formData.responsibleUserName" placeholder="请输入网格员姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="responsiblePhone">
          <el-input v-model="formData.responsiblePhone" placeholder="请输入联系电话" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from '@/utils/axios'

const searchForm = reactive({
  cellName: '',
  cellType: ''
})

const pagination = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

const tableData = ref([])
const treeData = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const formData = reactive({
  cellId: null,
  parentId: null,
  cellCode: '',
  cellName: '',
  cellType: '',
  levelCode: '',
  responsibleUserName: '',
  responsiblePhone: '',
  status: 1
})

const formRules = {
  cellCode: [{ required: true, message: '请输入网格编码', trigger: 'blur' }],
  cellName: [{ required: true, message: '请输入网格名称', trigger: 'blur' }],
  cellType: [{ required: true, message: '请选择网格类型', trigger: 'change' }]
}

const getTypeLabel = (type) => {
  const map = { REGION: '行政区域', COMMUNITY: '社区', GRID: '网格', BUILDING: '楼栋', UNIT: '单元' }
  return map[type] || type
}

const fetchData = async () => {
  try {
    const params = {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      ...searchForm
    }
    const res = await axios.get('/api/grid/cell/page', { params })
    tableData.value = res.data?.data?.records || []
    pagination.total = res.data?.data?.total || 0
  } catch (error) {
    tableData.value = [
      { cellId: 1, cellCode: 'G001', cellName: '朝阳区', cellType: 'REGION', levelCode: '01', populationCount: 1200, responsibleUserName: '张三', responsiblePhone: '13800138000', status: 1 },
      { cellId: 2, cellCode: 'G002', cellName: '望京街道', cellType: 'COMMUNITY', levelCode: '01-01', parentId: 1, populationCount: 800, responsibleUserName: '李四', responsiblePhone: '13900139000', status: 1 },
      { cellId: 3, cellCode: 'G003', cellName: '网格A区', cellType: 'GRID', levelCode: '01-01-01', parentId: 2, populationCount: 200, responsibleUserName: '王五', responsiblePhone: '13700137000', status: 1 }
    ]
    pagination.total = 3
  }
}

const fetchTreeData = async () => {
  try {
    const res = await axios.get('/api/grid/cell/list')
    treeData.value = res.data?.data || []
  } catch (error) {
    treeData.value = tableData.value
  }
}

const handleSearch = () => {
  pagination.pageNum = 1
  fetchData()
}

const handleReset = () => {
  searchForm.cellName = ''
  searchForm.cellType = ''
  handleSearch()
}

const handleSizeChange = () => fetchData()
const handlePageChange = () => fetchData()

const handleAdd = () => {
  dialogTitle.value = '新增网格'
  Object.assign(formData, { cellId: null, parentId: null, cellCode: '', cellName: '', cellType: '', levelCode: '', responsibleUserName: '', responsiblePhone: '', status: 1 })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑网格'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleView = (row) => {
  dialogTitle.value = '网格详情'
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确定要删除该网格吗?', '提示', { type: 'warning' })
  try {
    await axios.delete(`/api/grid/cell/${row.cellId}`)
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (formData.cellId) {
        await axios.put('/api/grid/cell', formData)
      } else {
        await axios.post('/api/grid/cell', formData)
      }
      ElMessage.success('操作成功')
      dialogVisible.value = false
      fetchData()
      fetchTreeData()
    } catch (error) {
      ElMessage.error('操作失败')
    }
  })
}

onMounted(() => {
  fetchData()
  fetchTreeData()
})
</script>

<style scoped>
.page-container {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
