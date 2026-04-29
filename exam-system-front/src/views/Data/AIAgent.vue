<template>
  <div class="ai-agent-page">
    <div class="page-header">
      <button class="back-btn" @click="goBack">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M15 18l-6-6 6-6" />
        </svg>
        返回
      </button>
      <h1>AI智能Agent办公</h1>
    </div>
    
    <div class="page-content">
      <div class="chat-section">
        <div class="chat-container">
          <div class="chat-header">
            <div class="agent-info">
              <span class="agent-icon">🤖</span>
              <span class="agent-name">智能助手</span>
            </div>
            <div class="agent-status">
              <span class="status-dot online"></span>
              <span>在线</span>
            </div>
          </div>
          
          <div class="chat-messages">
            <div class="message bot-message">
              <div class="message-avatar">🤖</div>
              <div class="message-content">
                <p>您好！我是您的智能办公助手，请问有什么可以帮您的？</p>
              </div>
            </div>
            <div v-for="msg in messages" :key="msg.id" :class="['message', msg.type]">
              <div class="message-avatar">{{ msg.type === 'user' ? '👤' : '🤖' }}</div>
              <div class="message-content">
                <p>{{ msg.content }}</p>
                <div v-if="msg.type === 'bot' && msg.thought" class="message-thought">
                  <div class="thought-icon">💡</div>
                  <span>{{ msg.thought }}</span>
                </div>
              </div>
            </div>
            <div v-if="isLoading" class="message bot-message">
              <div class="message-avatar">🤖</div>
              <div class="message-content">
                <div class="loading-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            </div>
          </div>
          
          <div class="chat-input">
            <input 
              v-model="inputMessage" 
              type="text" 
              placeholder="输入您的问题或指令..."
              @keyup.enter="sendMessage"
            />
            <button class="send-btn" @click="sendMessage">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="22" y1="2" x2="11" y2="13"></line>
                <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
              </svg>
            </button>
          </div>
        </div>
        
        <div class="quick-actions">
          <h3>快捷指令</h3>
          <div class="action-buttons">
            <button v-for="action in quickActions" :key="action.id" class="action-btn" @click="sendAction(action)">
              <span class="action-icon">{{ action.icon }}</span>
              <span class="action-text">{{ action.text }}</span>
            </button>
          </div>
        </div>
      </div>
      
      <div class="tools-section">
        <div class="tools-header">
          <h3>Agent工具</h3>
        </div>
        <div class="tools-grid">
          <div v-for="tool in tools" :key="tool.id" :class="['tool-card', { active: tool.active }]" @click="toggleTool(tool)">
            <div class="tool-icon">{{ tool.icon }}</div>
            <div class="tool-info">
              <div class="tool-name">{{ tool.name }}</div>
              <div class="tool-desc">{{ tool.desc }}</div>
            </div>
            <div class="tool-status">
              <span :class="['status-badge', tool.active ? 'active' : 'inactive']">
                {{ tool.active ? '已启用' : '已禁用' }}
              </span>
            </div>
          </div>
        </div>
      </div>
      
      <div class="tasks-section">
        <div class="section-header">
          <h3>Agent任务队列</h3>
          <span class="task-count">{{ agentTasks.length }} 个任务</span>
        </div>
        <div class="task-queue">
          <div v-for="task in agentTasks" :key="task.id" class="queue-item">
            <div class="task-icon">{{ task.icon }}</div>
            <div class="task-details">
              <div class="task-title">{{ task.title }}</div>
              <div class="task-meta">{{ task.status }} · {{ task.progress }}</div>
            </div>
            <div class="task-progress-bar">
              <div class="progress-fill" :style="{ width: task.percent + '%' }"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.push('/data')
}

const inputMessage = ref('')
const messages = ref([])
const isLoading = ref(false)

const quickActions = [
  { id: 1, icon: '📊', text: '生成报表' },
  { id: 2, icon: '📝', text: '撰写文档' },
  { id: 3, icon: '📅', text: '安排日程' },
  { id: 4, icon: '📧', text: '发送邮件' },
  { id: 5, icon: '🔍', text: '搜索资料' },
  { id: 6, icon: '🤔', text: '分析数据' }
]

const tools = [
  { id: 1, name: '文档处理', desc: '文档生成、摘要、翻译', icon: '📄', active: true },
  { id: 2, name: '数据查询', desc: '数据库查询、数据分析', icon: '📊', active: true },
  { id: 3, name: '日程管理', desc: '日历查询、会议安排', icon: '📅', active: false },
  { id: 4, name: '邮件发送', desc: '自动发送邮件', icon: '📧', active: true },
  { id: 5, name: '网页搜索', desc: '实时搜索获取信息', icon: '🔍', active: false },
  { id: 6, name: '代码生成', desc: '生成代码片段', icon: '💻', active: true }
]

const agentTasks = [
  { id: 1, title: '周报生成任务', status: '执行中', progress: '65%', percent: 65, icon: '📊' },
  { id: 2, title: '文档翻译', status: '等待中', progress: '0%', percent: 0, icon: '🌐' },
  { id: 3, title: '数据分析报告', status: '已完成', progress: '100%', percent: 100, icon: '📈' }
]

const sendMessage = () => {
  if (!inputMessage.value.trim()) return
  
  messages.value.push({
    id: Date.now(),
    type: 'user',
    content: inputMessage.value
  })
  
  isLoading.value = true
  inputMessage.value = ''
  
  setTimeout(() => {
    isLoading.value = false
    messages.value.push({
      id: Date.now() + 1,
      type: 'bot',
      content: '好的，我已经收到您的请求。让我分析一下...\n\n根据您的需求，我将为您提供专业的解决方案。这是一个详细的回答，包含多个要点：\n\n1. 首先分析问题的核心要点\n2. 提供可行的解决方案\n3. 给出具体的实施步骤\n\n如果您需要更详细的信息，请随时告诉我！',
      thought: '已调用文档处理工具进行分析'
    })
  }, 1500)
}

const sendAction = (action) => {
  inputMessage.value = action.text
  sendMessage()
}

const toggleTool = (tool) => {
  tool.active = !tool.active
}
</script>

<style scoped>
.ai-agent-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #0f172a 0%, #1e1b4b 100%);
}

.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 20px 30px;
  background: rgba(30, 27, 75, 0.8);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: #8b5cf6;
  border: none;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  font-size: 14px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: white;
}

.page-content {
  padding: 20px 30px;
  max-width: 1400px;
  margin: 0 auto;
}

.chat-section {
  display: grid;
  grid-template-columns: 3fr 1fr;
  gap: 20px;
  margin-bottom: 20px;
}

.chat-container {
  background: rgba(30, 41, 59, 0.8);
  border-radius: 16px;
  overflow: hidden;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: rgba(139, 92, 246, 0.2);
}

.agent-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.agent-icon {
  font-size: 28px;
}

.agent-name {
  font-size: 18px;
  font-weight: 600;
  color: white;
}

.agent-status {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #22c55e;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.status-dot.online {
  background: #22c55e;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.chat-messages {
  padding: 20px;
  height: 400px;
  overflow-y: auto;
}

.message {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.message.user {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(255,255,255,0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex-shrink: 0;
}

.message-content {
  max-width: 70%;
}

.message-content p {
  margin: 0;
  padding: 12px 16px;
  border-radius: 16px;
  line-height: 1.5;
}

.bot-message .message-content p {
  background: rgba(139, 92, 246, 0.3);
  color: white;
  border-radius: 0 16px 16px 16px;
}

.user .message-content p {
  background: #8b5cf6;
  color: white;
  border-radius: 16px 0 16px 16px;
}

.message-thought {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 8px;
  padding: 8px 12px;
  background: rgba(255,255,255,0.1);
  border-radius: 8px;
  font-size: 12px;
  color: #94a3b8;
}

.thought-icon {
  font-size: 14px;
}

.loading-dots {
  display: flex;
  gap: 4px;
  padding: 12px 16px;
}

.loading-dots span {
  width: 8px;
  height: 8px;
  background: #8b5cf6;
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out both;
}

.loading-dots span:nth-child(1) { animation-delay: -0.32s; }
.loading-dots span:nth-child(2) { animation-delay: -0.16s; }

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

.chat-input {
  display: flex;
  gap: 10px;
  padding: 20px;
  background: rgba(15, 23, 42, 0.8);
}

.chat-input input {
  flex: 1;
  padding: 14px 18px;
  background: rgba(255,255,255,0.1);
  border: 1px solid rgba(255,255,255,0.2);
  border-radius: 12px;
  color: white;
  font-size: 14px;
}

.chat-input input::placeholder {
  color: rgba(255,255,255,0.5);
}

.send-btn {
  padding: 14px 20px;
  background: #8b5cf6;
  border: none;
  border-radius: 12px;
  color: white;
  cursor: pointer;
}

.quick-actions {
  background: rgba(30, 41, 59, 0.8);
  border-radius: 16px;
  padding: 20px;
}

.quick-actions h3 {
  margin: 0 0 15px;
  font-size: 16px;
  color: white;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px;
  background: rgba(255,255,255,0.05);
  border: 1px solid rgba(255,255,255,0.1);
  border-radius: 10px;
  color: white;
  cursor: pointer;
  text-align: left;
  transition: all 0.3s;
}

.action-btn:hover {
  background: rgba(139, 92, 246, 0.2);
  border-color: rgba(139, 92, 246, 0.5);
}

.action-icon {
  font-size: 18px;
}

.action-text {
  font-size: 14px;
}

.tools-section {
  margin-bottom: 20px;
}

.tools-header h3 {
  margin: 0 0 15px;
  font-size: 18px;
  color: white;
}

.tools-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 15px;
}

.tool-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(255,255,255,0.1);
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
}

.tool-card:hover {
  border-color: rgba(139, 92, 246, 0.5);
}

.tool-card.active {
  border-color: #8b5cf6;
  background: rgba(139, 92, 246, 0.1);
}

.tool-icon {
  font-size: 28px;
}

.tool-info {
  flex: 1;
}

.tool-name {
  font-size: 14px;
  font-weight: 500;
  color: white;
  margin-bottom: 4px;
}

.tool-desc {
  font-size: 12px;
  color: #94a3b8;
}

.status-badge {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
}

.status-badge.active {
  background: rgba(34, 197, 94, 0.2);
  color: #22c55e;
}

.status-badge.inactive {
  background: rgba(148, 163, 184, 0.2);
  color: #94a3b8;
}

.tasks-section {
  background: rgba(30, 41, 59, 0.8);
  border-radius: 16px;
  padding: 20px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.section-header h3 {
  margin: 0;
  font-size: 18px;
  color: white;
}

.task-count {
  padding: 4px 12px;
  background: rgba(139, 92, 246, 0.2);
  border-radius: 20px;
  font-size: 13px;
  color: #8b5cf6;
}

.task-queue {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.queue-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  background: rgba(15, 23, 42, 0.8);
  border-radius: 10px;
}

.task-icon {
  font-size: 24px;
}

.task-details {
  flex: 1;
}

.task-title {
  font-size: 14px;
  color: white;
  margin-bottom: 4px;
}

.task-meta {
  font-size: 12px;
  color: #94a3b8;
}

.task-progress-bar {
  width: 120px;
  height: 6px;
  background: rgba(255,255,255,0.1);
  border-radius: 3px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #8b5cf6 0%, #22c55e 100%);
  border-radius: 3px;
}

@media (max-width: 900px) {
  .chat-section {
    grid-template-columns: 1fr;
  }
  
  .tools-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>