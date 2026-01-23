<template>
  <div class="module-container">
    <el-page-header content="功能模块选择"></el-page-header>
    
    <div class="module-cards">
      <el-card class="module-card" @click="handleGoToExams">
        <h3>考试管理</h3>
        <p>创建、编辑和管理考试</p>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ElPageHeader, ElCard } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()

const handleGoToExams = () => {
  router.push('/exams')
}
</script>

<style scoped>
.module-container {
  padding: 20px;
}

.module-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 20px;
}

.module-card {
  width: 300px;
  cursor: pointer;
  transition: transform 0.2s;
}

.module-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.module-card h3 {
  margin: 0 0 10px 0;
  color: #1989fa;
}

.module-card p {
  margin: 0;
  color: #666;
}
</style>