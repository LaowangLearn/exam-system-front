<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2 class="login-title">考试系统登录</h2>
      <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" label-width="80px">
        <el-form-item label="账号" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-btn" @click="handleLogin">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElForm, ElFormItem, ElInput, ElButton, ElCard, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import request from '../utils/request'

// 路由实例
const router = useRouter()

// 表单引用
const loginFormRef = ref<any>(null)

// 登录表单
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单校验规则
const loginRules = reactive({
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

// 处理登录
const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        const res = await request.post('/auth/login', loginForm) as any
        if (res.code === 200) {
          // 保存用户名（简化版认证）
          localStorage.setItem('username', loginForm.username)
          ElMessage.success('登录成功')
          router.push('/modules')
        }
      } catch (error) {
        ElMessage.error('账号或密码错误')
      }
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  color: #1989fa;
}

.login-btn {
  width: 100%;
}
</style>