<template>
  <div class="login-container">
    <div class="grid-bg"></div>
    <div class="floating-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
    </div>

    <div class="login-wrapper">
      <div class="login-card glass card-shine">
        <div class="card-glow"></div>

        <div class="login-header">
          <div class="logo-icon pulse-glow">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
              <polyline points="2 17 12 22 22 17"></polyline>
              <polyline points="2 12 12 17 22 12"></polyline>
            </svg>
          </div>
          <h2 class="login-title">考试系统登录</h2>
          <p class="login-subtitle">欢迎回来，请登录您的账号</p>
        </div>

        <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" class="login-form">
          <el-form-item prop="username">
            <div class="input-wrapper">
              <div class="input-icon">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
              </div>
              <el-input
                v-model="loginForm.username"
                placeholder="请输入账号"
                class="custom-input"
              />
            </div>
          </el-form-item>

          <el-form-item prop="password">
            <div class="input-wrapper">
              <div class="input-icon">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                  <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                </svg>
              </div>
              <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                class="custom-input"
                @keyup.enter="handleLogin"
              />
            </div>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" class="login-btn" @click="handleLogin">
              <span>登 录</span>
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="5" y1="12" x2="19" y2="12"></line>
                <polyline points="12 5 19 12 12 19"></polyline>
              </svg>
            </el-button>
          </el-form-item>
        </el-form>

        <div class="login-footer">
          <div class="footer-line"></div>
          <p class="footer-text">考试管理系统 v1.0</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const loginFormRef = ref<any>(null)

const loginForm = reactive({
  username: '',
  password: ''
})

const loginRules = reactive({
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})

const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        localStorage.setItem('username', loginForm.username)
        ElMessage.success('登录成功')
        router.push('/modules')
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
  min-height: 100vh;
  background: linear-gradient(135deg, #0f0f1a 0%, #1a1a2e 50%, #16213e 100%);
  position: relative;
  overflow: hidden;
}

.login-wrapper {
  position: relative;
  z-index: 10;
  width: 100%;
  max-width: 420px;
  padding: 20px;
}

.login-card {
  position: relative;
  padding: 48px 40px;
  border-radius: 24px;
  overflow: hidden;
}

.card-glow {
  position: absolute;
  top: -100px;
  left: -100px;
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(0, 240, 255, 0.15) 0%, transparent 70%);
  pointer-events: none;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.logo-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 72px;
  height: 72px;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 20px;
  color: #00f0ff;
  margin-bottom: 24px;
}

.login-title {
  font-size: 1.8rem;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 8px;
  letter-spacing: 2px;
}

.login-subtitle {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.5);
}

.login-form {
  margin-top: 20px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 16px;
  color: rgba(0, 240, 255, 0.6);
  z-index: 10;
  pointer-events: none;
}

.custom-input {
  width: 100%;
}

.custom-input :deep(.el-input__wrapper) {
  padding-left: 48px !important;
  height: 52px;
  background: rgba(255, 255, 255, 0.05) !important;
  border: 1px solid rgba(255, 255, 255, 0.1) !important;
  border-radius: 12px;
  box-shadow: none !important;
  transition: all 0.3s ease;
}

.custom-input :deep(.el-input__wrapper:hover) {
  border-color: rgba(0, 240, 255, 0.3) !important;
}

.custom-input :deep(.el-input__wrapper.is-focus) {
  border-color: #00f0ff !important;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2) !important;
  background: rgba(0, 240, 255, 0.05) !important;
}

.custom-input :deep(.el-input__inner) {
  color: #ffffff !important;
  font-size: 15px;
}

.custom-input :deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.35) !important;
}

.login-btn {
  width: 100%;
  height: 52px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2) 0%, rgba(102, 126, 234, 0.2) 100%) !important;
  border: 1px solid rgba(0, 240, 255, 0.4) !important;
  border-radius: 12px;
  color: #00f0ff !important;
  font-size: 16px;
  font-weight: 500;
  letter-spacing: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  transition: all 0.3s ease;
  margin-top: 10px;
}

.login-btn:hover {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.3) 0%, rgba(102, 126, 234, 0.3) 100%) !important;
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(0, 240, 255, 0.3);
}

.login-btn:active {
  transform: translateY(0);
}

.login-btn svg {
  transition: transform 0.3s ease;
}

.login-btn:hover svg {
  transform: translateX(4px);
}

.login-footer {
  margin-top: 40px;
  text-align: center;
}

.footer-line {
  width: 60px;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(0, 240, 255, 0.3), transparent);
  margin: 0 auto 16px;
}

.footer-text {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.3);
}

.floating-shapes {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  pointer-events: none;
}

.shape {
  position: absolute;
  border-radius: 50%;
  opacity: 0.1;
}

.shape-1 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #00f0ff, #667eea);
  top: -100px;
  right: -100px;
  animation: float 8s ease-in-out infinite;
}

.shape-2 {
  width: 200px;
  height: 200px;
  background: linear-gradient(135deg, #764ba2, #667eea);
  bottom: -50px;
  left: -50px;
  animation: float 10s ease-in-out infinite reverse;
}

.shape-3 {
  width: 150px;
  height: 150px;
  background: linear-gradient(135deg, #00f0ff, #764ba2);
  top: 50%;
  left: 50%;
  animation: float 12s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) rotate(0deg); }
  25% { transform: translate(20px, -20px) rotate(5deg); }
  50% { transform: translate(0, -40px) rotate(0deg); }
  75% { transform: translate(-20px, -20px) rotate(-5deg); }
}
</style>