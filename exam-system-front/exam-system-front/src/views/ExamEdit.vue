<template>
    <div class="exam-edit-container">
        <el-page-header content="编辑考试"></el-page-header>

        <el-form :model="examForm" :rules="examRules" ref="examFormRef" label-width="100px" style="margin-top: 20px">
            <!-- 考试基本信息 -->
            <el-form-item label="考试标题" prop="title">
                <el-input v-model="examForm.title" placeholder="请输入考试标题"></el-input>
            </el-form-item>
            <el-form-item label="考试描述" prop="description">
                <el-input v-model="examForm.description" type="textarea" :rows="5" placeholder="请输入考试描述"></el-input>
            </el-form-item>

            <!-- 试题列表 -->
            <el-form-item label="试题内容">
                <div class="question-item" v-for="(question, index) in examForm.questions" :key="index">
                    <div class="question-header">
                        <span>第 {{ index + 1 }} 题</span>
                        <el-button type="text" @click="handleDeleteQuestion(index)" style="color: #f56c6c">
                            删除
                        </el-button>
                    </div>
                    <el-input v-model="question.questionContent" placeholder="请输入题干内容"
                        style="margin: 10px 0"></el-input>
                    <el-row :gutter="20" style="margin-bottom: 10px">
                        <el-col :span="6">
                            <el-input v-model="question.optionA" placeholder="选项A"></el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-input v-model="question.optionB" placeholder="选项B"></el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-input v-model="question.optionC" placeholder="选项C"></el-input>
                        </el-col>
                        <el-col :span="6">
                            <el-input v-model="question.optionD" placeholder="选项D"></el-input>
                        </el-col>
                    </el-row>
                    <el-input v-model="question.correctAnswer" placeholder="请输入正确答案（A/B/C/D）"></el-input>
                    <el-divider v-if="index < examForm.questions.length - 1"></el-divider>
                </div>

                <el-button type="primary" plain @click="handleAddQuestion" style="width: 100%; margin-top: 10px">
                    <el-icon>
                        <Plus />
                    </el-icon> 添加试题
                </el-button>
            </el-form-item>

            <!-- 操作按钮 -->
            <el-form-item>
                <el-button type="primary" @click="handleSaveDraft">暂存草稿</el-button>
                <el-button type="success" @click="handleSubmitExam">提交考试</el-button>
                <el-button @click="handleBack">返回</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElPageHeader, ElForm, ElFormItem, ElInput, ElButton, ElRow, ElCol, ElDivider, ElMessage, ElIcon } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { useRouter, useRoute } from 'vue-router'
import request from '../utils/request'

// 路由实例
const router = useRouter()
const route = useRoute()

// 表单引用
const examFormRef = ref<any>(null)

// 考试ID
const examId = route.params.id as string

// 用户名
const username = localStorage.getItem('username') || ''

// 考试表单
const examForm = reactive({
    id: undefined as number | undefined,
    title: '',
    description: '',
    createUser: username,
    questions: [
        {
            questionContent: '',
            optionA: '',
            optionB: '',
            optionC: '',
            optionD: '',
            correctAnswer: ''
        }
    ]
})

// 表单校验规则
const examRules = reactive({
    title: [{ required: true, message: '请输入考试标题', trigger: 'blur' }],
    description: [{ required: true, message: '请输入考试描述', trigger: 'blur' }]
})

// 加载考试详情（编辑时）
const loadExamDetail = async () => {
    if (!examId) return
    try {
        const res = await request.get(`/exams/${examId}`) as any
        examForm.id = res.exam.id
        examForm.title = res.exam.title
        examForm.description = res.exam.description
        examForm.questions = res.questions.length > 0 ? res.questions : examForm.questions
    } catch (error) {
        ElMessage.error('考试详情加载失败')
    }
}

// 添加试题
const handleAddQuestion = () => {
    examForm.questions.push({
        questionContent: '',
        optionA: '',
        optionB: '',
        optionC: '',
        optionD: '',
        correctAnswer: ''
    })
}

// 删除试题
const handleDeleteQuestion = (index: number) => {
    if (examForm.questions.length <= 1) {
        ElMessage.warning('至少保留1道试题')
        return
    }
    examForm.questions.splice(index, 1)
}

// 暂存草稿
const handleSaveDraft = async () => {
    if (!examFormRef.value) return
    await examFormRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                const requestData = {
                    exam: {
                        id: examForm.id,
                        title: examForm.title,
                        description: examForm.description,
                        createUser: examForm.createUser
                    },
                    questions: examForm.questions
                }
                await request.post('/exams', requestData)
                ElMessage.success('暂存成功')
                router.push('/exams')
            } catch (error) {
                ElMessage.error('暂存失败')
            }
        }
    })
}

// 提交考试
const handleSubmitExam = async () => {
    if (!examFormRef.value) return
    await examFormRef.value.validate(async (valid: boolean) => {
        if (valid) {
            try {
                // 先暂存，再提交
                const requestData = {
                    exam: {
                        id: examForm.id,
                        title: examForm.title,
                        description: examForm.description,
                        createUser: examForm.createUser
                    },
                    questions: examForm.questions
                }
                const res = await request.post('/exams', requestData) as any

                if (res.id) {
                    await request.put(`/exams/${res.id}/submit`)
                    ElMessage.success('提交成功')
                    router.push('/exams')
                }
            } catch (error) {
                ElMessage.error('提交失败')
            }
        }
    })
}

// 返回列表
const handleBack = () => {
    router.push('/exams')
}

// 页面挂载时加载数据
onMounted(() => {
    loadExamDetail()
})
</script>

<style scoped>
.exam-edit-container {
    padding: 20px;
}

.question-item {
    padding: 20px;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    margin-bottom: 10px;
}

.question-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: bold;
}
</style>