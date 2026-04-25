<template>
  <div class="exam-list-container">
    <div class="grid-bg"></div>

    <div class="exam-list-header">
      <div class="header-left">
        <button class="back-button" @click="goBack">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="19" y1="12" x2="5" y2="12"></line>
            <polyline points="12 19 5 12 12 5"></polyline>
          </svg>
          返回中心
        </button>
        <h1 class="page-title">考试列表</h1>
      </div>
      <button class="create-button" @click="handleCreateExam">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <line x1="12" y1="5" x2="12" y2="19"></line>
          <line x1="5" y1="12" x2="19" y2="12"></line>
        </svg>
        新建考试
      </button>
    </div>

    <div class="table-wrapper glass">
      <el-table :data="examList" stripe>
        <el-table-column prop="title" label="考试标题" min-width="280">
          <template #default="scope">
            <div class="exam-title-cell">
              <div class="exam-icon">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
                  <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
                </svg>
              </div>
              <span>{{ scope.row.title }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120" align="center">
          <template #default="scope">
            <span class="status-badge" :class="scope.row.status.code === 0 ? 'draft' : 'published'">
              {{ scope.row.status.code === 0 ? '草稿' : '已发布' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <div class="action-buttons">
              <button
                class="action-btn edit"
                :disabled="scope.row.status.code === 1"
                @click="handleEditExam(scope.row.id)"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
              </button>
              <button
                class="action-btn submit"
                :disabled="scope.row.status.code === 1"
                @click="handleSubmitExam(scope.row.id)"
              >
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polyline points="9 11 12 14 22 4"></polyline>
                  <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"></path>
                </svg>
              </button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div v-if="examList.length === 0" class="empty-state">
        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1">
          <path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"></path>
          <path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"></path>
        </svg>
        <p>暂无考试数据</p>
        <button @click="handleCreateExam">创建第一个考试</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElTable, ElTableColumn, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../utils/request'

const router = useRouter()

const goBack = () => {
  router.push('/modules')
}

const username = localStorage.getItem('username') || ''
const examList = ref<any[]>([])

const loadExamList = async () => {
  try {
    const res = await request.get(`/exams/list/${username}`) as any
    examList.value = res || []
  } catch (error) {
    ElMessage.error('考试列表加载失败')
    examList.value = []
  }
}

const handleCreateExam = () => {
  router.push('/exams/edit')
}

const handleEditExam = (id: number) => {
  router.push(`/exams/edit/${id}`)
}

const handleSubmitExam = async (id: number) => {
  try {
    const examIndex = examList.value.findIndex(exam => exam.id === id)
    if (examIndex !== -1) {
      examList.value[examIndex].status.code = 1
      examList.value[examIndex].updateTime = new Date().toISOString().replace('T', ' ').substring(0, 19)
      ElMessage.success('提交成功')
    }
  } catch (error) {
    ElMessage.error('提交失败，请稍后重试')
  }
}

onMounted(() => {
  loadExamList()
})
</script>

<style scoped>
.exam-list-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f0f1a 0%, #1a1a2e 50%, #16213e 100%);
  padding: 40px 30px;
  position: relative;
}

.exam-list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  position: relative;
  z-index: 10;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.back-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: rgba(0, 240, 255, 0.08);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
  padding: 10px 20px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.back-button:hover {
  background: rgba(0, 240, 255, 0.15);
  border-color: rgba(0, 240, 255, 0.5);
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
}

.page-title {
  font-size: 1.8rem;
  color: #ffffff;
  font-weight: 600;
  letter-spacing: 2px;
}

.create-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2) 0%, rgba(102, 126, 234, 0.2) 100%);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.4);
  padding: 12px 24px;
  border-radius: 12px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.create-button:hover {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.3) 0%, rgba(102, 126, 234, 0.3) 100%);
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 240, 255, 0.25);
}

.table-wrapper {
  position: relative;
  z-index: 10;
  border-radius: 16px;
  padding: 24px;
  overflow: hidden;
}

.exam-title-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.exam-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: rgba(0, 240, 255, 0.1);
  border-radius: 10px;
  color: #00f0ff;
}

.status-badge {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.status-badge.draft {
  background: rgba(255, 193, 7, 0.15);
  color: #ffc107;
  border: 1px solid rgba(255, 193, 7, 0.3);
}

.status-badge.published {
  background: rgba(76, 175, 80, 0.15);
  color: #4caf50;
  border: 1px solid rgba(76, 175, 80, 0.3);
}

.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
}

.action-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border-radius: 8px;
  border: 1px solid;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn.edit {
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

.action-btn.edit:hover:not(:disabled) {
  background: rgba(0, 240, 255, 0.2);
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

.action-btn.submit {
  background: rgba(76, 175, 80, 0.1);
  border-color: rgba(76, 175, 80, 0.3);
  color: #4caf50;
}

.action-btn.submit:hover:not(:disabled) {
  background: rgba(76, 175, 80, 0.2);
  box-shadow: 0 0 15px rgba(76, 175, 80, 0.3);
}

.action-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: rgba(255, 255, 255, 0.4);
}

.empty-state svg {
  margin-bottom: 20px;
  opacity: 0.5;
}

.empty-state p {
  font-size: 1.1rem;
  margin-bottom: 20px;
}

.empty-state button {
  background: rgba(0, 240, 255, 0.1);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
  padding: 10px 24px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.empty-state button:hover {
  background: rgba(0, 240, 255, 0.2);
}

@media (max-width: 768px) {
  .exam-list-header {
    flex-direction: column;
    gap: 20px;
    align-items: flex-start;
  }

  .header-left {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
}
</style>