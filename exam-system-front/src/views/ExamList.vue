<template>
    <div class="exam-list-container">
        <div class="exam-list-header">
            <div class="header-left">
                <button class="back-button" @click="goBack">返回中心</button>
                <el-page-header content="考试列表"></el-page-header>
            </div>
            <el-button type="primary" @click="handleCreateExam">新建考试</el-button>
        </div>

        <el-table :data="examList" border style="width: 100%; margin-top: 20px">
            <el-table-column prop="title" label="考试标题" min-width="300"></el-table-column>
            <el-table-column prop="status" label="状态">
                <template #default="scope">
                    <el-tag v-if="scope.row.status.code === 0" type="warning">草稿</el-tag>
                    <el-tag v-else type="success">已提交</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="200"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间" width="200"></el-table-column>
            <el-table-column label="操作" width="200">
                <template #default="scope">
                    <el-button type="text" @click="handleEditExam(scope.row.id)"
                        :disabled="scope.row.status.code === 1">
                        编辑
                    </el-button>
                    <el-button type="text" @click="handleSubmitExam(scope.row.id)"
                        :disabled="scope.row.status.code === 1" style="color: #1989fa">
                        提交
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElPageHeader, ElButton, ElTable, ElTableColumn, ElTag, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../utils/request'

// 路由实例
const router = useRouter()

// 返回中心
const goBack = () => {
  router.push('/')
}

// 用户名
const username = localStorage.getItem('username') || ''

// 考试列表
const examList = ref<any[]>([])

// 加载考试列表
const loadExamList = async () => {
    try {
        console.log('加载考试列表，用户名:', username)
        const res = await request.get(`/exams/list/${username}`) as any
        examList.value = res || []
        console.log('考试列表数据:', examList.value)
    } catch (error) {
        console.error('加载失败:', error)
        ElMessage.error('考试列表加载失败')
        examList.value = []
    }
}

// 新建考试
const handleCreateExam = () => {
    router.push('/exams/edit')
}

// 编辑考试
const handleEditExam = (id: number) => {
    router.push(`/exams/edit/${id}`)
}

// 提交考试
const handleSubmitExam = async (id: number) => {
    try {
        // 本地模拟提交
        console.log('提交考试，ID:', id)
        // 更新本地模拟数据
        const examIndex = examList.value.findIndex(exam => exam.id === id)
        if (examIndex !== -1) {
            examList.value[examIndex].status.code = 1
            examList.value[examIndex].updateTime = new Date().toISOString().replace('T', ' ').substring(0, 19)
            ElMessage.success('提交成功')
        }
    } catch (error) {
        console.error('提交失败:', error)
        ElMessage.error('提交失败，请稍后重试')
    }
}

// 页面挂载时加载数据
onMounted(() => {
    loadExamList()
})
</script>

<style scoped>
.exam-list-container {
    padding: 20px;
}

.exam-list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header-left {
    display: flex;
    align-items: center;
    gap: 16px;
}

.back-button {
    background: #3498db;
    color: white;
    border: none;
    padding: 6px 12px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 13px;
    transition: all 0.3s ease;
}

.back-button:hover {
    background: #2980b9;
    transform: translateY(-1px);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>