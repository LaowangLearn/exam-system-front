<template>
  <div class="gov-grid-cell">
    <div class="page-header">
      <h1>网格单元管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">新增网格</el-button>
      </div>
    </div>
    
    <el-card>
      <div class="tree-container">
        <el-tree
          :data="treeData"
          :props="treeProps"
          default-expand-all
          node-key="id"
          @node-click="handleNodeClick"
        >
          <template #default="{ node, data }">
            <span class="custom-tree-node">
              <span>{{ data.label }}</span>
              <span class="node-actions" v-if="data.id">
                <el-button size="mini" @click.stop="handleEdit(data)">编辑</el-button>
                <el-button size="mini" type="danger" @click.stop="handleDelete(data)">删除</el-button>
              </span>
            </span>
          </template>
        </el-tree>
      </div>
    </el-card>
    
    <el-card v-if="selectedNode" title="网格详情">
      <el-form :model="selectedNode" label-width="100px">
        <el-form-item label="网格编号">
          <el-input :value="selectedNode.code" disabled />
        </el-form-item>
        <el-form-item label="网格名称">
          <el-input :value="selectedNode.label" disabled />
        </el-form-item>
        <el-form-item label="网格员">
          <el-input :value="selectedNode.gridderName" disabled />
        </el-form-item>
        <el-form-item label="网格类型">
          <el-tag>{{ getTypeLabel(selectedNode.cellType) }}</el-tag>
        </el-form-item>
        <el-form-item label="状态">
          <el-tag :type="selectedNode.status === 1 ? 'success' : 'danger'">
            {{ selectedNode.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input :value="selectedNode.createTime" disabled />
        </el-form-item>
      </el-form>
    </el-card>
    
    <!-- 新增/编辑弹窗 -->
    <el-dialog :title="isEdit ? '编辑网格' : '新增网格'" :visible.sync="dialogVisible">
      <el-form :model="formData" label-width="100px">
        <el-form-item label="网格名称" prop="label">
          <el-input v-model="formData.label" />
        </el-form-item>
        <el-form-item label="网格编号" prop="code">
          <el-input v-model="formData.code" />
        </el-form-item>
        <el-form-item label="网格员" prop="gridderName">
          <el-input v-model="formData.gridderName" />
        </el-form-item>
        <el-form-item label="网格类型" prop="cellType">
          <el-select v-model="formData.cellType">
            <el-option label="基础网格" :value="1" />
            <el-option label="专属网格" :value="2" />
            <el-option label="特殊网格" :value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'

const treeProps = {
  children: 'children',
  label: 'label'
}

const treeData = ref([
  {
    id: 1,
    label: '第一街道',
    code: 'JD001',
    gridderName: '张三',
    cellType: 1,
    status: 1,
    createTime: '2026-01-15 10:30',
    children: [
      {
        id: 11,
        label: '第一社区',
        code: 'SQ001',
        gridderName: '李四',
        cellType: 1,
        status: 1,
        createTime: '2026-01-16 09:00',
        children: [
          {
            id: 111,
            label: '网格A1',
            code: 'WG001',
            gridderName: '王五',
            cellType: 1,
            status: 1,
            createTime: '2026-01-17 14:00'
          },
          {
            id: 112,
            label: '网格A2',
            code: 'WG002',
            gridderName: '赵六',
            cellType: 1,
            status: 1,
            createTime: '2026-01-18 11:00'
          }
        ]
      },
      {
        id: 12,
        label: '第二社区',
        code: 'SQ002',
        gridderName: '孙七',
        cellType: 2,
        status: 1,
        createTime: '2026-01-19 15:30'
      }
    ]
  },
  {
    id: 2,
    label: '第二街道',
    code: 'JD002',
    gridderName: '周八',
    cellType: 1,
    status: 1,
    createTime: '2026-02-01 08:00',
    children: [
      {
        id: 21,
        label: '第三社区',
        code: 'SQ003',
        gridderName: '吴九',
        cellType: 3,
        status: 1,
        createTime: '2026-02-02 10:00'
      }
    ]
  }
])

const selectedNode = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const formData = reactive({
  id: '',
  label: '',
  code: '',
  gridderName: '',
  cellType: 1
})

const handleNodeClick = (data) => {
  selectedNode.value = { ...data }
}

const handleAdd = () => {
  isEdit.value = false
  formData.id = ''
  formData.label = ''
  formData.code = ''
  formData.gridderName = ''
  formData.cellType = 1
  dialogVisible.value = true
}

const handleEdit = (data) => {
  isEdit.value = true
  formData.id = data.id
  formData.label = data.label
  formData.code = data.code
  formData.gridderName = data.gridderName
  formData.cellType = data.cellType
  dialogVisible.value = true
}

const handleDelete = (data) => {
  alert(`删除网格: ${data.label}`)
}

const handleSubmit = () => {
  alert(`提交: ${formData.label}`)
  dialogVisible.value = false
}

const getTypeLabel = (type) => {
  const types = { 1: '基础网格', 2: '专属网格', 3: '特殊网格' }
  return types[type] || '未知'
}
</script>

<style scoped>
.gov-grid-cell {
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

.tree-container {
  height: 400px;
  overflow-y: auto;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  padding-right: 8px;
}

.node-actions {
  display: flex;
  gap: 8px;
}
</style>
