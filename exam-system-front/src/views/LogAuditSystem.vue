<template>
  <div class="login-container">
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
        <ul class="features">
          <li>风险防范安全感知</li>
          <li>安全专家分析</li>
          <li>可视化视角</li>
        </ul>
      </div>
      
      <div class="right-section">
        <div class="login-form">
          <div class="form-tabs">
            <div class="tab active">用户登录</div>
            <div class="tab">证书登录</div>
          </div>
          
          <form>
            <div class="form-group">
              <input type="text" placeholder="请输入用户名" class="form-input">
            </div>
            <div class="form-group">
              <input type="password" placeholder="请输入密码" class="form-input">
            </div>
            <div class="form-group">
              <div class="verification-code">
                <input type="text" placeholder="请输入图形验证码" class="form-input code-input">
                <div class="code-image" @click="generateVerificationCode">{{ verificationCode }}</div>
              </div>
            </div>
            <div class="form-group">
              <div class="verification-code">
                <input type="text" placeholder="请输入短信验证码" class="form-input code-input">
                <button type="button" class="send-code-button">获取验证码</button>
              </div>
            </div>
            <div class="form-group">
              <button type="submit" class="login-button">登录</button>
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

// 页面加载时生成验证码
onMounted(() => {
  generateVerificationCode()
})

// 登录逻辑可以在这里实现
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #e63946 0%, #c1121f 100%);
  display: flex;
  flex-direction: column;
  font-family: Arial, sans-serif;
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
}

.left-section h1 {
  font-size: 2.5rem;
  margin-bottom: 40px;
  line-height: 1.2;
}

.features {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  font-size: 1.1rem;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.features li::before {
  content: "•";
  margin-right: 10px;
  font-size: 1.5rem;
  color: #f1faee;
}

/* 右侧区域 */
.right-section {
  flex: 0 0 400px;
  z-index: 1;
}

.login-form {
  background: white;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.form-tabs {
  display: flex;
  margin-bottom: 30px;
  border-bottom: 1px solid #e0e0e0;
}

.tab {
  flex: 1;
  text-align: center;
  padding: 10px 0;
  cursor: pointer;
  color: #666;
  transition: all 0.3s ease;
}

.tab.active {
  color: #e63946;
  border-bottom: 2px solid #e63946;
  font-weight: bold;
}

/* 表单样式 */
.form-group {
  margin-bottom: 20px;
}

.form-input {
  width: 100%;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

.form-input:focus {
  outline: none;
  border-color: #e63946;
  box-shadow: 0 0 0 2px rgba(230, 57, 70, 0.1);
}

.login-button {
  width: 100%;
  padding: 15px;
  background: #e63946;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s ease;
}

.login-button:hover {
  background: #c1121f;
}

/* 验证码样式 */
.verification-code {
  display: flex;
  gap: 10px;
}

.code-input {
  flex: 1;
}

.code-image {
  width: 100px;
  height: 48px;
  background: #f0f0f0;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
}

.code-image:hover {
  background: #e0e0e0;
  transform: scale(1.05);
}

/* 发送验证码按钮样式 */
.send-code-button {
  width: 120px;
  height: 48px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
}

.send-code-button:hover {
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
  }
  
  .left-section h1 {
    font-size: 2rem;
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