<template>
  <div class="login-container new">
    <!-- 页面头部 -->
    <div class="header">
      <div class="header-content">
        <button class="back-button" @click="goBack">返回其他平台</button>
        <div class="logo">
          <div class="logo-icon">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
              <polyline points="2 17 12 22 22 17"></polyline>
              <polyline points="2 12 12 17 22 12"></polyline>
            </svg>
          </div>
          <div class="logo-text">
            <h2>安信天行</h2>
            <p>让网络安全变得简单</p>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 主内容区 -->
    <div class="main-content">
      <div class="left-section">
        <h1>安信天行日志分析管理系统</h1>
        <p class="subtitle">新一代智能安全分析平台</p>
        <ul class="features">
          <li>
            <svg class="feature-icon" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
              <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
            智能风险识别
          </li>
          <li>
            <svg class="feature-icon" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            </svg>
            实时安全监控
          </li>
          <li>
            <svg class="feature-icon" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
              <line x1="8" y1="21" x2="16" y2="21"></line>
              <line x1="12" y1="17" x2="12" y2="21"></line>
            </svg>
            可视化数据分析
          </li>
        </ul>
      </div>
      
      <div class="right-section">
        <div class="login-form">
          <div class="form-header">
            <h3>用户登录</h3>
            <p>请输入您的账号信息</p>
          </div>
          
          <form @submit.prevent="handleLogin">
            <div class="form-group">
              <label class="form-label">用户名</label>
              <input type="text" v-model="loginForm.username" placeholder="请输入用户名" class="form-input">
            </div>
            <div class="form-group">
              <label class="form-label">密码</label>
              <input type="password" v-model="loginForm.password" placeholder="请输入密码" class="form-input">
            </div>
            <div class="form-group">
              <div class="verification-code">
                <div class="code-input-wrapper">
                  <label class="form-label">图形验证码</label>
                  <input type="text" v-model="loginForm.verificationCode" placeholder="请输入图形验证码" class="form-input code-input">
                </div>
                <div class="code-image" @click="generateVerificationCode">{{ verificationCode }}</div>
              </div>
            </div>
            <div class="form-group">
              <div class="verification-code">
                <div class="code-input-wrapper">
                  <label class="form-label">短信验证码</label>
                  <input type="text" v-model="loginForm.smsCode" placeholder="请输入短信验证码" class="form-input code-input">
                </div>
                <button type="button" class="send-code-button" :disabled="countdown > 0" @click="sendSmsCode">{{ countdown > 0 ? `${countdown}秒后重发` : '获取验证码' }}</button>
              </div>
            </div>
            <div class="form-group form-actions">
              <div class="remember-me">
                <input type="checkbox" id="remember" v-model="loginForm.remember">
                <label for="remember">记住我</label>
              </div>
              <a href="#" class="forgot-password">忘记密码？</a>
            </div>
            <div class="form-group">
              <button type="submit" class="login-button">登录系统</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    
    <!-- 页面底部 -->
    <div class="footer">
      <p>版权所有 © 2024 北京安信天行科技有限公司</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const verificationCode = ref('')
const countdown = ref(0)

const loginForm = ref({
  username: '',
  password: '',
  verificationCode: '',
  smsCode: '',
  remember: false
})

// 返回其他平台页面
const goBack = () => {
  router.push('/other-platforms')
}

// 生成随机验证码（包含数字和字母）
const generateVerificationCode = () => {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  let code = ''
  for (let i = 0; i < 4; i++) {
    code += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  verificationCode.value = code
}

// 发送短信验证码
const sendSmsCode = () => {
  // 这里可以添加发送短信验证码的逻辑
  countdown.value = 60
  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
    }
  }, 1000)
}

// 登录处理
const handleLogin = () => {
  // 这里可以添加登录逻辑
  console.log('登录表单数据:', loginForm.value)
  // 登录成功后跳转到相应的功能页面
  router.push('/modules')
}

// 页面加载时生成验证码
onMounted(() => {
  generateVerificationCode()
})
</script>

<style scoped>
.login-container.new {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

/* 页面头部 */
.header {
  padding: 20px 40px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.back-button {
  background: rgba(255, 255, 255, 0.2);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.3);
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.back-button:hover {
  background: rgba(255, 255, 255, 0.3);
  transform: translateY(-2px);
}

.logo {
  display: flex;
  align-items: center;
  color: white;
}

.logo-icon {
  margin-right: 15px;
  font-size: 40px;
}

.logo-text h2 {
  margin: 0;
  font-size: 1.5rem;
  font-weight: bold;
}

.logo-text p {
  margin: 5px 0 0;
  font-size: 0.9rem;
  opacity: 0.8;
}

/* 主内容区 */
.main-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 100px;
  position: relative;
  overflow: hidden;
}

/* 左侧区域 */
.left-section {
  flex: 1;
  color: white;
  z-index: 1;
  padding-right: 50px;
}

.left-section h1 {
  font-size: 2.8rem;
  margin-bottom: 20px;
  line-height: 1.2;
  font-weight: 700;
}

.subtitle {
  font-size: 1.2rem;
  margin-bottom: 40px;
  opacity: 0.9;
}

.features {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  font-size: 1.1rem;
  margin-bottom: 25px;
  display: flex;
  align-items: flex-start;
  gap: 15px;
}

.feature-icon {
  margin-top: 2px;
  flex-shrink: 0;
}

/* 右侧区域 */
.right-section {
  flex: 0 0 420px;
  z-index: 1;
}

.login-form {
  background: white;
  border-radius: 16px;
  padding: 45px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
}

.login-form:hover {
  transform: translateY(-5px);
}

.form-header {
  margin-bottom: 35px;
  text-align: center;
}

.form-header h3 {
  margin: 0 0 10px 0;
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
}

.form-header p {
  margin: 0;
  color: #666;
  font-size: 0.95rem;
}

/* 表单样式 */
.form-group {
  margin-bottom: 25px;
}

.form-label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
  font-size: 0.95rem;
}

.form-input {
  width: 100%;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-size: 1rem;
  transition: all 0.3s ease;
  background: #f9f9f9;
}

.form-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
  background: white;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
}

.remember-me label {
  font-size: 0.9rem;
  color: #666;
  cursor: pointer;
}

.forgot-password {
  font-size: 0.9rem;
  color: #667eea;
  text-decoration: none;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #764ba2;
  text-decoration: underline;
}

.login-button {
  width: 100%;
  padding: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.3);
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

/* 验证码样式 */
.verification-code {
  display: flex;
  gap: 15px;
  align-items: flex-start;
}

.code-input-wrapper {
  flex: 1;
}

.code-input {
  width: 100%;
}

.code-image {
  width: 110px;
  height: 52px;
  background: linear-gradient(135deg, #f0f0f0 0%, #e0e0e0 100%);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 28px;
}

.code-image:hover {
  background: linear-gradient(135deg, #e0e0e0 0%, #d0d0d0 100%);
  transform: scale(1.05);
}

/* 发送验证码按钮样式 */
.send-code-button {
  width: 130px;
  height: 52px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  margin-top: 28px;
}

.send-code-button:hover:not(:disabled) {
  background: #764ba2;
  transform: scale(1.05);
}

.send-code-button:disabled {
  background: #ccc;
  cursor: not-allowed;
  transform: none;
}

/* 页面底部 */
.footer {
  padding: 20px;
  text-align: center;
  color: white;
  opacity: 0.8;
  font-size: 0.9rem;
}

/* 背景装饰 */
.main-content::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0) 70%);
  animation: pulse 8s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.3;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.5;
  }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content {
    flex-direction: column;
    padding: 40px 20px;
    text-align: center;
  }
  
  .left-section {
    margin-bottom: 40px;
    padding-right: 0;
  }
  
  .left-section h1 {
    font-size: 2.2rem;
  }
  
  .features li {
    justify-content: center;
    text-align: left;
  }
  
  .right-section {
    flex: 0 0 100%;
  }
  
  .login-form {
    max-width: 400px;
    margin: 0 auto;
  }
}
</style>