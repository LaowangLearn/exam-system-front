<template>
  <div class="gov-document-create">
    <div class="page-header">
      <h1>新建公文</h1>
      <div class="header-actions">
        <el-button @click="handleSave">保存草稿</el-button>
        <el-button type="primary" @click="handleSubmit">提交审批</el-button>
      </div>
    </div>
    
    <el-card>
      <el-form :model="formData" label-width="100px">
        <el-form-item label="公文类型" prop="docType">
          <el-select v-model="formData.docType">
            <el-option label="通知" :value="1" />
            <el-option label="报告" :value="2" />
            <el-option label="请示" :value="3" />
            <el-option label="批复" :value="4" />
            <el-option label="函" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="密级" prop="docLevel">
          <el-select v-model="formData.docLevel">
            <el-option label="普通" :value="4" />
            <el-option label="秘密" :value="3" />
            <el-option label="机密" :value="2" />
            <el-option label="绝密" :value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="公文标题" prop="docTitle">
          <el-input v-model="formData.docTitle" placeholder="请输入公文标题" />
        </el-form-item>
        <el-form-item label="主送机关" prop="sendTo">
          <el-input v-model="formData.sendTo" placeholder="请输入主送机关" />
        </el-form-item>
        <el-form-item label="抄送机关" prop="copyTo">
          <el-input v-model="formData.copyTo" placeholder="请输入抄送机关" />
        </el-form-item>
        <el-form-item label="公文正文" prop="docContent">
          <el-textarea v-model="formData.docContent" :rows="15" placeholder="请输入公文正文" />
        </el-form-item>
        <el-form-item label="附件" prop="attachments">
          <div class="upload-area">
            <el-upload
              class="upload-demo"
              action="/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="formData.attachments"
              list-type="text"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const formData = reactive({
  docType: 1,
  docLevel: 4,
  docTitle: '',
  sendTo: '',
  copyTo: '',
  docContent: '',
  attachments: []
})

const handleSave = () => {
  alert('保存草稿成功')
}

const handleSubmit = () => {
  alert('提交审批成功')
}

const handlePreview = () => {
  console.log('预览附件')
}

const handleRemove = () => {
  console.log('移除附件')
}
</script>

<style scoped>
.gov-document-create {
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

.header-actions {
  display: flex;
  gap: 12px;
}

.upload-area {
  padding: 16px;
  border: 1px dashed #d1d5db;
  border-radius: 8px;
  background: #f8fafc;
}
</style>
