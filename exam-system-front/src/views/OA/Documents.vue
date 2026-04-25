<template>
  <div class="oa-documents">
    <button class="back-btn" @click="goBack">返回企业系统</button>
    <div class="page-header">
      <h1>公文管理</h1>
      <button class="add-btn" @click="openAddDocumentDialog">添加公文</button>
    </div>

    <div class="document-filter">
      <div class="filter-row">
        <input type="text" v-model="searchKeyword" placeholder="搜索公文标题" />
        <select v-model="statusFilter">
          <option value="">全部状态</option>
          <option value="DRAFT">草稿</option>
          <option value="SUBMITTED">已提交</option>
          <option value="APPROVED">已批准</option>
          <option value="REJECTED">已拒绝</option>
        </select>
        <button class="search-btn" @click="filterDocuments">搜索</button>
      </div>
    </div>

    <div class="document-list">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>标题</th>
            <th>类型</th>
            <th>状态</th>
            <th>创建人</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="doc in filteredDocuments" :key="doc.id">
            <td>{{ doc.id }}</td>
            <td>{{ doc.title }}</td>
            <td>{{ doc.type }}</td>
            <td>
              <span class="status" :class="doc.status">{{ doc.status }}</span>
            </td>
            <td>{{ doc.creatorName }}</td>
            <td>{{ doc.createTime }}</td>
            <td>
              <button class="view-btn" @click="viewDocument(doc.id)">查看</button>
              <button class="edit-btn" @click="openEditDocumentDialog(doc)">编辑</button>
              <button class="delete-btn" @click="deleteDocument(doc.id)">删除</button>
              <button v-if="doc.status === 'DRAFT'" class="submit-btn" @click="submitDocument(doc.id)">提交</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 新建/编辑公文对话框 -->
    <div v-if="showDocumentDialog" class="dialog-overlay" @click="closeDocumentDialog">
      <div class="dialog large" @click.stop>
        <h2>{{ editingDocument ? '编辑公文' : '新建公文' }}</h2>
        <form @submit.prevent="saveDocument">
          <div class="form-group">
            <label>标题</label>
            <input type="text" v-model="form.title" required />
          </div>
          <div class="form-group">
            <label>类型</label>
            <select v-model="form.type">
              <option value="通知">通知</option>
              <option value="报告">报告</option>
              <option value="请示">请示</option>
              <option value="批复">批复</option>
              <option value="函">函</option>
            </select>
          </div>
          <div class="form-group">
            <label>内容</label>
            <textarea v-model="form.content" rows="10" required></textarea>
          </div>
          <div class="dialog-actions">
            <button type="button" class="cancel-btn" @click="closeDocumentDialog">取消</button>
            <button type="button" class="draft-btn" @click="saveAsDraft">保存草稿</button>
            <button type="submit" class="submit-btn">提交</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 查看公文对话框 -->
    <div v-if="showViewDialog" class="dialog-overlay" @click="closeViewDialog">
      <div class="dialog large" @click.stop>
        <h2>{{ viewDocumentData.title }}</h2>
        <div class="document-meta">
          <p>类型：{{ viewDocumentData.type }}</p>
          <p>状态：{{ viewDocumentData.status }}</p>
          <p>创建人：{{ viewDocumentData.creatorName }}</p>
          <p>创建时间：{{ viewDocumentData.createTime }}</p>
        </div>
        <div class="document-content">
          {{ viewDocumentData.content }}
        </div>
        <div class="dialog-actions">
          <button class="close-btn" @click="closeViewDialog">关闭</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/enterprise')
}

// 公文列表
const documents = ref([
  { id: 1, title: '关于2026年工作计划的通知', type: '通知', status: 'APPROVED', creatorId: 1, creatorName: '管理员', createTime: '2026-02-09 10:00' },
  { id: 2, title: '员工福利政策调整方案', type: '报告', status: 'PENDING', creatorId: 2, creatorName: '技术经理', createTime: '2026-02-08 15:30' },
  { id: 3, title: '技术部门人员调整通知', type: '通知', status: 'DRAFT', creatorId: 2, creatorName: '技术经理', createTime: '2026-02-07 09:15' }
])

// 搜索和筛选
const searchKeyword = ref('')
const statusFilter = ref('')

// 对话框状态
const showDocumentDialog = ref(false)
const showViewDialog = ref(false)
const editingDocument = ref(false)
const form = ref({
  id: 0,
  title: '',
  content: '',
  type: '通知',
  status: 'DRAFT',
  creatorId: 1
})

const viewDocumentData = ref({
  id: 0,
  title: '',
  content: '',
  type: '',
  status: '',
  creatorName: '',
  createTime: ''
})

// 过滤后的公文列表
const filteredDocuments = computed(() => {
  return documents.value.filter(doc => {
    const matchesKeyword = !searchKeyword.value || doc.title.includes(searchKeyword.value)
    const matchesStatus = !statusFilter.value || doc.status === statusFilter.value
    return matchesKeyword && matchesStatus
  })
})

// 打开新建公文对话框
const openAddDocumentDialog = () => {
  editingDocument.value = false
  form.value = {
    id: 0,
    title: '',
    content: '',
    type: '通知',
    status: 'DRAFT',
    creatorId: 1
  }
  showDocumentDialog.value = true
}

// 打开编辑公文对话框
const openEditDocumentDialog = (doc: any) => {
  editingDocument.value = true
  form.value = { ...doc }
  showDocumentDialog.value = true
}

// 关闭公文对话框
const closeDocumentDialog = () => {
  showDocumentDialog.value = false
}

// 保存公文
const saveDocument = () => {
  form.value.status = 'SUBMITTED'
  if (editingDocument.value) {
    // 编辑现有公文
    const index = documents.value.findIndex(d => d.id === form.value.id)
    if (index !== -1) {
      documents.value[index] = { ...form.value, creatorName: '管理员' }
    }
  } else {
    // 添加新公文
    const newDoc = {
      ...form.value,
      id: documents.value.length + 1,
      creatorName: '管理员',
      createTime: new Date().toLocaleString()
    }
    documents.value.push(newDoc)
  }
  closeDocumentDialog()
}

// 保存为草稿
const saveAsDraft = () => {
  form.value.status = 'DRAFT'
  if (editingDocument.value) {
    // 编辑现有公文
    const index = documents.value.findIndex(d => d.id === form.value.id)
    if (index !== -1) {
      documents.value[index] = { ...form.value, creatorName: '管理员' }
    }
  } else {
    // 添加新公文
    const newDoc = {
      ...form.value,
      id: documents.value.length + 1,
      creatorName: '管理员',
      createTime: new Date().toLocaleString()
    }
    documents.value.push(newDoc)
  }
  closeDocumentDialog()
}

// 删除公文
const deleteDocument = (id: number) => {
  if (confirm('确定要删除这个公文吗？')) {
    documents.value = documents.value.filter(d => d.id !== id)
  }
}

// 提交公文
const submitDocument = (id: number) => {
  const doc = documents.value.find(d => d.id === id)
  if (doc) {
    doc.status = 'SUBMITTED'
  }
}

// 查看公文
const viewDocument = (id: number) => {
  const doc = documents.value.find(d => d.id === id)
  if (doc) {
    viewDocumentData.value = { ...doc }
    showViewDialog.value = true
  }
}

// 关闭查看对话框
const closeViewDialog = () => {
  showViewDialog.value = false
}

// 过滤公文
const filterDocuments = () => {
  // 过滤逻辑已在computed中实现
  console.log('过滤公文')
}

onMounted(() => {
  // 加载公文数据
  console.log('加载公文数据')
})
</script>

<style scoped>
.oa-documents {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.page-header h1 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

.add-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.add-btn:hover {
  background: #764ba2;
}

.document-filter {
  margin-bottom: 20px;
  background: white;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.filter-row {
  display: flex;
  gap: 10px;
  align-items: center;
}

.filter-row input,
.filter-row select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.filter-row input {
  flex: 1;
}

.search-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

.document-list {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.document-list table {
  width: 100%;
  border-collapse: collapse;
}

.document-list th, .document-list td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.document-list th {
  background: #f8f9fa;
  font-weight: bold;
  color: #333;
}

.document-list tr:hover {
  background: #f8f9fa;
}

.status {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 10px;
  font-weight: bold;
}

.status.DRAFT {
  background: #e2e3e5;
  color: #383d41;
}

.status.SUBMITTED {
  background: #fff3cd;
  color: #856404;
}

.status.APPROVED {
  background: #d4edda;
  color: #155724;
}

.status.REJECTED {
  background: #f8d7da;
  color: #721c24;
}

.view-btn, .edit-btn, .delete-btn, .submit-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
  margin-right: 5px;
}

.view-btn {
  background: #17a2b8;
  color: white;
}

.edit-btn {
  background: #667eea;
  color: white;
}

.delete-btn {
  background: #dc3545;
  color: white;
}

.submit-btn {
  background: #28a745;
  color: white;
}

/* 对话框样式 */
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.dialog {
  background: white;
  border-radius: 8px;
  padding: 30px;
  width: 90%;
  max-width: 600px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.dialog.large {
  max-width: 800px;
  max-height: 80vh;
  overflow-y: auto;
}

.dialog h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
  font-weight: bold;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group textarea {
  resize: vertical;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.cancel-btn, .draft-btn, .submit-btn, .close-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.cancel-btn {
  background: #6c757d;
  color: white;
}

.draft-btn {
  background: #ffc107;
  color: #212529;
}

.submit-btn {
  background: #28a745;
  color: white;
}

.close-btn {
  background: #667eea;
  color: white;
}

.document-meta {
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 4px;
}

.document-meta p {
  margin: 5px 0;
  color: #666;
}

.document-content {
  line-height: 1.6;
  color: #333;
  white-space: pre-line;
}

@media (max-width: 768px) {
  .filter-row {
    flex-direction: column;
    align-items: stretch;
  }

  .document-list table {
    font-size: 0.8rem;
  }

  .document-list th, .document-list td {
    padding: 8px;
  }

  .view-btn, .edit-btn, .delete-btn, .submit-btn {
    padding: 3px 6px;
    font-size: 0.7rem;
  }
}
</style>