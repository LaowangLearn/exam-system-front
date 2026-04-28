<template>
  <div class="api-system">
    <button class="back-btn" @click="goBack">返回系统中心</button>
    <div class="api-header">
      <h1>公共 API 模块</h1>
      <p>提供系统对外接口服务</p>
    </div>
    
    <div class="api-content">
      <div class="api-section">
        <h2>API 文档</h2>
        <div class="api-docs">
          <div class="api-card">
            <h3>认证 API</h3>
            <ul>
              <li><code>POST /api/auth/login</code> - 用户登录</li>
              <li><code>POST /api/auth/logout</code> - 用户登出</li>
              <li><code>GET /api/auth/refresh</code> - 刷新令牌</li>
            </ul>
          </div>
          
          <div class="api-card">
            <h3>用户 API</h3>
            <ul>
              <li><code>GET /api/users</code> - 获取用户列表</li>
              <li><code>GET /api/users/:id</code> - 获取用户详情</li>
              <li><code>POST /api/users</code> - 创建用户</li>
              <li><code>PUT /api/users/:id</code> - 更新用户</li>
              <li><code>DELETE /api/users/:id</code> - 删除用户</li>
            </ul>
          </div>
          
          <div class="api-card">
            <h3>考试 API</h3>
            <ul>
              <li><code>GET /api/exams</code> - 获取考试列表</li>
              <li><code>GET /api/exams/:id</code> - 获取考试详情</li>
              <li><code>POST /api/exams</code> - 创建考试</li>
              <li><code>PUT /api/exams/:id</code> - 更新考试</li>
              <li><code>DELETE /api/exams/:id</code> - 删除考试</li>
            </ul>
          </div>
        </div>
      </div>
      
      <div class="api-section">
        <h2>API 测试工具</h2>
        <div class="api-test-tool">
          <div class="test-form">
            <div class="form-group">
              <label>API 端点</label>
              <input type="text" v-model="apiEndpoint" placeholder="例如: /api/users" />
            </div>
            <div class="form-group">
              <label>请求方法</label>
              <select v-model="requestMethod">
                <option value="GET">GET</option>
                <option value="POST">POST</option>
                <option value="PUT">PUT</option>
                <option value="DELETE">DELETE</option>
              </select>
            </div>
            <div class="form-group">
              <label>请求体 (JSON)</label>
              <textarea v-model="requestBody" placeholder="{'key': 'value'}"></textarea>
            </div>
            <button class="test-btn" @click="testAPI">测试 API</button>
          </div>
          
          <div class="test-result">
            <h3>测试结果</h3>
            <pre>{{ testResult }}</pre>
          </div>
        </div>
      </div>
      
      <div class="api-section">
        <h2>API 密钥管理</h2>
        <div class="api-keys">
          <div class="key-card">
            <h3>当前 API 密钥</h3>
            <div class="key-value">{{ apiKey }}</div>
            <button class="regenerate-btn" @click="regenerateAPIKey">重新生成密钥</button>
          </div>
          <div class="key-info">
            <p>API 密钥用于调用系统接口时的身份验证。请妥善保管您的 API 密钥，不要分享给他人。</p>
            <p>每次调用 API 时，需要在请求头中添加 <code>Authorization: Bearer {apiKey}</code>。</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// API 测试相关
const apiEndpoint = ref('/api/users')
const requestMethod = ref('GET')
const requestBody = ref('')
const testResult = ref('')

// API 密钥
const apiKey = ref('sk_1a2b3c4d5e6f7g8h9i0j')

// 返回系统中心
const goBack = () => {
  router.push('/')
}

// 测试 API
const testAPI = () => {
  // 模拟 API 测试结果
  testResult.value = JSON.stringify({
    status: 'success',
    message: 'API 测试成功',
    data: {
      endpoint: apiEndpoint.value,
      method: requestMethod.value,
      timestamp: new Date().toISOString()
    }
  }, null, 2)
}

// 重新生成 API 密钥
const regenerateAPIKey = () => {
  // 模拟生成新的 API 密钥
  const chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
  let newKey = 'sk_'
  for (let i = 0; i < 20; i++) {
    newKey += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  apiKey.value = newKey
}
</script>

<style scoped>
.api-system {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 20px;
  position: relative;
}

.back-btn {
  position: absolute;
  top: 20px;
  left: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  padding: 10px 25px;
  border-radius: 25px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
  z-index: 10;
}

.back-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
}

.api-header {
  text-align: center;
  margin-bottom: 40px;
  margin-top: 80px;
}

.api-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin-bottom: 10px;
}

.api-header p {
  font-size: 1.2rem;
  color: #666;
}

.api-content {
  max-width: 1200px;
  margin: 0 auto;
}

.api-section {
  background: white;
  border-radius: 10px;
  padding: 30px;
  margin-bottom: 30px;
  box-shadow: 0 2px 15px rgba(0, 0, 0, 0.1);
}

.api-section h2 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 20px;
  border-bottom: 2px solid #667eea;
  padding-bottom: 10px;
}

.api-docs {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.api-card {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  border-left: 4px solid #667eea;
}

.api-card h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.api-card ul {
  list-style: none;
  padding: 0;
}

.api-card li {
  margin-bottom: 10px;
  padding-left: 20px;
  position: relative;
}

.api-card li::before {
  content: '→';
  position: absolute;
  left: 0;
  color: #667eea;
}

.api-card code {
  background: #e9ecef;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
}

.api-test-tool {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.test-form {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group textarea {
  resize: vertical;
  min-height: 100px;
}

.test-btn {
  background: #667eea;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
}

.test-btn:hover {
  background: #764ba2;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.test-result {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

.test-result h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.test-result pre {
  background: #2d3748;
  color: #e2e8f0;
  padding: 15px;
  border-radius: 4px;
  overflow-x: auto;
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
  line-height: 1.4;
}

.api-keys {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.key-card {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  text-align: center;
}

.key-card h3 {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

.key-value {
  background: #2d3748;
  color: #e2e8f0;
  padding: 15px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 1.1rem;
  margin-bottom: 20px;
  word-break: break-all;
}

.regenerate-btn {
  background: #48bb78;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.regenerate-btn:hover {
  background: #38a169;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(72, 187, 120, 0.4);
}

.key-info {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

.key-info p {
  margin-bottom: 10px;
  line-height: 1.6;
  color: #666;
}

.key-info code {
  background: #e9ecef;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .api-test-tool,
  .api-keys {
    grid-template-columns: 1fr;
  }
  
  .api-docs {
    grid-template-columns: 1fr;
  }
  
  .api-header h1 {
    font-size: 2rem;
  }
  
  .api-section {
    padding: 20px;
  }
}
</style>