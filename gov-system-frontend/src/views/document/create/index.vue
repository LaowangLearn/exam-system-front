<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <span>起草公文</span>
      </template>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公文类型" prop="docType">
              <el-select v-model="formData.docType" placeholder="请选择">
                <el-option label="通知" value="NOTICE" />
                <el-option label="命令" value="COMMAND" />
                <el-option label="决定" value="DECISION" />
                <el-option label="公告" value="PROCLAMATION" />
                <el-option label="报告" value="REPORT" />
                <el-option label="议案" value="PROPOSAL" />
                <el-option label="函" value="LETTER" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急程度" prop="urgencyLevel">
              <el-select v-model="formData.urgencyLevel" placeholder="请选择">
                <el-option label="低" value="LOW" />
                <el-option label="一般" value="NORMAL" />
                <el-option label="紧急" value="URGENT" />
                <el-option label="特急" value="VERY_URGENT" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="密级" prop="secretLevel">
          <el-radio-group v-model="formData.secretLevel">
            <el-radio :label="0">普通</el-radio>
            <el-radio :label="1">内部</el-radio>
            <el-radio :label="2">秘密</el-radio>
            <el-radio :label="3">机密</el-radio>
            <el-radio :label="4">绝密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="公文标题" prop="docTitle">
          <el-input v-model="formData.docTitle" placeholder="请输入公文标题" />
        </el-form-item>
        <el-form-item label="主题词">
          <el-input v-model="formData.docSubject" placeholder="请输入主题词，多个用逗号分隔" />
        </el-form-item>
        <el-form-item label="主送单位">
          <el-input v-model="formData.main送UnitNames" placeholder="请输入主送单位名称" />
        </el-form-item>
        <el-form-item label="抄送单位">
          <el-input v-model="formData.ccUnitNames" placeholder="请输入抄送单位名称" />
        </el-form-item>
        <el-form-item label="正文内容" prop="draftContent">
          <el-input v-model="formData.draftContent" type="textarea" :rows="12" placeholder="请输入公文正文内容" />
        </el-form-item>
        <el-form-item label="附件">
          <el-upload action="#" :auto-upload="false" :limit="5">
            <el-button>选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div class="form-footer">
        <el-button @click="handleCancel">取消</el-button>
        <el-button @click="handleSave">保存草稿</el-button>
        <el-button type="primary" @click="handleSubmit">提交审批</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'

const router = useRouter()
const formRef = ref()
const formData = reactive({
  docType: 'NOTICE', urgencyLevel: 'NORMAL', secretLevel: 0, docTitle: '', docSubject: '',
  main送UnitNames: '', ccUnitNames: '', draftContent: ''
})

const formRules = {
  docType: [{ required: true, message: '请选择公文类型', trigger: 'change' }],
  docTitle: [{ required: true, message: '请输入公文标题', trigger: 'blur' }],
  draftContent: [{ required: true, message: '请输入正文内容', trigger: 'blur' }]
}

const handleCancel = () => router.back()

const handleSave = async () => {
  try {
    await axios.post('/api/document', formData)
    ElMessage.success('保存成功')
    router.push('/document/list')
  } catch { ElMessage.error('保存失败') }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      const res = await axios.post('/api/document', formData)
      const docId = res.data?.data?.docId || 1
      await axios.put(`/api/document/submit/${docId}`)
      ElMessage.success('提交成功')
      router.push('/document/list')
    } catch { ElMessage.error('提交失败') }
  })
}
</script>

<style scoped>
.page-container { width: 100%; }
.form-footer { margin-top: 20px; text-align: right; }
</style>
